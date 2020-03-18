package demo.testing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.po.TestEventExample;
import demo.baseCommon.service.CommonService;

@Service
public class TestService extends CommonService {
	@Autowired
	private TestEventMapper eventMapper;
	
	public void test() {
		TestEventExample example = new TestEventExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		List<TestEvent> poList = eventMapper.selectByExample(example);
		System.out.println(poList);
	}
}
