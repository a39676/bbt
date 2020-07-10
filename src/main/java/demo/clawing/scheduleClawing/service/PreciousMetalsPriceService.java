package demo.clawing.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface PreciousMetalsPriceService {

	InsertTestEventResult insertClawingEvent();

	/**
	 * 2020-07-10
	 * 改用 goldPriceOrgAPI 查询
	 * 暂时保留页面查询功能代码
	 * @param te
	 * @return
	 */
	CommonResultBBT clawing(TestEvent te);

	CommonResultBBT goldPriceOrgAPI(TestEvent te);

}
