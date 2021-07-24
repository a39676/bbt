package demo.testing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.baseCommon.service.CommonService;
import demo.testing.config.CustomAfterAnnotation;
import demo.testing.config.CustomBeforeAnnotation;
import demo.testing.config.LogExecutionTime;
import demo.testing.service.TestService;

@Service
public class TestServiceImpl extends CommonService implements TestService {

	@SuppressWarnings("unused")
	@Autowired
	private TestEventMapper eventMapper;
	
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
