package demo.config.costom_component;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

//		TODO
//		增加自定义登陆逻辑
//		if (shouldAuthenticateAgainstThirdPartySystem()) {
		if ("".equals("")) {
			// use the credentials
			// and authenticate against the third-party system
			return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}