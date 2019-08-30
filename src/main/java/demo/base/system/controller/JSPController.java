package demo.base.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.constant.JSPUrl;
import demo.baseCommon.controller.CommonController;

@Controller
@RequestMapping(value = JSPUrl.jspRoot)
public class JSPController extends CommonController {
	
	@GetMapping(value = JSPUrl.jspNavigationNotLogin)
	public ModelAndView navigationNotLogin() {
		return new ModelAndView(BaseViewConstant.navigationNotLogin);
	}
	
	@GetMapping(value = JSPUrl.jspNavigationRoleUser)
	public ModelAndView navigationRoleUser() {
		return new ModelAndView(BaseViewConstant.navigationRoleUser);
	}
	
	@GetMapping(value = JSPUrl.jspNavigationAdmin)
	public ModelAndView navigationAdmin() {
		return new ModelAndView(BaseViewConstant.navigationAdmin);
	}
}
