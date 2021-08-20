package demo.scriptCore.scheduleClawing.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.scheduleClawing.mq.sender.CryptoCoinDailyDataAckProducer;
import demo.scriptCore.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;
import demo.scriptCore.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.scriptCore.scheduleClawing.service.CryptoCoinPriceService;
import demo.scriptCore.scheduleClawing.service.CryptoCompareService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import net.sf.json.JSONObject;

@Service
public class CryptoCoinPriceServiceImpl extends SeleniumCommonService implements CryptoCoinPriceService {

	@Autowired
	private CryptoCoinDailyDataAckProducer cryptoCoinDailyDataAckProducer;
	@Autowired
	private CryptoCompareService cryptoCompareService;

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;

	private String cryptoCoinDailyDataCollect = "cryptoCoinDailyDataCollect";
	private String cryptoCoinDailyDataCollectParam = "cryptoCoinDailyDataCollectParam.json";

	private ScheduleClawingType cryptoCoinDailyData = ScheduleClawingType.CRYPTO_COIN_DAILY_DATA;

	private TestEvent buildCryptoCoinDailyDataCollectingEvent() {
		String paramterFolderPath = getParameterSaveingPath(cryptoCoinDailyDataCollect);
		File paramterFile = new File(paramterFolderPath + File.separator + cryptoCoinDailyDataCollectParam);
		if (!paramterFile.exists()) {
//			TODO test event if paramterFile NOT exists
			return null;
		}

		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setEventId(cryptoCoinDailyData.getId());
		bo.setFlowName(cryptoCoinDailyData.getFlowName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}
	

	@Override
	public InsertTestEventResult insertCryptoCoinDailyDataCollectEvent(CryptoCoinDailyDataQueryDTO dto) {
		if(dto == null || StringUtils.isBlank(dto.getCoinName()) || dto.getCounting() <= 0) {
			return new InsertTestEventResult();
		}
		
		TestEvent te = buildCryptoCoinDailyDataCollectingEvent();
		if(StringUtils.isBlank(dto.getCurrencyName())) {
			dto.setCurrencyName(CurrencyType.USD.getName());
		}
		return testEventService.insertTestEvent(te, JSONObject.fromObject(dto));
	}

	@Override
	public CommonResult cryptoCoinDailyDataAPI(TestEvent te) {
		// TODO 正在整理  分 crypto compare / binance api 
		
		/*
		 * 2021-08-13
		 * 暂时无精力再修缮
		 * 能用就算的部分代码
		 * 请勿以此为模板
		 */
		
		TestEventBO tbo = auxTool.beforeRunning(te);
		
		CryptoCoinDailyDataQueryDTO queryDTO = null;
		
		try {
			JSONObject paramJson = tryFindParam(te.getId());
			queryDTO = new Gson().fromJson(paramJson.toString(), CryptoCoinDailyDataQueryDTO.class);
		} catch (Exception e) {
		}
		
		CryptoCoinDailyDataResult apiResult = null;
		
		if(queryDTO == null || queryDTO.getDataSourceCode() == null || CryptoCoinDataSourceType.CRYPTO_COMPARE.getCode().equals(queryDTO.getDataSourceCode())) {
			apiResult = cryptoCompareService.cryptoCoinDailyDataAPI(te, tbo.getReport());
		} else if(CryptoCoinDataSourceType.BINANCE.getCode().equals(queryDTO.getDataSourceCode())){
//			TODO need binance daily data API
		}
		
		CommonResult r = new CommonResult();
		
		if(apiResult.isSuccess()) {
			cryptoCoinDailyDataAckProducer.sendHistoryPrice(apiResult.getData());
			redisConnectService.deleteValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + te.getId());
		} else {
			/*
			 * TODO what to do when crypto coin daily data query fail
			 */
		}
		
		r.setSuccess(apiResult.isSuccess());
		r.setMessage(apiResult.getMessage());
		
		return r;
	}

}
