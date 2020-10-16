package demo.clawing.scheduleClawing.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.mq.sender.CroptoCoinTransmissionAckProducer;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.CryptoCoinPriceService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinPriceDTO;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinPriceServiceImpl extends SeleniumCommonService implements CryptoCoinPriceService {

	@Autowired
	private CroptoCoinTransmissionAckProducer croptoCoinTransmissionAckProducer;

	private String testEventName = "cryptoCoinPriceCollection";

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.cryptoCoinPrice;

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
	public CommonResultBBT cryptoCoinAPI(TestEvent te) {

		JsonReportDTO reportDTO = new JsonReportDTO();
		String reportOutputFolderPath = getReportOutputPath(testEventName);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		CommonResultBBT r = cryptoCoinApi(te, reportDTO);
		
		r = cryptoCoinApi(te, reportDTO);

		return r;
	}

	private CommonResultBBT cryptoCoinApi(TestEvent te, JsonReportDTO reportDTO) {
		CommonResultBBT r = new CommonResultBBT();

		String cryptoCoinApiUrl = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,CNY";
		HttpUtil h = new HttpUtil();

		try {
			String httpResponse = h.sendGet(cryptoCoinApiUrl);
			JSONObject json = JSONObject.fromObject(httpResponse);

			jsonReporter.appendContent(reportDTO, json.toString());
			
			CryptoCoinPriceDTO cryptoCoinPriceDTO = new CryptoCoinPriceDTO();
			cryptoCoinPriceDTO.setCurrency(CurrencyType.USD.getName());
			cryptoCoinPriceDTO.setPrice(json.getDouble(CurrencyType.USD.getName()));
			
			transPreciousMetalPriceToCX(cryptoCoinPriceDTO);

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

	private void transPreciousMetalPriceToCX(CryptoCoinPriceDTO dto) {
		croptoCoinTransmissionAckProducer.send(dto);
	}
}
