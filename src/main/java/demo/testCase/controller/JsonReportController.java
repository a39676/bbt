package demo.testCase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.jsonReport.pojo.bo.TestReportBO;
import autoTest.jsonReport.pojo.constant.JsonReportInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import demo.baseCommon.controller.CommonController;
import demo.testCase.service.JsonReportService;

@Controller
@RequestMapping(value = {JsonReportInteractionUrl.root})
public class JsonReportController extends CommonController {

	@Autowired
	private JsonReportService jsonReportService;
	
	@PostMapping(value = JsonReportInteractionUrl.findReportsByCondition)
	@ResponseBody
	public List<TestReportBO> findReportsByCondition(@RequestBody FindTestEventPageByConditionDTO dto) {
		return jsonReportService.findReportsByCondition(dto);
	}
	
	@PostMapping(value = JsonReportInteractionUrl.findReportByTestEventId)
	@ResponseBody
	public FindReportByTestEventIdResult findReportByTestEventId(@RequestBody FindReportByTestEventIdDTO dto) {
		return jsonReportService.findReportByTestEventId(dto);
	}
}
