package demo.dfsw.oa.newSoft.web.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.dfsw.oa.newSoft.web.service.MainTestService;
import demo.selenium.service.JavaScriptService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class MainTestServiceImpl extends CommonService implements MainTestService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private JavaScriptService jsUtil;
	
	private String mainUrl = "http://localhost:8080/NSOA7-GROUP";
	
	public void test() {
		auxTool.toString();
		jsUtil.toString();
		
		WebDriver d = webDriverService.buildIEWebDriver();
		
		try {
			d.get(mainUrl);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
