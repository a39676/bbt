//加入时间早于 2017/04/24  动机已不记得  暂时注释处理,  如果未用到, 可能删除

//package demo.web.handler;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//public class MyAccessDeniedHandlerImpl implements AccessDeniedHandler {
//
//	public MyAccessDeniedHandlerImpl() {
//
//	}
//
//	public String getAccessDeniedUrl() {
//		return accessDeniedUrl;
//	}
//
//	public void setAccessDeniedUrl(String accessDeniedUrl) {
//		this.accessDeniedUrl = accessDeniedUrl;
//	}
//
//	public MyAccessDeniedHandlerImpl(String accessDeniedUrl) {
//		this.accessDeniedUrl = accessDeniedUrl;
//	}
//
//	private String accessDeniedUrl;
//
//	@Override
//	public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException reason)
//			throws ServletException, IOException {
//		boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
//
//		// 如果是ajax请求
//		if (isAjax) {
//
//			String jsonObject = "{\"message\":\"You are not privileged to request this resource.\","
//					+ "\"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";
//			String contentType = "application/json";
//			resp.setContentType(contentType);
////			String jsonObject = "noright";
//			PrintWriter out = resp.getWriter();
//			out.print(jsonObject);
//			out.flush();
//			out.close();
//			return;
//		} else {
//
//			String path = req.getContextPath();
//			String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
//			resp.sendRedirect(basePath + accessDeniedUrl);
//		}
//
//	}
//}