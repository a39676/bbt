package demo.scriptCore.neobux.service.impl;

import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.scriptCore.neobux.service.NeobuxOptionService;

@Service
public class NeobuxOptionServiceImpl extends CommonService implements NeobuxOptionService {

	private String neobuxUsername = "neobuxUsername";
	private String neobuxPwd = "neobuxPwd";
	
	@Override
	public String getNeobuxUsername() {
		return redisConnectService.getValByName(neobuxUsername);
	}
	
	@Override
	public String getNeobuxPwd() {
		return redisConnectService.getValByName(neobuxPwd);
	}
}
