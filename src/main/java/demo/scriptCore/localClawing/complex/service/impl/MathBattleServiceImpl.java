package demo.scriptCore.localClawing.complex.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.complex.service.MathBattleService;
import demo.selenium.service.impl.AutomationTestCommonService;

@Service
public class MathBattleServiceImpl extends AutomationTestCommonService implements MathBattleService {

	@Override
	public void start() {
		WebDriver d = webDriverService.buildChromeWebDriver();

		WebElement elementX = null;
		WebElement elementOp = null;
		WebElement elementY = null;
		WebElement elementTaskRes = null;

		WebElement buttonCorrect = null;
		WebElement buttonWrong = null;

		Double x = 0D;
		Double y = 0D;
		Double result = 0D;
		Double taskRes = 0D;
		String opStr = null;
		boolean flag = false;
		try {
//			org
			d.get("https://tbot.xyz/math/#eyJ1Ijo2MDUyODgyOTYsIm4iOiJEYXZlbiAiLCJnIjoiTWF0aEJhdHRsZSIsImNpIjoiLTMzMzMyNDc1NDMzNjIzMzcwODUiLCJpIjoiQlFBQUFLVUJBQUJsUHEteFhJT2hCREQ5ZDBVIn1iMzg0ODhhMWVjOTUxMTE5MjM3NDA5YWEwNGI2ZTA3Mg==&tgShareScoreUrl=tg%3A%2F%2Fshare_game_score%3Fhash%3DENKd8fqcPFeNlDUN3P7GzxOZld17Zc4Ps8SoFtrZyghcI4zOBrehNdFc9ZbuUTRh");
//			沙雕根据地
//			d.get("https://tbot.xyz/math/#eyJ1Ijo2MDUyODgyOTYsIm4iOiJEYXZlbiAiLCJnIjoiTWF0aEJhdHRsZSIsImNpIjoiLTg5MzU3OTM1ODgyMDYyMzA4NTEiLCJpIjoiQlFBQUFHMzVFZ0JBeU82cVU1MXY2M0hOQW9JIn03MWQwY2Q1MDIxODc0MzZiMTRmYTJiMTZhNmVhNTg1ZQ==&tgShareScoreUrl=tg%3A%2F%2Fshare_game_score%3Fhash%3Dj372_ZogOAlgFmgGMKxWLCcRWBYxM5zK181cWlnvvD2TO2Ofd38ygqBz-uiCvhAu");
//			沙雕英雄
//			d.get("https://tbot.xyz/math/#eyJ1Ijo2MDUyODgyOTYsIm4iOiJEYXZlbiAiLCJnIjoiTWF0aEJhdHRsZSIsImNpIjoiLTc0MzM0ODMzMTIzMTY5NTk2MjMiLCJpIjoiQlFBQUFFQnJEZ0N5ME4yN1lSTTh4VHlMOUdVIn02YTU0YzVmMzQ3YTE5ZDNlNzBlMGUwOWRlOGRlNjU3ZA==&tgShareScoreUrl=tg%3A%2F%2Fshare_game_score%3Fhash%3D10knhs37PaQ3CBXcSBbHKMT_LueYnSwgtVY4f7s-eXqGt11XjoCC6mOAqzLhQMdi");
			WebElement startButton = d.findElement(By.id("button_correct"));
			startButton.click();

			int i = 0;
			while (i < 2980) {

				elementX = d.findElement(By.id("task_x"));
				elementOp = d.findElement(By.id("task_op"));
				elementY = d.findElement(By.id("task_y"));
				elementTaskRes = d.findElement(By.id("task_res"));

				x = Double.parseDouble(elementX.getText());
				y = Double.parseDouble(elementY.getText());
				taskRes = Double.parseDouble(elementTaskRes.getText());
				opStr = elementOp.getText();

				if ("+".equals(opStr)) {
					result = x + y;
				} else if ("–".equals(opStr)) {
					result = x - y;
				} else if ("×".equals(opStr)) {
					result = x * y;
				} else {
					result = x / y;
				}

				flag = result.equals(taskRes);

				System.out.println(x + opStr + y + " = " + result + ", taskRes = " + taskRes + " : " + flag);

				buttonCorrect = d.findElement(By.id("button_correct"));
				buttonWrong = d.findElement(By.id("button_wrong"));

				if (flag) {
					buttonCorrect.click();
				} else {
					buttonWrong.click();
				}

				i++;
				
			}
			
			Thread.sleep(30880L);
		} catch (Exception e) {
			e.printStackTrace();
		}


		d.quit();
	}

}
