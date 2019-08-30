package demo.base.system.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.service.HomePageService;
import demo.base.user.pojo.type.RolesType;
import demo.baseCommon.service.CommonService;
import demo.util.BaseUtilCustom;

@Service
public class HomePageServiceImpl extends CommonService implements HomePageService {

	@Autowired
	private BaseUtilCustom baseUtilCustom;

	@Autowired
	private SystemConstantService systemConstantService;

	
	@Override
	public ModelAndView baseRootHandlerV3(@RequestParam(value = "vcode", defaultValue = "") String vcode,
			String hostName) {

		ModelAndView view = new ModelAndView();
//		TODO
//		考虑随域名变更起始页面
		view.setViewName(BaseViewConstant.homeV3);

		view.addObject("title", systemConstantService.getValByName(SystemConstantStore.webSiteTitle));

		if(!"dev".equals(envName)) {
			if (StringUtils.isBlank(hostName)) {
				view.setViewName(BaseViewConstant.empty);
				return view;
			}
		}

		if (hostName.contains(systemConstantService.getValByName(SystemConstantStore.hostName2))) {
			if(!"1".contentEquals(systemConstantService.getValByName(SystemConstantStore.jobing))) {
				view.setViewName(BaseViewConstant.empty);
				return view;
			}
		}


		List<String> roles = baseUtilCustom.getRoles();
		if (roles != null && roles.size() > 0 && roles.contains(RolesType.ROLE_USER.getName())) {
			HashMap<String, Object> authDetailMap = baseUtilCustom.getAuthDetail();
			if (authDetailMap != null && authDetailMap.containsKey("nickName")) {
				view.addObject("nickName", authDetailMap.get("nickName"));
			}
		} 

		return view;
	}

}
