package demo.clawing.scheduleClawing.service.component.webSocket;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.clawing.scheduleClawing.mq.sender.CryptoCoinPriceCacheDataAckProducer;
import demo.clawing.scheduleClawing.pojo.bo.BinanceWebSocketConfigBO;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BinanceWSClient extends SeleniumCommonService {

	private WebSocket ws = null;

	@Autowired
	private FileUtilCustom ioUtil;

	@Autowired
	private CryptoCoinPriceCacheDataAckProducer croptoCoinPriceCacheDataAckProducer;

	public WebSocket getWs() {
		if (ws == null) {
			createWebSocket(getDefaultConfig());
		}
		return ws;
	}

	private BinanceWebSocketConfigBO getDefaultConfig() {
		BinanceWebSocketConfigBO bo = null;
		String systemParameterSavingFolderPath = globalOptionService.getParameterSavingFolder();
		String cryptoCompareParameterSavingFolderPath = systemParameterSavingFolderPath + File.separator
				+ CryptoCoinWebSocketConstant.BINANCE_PARAM_STORE_PATH;

		File configFile = new File(cryptoCompareParameterSavingFolderPath);

		if (!configFile.exists() || !configFile.isFile()) {
			log.error("binance config file not exists");
			return bo;
		}

		String jsonStr = ioUtil.getStringFromFile(cryptoCompareParameterSavingFolderPath);
		if (StringUtils.isBlank(jsonStr)) {
			log.error("binance config file format error");
			return bo;
		}

		try {
			bo = new Gson().fromJson(jsonStr, BinanceWebSocketConfigBO.class);
		} catch (Exception e) {
			log.error("binance config trans error");
			return bo;
		}

		return bo;
	}

//	   binance web scoket kline respon exaple 
//	   https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md
//	    {
//		  "e": "kline", // Event type
//		  "E": 1616819474380, // Event time
//		  "s": "FILUSDT",  // Symbol
//		  "k": {
//		    "t": 1616819460000,  // Kline start time
//		    "T": 1616819519999,  // Kline close time
//		    "s": "FILUSDT",  // Symbol
//		    "i": "1m",  // Interval
//		    "f": 20019412,  // First trade ID
//		    "L": 20019875,  // Last trade ID
//		    "o": "128.13010000",  // Open price
//		    "c": "128.37060000",  // Close price
//		    "h": "128.47660000",  // High price
//		    "l": "128.02660000",  // Low price
//		    "v": "1444.36000000",  // Base asset volume 交易币数
//		    "n": 464,  // Number of trades
//		    "x": false,  // Is this kline closed?
//		    "q": "185297.51924800",  // Quote asset volume 交易金额
//		    "V": "989.65000000",  // Taker buy base asset volume
//		    "Q": "126963.71005400",  // Taker buy quote asset volume
//		    "B": "0"  // Ignore
//		  }
//		}
	private CryptoCoinPriceCommonDataBO buildCommonDataFromMsg(String sourceMsg) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject sourceMsgJson = JSONObject.fromObject(sourceMsg);
			if (!sourceMsgJson.containsKey("k")) {
				return null;
			}
			bo = new CryptoCoinPriceCommonDataBO();

			JSONObject kDataJson = sourceMsgJson.getJSONObject("k");
			String symbol = sourceMsgJson.getString("s").toLowerCase();
			
			if(symbol.contains("usdt")) {
				bo.setCurrencyType(CurrencyType.USD.getCode());
				bo.setCoinType(symbol.replaceAll("usdt", ""));
			} else {
				return null;
			}
			
			bo.setStartPrice(new BigDecimal(kDataJson.getDouble("o")));
			bo.setEndPrice(new BigDecimal(kDataJson.getDouble("c")));
			bo.setHighPrice(new BigDecimal(kDataJson.getDouble("h")));
			bo.setLowPrice(new BigDecimal(kDataJson.getDouble("l")));
			try {
				Date tradDate = new Date(sourceMsgJson.getLong("E"));
				LocalDateTime tradDateTime = localDateTimeHandler.dateToLocalDateTime(tradDate);
				if (tradDateTime == null) {
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

	private WebSocket createWebSocket(BinanceWebSocketConfigBO configBO) {
		StringBuffer uriBuilder = new StringBuffer(configBO.getUri());
		uriBuilder.append("/ws");
		for(String symbol : configBO.getSubs()) {
			uriBuilder.append("/" + symbol + "@kline_1m");
		}
		try {
			WebSocket ws = new WebSocketFactory().setVerifyHostname(false).createSocket(uriBuilder.toString());
			return ws;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("binance create socket error: " + e);
			return null;
		}
	}

	private WebSocket setListener(WebSocket ws) {
		ws.addListener(new WebSocketAdapter() {
			@Override
			public void onTextMessage(WebSocket websocket, String message) throws Exception {
//				System.out.println(message);

				refreshLastActiveTime(CryptoCoinWebSocketConstant.SOCKET_INACTIVE_JUDGMENT_SECOND);
		
				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
					croptoCoinPriceCacheDataAckProducer.sendPriceCacheData(dataBO);
				}
			}

			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
				constantService
						.deleteValByName(CryptoCoinWebSocketConstant.BINANCE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
			}
		});
		return ws;
	}


	private void refreshLastActiveTime(int seconds) {
		constantService.setValByName(CryptoCoinWebSocketConstant.BINANCE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY,
				localDateTimeHandler.dateToStr(LocalDateTime.now()), seconds, TimeUnit.SECONDS);
	}

	public boolean getSocketLiveFlag() {
		return constantService.hasKey(CryptoCoinWebSocketConstant.BINANCE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
	}

	public CommonResult startWebSocket() {
		BinanceWebSocketConfigBO configBO = getDefaultConfig();
		CommonResult r = new CommonResult();
		if (configBO == null) {
			r.failWithMessage("binance socket load config error");
			return r;
		}

		ws = createWebSocket(configBO);
		if (ws == null) {
			r.failWithMessage("binance socket create scoket error");
			return r;
		}

		ws = setListener(ws);
		try {
			ws.connect();
			r.normalSuccess();
		} catch (WebSocketException e) {
			log.error("binance socket connect error: " + e.getLocalizedMessage());
		}

		return r;
	}

	public void wsDestory() {
		try {
			ws.sendClose();
			ws.disconnect();
		} catch (Exception e) {
			log.error("binance web socket disconnect error: " + e.getLocalizedMessage());
		}
		ws = null;
	}
}
