package demo.movie.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movie.service.DoubanClawingService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.MovieTestCaseType;

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

	private TestEvent buildTestEvent() {
		return buildTestEvent(MovieTestCaseType.doubanMovieInfoClaw);
	}

	public CommonResultBBT clawing() {
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();
		
		TestEvent te = buildTestEvent();
		
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		try {
			d.get(mainUrl);

			String mainWindowHandler = d.getWindowHandle();
			// TODO
			System.out.println(mainWindowHandler);
			
		} catch (Exception e) {
			log.error("error:{}, url: {}" + e.getMessage() + d.getCurrentUrl());
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
			if (d != null) {
				d.quit();
			}
		}
		
		return r;
	}
	

}
