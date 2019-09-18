package demo.testCase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestedProjectMapper;
import demo.testCase.pojo.po.TestedProject;
import demo.testCase.service.TestedProjectService;

@Service
public class TestedProjectServiceImpl extends CommonService implements TestedProjectService {

	@Autowired
	private TestedProjectMapper projectMapper;

	@Override
	public TestedProject findByProjectName(String projectName) {
		return projectMapper.findByProjectName(projectName);
	}
}
