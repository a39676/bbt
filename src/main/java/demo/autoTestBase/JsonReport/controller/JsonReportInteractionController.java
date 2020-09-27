package demo.autoTestBase.JsonReport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.jsonReport.pojo.bo.TestReportBO;
import autoTest.jsonReport.pojo.constant.AutoTestInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import demo.autoTestBase.JsonReport.service.JsonReportService;
import demo.baseCommon.controller.CommonController;

@Controller
@RequestMapping(value = {AutoTestInteractionUrl.root})
public class JsonReportInteractionController extends CommonController {

	@Autowired
	private JsonReportService jsonReportService;
	
	@PostMapping(value = AutoTestInteractionUrl.findReportsByCondition)
	@ResponseBody
	public List<TestReportBO> findReportsByCondition(@RequestBody FindTestEventPageByConditionDTO dto) {
		return jsonReportService.findReportsByCondition(dto);
	}
	
	@PostMapping(value = AutoTestInteractionUrl.findReportByTestEventId)
	@ResponseBody
	public FindReportByTestEventIdResult findReportByTestEventId(@RequestBody FindReportByTestEventIdDTO dto) {
		return jsonReportService.findReportByTestEventId(dto);
	}
}
