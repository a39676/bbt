package demo.clawing.scheduleClawing.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.pojo.result.CatchMetalPriceResult;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.PreciousMetalsPriceService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import metal.pojo.dto.PreciousMetailPriceDTO;
import metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;

@Service
public class PreciousMetalsPriceServiceImpl extends SeleniumCommonService implements PreciousMetalsPriceService{

	private String clawingEventName = "preciousMetalPriceClawing";
	
	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.preciousMetalPrice;
	
	private String mainUrl = "https://goldprice.org/gold-price-data.html";

	private TestEvent buildDailySignEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(testCastType.getId());
		bo.setEventName(testCastType.getEventName());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertClawingEvent() {
		TestEvent te = buildDailySignEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT clawing(TestEvent te) {
//		TODO
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;
		
		String reportOutputFolderPath = getReportOutputPath(clawingEventName);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			d = webDriverService.buildFireFoxWebDriver();
			
			/*
			 * TODO
			 * 未 catch 加载超时
			 * 未 检验页面加载完成
			 * 
			 */
			d.get(mainUrl);
			
			threadSleepRandomTime(1000L, 3000L);
			
			CatchMetalPriceResult goldPriceResult = catchGoldPrice(d, reportDTO);
			CatchMetalPriceResult silverPriceResult = catchSilverPrice(d, reportDTO);
			
			/*
			 * TODO
			 * 如何传递至 cx 服务
			 */
			
			sendPreciousMetalPrice(goldPriceResult);
			sendPreciousMetalPrice(silverPriceResult);
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			jsonReporter.appendContent(reportDTO, "异常: " + e.toString());
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}
		
		return r;
	}
	
	
	private CatchMetalPriceResult catchGoldPrice(WebDriver d, JsonReportDTO reportDTO) {
		CatchMetalPriceResult result = new CatchMetalPriceResult();
		
		PreciousMetailPriceDTO priceDTO = new PreciousMetailPriceDTO();
		
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			
			
			String metalSelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-metal")
					.addId("gpxtickerLeft_met")
					.getXpath();
			
			String currencySelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-currency")
					.addId("gpxtickerLeft_curr")
					.getXpath();
			
			String weightSelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-weight")
					.addId("gpxtickerLeft_wgt-au")
					.getXpath();
			

			try {
				Select metelSelector = new Select(d.findElement(By.xpath(metalSelectorXpath)));
				metelSelector.selectByVisibleText("Gold");
				
				Select currencySelector = new Select(d.findElement(By.xpath(currencySelectorXpath)));
				currencySelector.selectByValue("CNY");
				
				Select weightSelector = new Select(d.findElement(By.xpath(weightSelectorXpath)));
				weightSelector.selectByValue("kg");
				
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "查找黄金价格选择器异常");
				return result;
			}
			
			try {
				x.start("span").addId("gpxtickerLeft_price");
				WebElement goldPriceSpan = d.findElement(By.xpath(x.getXpath()));
				
				String priceStr = goldPriceSpan.getText();
				Double price = Double.parseDouble(priceStr);
				
				priceDTO.setPrice(price);
				priceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());
				priceDTO.setMetalType(MetalType.gold.getCode());
				
				result.setPriceDTO(priceDTO);
				result.setIsSuccess();
				
				return result;
				
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "查找黄金价格异常");
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	
	private CatchMetalPriceResult catchSilverPrice(WebDriver d, JsonReportDTO reportDTO) {
		CatchMetalPriceResult result = new CatchMetalPriceResult();
		
		PreciousMetailPriceDTO priceDTO = new PreciousMetailPriceDTO();
		
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			
			
			String metalSelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-metal")
					.addId("gpxtickerMiddle_met")
					.getXpath();
			
			String currencySelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-currency")
					.addId("gpxtickerMiddle_curr")
					.getXpath();
			
			String weightSelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-weight")
					.addId("gpxtickerMiddle_wgt-ag")
					.getXpath();
			

			try {
				Select metelSelector = new Select(d.findElement(By.xpath(metalSelectorXpath)));
				metelSelector.selectByVisibleText("Silver");
				
				Select currencySelector = new Select(d.findElement(By.xpath(currencySelectorXpath)));
				currencySelector.selectByValue("CNY");
				
				Select weightSelector = new Select(d.findElement(By.xpath(weightSelectorXpath)));
				weightSelector.selectByValue("kg");
				
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "查找白银价格选择器异常");
				return result;
			}
			
			try {
				x.start("span").addId("gpxtickerLeft_price");
				WebElement goldPriceSpan = d.findElement(By.xpath(x.getXpath()));
				
				String priceStr = goldPriceSpan.getText();
				Double price = Double.parseDouble(priceStr);
				
				priceDTO.setPrice(price);
				priceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());
				priceDTO.setMetalType(MetalType.silver.getCode());
				
				result.setPriceDTO(priceDTO);
				result.setIsSuccess();
				
				return result;
				
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "查找白银价格异常");
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	private void sendPreciousMetalPrice(CatchMetalPriceResult result) {
		/*
		 * TODO
		 * 多个同时发送?逐个?
		 */
	}
}
