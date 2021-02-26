package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

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
import demo.clawing.scheduleClawing.mq.sender.MetalPriceTransmissionAckProducer;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.PreciousMetalsPriceService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.precious_metal.pojo.constant.PreciousMetalConstant;
import finance.precious_metal.pojo.dto.PreciousMetailPriceDTO;
import finance.precious_metal.pojo.result.CatchMetalPriceResult;
import finance.precious_metal.pojo.type.MetalType;
import net.sf.json.JSONObject;
import tool.pojo.type.UtilOfWeightType;
import toolPack.httpHandel.HttpUtil;

@Service
public class PreciousMetalsPriceServiceImpl extends SeleniumCommonService implements PreciousMetalsPriceService {

	@Autowired
	private MetalPriceTransmissionAckProducer metalPriceTransmissionAckProducer;

	private String testEventName = "preciousMetalPriceClawing";

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.PRECIOUS_METAL_PRICE;

	private String goldPricePageUrl = "https://goldprice.org/gold-price-data.html";

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

		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(testEventName);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		CommonResultBBT r = goldPriceApi(te, reportDTO);
		
		long minMS = 1;
		long maxMS = 11;
		Long l = ThreadLocalRandom.current().nextLong(minMS, maxMS);
		
		if(l > 5) {
			r = goldPriceApi(te, reportDTO);
		} else {
			r = nfusionsolutionApi(te, reportDTO);
		}

		return r;
	}

	private CommonResultBBT goldPriceApi(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT r = new CommonResultBBT();

		/*
		 * json.date 只是通讯时的时间 response json example:
		 * {"ts":1594346507885,"tsj":1594346506249,"date":"Jul 9th 2020, 10:01:46 pm NY"
		 * ,"items":[{"curr":"CNY","xauPrice":12625.2846,"xagPrice":130.7432,"chgXau":42
		 * .4616,"chgXag":0.7666,"pcXau":0.3375,"pcXag":0.5898,"xauClose":12582.82303,
		 * "xagClose":129.97661},{"curr":"USD","xauPrice":1803.4575,"xagPrice":18.676,
		 * "chgXau":0.0875,"chgXag":0.0295,"pcXau":0.0049,"pcXag":0.1582,"xauClose":1803
		 * .37,"xagClose":18.6465}]}
		 * 
		 */
		String goldPriceApiUrl = "https://data-asg.goldprice.org/dbXRates/USD,CNY";
		HttpUtil h = new HttpUtil();

		try {
			String result = h.sendGet(goldPriceApiUrl);
			JSONObject json = JSONObject.fromObject(result);
			JSONObject cnyPriceJson = json.getJSONArray("items").getJSONObject(0);
			String auPriceStr = cnyPriceJson.getString("xauPrice");
			String agPriceStr = cnyPriceJson.getString("xagPrice");

			double auOZPrice = Double.parseDouble(auPriceStr);
			double auKgPrice = auOZPrice / PreciousMetalConstant.goleOunceToGram.doubleValue() * 1000;

			double agOZPrice = Double.parseDouble(agPriceStr);
			double agKgPrice = agOZPrice / PreciousMetalConstant.goleOunceToGram.doubleValue() * 1000;

			String dateStr = json.getString("date");
			LocalDateTime date = strToLocalDateTime(dateStr);

			PreciousMetailPriceDTO goldPriceDTO = new PreciousMetailPriceDTO();
			goldPriceDTO.setPrice(auKgPrice);
			goldPriceDTO.setMetalType(MetalType.gold.getCode());
			goldPriceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());

			PreciousMetailPriceDTO silverPriceDTO = new PreciousMetailPriceDTO();
			silverPriceDTO.setPrice(agKgPrice);
			silverPriceDTO.setMetalType(MetalType.silver.getCode());
			silverPriceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());

			if (date != null) {
				String transDateStr = localDateTimeHandler.dateToStr(date);
				goldPriceDTO.setTransactionDateTime(transDateStr);
				silverPriceDTO.setTransactionDateTime(transDateStr);
			}

			transPreciousMetalPriceToCX(goldPriceDTO);
			transPreciousMetalPriceToCX(silverPriceDTO);

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			jsonReporter.appendContent(reportDTO, "异常(goldPriceApi): " + e);

		} finally {
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}

		return r;
	}

	private CommonResultBBT nfusionsolutionApi(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT goldResult = nfusionsolutionApi(te, reportDTO, MetalType.gold);
		CommonResultBBT silverResult = nfusionsolutionApi(te, reportDTO, MetalType.silver);
		
		CommonResultBBT r = new CommonResultBBT();
		if(goldResult.isFail()) {
			r.addMessage(goldResult.getMessage());
		}
		if(silverResult.isFail()) {
			r.addMessage(silverResult.getMessage());
		}
		
		if(goldResult.isSuccess() && silverResult.isSuccess()) {
			r.setIsSuccess();
		}
		
		return r;
	}
	private CommonResultBBT nfusionsolutionApi(TestEvent te, JsonReportDTO reportDTO, MetalType metalType) {
		CommonResultBBT r = new CommonResultBBT();

		/*
		 * json example: { "metal": "Silver", "currency": "CNY",
		 * "currencySize": "medium", "price": 193.55, "change": 5.7, "changePercent":
		 * 3.04903, "timestamp": "Aug 06, 2020 at 5:56 am EDT", "currencySymbol":
		 * "&#xa5;", "showCurrencySymbol": false, "intradayOffset": -4 }
		 * 
		 */
		String nfusionsolutionsApiSilverUrl = "https://redist.nfusionsolutions.biz/client/jmbullion/Ajax/SmallHistorical?metal=" + metalType.getName() + "&currency=cny";
		HttpUtil h = new HttpUtil();

		try {
			String result = h.sendGet(nfusionsolutionsApiSilverUrl);
			
			JSONObject json = JSONObject.fromObject(result);
			
			String silverPriceStr = json.getString("price");

			double ozPrice = Double.parseDouble(silverPriceStr);
			double kgPrice = ozPrice / PreciousMetalConstant.goleOunceToGram.doubleValue() * 1000;

			/* 
			 * TODO
			 * dateStr example: Aug 06, 2020 at 5:56 am EDT */
			String dateStr = json.getString("timestamp");
			LocalDateTime date = strToLocalDateTime(dateStr);

			PreciousMetailPriceDTO priceDTO = new PreciousMetailPriceDTO();
			priceDTO.setPrice(kgPrice);
			priceDTO.setMetalType(metalType.getCode());
			priceDTO.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());


			if (date != null) {
				String transDateStr = localDateTimeHandler.dateToStr(date);
				priceDTO.setTransactionDateTime(transDateStr);
			}

			transPreciousMetalPriceToCX(priceDTO);

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			jsonReporter.appendContent(reportDTO, "异常(nfusionsolutionApi): " + e);

		} finally {
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}

		return r;
	}

	/**
	 * 
	 * 为了将参数中的特殊格式时间转换为 LocalDateTime
	 * 
	 * @param dateStr example:Jul 9th 2020, 10:01:46 pm NY
	 * @return
	 */
	private LocalDateTime strToLocalDateTime(String dateStr) {
		dateStr = dateStr.replaceAll("(st|nd|rd|th)", "");
		dateStr = dateStr.replaceAll(" NY", "");

		boolean pmFlag = dateStr.toLowerCase().contains("pm");
		dateStr = dateStr.replaceAll("( am| pm)", "");

		DateTimeFormatter dateFormat = null;
		if (dateStr.matches("^[a-zA-Z]{3}\\s\\d{1}\\s\\d{4}\\,\\s\\d{1,2}:\\d{2}:\\d{2}$")) {
			dateFormat = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm:ss", Locale.US);
		} else {
			dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss", Locale.US);
		}

		LocalDateTime date = null;
		try {
			date = LocalDateTime.parse(dateStr, dateFormat);
		} catch (Exception e) {
			return null;
		}

		if (pmFlag) {
			date = date.plusHours(12);
		}

		return date;
	}

	@Override
	public CommonResultBBT clawing(TestEvent te) {
		/*
		 * 页面不稳定, 亦不适合高强度访问, 搁置;
		 */
		CommonResultBBT r = new CommonResultBBT();

		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;

		String reportOutputFolderPath = getReportOutputPath(testEventName);

		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		try {

			d = webDriverService.buildFireFoxWebDriver();

			try {
				d.get(goldPricePageUrl);
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
		for (CatchMetalPriceResult result : resultList) {
			if (result.isSuccess()) {
				transPreciousMetalPriceToCX(result.getPriceDTO());
			}
		}
	}

	private void transPreciousMetalPriceToCX(PreciousMetailPriceDTO dto) {
		metalPriceTransmissionAckProducer.send(dto);
	}
}
