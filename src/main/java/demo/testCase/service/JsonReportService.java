package demo.testCase.service;

import java.util.List;

import demo.testCase.pojo.bo.TestReportBO;
import demo.testCase.pojo.dto.FindReportByTestEventIdDTO;
import demo.testCase.pojo.dto.FindTestEventPageByConditionDTO;
import demo.testCase.pojo.result.FindReportByTestEventIdResult;

public interface JsonReportService {

	List<TestReportBO> findReportsByCondition(FindTestEventPageByConditionDTO dto);

	FindReportByTestEventIdResult findReportByTestEventId(FindReportByTestEventIdDTO dto);

}
