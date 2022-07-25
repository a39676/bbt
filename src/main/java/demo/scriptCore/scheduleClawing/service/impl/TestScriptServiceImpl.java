package demo.scriptCore.scheduleClawing.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import demo.scriptCore.common.service.AutomationTestCommonService;

@Service
public class TestScriptServiceImpl extends AutomationTestCommonService {

	public void test() {

		WebDriver d = null;

		try {

			d = webDriverService.buildChromeWebDriver();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			tryQuitWebDriver(d);
		}

	}

}
