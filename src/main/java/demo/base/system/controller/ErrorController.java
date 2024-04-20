package demo.base.system.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseUrl;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends ExceptionController {
	
	@GetMapping(value = BaseUrl.error403)
	public ModelAndView accesssDenied(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		
		view.addObject("message", "accesssDenied");
		view.addObject("urlRedirect", "/");
		return view;
	}
	
}
