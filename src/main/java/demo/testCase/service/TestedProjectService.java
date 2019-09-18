package demo.testCase.service;

import demo.testCase.pojo.po.TestedProject;

public interface TestedProjectService {

	TestedProject findByProjectName(String projectName);

}
