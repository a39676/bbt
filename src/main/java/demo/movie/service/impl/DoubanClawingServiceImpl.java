package demo.movie.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.movie.service.DoubanClawingService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;

@Service
public final class DoubanClawingServiceImpl extends MovieClawingCommonService implements DoubanClawingService {

//	@Autowired
//	private FileUtilCustom iou;
//	
//	@Autowired
//	private SystemConstantService constantService;
//	@Autowired
//	private SeleniumGlobalOptionService globalOptionService;
	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private MovieClawingOptionService optionService;
//	@Autowired
//	private JavaScriptServiceImpl jsUtil;
//
//	@Autowired
//	private MovieRecordMapper recordMapper;
//	@Autowired
//	private MovieInfoMapper infoMapper;
//	@Autowired
//	private MovieIntroductionMapper introduectionMapper;
//	
	private String mainUrl = "https://www.douban.com/";
//
	private TestEvent buildTestEvent() {
		/*
		 * TODO
		 */
		TestEvent te = new TestEvent();
		te.setCaseId(6L);
		te.setId(snowFlake.getNextId());
		te.setEventName("douban");
		return te;
	}
	

	public String clawing() {
		if(existsRuningEvent()) {
			return "existsRuningEvent";
		}
		boolean exceptionFlag = false;
		TestEvent te = buildTestEvent();
		startEvent(te);
		
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		try {
			d.get(mainUrl);

			String mainWindowHandler = d.getWindowHandle();
			// TODO
			System.out.println(mainWindowHandler);
			
		} catch (Exception e) {
			exceptionFlag = true;
			log.error("error:{}, url: {}" + e.getMessage() + d.getCurrentUrl());
			endEventFail(te);
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
		return "exceptionFlag: " + exceptionFlag;
	}
	

}
