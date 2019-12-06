package demo.badJoke.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.badJoke.sms.pojo.type.BadJokeCaseType;
import demo.badJoke.sms.service.BadJokeCasePrefixService;
import demo.badJoke.sms.service.BadJokeSMSService;
import demo.baseCommon.pojo.result.CommonResultBBT;

@Service
public class BadJokeCasePrefixServiceImpl implements BadJokeCasePrefixService {

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
