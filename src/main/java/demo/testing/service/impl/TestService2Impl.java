package demo.testing.service.impl;

import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testing.service.TestService2;

@Service
public class TestService2Impl extends CommonService implements TestService2 {

	@Override
	public String test2() {
		return "testing str";
	}
}
