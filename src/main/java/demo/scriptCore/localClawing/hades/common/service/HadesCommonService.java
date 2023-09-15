package demo.scriptCore.localClawing.hades.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.selenium.service.impl.AutomationTestCommonService;

public abstract class HadesCommonService extends AutomationTestCommonService {

	@Autowired
	protected HadesOptionFile hadesOptionFile;
	
	
}
