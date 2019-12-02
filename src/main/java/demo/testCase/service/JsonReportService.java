package demo.testCase.service;

import java.util.List;

import demo.testCase.pojo.bo.TestReportBO;
import demo.testCase.pojo.dto.FindTestEventPageByConditionDTO;

public interface JsonReportService {

	List<TestReportBO> findReportsByTestEvent(FindTestEventPageByConditionDTO dto);

}
