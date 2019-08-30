package demo.base.user.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import demo.baseCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

@Component
public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authenticationException) throws IOException, ServletException {
		CommonResult result = new CommonResult();
		result.failWithMessage(authenticationException.getMessage());
		response.getWriter().println(JSONObject.fromObject(result));
	}

}
