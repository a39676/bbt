package demo.scriptCore.localClawing.hades.ui.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.hades.common.service.HadesCommonService;

@Service
public class LoginPage extends HadesCommonService {

	private String url = hadesOptionFile.getHostUrl() + "/#/adminLogin";

	private String usernameInputXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/form[1]/div[3]/div[1]/div[1]/input[1]";
	private String pwdInputXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/form[1]/div[4]/div[1]/div[1]/input[1]";
	private String lgoinButtonXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/form[1]/button[1]/span[1]";

	public void getLoginPage(WebDriver d) {
		d.get(url);
	}

	public void login(WebDriver d, String username, String pwd) {
		WebElement usrenameInput = d.findElement(By.xpath(usernameInputXpath));
		usrenameInput.click();
		usrenameInput.clear();
		usrenameInput.sendKeys(username);

		WebElement pwdInput = d.findElement(By.xpath(pwdInputXpath));
		pwdInput.click();
		pwdInput.clear();
		pwdInput.sendKeys(pwd);

		WebElement loginButton = d.findElement(By.xpath(lgoinButtonXpath));
		loginButton.click();
	}
}
