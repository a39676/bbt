package demo.base.user.service.impl;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.po.UsersDetail;
import demo.baseCommon.pojo.result.CommonResult;
import demo.util.BaseUtilCustom;
import net.sf.json.JSONObject;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private BaseUtilCustom baseUtilCustom;

	@Autowired
	private UsersMapper usersMapper;
	
//	@Autowired
//	private UserIpMapper userIpMapper;
	
	protected Log logger = LogFactory.getLog(this.getClass());
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		HashMap<String, Object> detailMap = new HashMap<String, Object>();
		UsersDetail userDetail = usersMapper.getUserDetailByUserName(auth.getName());
		detailMap.put("userId", userDetail.getUserId());
		detailMap.put("email", userDetail.getEmail());
		detailMap.put("nickName", userDetail.getNickName());
		baseUtilCustom.setAuthDetail(detailMap);
		
//		String remoteAddr = null;
//		if (request != null) {
//            remoteAddr = request.getHeader("X-FORWARDED-FOR");
//            if (remoteAddr == null || "".equals(remoteAddr)) {
//                remoteAddr = request.getRemoteAddr();
//            }
//        }
//		UserIp ui = new UserIp();
//		ui.setIp(NumericUtilCustom.ipToLong(remoteAddr));
//		ui.setUri(request.getRequestURI());
//		ui.setUserId(userId);
//		userIpMapper.insertSelective(ui);
		
		handle(request, response, auth);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

//		redirectStrategy.sendRedirect(request, response, targetUrl);
		CommonResult result = new CommonResult();
		result.successWithMessage("/");
		response.getWriter().println(JSONObject.fromObject(result));
		return;
	}

	protected String determineTargetUrl(Authentication authentication) {
		// 资料留存
		// 依据实际情况,全部跳转首页.
//		boolean isUser = false;
//		boolean isAdmin = false;
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		for (GrantedAuthority grantedAuthority : authorities) {
//			if (grantedAuthority.getAuthority().contains("ROLE_USER")) {
//				isUser = true;
//				break;
//			} else if (grantedAuthority.getAuthority().contains("ROLE_ADMIN")) {
//				isAdmin = true;
//				break;
//			}
//		}
//
//		if (isUser) {
//			return "/homepage.html";
//		} else if (isAdmin) {
//			return "/console.html";
//		} else {
//			throw new IllegalStateException();
//		}
		return "/";
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}
