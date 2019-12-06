package demo.clawing.neobux.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.clawing.neobux.service.NeobuxOptionService;

@Service
public class NeobuxOptionServiceImpl extends CommonService implements NeobuxOptionService {

	@Autowired
	private SystemConstantService constantService;
	
	private String neobuxUsername = "neobuxUsername";
	private String neobuxPwd = "neobuxPwd";
	
	@Override
	public String getNeobuxUsername() {
		return constantService.getValByName(neobuxUsername);
	}
	
	@Override
	public String getNeobuxPwd() {
		return constantService.getValByName(neobuxPwd);
	}
}
