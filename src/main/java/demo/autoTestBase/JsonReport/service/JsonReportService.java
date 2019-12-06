package demo.autoTestBase.JsonReport.service;

import java.util.List;

import autoTest.jsonReport.pojo.bo.TestReportBO;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;

public interface JsonReportService {

	List<TestReportBO> findReportsByCondition(FindTestEventPageByConditionDTO dto);

	FindReportByTestEventIdResult findReportByTestEventId(FindReportByTestEventIdDTO dto);

}
