package demo.clawing.lottery.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.lottery.mapper.LotterySixMapper;
import demo.clawing.lottery.mapper.LotterySixSoldDetailMapper;
import demo.clawing.lottery.pojo.po.LotterySix;
import demo.clawing.lottery.pojo.po.LotterySixSoldDetail;
import demo.clawing.lottery.pojo.type.ClawTaskType;
import demo.clawing.lottery.service.LotterySixService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import toolPack.dateTimeHandle.DateTimeUtilCommon;

@Service
public class LotterySixServiceImpl extends SeleniumCommonService implements LotterySixService {

	@Autowired
	private LotterySixMapper lotterySixMapper;
	@Autowired
	private LotterySixSoldDetailMapper lotterySixSoldDetailMapper;

	private String clawEventName = "lotterySixClaw";

	private TestModuleType testModuleType = TestModuleType.lottery;
	private ClawTaskType clawTaskType = ClawTaskType.lotterySix;
	
	private TestEvent buildTaskEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(clawTaskType.getId());
		bo.setEventName(clawTaskType.getEventName());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertTaskEvent() {
		TestEvent te = buildTaskEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT clawTask(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();

		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;

//		String screenshotPath = getScreenshotSaveingPath(clawEventName);
		String reportOutputFolderPath = getReportOutputPath(clawEventName);

		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		try {

			d = webDriverService.buildFireFoxWebDriver();

			try {
				d.get("http://www.cwl.gov.cn/kjxx/ssq/kjgg/");
				jsonReporter.appendContent(reportDTO, "get in time");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();

			x.start("div").addAttribute("class", "bgzt").findChild("table").findChild("tbody");
			String targetTbodyXpath = x.getXpath();
			
			int dayRange = 36;
			LocalDateTime startDate = findStartDate();
			LocalDateTime now = LocalDateTime.now();
			
			while(startDate.plusDays(dayRange).isBefore(now)) {
				setDataRange(d, startDate, startDate.plusDays(dayRange));
				collectPageData(d, targetTbodyXpath);
				startDate = startDate.plusDays(dayRange + 1);
			}
			setDataRange(d, startDate, now);
			collectPageData(d, targetTbodyXpath);

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
//			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
//			screenshotDTO.setDriver(d);
//			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath,
//					null);
//			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
//			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());

		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}

		return r;
	}

	private void setDataRange(WebDriver d, LocalDateTime startDate, LocalDateTime endDate) {
		XpathBuilderBO x = new XpathBuilderBO();
		// 点击自定义查询
		x.start("div").addAttribute("class", "aqcx").findChild("dl", 1).findChild("dd", 1).findChild("ul", 1)
				.findChild("li", 4);
		WebElement queryByCustom = d.findElement(By.xpath(x.getXpath()));
		queryByCustom.click();

		// 点击"按日期"
		x.start("div").addAttribute("class", "zdyTc").findChild("div").addAttribute("class", "zdyTcNr").findChild("div")
				.addAttribute("class", "sqB").findChild("div").addAttribute("class", "sqQ").findChild("strong", 3);
		WebElement queryQi = d.findElement(By.xpath(x.getXpath()));
		queryQi.click();

		// 输入初始日期
		x.start("input").addAttribute("class", "Wdate inpRa");
		WebElement qiStartInput = d.findElement(By.xpath(x.getXpath()));
		qiStartInput.click();
		qiStartInput.clear();
		qiStartInput.sendKeys(localDateTimeHandler.dateToStr(startDate, DateTimeUtilCommon.normalDateFormat));

		// 输入结束日期
		x.start("input").addAttribute("class", "Wdate inpRz");
		WebElement qiEndInput = d.findElement(By.xpath(x.getXpath()));
		qiEndInput.click();
		qiEndInput.clear();
		qiEndInput.sendKeys(localDateTimeHandler.dateToStr(endDate, DateTimeUtilCommon.normalDateFormat));

		// 点击查询
		x.start("button").addAttribute("class", "anKs aRzR");
		WebElement queryButton = d.findElement(By.xpath(x.getXpath()));
		queryButton.click();

	}

	private void collectPageData(WebDriver d, String targetTbodyXpath) {
		XpathBuilderBO x = new XpathBuilderBO();
		x.setXpath(targetTbodyXpath).findChild("tr");
		String targetTrListXpath = x.getXpath();
		List<WebElement> trList = d.findElements(By.xpath(targetTrListXpath));
		LotterySix tmpPO = null;
		LotterySixSoldDetail tmpPODetail = null;
		for(int i = 1; i <= trList.size(); i++) {
			x.setXpath(targetTbodyXpath).findChild("tr", i);
			try {
				d.findElement(By.xpath(x.getXpath()));
				tmpPO = buildLotterySixByTr(d, x.getXpath());
				tmpPODetail = buildLotterySixSoldDetailByTr(d, x.getXpath(), tmpPO.getId());
				
				lotterySixMapper.insertSelective(tmpPO);
				lotterySixSoldDetailMapper.insertSelective(tmpPODetail);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
	}
	
	private LocalDateTime findStartDate() {
		LotterySix po = lotterySixMapper.findLastRecord();
		if(po == null) {
			return LocalDateTime.of(2013, 1, 1, 0, 0);
		} else {
			return po.getRecordTime().plusDays(1);
		}
	}

	private LotterySix buildLotterySixByTr(WebDriver d, String trXpath) {
		XpathBuilderBO x = new XpathBuilderBO();
		LotterySix po = new LotterySix();
		po.setId(snowFlake.getNextId());

		WebElement recordTimeTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 2).getXpath()));
		String recordTimeStr = recordTimeTD.getText();
		recordTimeStr = recordTimeStr.replaceAll("[^0-9-_]", "");
		LocalDateTime recordTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(recordTimeStr);
		po.setRecordTime(recordTime);

		WebElement redBallTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 3).getXpath()));
		String redBallStr = redBallTD.getText().replaceAll("[^0-9]", "");
		po.setRed1(Integer.parseInt(redBallStr.substring(0, 2)));
		po.setRed2(Integer.parseInt(redBallStr.substring(2, 4)));
		po.setRed3(Integer.parseInt(redBallStr.substring(4, 6)));
		po.setRed4(Integer.parseInt(redBallStr.substring(6, 8)));
		po.setRed5(Integer.parseInt(redBallStr.substring(8, 10)));
		po.setRed6(Integer.parseInt(redBallStr.substring(10, 12)));

		WebElement blueBallTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 4).getXpath()));
		po.setBlue1(Integer.parseInt(blueBallTD.getText()));

		WebElement totalAmountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 5).getXpath()));
		String totalAmountStr = totalAmountTD.getText().replaceAll("[^0-9]", "");
		po.setSoldAmount(new BigDecimal(totalAmountStr));

		return po;
	}

	private LotterySixSoldDetail buildLotterySixSoldDetailByTr(WebDriver d, String trXpath, Long lotterySixPOId) {
		XpathBuilderBO x = new XpathBuilderBO();
		LotterySixSoldDetail po = new LotterySixSoldDetail();
		po.setId(lotterySixPOId);

		WebElement recordOrgIdTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 1).getXpath()));
		po.setRecordOrgId(recordOrgIdTD.getText());

		WebElement recordTimeTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 2).getXpath()));
		String recordTimeStr = recordTimeTD.getText();
		recordTimeStr = recordTimeStr.replaceAll("[^0-9-_]", "");
		LocalDateTime recordTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(recordTimeStr);
		po.setRecordTime(recordTime);

		WebElement totalAmountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 5).getXpath()));
		String totalAmountStr = totalAmountTD.getText();
		po.setSoldAmount(filterAmountFromDesc(totalAmountStr));

		WebElement prize1CountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 6).getXpath()));
		String prize1CountStr = prize1CountTD.getText().replaceAll("[^0-9]", "");
		po.setPrize1Count(Integer.parseInt(prize1CountStr));
		WebElement prize1AmountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 7).getXpath()));
		String prize1AmountStr = prize1AmountTD.getText();
		po.setPrize1Amount(filterAmountFromDesc(prize1AmountStr));

		WebElement prize2CountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 8).getXpath()));
		String prize2CountStr = prize2CountTD.getText().replaceAll("[^0-9]", "");
		po.setPrize2Count(Integer.parseInt(prize2CountStr));
		WebElement prize2AmountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 9).getXpath()));
		String prize2AmountStr = prize2AmountTD.getText();
		po.setPrize2Amount(filterAmountFromDesc(prize2AmountStr));

		WebElement prize3CountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 10).getXpath()));
		String prize3CountStr = prize3CountTD.getText().replaceAll("[^0-9]", "");
		po.setPrize3Count(Integer.parseInt(prize3CountStr));
		WebElement prize3AmountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 11).getXpath()));
		String prize3AmountStr = prize3AmountTD.getText();
		po.setPrize3Amount(filterAmountFromDesc(prize3AmountStr));

		WebElement prizePoolAmountTD = d.findElement(By.xpath(x.setXpath(trXpath).findChild("td", 12).getXpath()));
		String prizePoolAmountStr = prizePoolAmountTD.getText().replaceAll("[^0-9]", "");
		po.setPrizePoolAmount(new BigDecimal(prizePoolAmountStr));

		return po;
	}
	
	private BigDecimal filterAmountFromDesc(String desc) {
		String[] s = desc.split("[^0-9]");
		for(String i : s) {
			if(numericUtil.matchInteger(i)) {
				return new BigDecimal(i);
			}
		}
		
		return null;
	}
}
