package demo.base.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseUrl;

/**
 * @author Acorn
 * 2017年4月15日
 * error pages
 * -----------------
 * 其他异常页面已经转移到 ExceptionController
 */
@Controller
public class ErrorController extends ExceptionController {
	
	@GetMapping(value = BaseUrl.error403)
	public ModelAndView accesssDenied(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		
		view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		view.addObject("urlRedirect", foundHostNameFromRequst(request));
		return view;
	}
	
//	@RequestMapping(value = UrlConstant.error)
//	public ModelAndView error(HttpServletRequest request) {
//		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
//		
//		view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
//		view.addObject("urlRedirect", foundHostNameFromRequst(request));
//		return view;
//	}
	
}
