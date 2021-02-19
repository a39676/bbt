package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.clawing.scheduleClawing.mq.sender.CroptoCoinPriceCacheDataAckProducer;
import demo.clawing.scheduleClawing.pojo.bo.CryptoCompareSocketConfigBO;
import demo.clawing.scheduleClawing.pojo.type.CryptoCompareWebSocketMsgType;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCompareConstant;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CryptoCompareWSClient extends SeleniumCommonService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private CroptoCoinPriceCacheDataAckProducer croptoCoinPriceCacheDataAckProducer;

	public CommonResult startWebSocket() {
		CryptoCompareSocketConfigBO configBO = getConfig();
		CommonResult r = new CommonResult();
		if (configBO == null) {
			r.failWithMessage("crypto compare socket load config error");
			return r;
		}

		WebSocket ws = createWebSocket(configBO);
		if (ws == null) {
			r.failWithMessage("crypto compare socket create scoket error");
			return r;
		}

		ws = setListener(ws);
		try {
			ws.connect();
			addSubscription(ws, configBO);
			r.normalSuccess();
		} catch (WebSocketException e) {
			log.error("crypto compare socket connect error: " + e.getLocalizedMessage());
		}

		return r;
	}

	private WebSocket createWebSocket(CryptoCompareSocketConfigBO configBO) {
		String uriStr = configBO.getUri() + "?api_key=" + configBO.getApiKey();
		try {
			WebSocket ws = new WebSocketFactory().setVerifyHostname(false).createSocket(uriStr);
			return ws;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("create socket error: " + e);
			return null;
		}
	}

	private WebSocket setListener(WebSocket ws) {
		ws.addListener(new WebSocketAdapter() {
			@Override
			public void onTextMessage(WebSocket websocket, String message) throws Exception {
				System.out.println(message);
				
				CryptoCompareWebSocketMsgType connectionType = checkConnection(message);
				if(connectionType== null) {
					ws.disconnect();
				} else if(connectionType.getCode() < 400 || CryptoCompareWebSocketMsgType.HEARTBEAT.equals(connectionType)) {
					refreshLastActiveTime(CryptoCompareConstant.SOCKET_INACTIVE_JUDGMENT_SECOND);
				} else if(CryptoCompareWebSocketMsgType.TOO_MANY_SOCKETS_MAX_.getCode().equals(connectionType.getCode())) {
					refreshLastActiveTime(CryptoCompareConstant.SOCKET_COLDDOWN_SECOND);
				} else {
					ws.disconnect();
				}
				
				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
					croptoCoinPriceCacheDataAckProducer.sendPriceCacheData(dataBO);
				}
			}

			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
				constantService.deleteValByName(CryptoCompareConstant.SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
			}
		});
		return ws;
	}

	private void addSubscription(WebSocket ws, CryptoCompareSocketConfigBO bo) {
		JSONObject json = new JSONObject();
		json.put("action", "SubAdd");
		JSONArray subs = new JSONArray();
		String subscriptionformat = "5~CCCAGG~%s~%s";
		String subscription = null;
		CryptoCoinType coinType = null;
		CurrencyType currencyType = null;
		for (String cryptoCoinCode : bo.getTargetCoins()) {
			coinType = CryptoCoinType.getType(cryptoCoinCode);
			if (coinType == null) {
				continue;
			}
			for (String currencyCode : bo.getTargetCurrency()) {
				currencyType = CurrencyType.getType(currencyCode);
				if (currencyType == null) {
					continue;
				}
				subscription = String.format(subscriptionformat, coinType.getName(), currencyType.getName());
				subs.add(subscription);
			}
		}
		json.put("subs", subs);

		ws.sendText(json.toString());
	}

	public boolean getSocketLiveFlag() {
		return constantService.hasKey(CryptoCompareConstant.SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
	}

	private void refreshLastActiveTime(int seconds) {
		constantService.setValByName(
				CryptoCompareConstant.SOCKET_LAST_ACTIVE_TIME_REDIS_KEY,
				localDateTimeHandler.dateToStr(LocalDateTime.now()), 
				seconds, 
				TimeUnit.SECONDS);
	}

	private CryptoCompareSocketConfigBO getConfig() {
		CryptoCompareSocketConfigBO bo = null;
		String systemParameterSavingFolderPath = globalOptionService.getParameterSavingFolder();
		String cryptoCompareParameterSavingFolderPath = systemParameterSavingFolderPath + File.separator
				+ CryptoCompareConstant.PARAM_STORE_PATH;

		File configFile = new File(cryptoCompareParameterSavingFolderPath);

		if (!configFile.exists() || !configFile.isFile()) {
			log.error("crypto compare config file not exists");
			return bo;
		}

		String jsonStr = ioUtil.getStringFromFile(cryptoCompareParameterSavingFolderPath);
		if (StringUtils.isBlank(jsonStr)) {
			log.error("crypto compare config file format error");
			return bo;
		}

		try {
			bo = new Gson().fromJson(jsonStr, CryptoCompareSocketConfigBO.class);
		} catch (Exception e) {
			log.error("crypto compare config trans error");
			return bo;
		}

		return bo;
	}

	private CryptoCompareWebSocketMsgType checkConnection(String msg) {
		try {
			JSONObject j = JSONObject.fromObject(msg);
			Integer typeCode = j.getInt("TYPE");
			return CryptoCompareWebSocketMsgType.getType(typeCode);
		} catch (Exception e) {
			return null;
		}
	}
	
	private CryptoCoinPriceCommonDataBO buildCommonDataFromMsg(String sourceMsg) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject sourceMsgJson = JSONObject.fromObject(sourceMsg);
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setStartPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setEndPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setHighPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setLowPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setCoinType(CryptoCoinType.getType(sourceMsgJson.getString("FROMSYMBOL")).getCode());
			bo.setCurrencyType(CurrencyType.getType(sourceMsgJson.getString("TOSYMBOL")).getCode());
			try {
				Date tradDate = new Date(sourceMsgJson.getLong("LASTUPDATE") * 1000);
				LocalDateTime tradDateTime = localDateTimeHandler.dateToLocalDateTime(tradDate);
				if(tradDateTime == null) {
					tradDateTime = LocalDateTime.now();
				}
				bo.setStartTime(tradDateTime);
				bo.setEndTime(tradDateTime);
			} catch (Exception e) {
				bo.setStartTime(LocalDateTime.now());
				bo.setEndTime(LocalDateTime.now());
			}
		} catch (Exception e) {
			return null;
		}
		return bo;
	}

}
