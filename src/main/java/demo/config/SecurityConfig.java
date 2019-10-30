package demo.config;

import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.google.common.collect.ImmutableList;

import demo.base.admin.pojo.constant.AdminUrlConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.constant.UsersUrlConstant;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.service.impl.CustomAuthenticationFailHandler;
import demo.base.user.service.impl.CustomAuthenticationSuccessHandler;
import demo.base.user.service.impl.CustomUserDetailsService;
import demo.config.costom_component.CustomAuthenticationProvider;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.tool.pojo.constant.ToolUrlConstant;
import demo.tool.pojo.constant.UploadUrlConstant;
import demo.web.handler.LimitLoginAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SystemConstantService constantService;

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	private CustomAuthenticationFailHandler customAuthenticationFailHandler;

	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new LimitLoginAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String envName = constantService.getValByName("envName");
		http.authorizeRequests().antMatchers("/welcome**").permitAll().antMatchers(LoginUrlConstant.login + "/**")
				.permitAll().antMatchers(UsersUrlConstant.root + "/**").permitAll().antMatchers("/static_resources/**")
				.permitAll().antMatchers(AdminUrlConstant.root + "/**").access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN))
				.antMatchers(ToolUrlConstant.root + "/**").access(hasRole(RolesType.ROLE_SUPER_ADMIN))
				.antMatchers(UploadUrlConstant.uploadPriRoot + "/**")
				.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_DEV)).and().formLogin()
				.loginPage("/login/login").failureUrl("/login/login?error").loginProcessingUrl("/auth/login_check")
				.successHandler(customAuthenticationSuccessHandler).failureHandler(customAuthenticationFailHandler)
				.usernameParameter("user_name").passwordParameter("pwd").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).and().exceptionHandling()
				.accessDeniedPage("/403").and().rememberMe().tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(3600).and().authorizeRequests()
//		    尝试搭建 web socket, 修改同源策略
				.and().headers().frameOptions().sameOrigin().and()
		    	.csrf()
		    	.disable()
				.cors();
		if (!"dev".equals(envName)) {
			http.authorizeRequests().antMatchers("/test/**").access(hasRole(RolesType.ROLE_SUPER_ADMIN));
		}

		/*
		 * 增加filter在此 同样操作 但建议使用 http.addFilterAfter(newFilter,CsrfFilter.class);
		 */
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(StandardCharsets.UTF_8.displayName());
		encodingFilter.setForceEncoding(true);
		http.addFilterBefore(encodingFilter, CsrfFilter.class);

	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(ImmutableList.of("*"));
		configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must
		// not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/test/testIgnoring");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

	private String hasRole(RolesType roleType) {
		if (roleType == null) {
			return "hasRole('')";
		}

		return "hasRole('" + roleType.getName() + "')";
	}

	private String hasAnyRole(RolesType... roleTypes) {
		if (roleTypes == null) {
			return "hasRole('')";
		}

		StringBuffer roleExpressionBuilder = new StringBuffer("hasAnyRole(");

		for (RolesType roleType : roleTypes) {
			if (roleType != null) {
				roleExpressionBuilder.append("'" + roleType.getName() + "',");
			}
		}
		roleExpressionBuilder.replace(roleExpressionBuilder.length() - 1, roleExpressionBuilder.length(), "");
		roleExpressionBuilder.append(")");
		return roleExpressionBuilder.toString();
	}
}
