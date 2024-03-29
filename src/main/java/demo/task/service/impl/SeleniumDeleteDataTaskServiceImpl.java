package demo.task.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.selenium.service.OldDataDeleteService;

@Component
public class SeleniumDeleteDataTaskServiceImpl {

	@Autowired
	private OldDataDeleteService oldDataDeleteService;

	@Scheduled(cron = "04 13 01 * * *")
	public void deleteOldDownload() throws IOException {
		oldDataDeleteService.deleteOldDownload();
	}
	
	@Scheduled(cron = "05 15 01 * * *")
	public void deleteOldScreenshot() throws IOException {
		oldDataDeleteService.deleteOldScreenshot();
		oldDataDeleteService.deleteOldCaptchaImg();
	}
	
}
