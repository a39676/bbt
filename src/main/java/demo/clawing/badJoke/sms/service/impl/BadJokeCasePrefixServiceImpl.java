package demo.clawing.badJoke.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.badJoke.sms.pojo.type.BadJokeCaseType;
import demo.clawing.badJoke.sms.service.BadJokeSMSService;

@Service
public class BadJokeCasePrefixServiceImpl implements RunSubEventPrefixService {

	@Autowired
	private BadJokeSMSService badJokeSMSService;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		if(caseId == null) {
			return null;
		}
		
		if (BadJokeCaseType.badJokeSms.getId().equals(caseId)) {
			/*
			 * TODO
			 */
			badJokeSMSService.toString();
//			return badJokeSMSService.
			
		} 
		return null;
	}
	
}
