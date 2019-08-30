package demo.test.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.constant.TestViewConstants;
import demo.test.service.TestService;
import demo.weka.pojo.result.WekaCommonResult;
import demo.weka.service.WekaCluster;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {
	
//	@Autowired
//	private TestMapper testMapper;
//	@Autowired
//	private WeixinService weixinService;
	@Autowired
	private TestService testService;
	@Autowired
	private WekaCluster weka;
	
	@ApiOperation(value="测试", notes="测试notes")
	@GetMapping(value = { "/test" })
	public ModelAndView jspTest(HttpServletRequest request) throws Exception {
		insertVisitIp(request);
		ModelAndView view = new ModelAndView();
		String v = testService.redisGet();
		view.addObject("message", v);
		view.addObject("title", "test title");
		view.setViewName(TestViewConstants.test);
		return view;
	}

	@GetMapping(value = { "/testWebSocket" })
	public ModelAndView testWebSocket(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		view.setViewName("testJSP/testWebSocket");
		return view;
	}

	@GetMapping(value = { "/testWeka1" })
	public ModelAndView testWeka1(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		view.setViewName(TestViewConstants.test01);
		WekaCommonResult result = weka.kMeansTest("D:/auxiliary/tmp/wekaTest3.csv", 4, 1);
		view.addObject("message", result.getMessage());
		return view;
	}
	
	@GetMapping(value = { "/testException" })
	public ModelAndView testException(HttpServletRequest request) {
		log.debug("dateTime: {}", new Date());
		log.error("dateTime error: {}", new Date());
		
		ModelAndView v = new ModelAndView();
		return v;
	}

	@GetMapping(value = "/roleGetTest")
	public void roleGetTest() { 
		testService.roleGetTest();
	}
	
}
