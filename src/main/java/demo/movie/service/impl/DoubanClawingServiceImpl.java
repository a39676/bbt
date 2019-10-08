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
	@Override
	protected TestEvent buildTesetEvent() {
		/*
		 * TODO
		 */
		TestEvent te = new TestEvent();
		te.setCaseId(6L);
		te.setId(snowFlake.getNextId());
		te.setEventName("douban");
		return te;
	}
	

	public void clawing() {
		if(existsRuningEvent()) {
			return;
		}
		TestEvent te = buildTesetEvent();
		startEvent(te);
		
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		try {
			d.get(mainUrl);

			String mainWindowHandler = d.getWindowHandle();
			// TODO
			System.out.println(mainWindowHandler);
			
		} catch (Exception e) {
			log.error("error:{}, url: {}" + e.getMessage() + d.getCurrentUrl());
			endEvent(te, false);
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}
	

}
