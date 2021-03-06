package demo.clawing.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.testEvent.pojo.constant.SearchingDemoUrl;
import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;
import demo.clawing.demo.service.SearchingDemoManagerService;

@Controller
@RequestMapping(value = SearchingDemoUrl.root)
public class SearchingDemoController {

	@Autowired
	private SearchingDemoManagerService searchingDemoManagerService;
	
	@PostMapping(value = SearchingDemoUrl.insert)
	@ResponseBody
	public InsertSearchingDemoEventResult insert(@RequestBody InsertSearchingDemoTestEventDTO dto) {
		return searchingDemoManagerService.insert(dto);
	}
	
}
