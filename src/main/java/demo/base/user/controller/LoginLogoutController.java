package demo.base.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.baseCommon.controller.CommonController;
import net.sf.json.JSONObject;



/**
 * @author Acorn
 * 2017年4月15日
 * login and logout
 */
@Controller
@RequestMapping(value = LoginUrlConstant.login)
public class LoginLogoutController extends CommonController {
	
	@GetMapping(value = LoginUrlConstant.login)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		HttpServletRequest request,
		HttpServletResponse response) {

		insertVisitIp(request);
		ModelAndView view = new ModelAndView();
		CommonResult result = new CommonResult();
		if (error != null) {
			view.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
			
			/*
			 * 2018-05-22
			 * 登录跳转, 转移到 CustomAuthenticationSuccessHandler.determineTargetUrl()
			 * 此处暂时搁置 
			 * 
			 */
			//login form for update page
            //if login error, get the targetUrl from session again.
//			String targetUrl = getRememberMeTargetUrlFromSession(request);
//			if(StringUtils.hasText(targetUrl)){
//				view.addObject("targetUrl", targetUrl);
//				view.addObject("loginUpdate", true);
//			}
			result.failWithMessage(error);
			outputJson(response, JSONObject.fromObject(result));
		}

		if (logout != null) {
			view.setViewName("redirect:" + BaseUrl.baseRoot);
			return view;
		}
		view.setViewName(BaseViewConstant.loginCustomV3);

		return view;

	}
	
	@GetMapping(value = LoginUrlConstant.logout)
	public void logout (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    CommonResult result = new CommonResult();
	    result.setIsSuccess();
	    outputJson(response, JSONObject.fromObject(result));
	}

	//customize the error message
	private String getErrorMessage(HttpServletRequest request, String key){

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "用户名或密码有误";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "用户名或密码有误";
		}

		return error;
	}
	
}
