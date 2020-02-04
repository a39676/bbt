package demo.testing.service;

import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.service.CommonService;
import mail.service.MailToolService;
import toolPack.emailHandle.mailService.send.SendEmail;

@Service
public class TestService extends CommonService {
	
	@Autowired
	private MailToolService mailToolService;
	
	public void testSendMail() {
		
		Properties properties = mailToolService.buildSinaSmtpSslProperties();
		
		SendEmail sm = new SendEmail();
		sm.sendMail(
				constantService.getValByName(SystemConstantStore.adminMailName), 
				constantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(constantService.getValByName(SystemConstantStore.managerMail)),
				null,
				Arrays.asList(constantService.getValByName(SystemConstantStore.adminMailName)),
				"testTitle", 
				"testContent", 
				null,
				properties
				);
	}
	
}
