package demo.config.costom_component;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import auxiliaryCommon.pojo.type.BaseResultType;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.service.UsersService;
import numericHandel.NumericUtilCustom;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UsersService userService;

	@Autowired
	private SystemConstantService systemConstantService;
	@Autowired
	private NumericUtilCustom numberUtil;
	
	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	private static Integer maxAttempts = 0;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		
		String error = "";
		if(!numberUtil.matchInteger(systemConstantService.getValByName(SystemConstantStore.maxAttempts))) {
			throw new BadCredentialsException(BaseResultType.serviceError.getName());
		}
		maxAttempts = Integer.parseInt(systemConstantService.getValByName(SystemConstantStore.maxAttempts));
		
		try {

			Authentication auth = super.authenticate(authentication);

			// if reach here, means login success, else an exception will be thrown
			// reset the user_attempts
			userService.resetFailAttempts(authentication.getName());

			return auth;

		} catch (DisabledException e) {

			error = "User account is unabled! <br><br>Username : " + authentication.getName() + "<br>";
			throw new DisabledException(error);

		} catch (BadCredentialsException e) {
			error = "";
			// invalid login, update to user_attempts
			int insertCount = userService.insertFailAttempts(authentication.getName());
			if(insertCount > 0) {
				int attemptCount = userService.countAttempts(authentication.getName());
				error = "账号或密码错误,还有" + (maxAttempts - attemptCount) + "次尝试机会,如忘记密码,请尝试重置密码";
			}
			throw new BadCredentialsException(error);

		} catch (LockedException e) {

			// this user is locked!
			error = "";
			UserAttemptQuerayDTO param = new UserAttemptQuerayDTO();
			param.setUserName(authentication.getName());
			ArrayList<UserAttempts> userAttempts = userService.getUserAttempts(param);
			if (userAttempts != null && userAttempts.size() > 0) {
				Date lastAttempts = userAttempts.get(0).getAttemptTime();
				for (UserAttempts ua : userAttempts) {
					if (ua.getAttemptTime().after(lastAttempts)) {
						lastAttempts = ua.getAttemptTime();
					}
				}
				error = "用户已锁定" + "\n" + authentication.getName() + "\n" + "最后一次尝试登录时间 : " + lastAttempts + "\n"
						+ "请尝试\"重置密码\"";
			} else {
				error = e.getMessage();
			}

			throw new LockedException(error);

		}

	}

}
