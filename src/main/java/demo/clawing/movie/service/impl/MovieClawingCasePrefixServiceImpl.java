package demo.clawing.movie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.movie.pojo.type.MovieClawingCaseType;
import demo.clawing.movie.service.DyttClawingService;
import demo.clawing.movie.service.HomeFeiClawingService;
import demo.clawing.movie.service.MovieClawingCasePrefixService;

@Service
public class MovieClawingCasePrefixServiceImpl implements MovieClawingCasePrefixService {

	@Autowired
	private HomeFeiClawingService homeFeiService;
	@Autowired
	private DyttClawingService dyttService;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		if(caseId == null) {
			return null;
		}
		
		if (MovieClawingCaseType.dytt.getId().equals(caseId)) {
			return dyttService.clawing(te);
		} else if (MovieClawingCaseType.homeFeiCollection.getId().equals(caseId)) {
			return homeFeiService.collection(te);
		} else if (MovieClawingCaseType.homeFeiDownload.getId().equals(caseId)) {
			return homeFeiService.download(te);
		}
		return null;
	}
	
}
