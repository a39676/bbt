package demo.config.costom_component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.selenium.service.SeleniumGlobalOptionService;

@Component
//public class DatabaseFillerOnStartup implements ApplicationListener<ContextStartedEvent> {
public class OnStartup implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	
/*
 * ContextStartedEvent
 * ContextStoppedEvent
 * ContextRefreshedEvent
 * ContextClosedEvent
 * RequestHandleEvent
 */
	
	
	@Override
//	public void onApplicationEvent(ContextStartedEvent event) {
	public void onApplicationEvent(ApplicationReadyEvent event) {
		globalOptionService.getDownloadDir();
		globalOptionService.getScreenshotSavingFolder();
	}

}
