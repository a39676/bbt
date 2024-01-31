package demo.task.service.impl;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.baseCommon.service.CommonService;
import demo.scriptCore.scheduleClawing.cnStockMarketData.service.CnStockMarketDataService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;
import demo.task.service.impl.mq.producer.TestEventInsertAckProducer;

@Service
public class AutomationTaskServiceImpl extends CommonService {

	@Autowired
	private TestEventInsertAckProducer testEventInsertAckProducer;

	@Scheduled(cron = "20 19 09 * * *")
	@Scheduled(cron = "20 19 12 * * *")
	@Scheduled(cron = "20 19 17 * * *")
	@Scheduled(cron = "20 19 20 * * *")
	@Scheduled(cron = "20 19 23 * * *")
	public void sendNormalDataTask() {
		sendEducationInfomationCollectionTask();
		sendV2exJobInfomationCollectionTask();
	}

	private void sendEducationInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.EDUCATION_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}

	private void sendV2exJobInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.V2EX_JOB_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;

	@Scheduled(cron = "40 05 22 * * *")
	@Scheduled(cron = "40 05 10 * * *")
	public void sendDataQuery() {
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
		AutomationTestInsertEventDTO dto = null;
		if (hour >= 0 && hour <= 3) {
			dto = currencyExchangeRateService.sendDailyDataQuery();
		} else {
			dto = currencyExchangeRateService.sendDataQuery(false);
		}
		testEventInsertAckProducer.send(dto);
	}
	
	@Autowired
	private CnStockMarketDataService cnStockMarketDataService;

	@Scheduled(cron = "* */5 9-15 * 1-5 *")
	public void collectDatasOf5MinAndSend() {
		cnStockMarketDataService.collectDatasOf5MinAndSend();
	}
	
}
