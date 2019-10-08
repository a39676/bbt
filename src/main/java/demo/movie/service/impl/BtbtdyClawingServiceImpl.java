package demo.movie.service.impl;

import org.springframework.stereotype.Service;

import demo.movie.service.BtbtdyClawingService;
import demo.testCase.pojo.po.TestEvent;

@Service
public final class BtbtdyClawingServiceImpl extends MovieClawingCommonService implements BtbtdyClawingService {

//	@Autowired
//	private FileUtilCustom iou;
//	
//	@Autowired
//	private SystemConstantService constantService;
//	@Autowired
//	private SeleniumGlobalOptionService globalOptionService;
//	@Autowired
//	private WebDriverService webDriverService;
//	@Autowired
//	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private MovieClawingOptionService optionService;
//	@Autowired
//	private JavaScriptServiceImpl jsUtil;
//
//	@Autowired
//	private MovieRecordMapper recordMapper;
//	@Autowired
//	private MovieInfoMapper infoMapper;
//	@Autowired
//	private MovieIntroductionMapper introduectionMapper;
//	
//	private String mainUrl = "http://www.btbtdy.com";
//
	@Override
	protected TestEvent buildTesetEvent() {
		/*
		 * TODO
		 */
		TestEvent te = new TestEvent();
		te.setCaseId(6L);
		te.setId(snowFlake.getNextId());
		te.setEventName("homeFei");
		return te;
	}
	

	

}
