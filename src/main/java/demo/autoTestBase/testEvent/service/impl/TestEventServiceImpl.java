package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.mq.producer.TestEventExecuteQueueAckProducer;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.scriptCore.collecting.jandan.service.impl.ClawCollectPrefixServiceImpl;
import demo.scriptCore.demo.service.BingDemoPrefixService;
import demo.scriptCore.demo.service.impl.BingDemoPrefixServiceImpl;
import demo.scriptCore.localClawing.service.impl.LocalClawingPrefixServiceImpl;
import demo.scriptCore.scheduleClawing.service.impl.ScheduleClawingPrefixServiceImpl;
import net.sf.json.JSONObject;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private TestEventExecuteQueueAckProducer testEventAckProducer;

	@Autowired
	private BingDemoPrefixServiceImpl searchingDemoService;
	@Autowired
	private BingDemoPrefixService bingDemoPrefixService;
	@Autowired
	private ScheduleClawingPrefixServiceImpl scheduleClawingPrefixService;
	@Autowired
	private ClawCollectPrefixServiceImpl clawCollectPrefixService;
	@Autowired
	private LocalClawingPrefixServiceImpl localClawingPrefixService;

	private String pauseWordRedisKey = "testEventPauseWord";
	private String safeWord = "breakNow";

	private String findPauseWord() {
		return redisConnectService.getValByName(pauseWordRedisKey);
	}

	@Override
	public InsertTestEventResult insertExecuteTestEvent(TestEvent po) {
		return insertTestEvent(po, null);
	}

	/*
	 * TODO 废弃此添加动态参数的方法?
	 */
	@Override
	public InsertTestEventResult insertTestEvent(TestEvent po, JSONObject paramJson) {
		InsertTestEventResult r = new InsertTestEventResult();
		if (po == null || po.getId() == null || po.getFlowId() == null || po.getModuleId() == null) {
			return r;
		}
		r.setNewTestEventId(po.getId());
		r.setCode(po.getEventName());
		r.setModuleId(po.getModuleId());
		r.setCaseId(po.getFlowId());

		if (paramJson != null && StringUtils.isNotBlank(paramJson.toString())) {
			redisConnectService.setValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + po.getId(),
					paramJson.toString(), 1L, TimeUnit.DAYS);
		}
		testEventAckProducer.send(po);

		r.setIsSuccess();
		return r;
	}

	@Override
	public TestEventBO reciveTestEventAndRun(TestEvent te) {

		if (te == null) {
			return new TestEventBO();
		}

		if (existsRuningEvent()) {
			return new TestEventBO();
		}

		return runEvent(te);
	}

	private TestEventBO runEvent(TestEvent event) {
		String breakWord = findPauseWord();
		if (safeWord.equals(breakWord)) {
			return new TestEventBO();
		}

		TestEventBO runResult = null;
		if (event.getAppointment() == null || event.getAppointment().isBefore(LocalDateTime.now())) {
			startEvent(event);
			try {
				runResult = runSubEvent(event);
			} catch (Exception e) {
				runResult = new TestEventBO();
			}
			endEvent(event);
		}

		return runResult;
	}

	private TestEventBO runSubEvent(TestEvent te) {
		Long moduleId = te.getModuleId();
		if (moduleId == null) {
			return null;
		}

		if (TestModuleType.ATDemo.getId().equals(moduleId)) {
			return searchingDemoService.runSubEvent(te);
		} else if (TestModuleType.scheduleClawing.getId().equals(moduleId)) {
			return scheduleClawingPrefixService.runSubEvent(te);
		} else if (TestModuleType.collecting.getId().equals(moduleId)) {
			return clawCollectPrefixService.runSubEvent(te);
		} else if (TestModuleType.localClawing.getId().equals(moduleId)) {
			return localClawingPrefixService.runSubEvent(te);
		}
		return new TestEventBO();
	}

	@Override
	public boolean checkExistsRuningEvent() {
		return existsRuningEvent();
	}

	@Override
	public void fixRuningEventStatusManual() {
		redisConnectService.setValByName(runningEventRedisKey, "false");
	}

	@Override
	public void insertTestEvent(AutomationTestInsertEventDTO dto) {
		Long moduleId = dto.getTestModuleType();
		if (moduleId == null) {
			return;
		}

		if (TestModuleType.ATDemo.getId().equals(moduleId)) {
			bingDemoPrefixService.insertSearchInHomeEvent(dto);
//		} else if (TestModuleType.scheduleClawing.getId().equals(moduleId)) {
//			return scheduleClawingPrefixService.runSubEvent(dto);
//		} else if (TestModuleType.collecting.getId().equals(moduleId)) {
//			return clawCollectPrefixService.runSubEvent(dto);
//		} else if (TestModuleType.localClawing.getId().equals(moduleId)) {
//			return localClawingPrefixService.runSubEvent(dto);
		}
	}
}
