package demo.testing.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.po.TestEventExample;
import demo.baseCommon.service.CommonService;

@SuppressWarnings("unused")
@Service
public class TestService extends CommonService {
	
	@Autowired
	private TestEventMapper eventMapper;
	
	
}
