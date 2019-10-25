package demo.badJoke.sms.service;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.badJoke.sms.pojo.dto.BadJokeSMSDTO;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.service.impl.ClawingCommonService;
import demo.selenium.pojo.bo.XpathBuilderBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestCaseType;
import demo.testCase.service.TestEventService;

@Service
public class BadJokeSMSService extends ClawingCommonService {

//	@Autowired
//	private SystemConstantService constantService;
	
	@Autowired
	private TestEventService testEventService;
//	@Autowired
//	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	
	private String normalPwd = "398Apk_Lor";
	private String normalUsername = "testing";
	
	private TestEvent buildTestEvent() {
		return buildTestEvent(TestCaseType.badJokeSms);
	}
	
	public Integer insertBadJokeSMSEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertSelective(te);
	}
	
	/*
	 * 
	 * TODO
	 * 是否统计最近10次的成功率? 根据网站?
	 * 申请插入任务队列, 此处必须附加目标号码(并校验), 可附带执行的号码数量, 执行url数量
	 * 是否加入定时任务?...
	 * 根据参数, 优先查找久远号码N个, 执行N条URL行动
	 * 最外围 try catch 需要包括 finally {d.quit();}
	 * 预留一个 break 条件, 每次执行前, 检查需要终止的号码, 
	 * 预留一个万用终止条件(包括通过一个万用号码终止当前号码, 通过一个号码终止所有队列中等待的号码) 
	 * 
	 */
	
	public void random(Integer i, WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		/*
		 * TODO
		 * 应该有更好的方法
		 */
		if(i == 1) {
			_91wenwen(d, te, dto);
		} else if(i == 2) {
			zhiWang(d, te, dto);
		} else if(i == 3) {
		} else if(i == 4) {
		} else if(i == 5) {
		} else if(i == 6) {
		} else if(i == 7) {
		} else if(i == 8) {
		} else if(i == 9) {
		} else if(i == 10) {
		} else if(i == 11) {
		} else if(i == 12) {
		} else if(i == 13) {
		} else if(i == 14) {
		} else if(i == 15) {
		} else if(i == 16) {
		} else if(i == 17) {
		} else if(i == 18) {
		} else if(i == 19) {
		} else if(i == 20) {
		}
	}
	
	public CommonResultBBT _91wenwen(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.91wenwen.net/user/mobile/reg";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			WebElement mobileInput = d.findElement(By.id("signup_mobile_mobile_phone"));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			d.findElement(By.id("SendSmsCodeBtn")).click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT zhiWang(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "http://my.cnki.net/elibregister/CommonRegister.aspx";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			// 选择手机注册
			WebElement phoneRegButton = d.findElement(By.id("phoneRe"));
			if(phoneRegButton.isDisplayed()) {
				phoneRegButton.click();
			}

			WebElement vcodeInput = d.findElement(By.id("phoneTxtCheckCode"));
			vcodeInput.clear();
			vcodeInput.click();
			
			WebElement mobileInput = d.findElement(By.id("txtMobile"));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			WebElement pwdInput = d.findElement(By.id("phonePsd"));
			pwdInput.clear();
			pwdInput.sendKeys(normalPwd);
			
			WebElement vcodeImg = d.findElement(By.id("phoneCheckCode"));
			
			WebElement sendSmsButton = d.findElement(By.id("smsbtn"));
			
			String vcode = null;
			WebElement vcodeIsCorrect = d.findElement(By.id("trphone_code"));
			
			int vcodeCount = 0;
			while(vcodeIsCorrect.isDisplayed() && vcodeCount < 15) {
				try {
					vcode = auxTool.captchaHandle(d, vcodeImg, te);
					vcodeInput.sendKeys(vcode);
					vcodeCount++;
				} catch (Exception e) {
				}
			}
			
			if(!vcodeIsCorrect.isDisplayed()) {
				sendSmsButton.click();
				r.setIsSuccess();
			}
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT yinXiang(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://static.app.yinxiang.com/embedded-web/registration/index.html#/registration";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			// 选择手机注册
			WebElement phoneRegButton = d.findElement(By.id("phoneRe"));
			if(phoneRegButton.isDisplayed()) {
				phoneRegButton.click();
			}

			WebElement vcodeInput = d.findElement(By.id("phoneTxtCheckCode"));
			vcodeInput.clear();
			vcodeInput.click();
			
			WebElement mobileInput = d.findElement(By.id("txtMobile"));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			WebElement pwdInput = d.findElement(By.id("phonePsd"));
			pwdInput.clear();
			pwdInput.sendKeys(normalPwd);
			
			WebElement vcodeImg = d.findElement(By.id("phoneCheckCode"));
			
			WebElement sendSmsButton = d.findElement(By.id("smsbtn"));
			
			String vcode = null;
			WebElement vcodeIsCorrect = d.findElement(By.id("trphone_code"));;
			
			int vcodeCount = 0;
			while(vcodeIsCorrect.isDisplayed() && vcodeCount < 15) {
				try {
					vcode = auxTool.captchaHandle(d, vcodeImg, te);
					vcodeInput.sendKeys(vcode);
					sendSmsButton.click();
					vcodeCount++;
				} catch (Exception e) {
				}
			}
			
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT mafengwo(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "http://www.mafengwo.cn/";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			XpathBuilderBO x = new XpathBuilderBO();
			x.start("a").addAttribute("href", "https://passport.mafengwo.cn/regist.html");
			
			WebElement regHref = d.findElement(By.xpath(x.getXpath()));
			regHref.click();
			
			x.start("name").addAttribute("type", "text").addAttribute("name", "passport").addAttribute("data-type", "mobile");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("button").addAttribute("type", "submit");
			List<WebElement> submitButtons = d.findElements(By.xpath(x.getXpath()));
			WebElement regButton = null;
			for(int i = 0; i < submitButtons.size() && regButton == null; i++) {
				if("立即注册".equals(submitButtons.get(i).getText())) {
					regButton = submitButtons.get(i);
				}
			}
			
			if(regButton != null) {
				regButton.click();
			}
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT zhipin(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.zhipin.com/";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			XpathBuilderBO x = new XpathBuilderBO();
			x.start("a").addAttribute("href", "https://signup.zhipin.com");
			
			WebElement regButton = d.findElement(By.xpath(x.getXpath()));
			regButton.click();
			
			/*
			 * TODO
			 * 滑块拖动需要一个通用工具
			 */
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT jumpw(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://passport.jumpw.com/views/register.jsp";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			WebElement mobileInput = d.findElement(By.id("Accountstr"));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			WebElement sendSmsButton = d.findElement(By.id("btn"));
			sendSmsButton.click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT nike(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.nike.com/cn/register";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			XpathBuilderBO x = new XpathBuilderBO();
			x.start("input").addAttribute("placeholder", "手机号码");
			
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("input").addAttribute("value", "发送验证码");
			WebElement sendSmsButton = d.findElement(By.xpath(x.getXpath()));
			sendSmsButton.click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT chunQiu(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://account.ch.com/NonRegistrations-Regist";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			XpathBuilderBO x = new XpathBuilderBO();
			x.start("input").addAttribute("placeholder", "请输入手机号");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			WebElement sendSmsButton = d.findElement(By.id("getDynamicPwd"));
			sendSmsButton.click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT flyme(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://i.flyme.cn/mregister.html";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			WebElement mobileInput = d.findElement(By.id("phone"));
			mobileInput.click();
			mobileInput.sendKeys(dto.getMobileNum());
			
			XpathBuilderBO x = new XpathBuilderBO();
			x.start("span").addAttribute("class", "geetest_wait_dot geetest_dot_1");
			WebElement vaildClickSpan = d.findElement(By.xpath(x.getXpath()));
			Actions a = new Actions(d);
			a.moveToElement(vaildClickSpan).build().perform();
			Thread.sleep(300L);
			vaildClickSpan.click();
			
			Thread.sleep(2000L);
			
			x.start("div").addAttribute("id", "flymeService")
			.findChild("span").addAttribute("class", "mzchkbox")
			.findChild("span").addAttribute("class", "checkboxPic mzchkbox check_chk");
			
			WebElement agreeCheckbox = d.findElement(By.xpath(x.getXpath()));
			agreeCheckbox.click();
			
			WebElement nextStep = d.findElement(By.id("nextStep"));
			nextStep.click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT demo(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://puser.zjzwfw.gov.cn/sso/usp.do?action=register";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			WebElement usernameInput = d.findElement(By.id("loginName"));
			usernameInput.clear();
			usernameInput.sendKeys(normalUsername);
			
			/*
			 * TODO
			 */
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	/*
	public CommonResultBBT demo(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://passport.jumpw.com/views/register.jsp";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		try {
			d.get(url);
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	 */
	
	
	/*
	 * https://puser.zjzwfw.gov.cn/sso/usp.do?action=register
	 * http://www.surong360.com/SR360/application/user/emailRegisterPage.do
	 * https://www.rexxglobal.com/register_phone.html#
	 * https://passport.9you.com/mobile_regist.php
	 * https://ffp.hnair.com/FFPClub/member/register
	 * https://i.ruanmei.com/
	 * https://passport.umeng.com/signup
	 * https://account.weimob.com/register
	 * http://www.zhonghuanus.com/authorization/register.action
	 * https://www.kunlun.com/?act=passport.regist&refurl=http%3A%2F%2Fwww.kunlun.com%2F%3Fact%3Dpassport.usercenter
	 * http://oauth.hubei.gov.cn:9090/hbyzw/zwfw/personInfo/personInfoReg.jsp?appCode=hbrmzf
	 * https://leancloud.cn/dashboard/login.html#/signup
	 * https://reg.huaweicloud.com/registerui/cn/register.html#/register
	 * https://passport.jumei.com/i/account/signup
	 * https://www.wondercv.com/signin###
	 * https://account.glodon.com/register
	 * https://user.mihoyo.com/#/register/mobile?cb_route=%2Faccount%2Fhome
	 * https://hotel.bestwehotel.com/NewRegister/NewWebRegister/
	 * https://www.962222.net/pages/user/register.html
	 * https://aq.99.com/NDUser_Register_New.aspx
	 * 
	 */
	
	
}
