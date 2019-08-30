package demo.base.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.service.HomePageService;
import demo.baseCommon.controller.CommonController;

@Controller
public class HomePageController extends CommonController {
	
//	@Autowired
//	private BaseUtilCustom baseUtilCustom;
//	@Autowired
//	private AdminService adminService;
//	@Autowired
//	private ArticleController articleController;
//	@Autowired
//	private SystemConstantService systemConstantService;
	@Autowired
	private HomePageService baseService;
	
	@GetMapping(value = { BaseUrl.baseRoot })
	public ModelAndView welcomePageV2(@RequestParam(value = "vcode", defaultValue = "") String vcode,
			HttpServletRequest request) {
		if(StringUtils.isBlank(vcode)) {
			insertVisitIp(request);
		} else {
			insertVisitIp(request, "?vcode=" + vcode);
		}
		
		String hostName = foundHostNameFromRequst(request);
		ModelAndView view = baseService.baseRootHandlerV3(vcode, hostName);
		return view;
		
	}

}