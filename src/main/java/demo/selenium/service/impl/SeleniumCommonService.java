package demo.selenium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import at.service.ATJsonReportService;
import at.service.ScreenshotService;
import demo.baseCommon.service.CommonService;
import demo.image.ImageInteractionService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestModuleType;
import demo.testCase.service.TestEventService;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

public abstract class SeleniumCommonService extends CommonService {

	@Autowired
	protected ATJsonReportService jsonReporter;
	@Autowired
	protected ImageInteractionService imageInteractionService;
	@Autowired
	protected TestEventService testEventService;
	@Autowired
	protected WebDriverService webDriverService;
	@Autowired
	protected ScreenshotService screenshotService;
	@Autowired
	protected JavaScriptServiceImpl jsUtil;
	@Autowired
	protected SeleniumGlobalOptionService globalOptionService;
	
	protected TestEvent buildTestEvent(TestModuleType t, Long caseId) {
		if(t == null || caseId == null) {
			return null;
		}
		TestEvent te = new TestEvent();
		te.setCaseId(caseId);
		te.setModuleId(t.getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(t.getEventName());
		return te;
	}
	
	protected UploadImageToCloudinaryResult uploadImgToCloudinary(String imgFilePath) {
		UploadImageToCloudinaryDTO uploadImgDTO = new UploadImageToCloudinaryDTO();
		uploadImgDTO.setFilePath(imgFilePath);
		UploadImageToCloudinaryResult uploadImgResult = imageInteractionService.uploadImageToCloudinary(uploadImgDTO);
		return uploadImgResult;
	}
	
	protected int updateTestEventReportPath(TestEvent te, String reportPath) {
		return testEventService.updateTestEventReportPath(te, reportPath);
	}
}
