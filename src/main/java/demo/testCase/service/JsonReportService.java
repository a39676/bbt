package demo.testCase.service;

import java.util.List;

import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import demo.testCase.pojo.bo.TestReportBO;
import demo.testCase.pojo.result.FindReportByTestEventIdResult;

public interface JsonReportService {

	List<TestReportBO> findReportsByCondition(FindTestEventPageByConditionDTO dto);

	FindReportByTestEventIdResult findReportByTestEventId(FindReportByTestEventIdDTO dto);

}
