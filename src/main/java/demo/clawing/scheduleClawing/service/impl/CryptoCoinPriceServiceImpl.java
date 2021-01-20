package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.mq.sender.CroptoCoinTransmissionAckProducer;
import demo.clawing.scheduleClawing.pojo.bo.CryptoCoinHistoryPriceParamBO;
import demo.clawing.scheduleClawing.pojo.constant.CryptoCoinScheduleClawingConstant;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.CryptoCoinPriceService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceSubDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinPriceServiceImpl extends SeleniumCommonService implements CryptoCoinPriceService {

	@Autowired
	private CroptoCoinTransmissionAckProducer croptoCoinTransmissionAckProducer;

	private String cryptoCoinHistoryPriceCollect = "cryptoCoinHistoryPriceCollect";
	private String historyCryptoCoinPriceCollectingParam = "historyCryptoCoinPriceCollectingParam.json";

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType cryptoCoinHistoryPrice = ScheduleClawingType.cryptoCoinHistoryPrice;

	
	private TestEvent buildHistoryPriceCollectingEvent() {
		String paramterFolderPath = getParameterSaveingPath(cryptoCoinHistoryPriceCollect);
		File paramterFile = new File(paramterFolderPath + File.separator + historyCryptoCoinPriceCollectingParam);
		if (!paramterFile.exists()) {
//			TODO
			return null;
		}
		
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(cryptoCoinHistoryPrice.getId());
		bo.setEventName(cryptoCoinHistoryPrice.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertHistoryCryptoCoinPriceEvent() {
//		FIXME after 2021/01/01
		LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0);
		if(LocalDateTime.now().isBefore(startDate)) {
			return null;
		}
		TestEvent te = buildHistoryPriceCollectingEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT cryptoCoinHistoryPriceAPI(TestEvent te) {
		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(cryptoCoinHistoryPriceCollect);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		CommonResultBBT r = cryptoCoinHistoryPriceAPI(te, reportDTO);

		return r;
	}

	private CommonResultBBT cryptoCoinHistoryPriceAPI(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT r = new CommonResultBBT();
		
		// example: https://min-api.cryptocompare.com/data/v2/histominute?fsym=ETH&tsym=USD&limit=10
		String cryptoCoinApiUrlModel = "https://min-api.cryptocompare.com/data/v2/histominute?fsym=%s&tsym=%s&limit=%s&api_key=%s";
		HttpUtil h = new HttpUtil();

		try {
			
			String runcountingStr = constantService.getValByName(CryptoCoinScheduleClawingConstant.RUN_COUNT_KEY);
			Integer runcounting = 1;
			try {
				runcounting = Integer.parseInt(runcountingStr);
			} catch (Exception e) {
				constantService.setValByName(CryptoCoinScheduleClawingConstant.RUN_COUNT_KEY, "1");
			}
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			CryptoCoinHistoryPriceParamBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(jsonStr, CryptoCoinHistoryPriceParamBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingOptionBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingOptionBO.getLimit() == null || StringUtils.isBlank(clawingOptionBO.getApiKey())) {
				jsonReporter.appendContent(reportDTO, "参数文件参数异常");
				throw new Exception();
			}
			
			
			String httpResponse = null;
			CryptoCoinHistoryPriceDTO mainDTO = null;
			List<CryptoCoinHistoryPriceSubDTO> subDataList = null;

			if(runcounting - 1 >= clawingOptionBO.getCoinType().size()) {
				runcounting = 1;
			}
			String coinTypeName = clawingOptionBO.getCoinType().get(runcounting - 1);
			
			for(String currencyName : clawingOptionBO.getCurrency()) {
				mainDTO = new CryptoCoinHistoryPriceDTO();
				
				httpResponse = h.sendGet(String.format(cryptoCoinApiUrlModel, coinTypeName, currencyName, clawingOptionBO.getLimit(), clawingOptionBO.getApiKey()));
				jsonReporter.appendContent(reportDTO, httpResponse);
				
				subDataList = handleCryptoCoinHistoryResponse(httpResponse, coinTypeName, currencyName);
				mainDTO.setPriceHistoryData(subDataList);
				mainDTO.setCryptoCoinTypeName(coinTypeName);
				mainDTO.setCurrencyName(currencyName);
				
				croptoCoinTransmissionAckProducer.sendHistoryPrice(mainDTO);
			}
			
			runcounting = runcounting + 1;
			constantService.setValByName(CryptoCoinScheduleClawingConstant.RUN_COUNT_KEY, runcounting.toString());
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
	
	private List<CryptoCoinHistoryPriceSubDTO> handleCryptoCoinHistoryResponse(String response, String coinName, String currencyName) {
		List<CryptoCoinHistoryPriceSubDTO> list = new ArrayList<>();
		JSONObject json = JSONObject.fromObject(response);
		
		if(!"Success".equals(json.getString("Response"))) {
			return list;
		}
		
		JSONArray dataArray = json.getJSONObject("Data").getJSONArray("Data");
		JSONObject subJson = null;
		CryptoCoinHistoryPriceSubDTO priceDataDTO = null;
		
		Date tmpDate = null;
		for(int i = 0; i < dataArray.size(); i++) {
			subJson = (JSONObject) dataArray.get(i);
			priceDataDTO = new CryptoCoinHistoryPriceSubDTO();
			priceDataDTO.setCoinType(coinName);
			priceDataDTO.setCurrencyType(currencyName);
			priceDataDTO.setStart(subJson.getDouble("open"));
			priceDataDTO.setEnd(subJson.getDouble("close"));
			priceDataDTO.setHigh(subJson.getDouble("high"));
			priceDataDTO.setLow(subJson.getDouble("low"));
			priceDataDTO.setVolume(subJson.getDouble("volumeto"));
			tmpDate = new Date(subJson.getLong("time") * 1000L);
			priceDataDTO.setTime(localDateTimeHandler.dateToStr(localDateTimeHandler.dateToLocalDateTime(tmpDate)));
			list.add(priceDataDTO);
		}
		
		return list;
	}
}
