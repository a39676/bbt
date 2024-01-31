package demo.scriptCore.scheduleClawing.cnStockMarketData.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.config.costomComponent.BbtDynamicKey;
import demo.scriptCore.scheduleClawing.cnStockMarketData.pojo.dto.SinaStockMarketDataDTO;
import demo.scriptCore.scheduleClawing.cnStockMarketData.service.CnStockMarketDataService;
import demo.selenium.service.impl.AutomationTestCommonService;
import finance.cnStockMarket.pojo.bo.CnStockMarketDataBO;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tool.pojo.constant.BbtInteractionUrl;
import toolPack.httpHandel.HttpUtil;

@Service
public class CnStockMarketDataServiceImpl extends AutomationTestCommonService implements CnStockMarketDataService {

	@Autowired
	private CnStockMarkctOptionComponent cnStockMarkctOptionComponent;
	@Autowired
	private HttpUtil http;
	@Autowired
	private BbtDynamicKey bbtDynamicKey;

	private static final int END_OF_HOUR = 15;

	@Override
	public void collectDatasAndSend() {
		LocalTime now = LocalTime.now();
		if (now.getHour() > END_OF_HOUR || (now.getHour() == END_OF_HOUR && now.getMinute() > 0)) {
			return;
		}

		collectDatasOf5MinAndSend();
		collectDatasOf1MinAndSend();
	}

	private void collectDatasOf5MinAndSend() {
		cnStockMarkctOptionComponent = getOption();
		CnStockMarketDataDTO result = null;

		for (int watchCodeIndex = 0; watchCodeIndex < cnStockMarkctOptionComponent.getWatchingCode()
				.size(); watchCodeIndex++) {
			result = new CnStockMarketDataDTO();
			result.setTimeUtilOfDataFormat(TimeUnitType.MINUTE);
			result.setTimeCountingOfDataFormat(5);
			String stockCode = cnStockMarkctOptionComponent.getWatchingCode().get(watchCodeIndex);
			result.setStockCode(stockCode);
			List<CnStockMarketDataBO> dataList = sinaApiOf5Min(stockCode);
			result.setData(dataList);
			result.setKey(bbtDynamicKey.createKey());

			sendDataToCthulhu(result);
		}
	}

	private void collectDatasOf1MinAndSend() {
		cnStockMarkctOptionComponent = getOption();
		CnStockMarketDataDTO result = null;

		for (int watchCodeIndex = 0; watchCodeIndex < cnStockMarkctOptionComponent.getWatchingCode()
				.size(); watchCodeIndex++) {
			result = new CnStockMarketDataDTO();
			result.setTimeUtilOfDataFormat(TimeUnitType.MINUTE);
			result.setTimeCountingOfDataFormat(1);
			String stockCode = cnStockMarkctOptionComponent.getWatchingCode().get(watchCodeIndex);
			result.setStockCode(stockCode);
			List<CnStockMarketDataBO> dataList = tencentApiOfMin(stockCode);
			result.setData(dataList);
			result.setKey(bbtDynamicKey.createKey());

			sendDataToCthulhu(result);
		}
	}

	private List<CnStockMarketDataBO> sinaApiOf5Min(String stockCode) {
		List<CnStockMarketDataBO> result = new ArrayList<>();

//		http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=sh000001&scale=5&ma=5&datalen=10
		String urlModel = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=%s&scale=5&ma=5&datalen=%d";
		String url = null;
		Integer dataLen = 60 / 5 * 4 * 10; // data of 10 days
		String response = null;
		SinaStockMarketDataDTO dto = null;
		CnStockMarketDataBO dataBO = null;

		url = String.format(urlModel, stockCode, dataLen);
		try {
			response = http.sendGet(url);
			JSONArray jsonArray = JSONArray.fromObject(response);
			for (int dataIndex = 0; dataIndex < jsonArray.size(); dataIndex++) {
				dto = jsonToDto(jsonArray.getJSONObject(dataIndex));
				dataBO = sinaDataToCommonDataBO(dto);
				dataBO.setStockCode(stockCode);
				result.add(dataBO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private List<CnStockMarketDataBO> tencentApiOfMin(String stockCode) {
		List<CnStockMarketDataBO> result = new ArrayList<>();

//		https://web.ifzq.gtimg.cn/appstock/app/minute/query?code=sh000001
		String urlModel = "https://web.ifzq.gtimg.cn/appstock/app/minute/query?code=%s";
		String url = null;
		String response = null;
		CnStockMarketDataBO dataBO = null;

		url = String.format(urlModel, stockCode);
		try {
			response = http.sendGet(url);
			JSONObject json = JSONObject.fromObject(response);
			JSONArray dataArray = json.getJSONObject("data").getJSONObject(stockCode).getJSONObject("data")
					.getJSONArray("data");

			for (int dataIndex = 0; dataIndex < dataArray.size(); dataIndex++) {
				String dataStr = dataArray.getString(dataIndex);
				BigDecimal price = new BigDecimal(dataStr.split(" ")[1]);
				String timeStrInData = dataStr.split(" ")[0];
				LocalDateTime now = LocalDateTime.now();
				dataBO = new CnStockMarketDataBO();
				dataBO.setEndPrice(price);
				dataBO.setStartPrice(price);
				dataBO.setHighPrice(price);
				dataBO.setLowPrice(price);
				dataBO.setStockCode(stockCode);
				dataBO.setCreateTime(now);
				dataBO.setStartTime(now.withHour(Integer.parseInt(String.valueOf(timeStrInData.subSequence(0, 2))))
						.withMinute(Integer.parseInt(String.valueOf(timeStrInData.subSequence(2, 4)))).withSecond(0)
						.withNano(0));
				dataBO.setEndTime(dataBO.getStartTime());
				result.add(dataBO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private CnStockMarkctOptionComponent getOption() {
		if (cnStockMarkctOptionComponent.getWatchingCode() != null
				&& !cnStockMarkctOptionComponent.getWatchingCode().isEmpty()) {
			return cnStockMarkctOptionComponent;
		}

		cnStockMarkctOptionComponent.refreshOption();
		return cnStockMarkctOptionComponent;
	}

	private CnStockMarketDataBO sinaDataToCommonDataBO(SinaStockMarketDataDTO dto) {
		if (dto == null || dto.getDay() == null) {
			return new CnStockMarketDataBO();
		}
		CnStockMarketDataBO bo = new CnStockMarketDataBO();
		bo.setCreateTime(LocalDateTime.now());
		bo.setStartTime(dto.getDay());
		bo.setEndTime(bo.getStartTime().plusMinutes(5));
		bo.setEndPrice(dto.getClose());
		bo.setStartPrice(dto.getOpen());
		bo.setHighPrice(dto.getHigh());
		bo.setLowPrice(dto.getLow());
		bo.setVolume(dto.getVolume());
		return bo;
	}

	private SinaStockMarketDataDTO jsonToDto(JSONObject json) {
		SinaStockMarketDataDTO dto = new SinaStockMarketDataDTO();
		try {
			dto.setDay(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(json.getString("day")));
			dto.setOpen(new BigDecimal(json.getDouble("open")));
			dto.setClose(new BigDecimal(json.getDouble("close")));
			dto.setHigh(new BigDecimal(json.getDouble("high")));
			dto.setLow(new BigDecimal(json.getDouble("low")));
			dto.setVolume(new BigDecimal(json.getDouble("close")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	private void sendDataToCthulhu(CnStockMarketDataDTO data) {
		HttpUtil h = new HttpUtil();
		JSONObject json = JSONObject.fromObject(data);
		try {
			h.sendPostRestful(systemOptionService.getCthulhuHostname() + BbtInteractionUrl.ROOT
					+ BbtInteractionUrl.CN_STOCK_MARKET_DATA, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
