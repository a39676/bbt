package demo.bingDemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.service.ATJsonReportService;
import demo.bingDemo.service.BingDemoService;
import demo.clawing.service.impl.ClawingCommonService;
import demo.image.ImageInteractionService;

@Service
public class BingDemoServiceImpl extends ClawingCommonService implements BingDemoService {

	@Autowired
	private ATJsonReportService jsonReport;
	@Autowired
	private ImageInteractionService imageInteractionService;
	
	/*
	 * TODO
	 */
	
}
