//package demo.config.core;
//
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//import demo.config.RootConfig;
//
//public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    
//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		return new Class[] { RootConfig.class };
//	}
//
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		/*
//		duplicate servletConfig when use AbstractSecurityWebApplicationInitializer
//		return new Class[] { SpringMvcConfig.class };
//		*/
//		return null;
//	}
//
//	@Override
//	protected String[] getServletMappings() {
//		return new String[] { "/" };
//	}
//	
//	@Override
//    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
//        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
//        return dispatcherServlet;
//    }
//
////	添加spring security 时 , filter 转至 securityConfig 设置
////	@Override
////    protected Filter[] getServletFilters() {
////		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
////		characterEncodingFilter.setEncoding("UTF-8");
////		characterEncodingFilter.setForceEncoding(true);
////		return new Filter[] { characterEncodingFilter };
////    }
//	
//	
//}
