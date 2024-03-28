package demo.finance.common.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import finance.common.tool.KLineToolUnit;

public abstract class FinanceCommonService extends CommonService {

	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected KLineToolUnit kLineToolUnit;

	/**
	 * example: time = 14:03:05, minuteStepLong = 5, return 14:05:00 time =
	 * 14:13:00, minuteStepLong = 12, return 14:24:00
	 * 
	 * 仅限小范围使用, minuteStepLong 必须要能整除60
	 * 
	 * @param time
	 * @param minuteStepLong
	 * @return
	 */
	protected LocalDateTime nextStepStartTimeByMinute(LocalDateTime time, long minuteStepLong) {
		int currentMinute = time.getMinute();
		if (currentMinute % minuteStepLong == 0) {
			return time.plusMinutes(minuteStepLong).withSecond(0).withNano(0);
		}
		int addMinute = 1;
		while ((currentMinute + addMinute) % minuteStepLong != 0) {
			addMinute += 1;
		}
		return time.plusMinutes(addMinute).withSecond(0).withNano(0);
	}

}
