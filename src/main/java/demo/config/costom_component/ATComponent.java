package demo.config.costom_component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.service.ATReportService;
import at.service.ScreenshotService;
import at.service.Tess;
import demo.selenium.service.SeleniumGlobalOptionService;

@Component
public class ATComponent {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	
	@Bean
	public ATReportService getReportService() {
		return new ATReportService();
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
		String defaultScreenshotSavingFolder = globalOptionService.getScreenshotSavingFolder();
		System.out.println(defaultScreenshotSavingFolder);
//		TODO
		/*
		 * 注册 Bean 时能使用其他 Bean 吗? 待测试
		 */
		return s;
	}
}
