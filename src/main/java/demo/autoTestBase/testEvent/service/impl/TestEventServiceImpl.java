package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.mq.TestEventAckProducer;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.po.TestEventExample;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.clawing.collecting.jandan.service.impl.ClawCollectPrefixServiceImpl;
import demo.clawing.demo.service.impl.SearchingDemoPrefixServiceImpl;
import demo.clawing.localClawing.service.impl.LocalClawingPrefixServiceImpl;
import demo.clawing.scheduleClawing.service.impl.ScheduleClawingPrefixServiceImpl;
import net.sf.json.JSONObject;
import selenium.pojo.constant.SeleniumConstant;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private TestEventAckProducer testEventAckProducer;

	@Autowired
	private SearchingDemoPrefixServiceImpl searchingDemoService;
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
	public InsertTestEventResult insertTestEvent(TestEvent po) {
		return insertTestEvent(po, null);
	}

	@Override
	public InsertTestEventResult insertTestEvent(TestEvent po, JSONObject paramJson) {
		InsertTestEventResult r = new InsertTestEventResult();
		if (po == null || po.getId() == null || po.getCaseId() == null || po.getModuleId() == null) {
			return r;
		}
		r.setNewTestEventId(po.getId());
		r.setCode(po.getEventName());
		r.setModuleId(po.getModuleId());
		r.setCaseId(po.getCaseId());

		if (paramJson != null && StringUtils.isNotBlank(paramJson.toString())) {
			redisConnectService.setValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + po.getId(),
					paramJson.toString(), 1L, TimeUnit.DAYS);
		}
		testEventAckProducer.send(po);

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult reciveTestEventAndRun(TestEvent te) {

		if (te == null) {
			return new CommonResult();
		}

		if (existsRuningEvent()) {
			return new CommonResult();
		}

		return runEvent(te);
	}

	private CommonResult runEvent(TestEvent event) {
		String breakWord = findPauseWord();
		if (safeWord.equals(breakWord)) {
			return new CommonResult();
		}

		CommonResult runResult = null;
		if (event.getAppointment() == null || event.getAppointment().isBefore(LocalDateTime.now())) {
			startEvent(event);
			try {
				runResult = runSubEvent(event);
			} catch (Exception e) {
				runResult = new CommonResult();
			}
			endEvent(event, runResult.isSuccess(), runResult.getMessage());
		}

		return runResult;
	}

	private CommonResult runSubEvent(TestEvent te) {
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
		return new CommonResult();
	}

	@Override
	public int countWaitingEvent() {
		return eventMapper.countWaitingEvent();
	}

	@Override
	public int updateTestEventReportPath(TestEvent te, String reportPath) {
		if (te.getId() == null) {
			return 0;
		}
		te.setReportPath(reportPath);
		return eventMapper.updateByPrimaryKeySelective(te);
	}

	/*
	 * TODO 移除mail 模块后, 转移到 telegram 接口
	 */
//	public CommonResult sendFailReports() {
//		LocalDateTime now = LocalDateTime.now();
//		return sendFailReports(now.minusDays(2L), now);
//	}
//	public CommonResult sendFailReports(LocalDateTime startTime, LocalDateTime endTime) {
//		CommonResult r = new CommonResult();
//		TestEventExample example = new TestEventExample();
//		example.createCriteria().andIsDeleteEqualTo(false).andIsPassEqualTo(false).andEndTimeIsNotNull()
//				.andStartTimeBetween(startTime, endTime);
//		List<TestEvent> failEventList = eventMapper.selectByExample(example);
//		r.addMessage("failEventListSize : " + failEventList.size() + "\n");
//
//		LocalDateTime now = LocalDateTime.now();
//		String nowStr = localDateTimeHandler.dateToStr(now);
//		CommonResult mailResult = mailService.sendSimpleMail(0L,
//				redisConnectService.getValByName(SystemConstantStore.managerMail),
//				("截至: " + nowStr + " 最近2天有" + failEventList.size() + "个失败任务"),
//				(buildSendFailReportContent(failEventList)), null, MailType.sandFailTaskReport);
//		if (mailResult.isSuccess()) {
//			r.addMessage("send mail success" + "\n");
//		} else {
//			r.addMessage("send mail fali" + "\n");
//			r.addMessage(mailResult.getMessage());
//		}
//
//		return r;
//	}
//	private String buildSendFailReportContent(List<TestEvent> failEventList) {
//		Map<String, Integer> sumMap = new HashMap<String, Integer>();
//		for (TestEvent po : failEventList) {
//			if (sumMap.containsKey(po.getEventName())) {
//				sumMap.put(po.getEventName(), sumMap.get(po.getEventName()) + 1);
//			} else {
//				sumMap.put(po.getEventName(), 1);
//			}
//		}
//
//		LocalDateTime now = LocalDateTime.now();
//		String nowStr = localDateTimeHandler.dateToStr(now);
//		StringBuffer sb = new StringBuffer("截至: " + nowStr + " 最近2天有" + failEventList.size() + "个失败任务 \n");
//
//		for (Entry<String, Integer> entry : sumMap.entrySet()) {
//			sb.append(entry.getKey() + " : " + entry.getValue() + " 个 \n");
//		}
//		return sb.toString();
//	}

	@Override
	public boolean checkExistsRuningEvent() {
		return existsRuningEvent();
	}

	@Override
	public void fixRuningEventStatusManual() {
		redisConnectService.setValByName(runningEventRedisKey, "false");
	}

	@Override
	public void deleteOldTestEvent() {
		LocalDateTime deleteLine = LocalDateTime.now().plusMonths(SeleniumConstant.maxHistoryMonth);
		TestEventExample example = new TestEventExample();
		example.createCriteria().andIsDeleteEqualTo(false).andStartTimeGreaterThan(deleteLine);
		eventMapper.deleteByExample(example);
	}
}
