package demo.experiment.service.impl;

import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.service.impl.TestEventCommonService;
import demo.config.costomAnnotation.CustomAfterAnnotation;
import demo.config.costomAnnotation.CustomBeforeAnnotation;
import demo.config.costomAnnotation.LogExecutionTime;
import demo.experiment.service.TestService;

@Service
public class TestServiceImpl extends TestEventCommonService implements TestService {

	@Override
	@LogExecutionTime
	@CustomBeforeAnnotation
	@CustomAfterAnnotation
	public String testing(String arg1, String arg2) throws Exception {
		System.out.println("arg1: " + arg1 + ", arg2: " + arg2 + " in test service");
		try {
			Thread.sleep(300L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		if(StringUtils.isNotBlank(arg)) {
//			throw new Exception();
//		}
		return arg1 + "AfterHandle";
	}

	@Override
	public void sendMsg(String msg) {
		sendingMsg(msg);
	}
}
