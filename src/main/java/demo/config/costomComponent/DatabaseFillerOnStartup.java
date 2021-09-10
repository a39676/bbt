package demo.config.costomComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.autoTestBase.testEvent.service.impl.AutomationTestConstantService;
import demo.baseCommon.service.CommonService;

@Component
public class DatabaseFillerOnStartup extends CommonService implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private AutomationTestConstantService automationTestConstantService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		log.error("starting database filler");

		if (event.getApplicationContext().getParent() == null) {

			log.error("loading automation test option");
			automationTestConstantService.refreshConstant();
			
		}
		
		log.error("data base filler end");
	}

}
