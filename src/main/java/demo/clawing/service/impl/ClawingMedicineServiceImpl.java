package demo.clawing.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.clawing.service.ClawingMedicineService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class ClawingMedicineServiceImpl extends CommonService implements ClawingMedicineService {
	
	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;
	
	public void sinaMedicineClawing() {
		
	}

	@Override
	public void medicineTest() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("clawingMedicineTest");
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		try {
			String url = "https://med.sina.com/drug/leeDetail_A_15036.html";
			d.get(url);
			
			
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "class", "xx2");
			By medicineDetailBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement medicineDetail = d.findElement(medicineDetailBy);
			System.out.println(medicineDetail.getText());
			
			auxTool.takeScreenshot(d, te);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
				d.quit();
			}
		}
	}
}
