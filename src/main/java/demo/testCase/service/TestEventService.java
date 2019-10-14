package demo.testCase.service;

import demo.testCase.pojo.bo.TestEventBO;
import demo.testCase.pojo.po.TestEvent;

public interface TestEventService {

	Integer insertSelective(TestEvent po);

	TestEvent runNewTestEvent(TestEventBO bo);

	Integer endTestEvent(TestEvent po, boolean success, String remark);
	
	/**
	 * 个别异常情况下, testEvent 数据表未记录正常结束, 会导致其他 event 无法进行, 需要手动修正状态
	 * @return 
	 */
	int fixMovieClawingTestEventStatus();

}
