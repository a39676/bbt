package demo.clawing.scheduleClawing.service;

import at.report.pojo.dto.JsonReportDTO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.clawing.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;

public interface CryptoCompareService {

	/**
	 * 
	 * 每次调用, 仅获取一个币种法币对的数据
	 * @param te
	 * @param reportDTO
	 * @return
	 */
	CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEvent te, JsonReportDTO reportDTO);

}
