package demo.testCase.pojo.bo.oa.newSoft.web;

import demo.testCase.pojo.bo.TestEventBO;

public class TestEventLoginCorrect extends TestEventBO {

	private String userName;
	private String pwd;

	public String getUserName() {
		return userName;
	}

	public String getPwd() {
		return pwd;
	}

	@Override
	public TestEventBO build() {
		this.caseCode = "STUC_CC01002";
		this.projectName = "newSoftOA7.5";
		this.mainUrl = "http://localhost:8080/NSOA7-GROUP/Login.jsp";
		this.userName = "adminSystem";
		this.pwd = "1";
		return this;
	}

}
