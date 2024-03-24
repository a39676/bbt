package demo.config.costomComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.screenshot.service.ScreenshotService;
import at.tool.WebDriverATToolService;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.report.service.ATJsonReportService;
import autoTest.report.service.ATWordReportService;
import autoTest.tess.service.Tess;
import demo.baseCommon.pojo.constant.SystemConstant;

@Component
public class ATComponent {

//	@Autowired
//	private SeleniumGlobalOptionService globalOptionService;

	@Bean
	public ATWordReportService getReportService() {
		return new ATWordReportService();
	}

	@Bean
	public XpathBuilderBO getXpathBuilderBO() {
		return new XpathBuilderBO();
	}

	@Bean
	@Scope("singleton")
	public Tess getTess() {
		Tess t = new Tess();
		String dataPath = null;
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("linux")) {
			dataPath = SystemConstant.ROOT_PATH + "/auxiliary/tessdata";
		} else {
			dataPath = "d:/soft/tessdataInUse";
		}
		t.initITesseract(dataPath);
		return t;
	}

	@Bean
	public ScreenshotService getScreenshotService() {
		ScreenshotService s = new ScreenshotService();
		return s;
	}

	@Bean
	public WebDriverATToolService getWebATToolService() {
		return new WebDriverATToolService();
	}

	@Bean
	public ATJsonReportService getATJsonReportService() {
		return new ATJsonReportService();
	}
}
