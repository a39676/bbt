package demo.scriptCore.scheduleClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.testEvent.searchingDemo.pojo.constant.SearchingDemoUrl;
import demo.scriptCore.scheduleClawing.pojo.dto.InsertBingSearchDemoDTO;
import demo.scriptCore.scheduleClawing.service.BingDemoPrefixService;

@Controller
@RequestMapping(value = SearchingDemoUrl.ROOT)
public class BingDemoController {

	@Autowired
	private BingDemoPrefixService bingDemoPrefixService;
	
	@PostMapping(value = SearchingDemoUrl.INSERT_SEARCH_IN_HOMEPAGE)
	@ResponseBody
	public String insertSerachInHomepage(@RequestBody InsertBingSearchDemoDTO dto) {
		bingDemoPrefixService.insertSearchInHomeEvent(dto);
		return "done";
	}
	
}
