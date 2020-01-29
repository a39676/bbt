package demo.clawing.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.lottery.pojo.type.ClawTaskType;
import demo.clawing.lottery.service.LotteryPrefixService;
import demo.clawing.lottery.service.LotterySixService;

@Service
public class LotteryPrefixServiceImpl extends CommonService implements LotteryPrefixService {

	@Autowired
	private LotterySixService lotterySixService;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (ClawTaskType.lotterySix.getId().equals(caseId)) {
			return lotterySixService.clawTask(te);
		}
		return new CommonResultBBT();
	}
}
