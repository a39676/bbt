package demo.testing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.baseCommon.service.CommonService;

@Service
public class TestService extends CommonService {

	@SuppressWarnings("unused")
	@Autowired
	private TestEventMapper eventMapper;

}
