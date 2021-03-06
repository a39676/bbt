//package demo.clawing.scheduleClawing.service.component.webSocket;
//
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.google.gson.Gson;
//import com.neovisionaries.ws.client.WebSocket;
//import com.neovisionaries.ws.client.WebSocketAdapter;
//import com.neovisionaries.ws.client.WebSocketException;
//import com.neovisionaries.ws.client.WebSocketFactory;
//import com.neovisionaries.ws.client.WebSocketFrame;
//
//import auxiliaryCommon.pojo.result.CommonResult;
//import auxiliaryCommon.pojo.type.CurrencyType;
//import demo.clawing.scheduleClawing.pojo.bo.CryptoCompareSocketConfigBO;
//import demo.clawing.scheduleClawing.pojo.type.CryptoCompareWebSocketMsgType;
//import demo.selenium.service.impl.SeleniumCommonService;
//import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
//import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import toolPack.ioHandle.FileUtilCustom;
//
//@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//public class HuobiWSClientDemo extends SeleniumCommonService {
//
//	private WebSocket ws = null;
//	
//	@Autowired
//	private FileUtilCustom ioUtil;
//	
//
//	public WebSocket getWs() {
//		if (ws == null) {
//			createWebSocket(getDefaultConfig());
//		}
//		return ws;
//	}
//	
//	private CryptoCompareSocketConfigBO getDefaultConfig() {
//		CryptoCompareSocketConfigBO bo = null;
////		String systemParameterSavingFolderPath = globalOptionService.getParameterSavingFolder();
//		String systemParameterSavingFolderPath = "d:/home/u2/bbt/autoTestParameterFiles";
//		String cryptoCompareParameterSavingFolderPath = systemParameterSavingFolderPath + File.separator
//				+ CryptoCoinWebSocketConstant.HUO_BI_PARAM_STORE_PATH;
//
//		File configFile = new File(cryptoCompareParameterSavingFolderPath);
//
//		if (!configFile.exists() || !configFile.isFile()) {
//			log.error("Huobi config file not exists");
//			return bo;
//		}
//
//		String jsonStr = ioUtil.getStringFromFile(cryptoCompareParameterSavingFolderPath);
//		if (StringUtils.isBlank(jsonStr)) {
//			log.error("Huobi config file format error");
//			return bo;
//		}
//
//		try {
//			bo = new Gson().fromJson(jsonStr, CryptoCompareSocketConfigBO.class);
//		} catch (Exception e) {
//			log.error("Huobi config trans error");
//			return bo;
//		}
//
//		return bo;
//	}
//
//	private CryptoCompareWebSocketMsgType checkConnection(String msg) {
//		try {
//			JSONObject j = JSONObject.fromObject(msg);
//			Integer typeCode = j.getInt("TYPE");
//			return CryptoCompareWebSocketMsgType.getType(typeCode);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	private CryptoCoinPriceCommonDataBO buildCommonDataFromMsg(String sourceMsg) {
//		CryptoCoinPriceCommonDataBO bo = null;
//		try {
//			JSONObject sourceMsgJson = JSONObject.fromObject(sourceMsg);
//			if(!sourceMsgJson.containsKey("PRICE")) {
//				return null;
//			}
//			
//			bo = new CryptoCoinPriceCommonDataBO();
//			bo.setStartPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
//			bo.setEndPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
//			bo.setHighPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
//			bo.setLowPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
//			bo.setCoinType(sourceMsgJson.getString("FROMSYMBOL"));
//			bo.setCurrencyType(CurrencyType.getType(sourceMsgJson.getString("TOSYMBOL")).getCode());
//			try {
//				Date tradDate = new Date(sourceMsgJson.getLong("LASTUPDATE") * 1000);
//				LocalDateTime tradDateTime = localDateTimeHandler.dateToLocalDateTime(tradDate);
//				if(tradDateTime == null) {
//					tradDateTime = LocalDateTime.now();
//				}
//				bo.setStartTime(tradDateTime);
//				bo.setEndTime(tradDateTime);
//			} catch (Exception e) {
//				bo.setStartTime(LocalDateTime.now());
//				bo.setEndTime(LocalDateTime.now());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		return bo;
//	}
//	
//	private WebSocket createWebSocket(CryptoCompareSocketConfigBO configBO) {
//		String uriStr = configBO.getUri() + "?api_key=" + configBO.getApiKey();
//		try {
//			WebSocket ws = new WebSocketFactory().setVerifyHostname(false).createSocket(uriStr);
//			return ws;
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error("create socket error: " + e);
//			return null;
//		}
//	}
//	
//	private WebSocket setListener(WebSocket ws) {
//		ws.addListener(new WebSocketAdapter() {
//			@Override
//			public void onTextMessage(WebSocket websocket, String message) throws Exception {
////				System.out.println(message);
//				
//				CryptoCompareWebSocketMsgType connectionType = checkConnection(message);
//				if(connectionType== null) {
//					ws.disconnect();
//				} else if(connectionType.getCode() < 400 || CryptoCompareWebSocketMsgType.HEARTBEAT.equals(connectionType)) {
//					refreshLastActiveTime(CryptoCoinWebSocketConstant.SOCKET_INACTIVE_JUDGMENT_SECOND);
//				} else if(CryptoCompareWebSocketMsgType.TOO_MANY_SOCKETS_MAX_.getCode().equals(connectionType.getCode())) {
//					log.error("Huobi web socket error: " + connectionType.getName());
//					refreshLastActiveTime(CryptoCoinWebSocketConstant.SOCKET_COLDDOWN_SECOND);
//				} else {
//					log.error("Huobi web socket error: " + connectionType.getName());
//					ws.disconnect();
//				}
//				
//				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
//				if (dataBO != null) {
//					System.out.println(dataBO);
//				}
//			}
//
//			@Override
//			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
//					WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
//				constantService.deleteValByName(CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
//			}
//		});
//		return ws;
//	}
//	
//	/**
//	 * @param channelStrListchannelStr format 5~CCCAGG~BTC~USD
//	 * ref: https://min-api.cryptocompare.com/documentation/websockets
//	 */
//	public void addSubscription(List<String> channelStrList) {
//		JSONObject json = new JSONObject();
//		json.put("action", "SubAdd");
//		JSONArray subs = new JSONArray();
//		for (String subscription : channelStrList) {
//			subs.add(subscription);
//		}
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//	}
//	
//	public void addSubscription(String channelStr) {
//		JSONObject json = new JSONObject();
//		json.put("action", "SubAdd");
//		JSONArray subs = new JSONArray();
//		subs.add(channelStr);
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//	}
//	
//	public void removeSubscription(List<String> channelStrList) {
//		JSONObject json = new JSONObject();
//		json.put("action", "SubRemove");
//		JSONArray subs = new JSONArray();
//		for (String subscription : channelStrList) {
//			subs.add(subscription);
//		}
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//	}
//	
//	public void removeSubscription(String channelStr) {
//		JSONObject json = new JSONObject();
//		json.put("action", "SubRemove");
//		JSONArray subs = new JSONArray();
//		subs.add(channelStr);
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//	}
//
//	private void refreshLastActiveTime(int seconds) {
//		constantService.setValByName(
//				CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY,
//				localDateTimeHandler.dateToStr(LocalDateTime.now()), 
//				seconds, 
//				TimeUnit.SECONDS);
//	}
//	
//	public boolean getSocketLiveFlag() {
//		return constantService.hasKey(CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
//	}
//
//	public CommonResult startWebSocket() {
//		CryptoCompareSocketConfigBO configBO = getDefaultConfig();
//		CommonResult r = new CommonResult();
//		if (configBO == null) {
//			r.failWithMessage("Huobi socket load config error");
//			return r;
//		}
//
//		ws = createWebSocket(configBO);
//		if (ws == null) {
//			r.failWithMessage("Huobi socket create scoket error");
//			return r;
//		}
//
//		ws = setListener(ws);
//		try {
//			ws.connect();
//			addSubscription(configBO.getSubs());
//			r.normalSuccess();
//		} catch (WebSocketException e) {
//			log.error("Huobi socket connect error: " + e.getLocalizedMessage());
//		}
//
//		return r;
//	}
//	
//	public void wsDestory() {
//		try {
//			ws.sendClose();
//			ws.disconnect();
//		} catch (Exception e) {
//			log.error("Huobi web socket disconnect error: " + e.getLocalizedMessage());
//		}
//		ws = null;
//	}
//}
