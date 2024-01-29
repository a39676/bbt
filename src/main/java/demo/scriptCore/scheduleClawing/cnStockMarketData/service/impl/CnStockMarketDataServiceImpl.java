package demo.scriptCore.scheduleClawing.cnStockMarketData.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

	@Override
	public void collectDatasOf5MinAndSend() {
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

	private List<CnStockMarketDataBO> sinaApiOf5Min(String stockCode) {
//		http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=sh000001&scale=5&ma=5&datalen=10
		String urlModel = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=%s&scale=5&ma=5&datalen=%d";
		String url = null;
		Integer dataLen = 60 / 5 * 4 * 10; // data of 10 days
		String response = null;
		SinaStockMarketDataDTO dto = null;
		CnStockMarketDataBO dataBO = null;
		List<CnStockMarketDataBO> result = new ArrayList<>();
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
