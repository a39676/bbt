package demo.badJoke.sms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.web.WebATToolService;
import demo.badJoke.sms.pojo.dto.BadJokeSMSDTO;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.service.impl.ClawingCommonService;
import demo.selenium.service.impl.AuxiliaryToolServiceImpl;
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
	private AuxiliaryToolServiceImpl auxTool;
	@Autowired
	private WebATToolService webATTool;
	@Autowired
	private SystemConstantService constantService;
	
	private String normalPwd = "398ApkLor";
	private String normalNumPwd = "126578";
	private String normalUsername = "Aestingfv";
	private String normalCnLastName = "史";
	private String normalCnFirstName = "上飞";
	private String normalCnIdCardNum = "211481197507197853"; // 张凯泽
	
	private String breakWordRedisKey = "badJokeBreakWord";
	private String safeWord = "safeWord";
	
	private int maxCaptchaCount = 15;
	
	private TestEvent buildTestEvent() {
		return buildTestEvent(TestCaseType.badJokeSms);
	}
	
	public Integer insertBadJokeSMSEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertSelective(te);
	}
	
	/*
	 * TODO
	 * 是否统计最近10次的成功率? 根据网站?
	 * 申请插入任务队列, 此处必须附加目标号码(并校验), 可附带执行的号码数量, 执行url数量
	 * 是否加入定时任务?...
	 * 根据参数, 优先查找久远号码N个, 执行N条URL行动
	 * 最外围 try catch 需要包括 finally {d.quit();}
	 * 
	 */
	
	private String findBreakWord() {
		return constantService.getValByName(breakWordRedisKey);
	}
	
	public void random(Integer i, WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		/*
		 * 应该有更好的方法
		 */
		
		String breakWord = findBreakWord();
		if(StringUtils.isNotBlank(breakWord)) {
			if(breakWord.equals(safeWord) || breakWord.equals(dto.getMobileNum())) {
				return;
			}
		}
		
		CommonResultBBT result = null;
		if(i == 1) {
			result = _91wenwen(d, te, dto);
		} else if(i == 2) {
			result = zhiWang(d, te, dto);
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
		
		if(!result.isSuccess()) {
//			TODO
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

			WebElement captchaInput = d.findElement(By.id("phoneTxtCheckCode"));
			captchaInput.clear();
			captchaInput.click();
			
			WebElement mobileInput = d.findElement(By.id("txtMobile"));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			WebElement pwdInput = d.findElement(By.id("phonePsd"));
			pwdInput.clear();
			pwdInput.sendKeys(normalPwd);
			
			WebElement captchaImg = d.findElement(By.id("phoneCheckCode"));
			
			WebElement sendSmsButton = d.findElement(By.id("smsbtn"));
			
			String captchaCode = null;
			WebElement captchaIsCorrect = d.findElement(By.id("trphone_code"));
			
			int captchaCount = 0;
			while(captchaIsCorrect.isDisplayed() && captchaCount < maxCaptchaCount) {
				captchaImg.click();
				try {
					captchaCode = auxTool.captchaHandle(d, captchaImg, te);
					captchaInput.clear();
					captchaInput.sendKeys(captchaCode);
					pwdInput.click();
					captchaIsCorrect = d.findElement(By.id("trphone_code"));
					captchaCount++;
				} catch (Exception e) {
				}
			}
			
			if(!captchaIsCorrect.isDisplayed()) {
				sendSmsButton.click();
				r.setIsSuccess();
			}
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
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
			
//			TODO
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
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
			
			WebElement swipeButton = d.findElement(By.id("nc_3_n1z"));
			WebElement chuteEle = d.findElement(By.id("nc_3_n1t"));
			
			auxTool.swipeCaptchaHadle(d, swipeButton, chuteEle);
			
			x.start("div").addAttribute("type", "tel").addAttribute("name", "phone");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("button").addAttribute("data-url", "/wapi/zppassport/send/smsCode");
			WebElement smsSendButton = d.findElement(By.xpath(x.getXpath()));
			smsSendButton.click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
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
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT ctrip(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.ctrip.com/";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			d.get(url);
			
			try {
				WebElement languageChoose = d.findElement(By.id("divChooseLanguage"));
				if(languageChoose.isDisplayed()) {
					x.start("a").addAttribute("class", "language-flag-zh-cn");
					WebElement languageCNA = d.findElement(By.xpath(x.getXpath()));
					languageCNA.click();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			x.start("span").addAttribute("class", "set-text");
			
			List<WebElement> spans = d.findElements(By.xpath(x.getXpath()));
			WebElement regSpan = null;
			for(int i = 0; i < spans.size() && regSpan == null; i++) {
				if(spans.get(i).getText() != null && spans.get(i).getText().contains("免费注册")) {
					regSpan = spans.get(i);
				}
			}
			
			if(regSpan == null) {
				return r;
			}
			
			regSpan.click();
			
			x.start("a").addAttribute("class", "reg_btn reg_agree");
			WebElement regAgree = d.findElement(By.xpath(x.getXpath()));
			regAgree.click();
			
			x.start("div").addAttribute("class", "cpt-drop-btn");
			WebElement swipeButton = d.findElement(By.xpath(x.getXpath()));
			
			WebElement chuteDiv = d.findElement(By.id("slideCode"));
			
			auxTool.swipeCaptchaHadle(d, swipeButton, chuteDiv);
			/*
			 * TODO
			 */
			
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT wondercv(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://www.wondercv.com/signin";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		XpathBuilderBO x = new XpathBuilderBO();
		
		
		try {
			d.get(url);
			
			x.start("a").addAttribute("class", "to-other phone");
			d.findElement(By.xpath(x.getXpath())).click();
			
			x.start("input").addAttribute("type", "tel");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("input").addAttribute("data-url", "/verify_tokens/phone");
			d.findElement(By.xpath(x.getXpath())).click();
			
			/*
			 * TODO
			 * 考虑补充极验拖动验证
			 */
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT zjzwfw(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://puser.zjzwfw.gov.cn/sso/usp.do?action=register";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			d.get(url);
			
			x.start("input").addAttribute("type", "text").addAttribute("id", "loginName");
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(normalUsername);
			
			x.start("input").addAttribute("type", "text").addAttribute("id", "mobilePhone");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.click();
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("img").addAttribute("id", "captcha_img");
			By captchaImgBy = By.xpath(x.getXpath());
			WebElement captchaImg = d.findElement(captchaImgBy);
			captchaImg.click();
			
			String captchaCode = auxTool.captchaHandle(d, captchaImg, te);
			
			x.start("input").addAttribute("type", "text").addAttribute("id", "imgcode");
			WebElement captchaCodeInput = d.findElement(By.xpath(x.getXpath()));
			captchaCodeInput.click();
			captchaCodeInput.clear();
			captchaCodeInput.sendKeys(captchaCode);
			
			x.start("input").addAttribute("id", "smscode");
			WebElement smsCodeInput = d.findElement(By.xpath(x.getXpath()));
			
			x.start("div").addAttribute("class", "formErrorContent");
			By captchaErrorWarnBy = By.xpath(x.getXpath());
			WebElement captchaErrorWarnDiv = null;
			int captchaCount = 0;
			try {
				while((captchaErrorWarnDiv = d.findElement(captchaErrorWarnBy)) != null && captchaCount < 10) {
					mobileInput.click();
					captchaImg = d.findElement(captchaImgBy);
					captchaImg.click();
					captchaCode = auxTool.captchaHandle(d, captchaImg, te);
					captchaCodeInput.click();
					captchaCodeInput.clear();
					captchaCodeInput.sendKeys(captchaCode);
					smsCodeInput.click();
					captchaCount++;
				}
			} catch (Exception e) {
				captchaErrorWarnDiv = null;
			}
			
			if(captchaErrorWarnDiv != null) {
				r.setIsFail();
			} else {
				x.start("button").addAttribute("id", "getsmscode");
				WebElement sendSmsCode = d.findElement(By.xpath(x.getXpath()));
				sendSmsCode.click();
				
				r.setIsSuccess();
			}
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT _9you(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://passport.9you.com/mobile_regist.php";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			d.get(url);
			
			x.start("input").addAttribute("id", "username");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.click();
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("input").addAttribute("id", "password");
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(normalPwd);
			
			x.start("span").addAttribute("id", "sendcode");
			WebElement getSmsCode = d.findElement(By.xpath(x.getXpath()));
			getSmsCode.click();
			
			x.start("img").addAttribute("id", "phonecheckcode");
			By imgCaptchaBy = By.xpath(x.getXpath());
			WebElement captchaImg = d.findElement(imgCaptchaBy);
			captchaImg.click();
			
			x.start("input").addAttribute("class", "yzm");
			WebElement smsCodeInput = d.findElement(By.xpath(x.getXpath()));
			
			Thread.sleep(320L);
			
			x.start("a").addAttribute("href", "##").addAttribute("class", "okBtn");
			WebElement smsCodeBtn = d.findElement(By.xpath(x.getXpath()));
			
			int captchaCount = 0;
			String captchaCode = "";
			boolean alertFlag = true;
			while(captchaCount < maxCaptchaCount && (captchaCode.length() != 4 || alertFlag)) {
				if(captchaCount == 0) {
					alertFlag = webATTool.alertExists(d);
				}
				
				if(alertFlag) {
					Thread.sleep(520L);
					d.switchTo().alert().accept();
					Thread.sleep(520L);
				}
				captchaImg = d.findElement(imgCaptchaBy);
				captchaImg.click();
				Thread.sleep(520L);
				captchaCode = auxTool.captchaHandle(d, captchaImg, te);
				Thread.sleep(520L);
				smsCodeInput.click();
				smsCodeInput.clear();
				smsCodeInput.sendKeys(captchaCode);
				Thread.sleep(520L);
				smsCodeBtn.click();
				Thread.sleep(520L);
				alertFlag = webATTool.alertExists(d);
				captchaCount++;
			}
			
			if(alertFlag) {
				d.switchTo().alert().accept();
			}
			x.start("div").addAttribute("class", "yzmBod");
			WebElement captchaMask = d.findElement(By.xpath(x.getXpath()));
			if(!captchaMask.isDisplayed()) {
				r.setIsSuccess();
			}
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	public CommonResultBBT hnair(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "https://ffp.hnair.com/FFPClub/member/register";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			d.get(url);
			
			x.start("input").addAttribute("id", "s_lastNameZh");
			WebElement cnLastNameInput = d.findElement(By.xpath(x.getXpath()));
			cnLastNameInput.click();
			cnLastNameInput.clear();
			cnLastNameInput.sendKeys(normalCnLastName);
			
			x.start("input").addAttribute("id", "s_firstNameZh");
			WebElement cnFirstNameInput = d.findElement(By.xpath(x.getXpath()));
			cnFirstNameInput.click();
			cnFirstNameInput.clear();
			cnFirstNameInput.sendKeys(normalCnFirstName);
			
			x.start("input").addAttribute("id", "idNumber");
			WebElement idCardInput = d.findElement(By.xpath(x.getXpath()));
			idCardInput.click();
			idCardInput.clear();
			idCardInput.sendKeys(normalCnIdCardNum);
			
			x.start("input").addAttribute("id", "s_password");
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(normalNumPwd);
			
			x.start("input").addAttribute("id", "s_confirmPass");
			WebElement pwdConfirmInput = d.findElement(By.xpath(x.getXpath()));
			pwdConfirmInput.click();
			pwdConfirmInput.clear();
			pwdConfirmInput.sendKeys(normalNumPwd);
			
			x.start("input").addAttribute("id", "s_mobile");
			WebElement mobileInput = d.findElement(By.xpath(x.getXpath()));
			mobileInput.click();
			mobileInput.clear();
			mobileInput.sendKeys(dto.getMobileNum());
			
			x.start("img").addAttribute("id", "s_valid_img");
			By captchaImgBy = By.xpath(x.getXpath());
			WebElement captchaImg = d.findElement(captchaImgBy);
			
			x.start("input").addAttribute("id", "s_validateCodeImg");
			WebElement captchaInput = d.findElement(By.xpath(x.getXpath()));
			captchaInput.click();
			captchaInput.clear();
			
			x.start("input").addAttribute("id", "s_validateCode");
			WebElement smsCodeInput = d.findElement(By.xpath(x.getXpath()));
			smsCodeInput.click();
			
			x.start("input").addAttribute("id", "s_aHref");
			WebElement sendSmsBtn = d.findElement(By.xpath(x.getXpath()));
			
			boolean alertFlag = true;
			int captchaCount = 0;
			String captchaCode = null;
			while(captchaCount < maxCaptchaCount && alertFlag) {
				if(captchaCount == 0) {
					alertFlag = webATTool.alertExists(d);
				}
				
				if(alertFlag) {
					d.switchTo().alert().accept();
				}
				
				captchaImg.click();
				captchaCode = auxTool.captchaHandle(d, captchaImg, te);
				captchaInput.click();
				captchaInput.clear();
				captchaInput.sendKeys(captchaCode);
				sendSmsBtn.click();
				
				alertFlag = webATTool.alertExists(d);
				captchaCount++;
			}
			
			if(!alertFlag) {
				r.setIsSuccess();
			} else {
				d.switchTo().alert().accept();
			}
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	
	/*
	public CommonResultBBT demo(WebDriver d, TestEvent te, BadJokeSMSDTO dto) {
		String url = "";
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();

		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			d.get(url);
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			
			
		} finally {
			r.setMessage(report.toString());
		}
		return r;
	}
	 */
	
	
	/*
	 * 
	 * https://www.seekfunbook.com/osp/gotoRegister
	 * 
	 * https://www.rexxglobal.com/register_phone.html#
	 * https://i.ruanmei.com/
	 * https://passport.umeng.com/signup
	 * https://account.weimob.com/register
	 * http://www.zhonghuanus.com/authorization/register.action
	 * https://www.kunlun.com/?act=passport.regist&refurl=http%3A%2F%2Fwww.kunlun.com%2F%3Fact%3Dpassport.usercenter
	 * http://oauth.hubei.gov.cn:9090/hbyzw/zwfw/personInfo/personInfoReg.jsp?appCode=hbrmzf
	 * https://leancloud.cn/dashboard/login.html#/signup
	 * https://reg.huaweicloud.com/registerui/cn/register.html#/register
	 * https://passport.jumei.com/i/account/signup
	 * https://account.glodon.com/register
	 * https://user.mihoyo.com/#/register/mobile
	 * https://hotel.bestwehotel.com/NewRegister/NewWebRegister/
	 * https://www.962222.net/pages/user/register.html
	 * https://aq.99.com/NDUser_Register_New.aspx
	 * https://www.zoho.com.cn/mail/signup.html
	 * https://www.iflyrec.com/html/reg.html
	 * https://www.pgyer.com/user/register
	 * https://i.360.cn/reg#
	 * https://account.youku.com/register.htm
	 * https://upass.10jqka.com.cn/register
	 * 
	 * https://www.dailuobo.com/partner.html
	 * https://shimo.im/login
	 * 
	 */
	
	/*
	 * 需要简单滑动
	 * https://ibaotu.com/
	 */
	
	/*
	 * 需要拼图滑动
	 * https://user.48.cn/login/index.html
	 * https://www.smzdm.com/
	 * https://center.huashengdaili.com/zhuce/?channel=huashengdaili
	 */
	
}
