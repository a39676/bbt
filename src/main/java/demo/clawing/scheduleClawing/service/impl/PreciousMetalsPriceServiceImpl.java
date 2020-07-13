package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.PreciousMetalsPriceService;
import demo.interaction.preicous_metal.service.PreciousMetalTransService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import net.sf.json.JSONObject;
import precious_metal.pojo.constant.PreciousMetalConstant;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;
import precious_metal.pojo.dto.TransPreciousMetalPriceDTO;
import precious_metal.pojo.result.CatchMetalPriceResult;
import precious_metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;
import toolPack.httpHandel.HttpUtil;

@Service
public class PreciousMetalsPriceServiceImpl extends SeleniumCommonService implements PreciousMetalsPriceService {

	@Autowired
	private PreciousMetalTransService preciousMetalTransService;

	private String testEventName = "preciousMetalPriceClawing";

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.preciousMetalPrice;

	private String apiUrl = "https://goldprice.org/gold-price-data.html";

	private TestEvent buildTestEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(testCastType.getId());
		bo.setEventName(testCastType.getEventName());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertClawingEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT goldPriceOrgAPI(TestEvent te) {

		LocalDateTime now = LocalDateTime.now();
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(testEventName);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		/*
		 * response json eg:
		 * {"ts":1594346507885,"tsj":1594346506249,"date":"Jul 9th 2020, 10:01:46 pm NY"
		 * ,"items":[{"curr":"CNY","xauPrice":12625.2846,"xagPrice":130.7432,"chgXau":42
		 * .4616,"chgXag":0.7666,"pcXau":0.3375,"pcXag":0.5898,"xauClose":12582.82303,
		 * "xagClose":129.97661},{"curr":"USD","xauPrice":1803.4575,"xagPrice":18.676,
		 * "chgXau":0.0875,"chgXag":0.0295,"pcXau":0.0049,"pcXag":0.1582,"xauClose":1803
		 * .37,"xagClose":18.6465}]}
		 * 
		 */
		String url = "https://data-asg.goldprice.org/dbXRates/USD,CNY";
		HttpUtil h = new HttpUtil();
		
		
		try {
			String result = h.sendGet(url);
			System.out.println(result);
			JSONObject json = JSONObject.fromObject(result);
			JSONObject cnyPriceJson = json.getJSONArray("items").getJSONObject(0);
			String auPriceStr = cnyPriceJson.getString("xauPrice");
			String agPriceStr = cnyPriceJson.getString("xagPrice");

			double auOZPrice = Double.parseDouble(auPriceStr);
			double auKgPrice = auOZPrice / PreciousMetalConstant.goleOunceToGram.doubleValue() * 1000;

			double agOZPrice = Double.parseDouble(agPriceStr);
			double agKgPrice = agOZPrice / PreciousMetalConstant.goleOunceToGram.doubleValue() * 1000;
			
			

			/*
			 * 定时任务每30秒执行一次, 每10分钟保存1~2次数值
			 */
			if((now.getMinute() % 10 == 0)) {
				PreciousMetailPriceDTO goldPriceDTO = new PreciousMetailPriceDTO();
				goldPriceDTO.setPrice(auKgPrice);
				goldPriceDTO.setMetalType(MetalType.gold.getCode());
				goldPriceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());
				
				PreciousMetailPriceDTO silverPriceDTO = new PreciousMetailPriceDTO();
				silverPriceDTO.setPrice(agKgPrice);
				silverPriceDTO.setMetalType(MetalType.silver.getCode());
				silverPriceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());
				
				sendPreciousMetalPriceByDTO(Arrays.asList(goldPriceDTO, silverPriceDTO));
			}
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonReporter.appendContent(reportDTO, "异常: " + e);

		} finally {
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}

		return r;

	}

	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();

		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;

		String reportOutputFolderPath = getReportOutputPath(testEventName);

		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		try {

			d = webDriverService.buildFireFoxWebDriver();

			try {
				d.get(apiUrl);
				jsonReporter.appendContent(reportDTO, "进入 main url");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "进入 main url but timeout");
			}

			threadSleepRandomTime(1000L, 3000L);

			CatchMetalPriceResult goldPriceResult = catchGoldPrice(d, reportDTO);
			CatchMetalPriceResult silverPriceResult = catchSilverPrice(d, reportDTO);

			sendPreciousMetalPrice(Arrays.asList(goldPriceResult, silverPriceResult));

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

			String metalSelectorXpath = x.start("select").addAttribute("class", "form-control input-sm gpxticker-metal")
					.addId("gpxtickerLeft_met").getXpath();

			String currencySelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-currency").addId("gpxtickerLeft_curr")
					.getXpath();

			String weightSelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-weight").addId("gpxtickerLeft_wgt-au")
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
				Double price = Double.parseDouble(priceStr.replaceAll("[^\\d\\+\\-\\.]", ""));

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

			String metalSelectorXpath = x.start("select").addAttribute("class", "form-control input-sm gpxticker-metal")
					.addId("gpxtickerMiddle_met").getXpath();

			String currencySelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-currency").addId("gpxtickerMiddle_curr")
					.getXpath();

			String weightSelectorXpath = x.start("select")
					.addAttribute("class", "form-control input-sm gpxticker-weight").addId("gpxtickerMiddle_wgt-ag")
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
				x.start("span").addId("gpxtickerMiddle_price");
				WebElement goldPriceSpan = d.findElement(By.xpath(x.getXpath()));

				String priceStr = goldPriceSpan.getText();
				Double price = Double.parseDouble(priceStr.replaceAll("[^\\d\\+\\-\\.]", ""));

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

	private void sendPreciousMetalPrice(List<CatchMetalPriceResult> resultList) {
		List<PreciousMetailPriceDTO> priceList = new ArrayList<>();
		for (CatchMetalPriceResult result : resultList) {
			if (result.isSuccess()) {
				priceList.add(result.getPriceDTO());
			}
		}
		if (priceList.isEmpty()) {
			return;
		}
		sendPreciousMetalPriceByDTO(priceList);
	}

	private void sendPreciousMetalPriceByDTO(List<PreciousMetailPriceDTO> priceList) {
		if (priceList.isEmpty()) {
			return;
		}
		TransPreciousMetalPriceDTO dto = new TransPreciousMetalPriceDTO();
		dto.setPriceList(priceList);
		preciousMetalTransService.transPreciousMetalPriceToCX(dto);
	}
}
