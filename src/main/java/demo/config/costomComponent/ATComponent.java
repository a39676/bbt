package demo.config.costomComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.screenshot.service.ScreenshotService;
import at.tool.WebATToolService;
import autoTest.report.service.ATJsonReportService;
import autoTest.report.service.ATWordReportService;
import autoTest.tess.service.Tess;

@Component
public class ATComponent {

//	@Autowired
//	private SeleniumGlobalOptionService globalOptionService;
	
	@Bean
	public ATWordReportService getReportService() {
		return new ATWordReportService();
	}
	
	@Bean
	@Scope("singleton")
	public Tess getTess() {
		Tess t = new Tess();
		String dataPath = null;
		String osName = System.getProperty("os.name");
		if(osName != null && osName.toLowerCase().contains("linux")) {
			dataPath = "/home/u2/auxiliary/tessdata";
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
	public WebATToolService getWebATToolService() {
		return new WebATToolService();
	}
	
	@Bean
	public ATJsonReportService getATJsonReportService() {
		return new ATJsonReportService();
	}
}
