package demo.scriptCore.scheduleClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.service.EducationInfoCollectionService;
import demo.scriptCore.scheduleClawing.service.HeShaBiCaoService;
import demo.scriptCore.scheduleClawing.service.ScheduleClawingPrefixService;
import demo.scriptCore.scheduleClawing.service.UnderWayMonthTestService;
import demo.scriptCore.scheduleClawing.service.WuYiJobRefreshService;

@Service
public class ScheduleClawingPrefixServiceImpl extends AutomationTestCommonService
		implements ScheduleClawingPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	@Autowired
	private EducationInfoCollectionService educationInfoCollectionService;
	@Autowired
	private HeShaBiCaoService heShaBiCaoService;
	@Autowired
	private UnderWayMonthTestService underWayMonthTestService;

	@Override
	public TestEventBO runSubEvent(TestEventBO te) {
		Long caseId = te.getFlowId();

		if (ScheduleClawingType.WU_YI_JOB.getId().equals(caseId)) {
			return wuYiSign.clawing(te);
		} else if (ScheduleClawingType.EDUCATION_INFO.getId().equals(caseId)) {
			return educationInfoCollectionService.clawing(te);
		} else if (ScheduleClawingType.HE_SHA_BI_CAO_WECHAT_PREREGIST.getId().equals(caseId)) {
			return heShaBiCaoService.weixinPreReg(te);
		} else if (ScheduleClawingType.UNDER_WAY_MONTH_TEST.getId().equals(caseId)) {
			return underWayMonthTestService.monthTest(te);
		}

		return new TestEventBO();
	}

	@Override
	public TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto) {
		TestEventBO bo = buildTestEventBOPreHandle(dto);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		ScheduleClawingType caseType = ScheduleClawingType.getType(dto.getFlowType());
		bo.setFlowName(caseType.getFlowName());
		bo.setFlowId(caseType.getId());
		bo.setEventId(dto.getTestEventId());
		bo.setAppointment(dto.getAppointment());
		bo.setParamStr(dto.getParamStr());

		return bo;
	}
}
