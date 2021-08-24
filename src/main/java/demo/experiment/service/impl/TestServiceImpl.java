package demo.experiment.service.impl;

import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.config.costomAnnotation.CustomAfterAnnotation;
import demo.config.costomAnnotation.CustomBeforeAnnotation;
import demo.config.costomAnnotation.LogExecutionTime;
import demo.experiment.service.TestService;

@Service
public class TestServiceImpl extends CommonService implements TestService {

	
	@Override
	@LogExecutionTime
	@CustomBeforeAnnotation
	@CustomAfterAnnotation
	public String testing(String arg) throws Exception {
		System.out.println(arg + " in test service");
		try {
			Thread.sleep(300L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		if(StringUtils.isNotBlank(arg)) {
//			throw new Exception();
//		}
		return arg + "AfterHandle";
	}

}
