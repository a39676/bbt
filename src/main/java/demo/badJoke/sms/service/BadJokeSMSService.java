package demo.badJoke.sms.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.badJoke.sms.pojo.dto.BadJokeSMSDTO;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.service.impl.ClawingCommonService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestCaseType;
import demo.testCase.service.TestEventService;

@Service
public class BadJokeSMSService extends ClawingCommonService {

//	@Autowired
//	private SystemConstantService constantService;
	
	@Autowired
	private TestEventService testEventService;
	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	
	private TestEvent buildTestEvent() {
		return buildTestEvent(TestCaseType._91wenwen);
	}
	
	public Integer insertBadJokeSMSEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertSelective(te);
	}
	
	/*
	 * 
	 * TODO
	 * 是否统计最近10次的成功率
	 * 申请插入任务队列, 此处必须附加目标号码(并校验), 可附带执行的号码数量, 执行url数量
	 * 是否加入定时任务?...
	 * 根据参数, 优先查找久远号码N个, 执行N条URL行动
	 * 
	 */
	
	public CommonResultBBT _91wenwen(TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.91wenwen.net/user/mobile/reg";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			d.get(url);
			
			WebElement mobileInput = d.findElement(By.id("signup_mobile_mobile_phone"));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			d.findElement(By.id("SendSmsCodeBtn")).click();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
			if (d != null) {
				d.quit();
			}
		}
		return r;
	}
	
	public void zhiWang(TestEvent te, BadJokeSMSDTO dto) {
		String url = "http://my.cnki.net/elibregister/CommonRegister.aspx";
	}
	
	public void yinXiang(TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://static.app.yinxiang.com/embedded-web/registration/index.html#/registration";
	}
	
	public void mafengwo(TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://passport.mafengwo.cn/regist/";
	}
	
	public void zhipin(TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.zhipin.com/user/signup.html";
	}
	
	public void jumpw(TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://passport.jumpw.com/views/register.jsp";
	}
	
	
	/*
	 * https://www.nike.com/cn/register
	 * https://account.ch.com/NonRegistrations-Regist
	 * https://i.flyme.cn/mregister.html
	 * https://puser.zjzwfw.gov.cn/sso/usp.do?action=register
	 * http://www.surong360.com/SR360/application/user/emailRegisterPage.do
	 * https://www.rexxglobal.com/register_phone.html#
	 * https://passport.9you.com/mobile_regist.php
	 * https://ffp.hnair.com/FFPClub/member/register
	 * 
	 */
	
	
}
