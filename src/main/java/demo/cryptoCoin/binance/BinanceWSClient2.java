package demo.cryptoCoin.binance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.websocketcallback.WebSocketMessageCallback;

import demo.baseCommon.service.CommonService;
import demo.cryptoCoin.common.service.CryptoCoinCacheDataService;
import demo.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.cryptoCoin.common.service.CryptoCoinOptionService;
import finance.common.pojo.type.IntervalType;
import finance.cryptoCoin.binance.pojo.dto.DepthCompleteDTO;
import finance.cryptoCoin.binance.pojo.dto.DepthLevelDTO;
import finance.cryptoCoin.pojo.bo.BinanceWebScoketConnetionKeyBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BinanceWSClient2 extends CommonService {

	@Autowired
	private CryptoCoinConstantService constantService;
	@Autowired
	private CryptoCoinOptionService optionService;
	@Autowired
	private CryptoCoinCacheDataService cacheDataService;

	private WebSocketStreamClient wsStreamClient = null;
	private Map<BinanceWebScoketConnetionKeyBO, Integer> kLineConnectionIdMap = new HashMap<>();
	private Map<BinanceWebScoketConnetionKeyBO, Integer> bookTickerConnectionIdMap = new HashMap<>();
	private Map<BinanceWebScoketConnetionKeyBO, Integer> depthConnectionIdMap = new HashMap<>();
	private String defaultInterval = IntervalType.MINUTE_1.getName();
	private Integer defaultDepthSpeed = 100; // ms

	public void restartWebSocket() {
		wsStreamClient = null;
		createWebSocket();
	}

	public void createWebSocket() {
		getWebSorkcetStreamClient();
		for (String symbol : optionService.getSubscriptionSet()) {
			if (StringUtils.isNotBlank(symbol)) {
				addNewKLineSubcript(symbol + CurrencyTypeForCryptoCoin.USDT.getName(), defaultInterval);
			}
		}
	}

	private WebSocketStreamClient getWebSorkcetStreamClient() {
		if (wsStreamClient == null) {
			wsStreamClient = new WebSocketStreamClientImpl();
		}

		return wsStreamClient;
	}

	public void closeKLineConnection(String symbol, String interval) {
		if (wsStreamClient == null) {
			return;
		}
		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setInterval(interval);
		keyBO.setSymbol(symbol);
		Integer connectionId = kLineConnectionIdMap.get(keyBO);
		if (connectionId != null) {
			wsStreamClient.closeConnection(connectionId);
			kLineConnectionIdMap.remove(keyBO);
			log.error("Binance web socket remove kline connection, symbol: " + symbol + ", interval: " + interval
					+ ", ID: " + connectionId);
		}
	}

	public void addNewKLineSubcript(String symbol, String interval) {
		getWebSorkcetStreamClient();
		closeKLineConnection(symbol, interval);

		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setInterval(interval);
		keyBO.setSymbol(symbol);
		Integer connectionId = wsStreamClient.klineStream(symbol, interval, buildKLineDataCallback());
		log.error("Binance web socket add kline connection, symbol: " + symbol + ", interval: " + interval + ", ID: "
				+ connectionId);
		kLineConnectionIdMap.put(keyBO, connectionId);
	}

	public void closeDepthConnection(String symbol) {
		if (wsStreamClient == null) {
			return;
		}
		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setSymbol(symbol);
		Integer connectionId = depthConnectionIdMap.get(keyBO);
		if (connectionId != null) {
			wsStreamClient.closeConnection(connectionId);
			depthConnectionIdMap.remove(keyBO);
			log.error("Binance web socket remove depth connection, symbol: " + symbol + ", ID: " + connectionId);
		}
	}

	public void addNewDepthSubcript(String symbol) {
		getWebSorkcetStreamClient();
		closeDepthConnection(symbol);
		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setSymbol(symbol);
		Integer connectionId = wsStreamClient.diffDepthStream(symbol, defaultDepthSpeed, buildDepthDataCallback());
		log.error("Binance web socket add depth connection, symbol: " + symbol + ", ID: " + connectionId);
		depthConnectionIdMap.put(keyBO, connectionId);
	}

	public void closeBookTickerConnection(String symbol) {
		if (wsStreamClient == null) {
			return;
		}
		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setSymbol(symbol);
		Integer connectionId = bookTickerConnectionIdMap.get(keyBO);
		if (connectionId != null) {
			wsStreamClient.closeConnection(connectionId);
			bookTickerConnectionIdMap.remove(keyBO);
			log.error("Binance web socket remove book ticker connection, symbol: " + symbol + ", ID: " + connectionId);
		}
	}

	public void addNewBookTickerSubcript(String symbol) {
		getWebSorkcetStreamClient();
		closeBookTickerConnection(symbol);
		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setSymbol(symbol);
		Integer connectionId = wsStreamClient.bookTicker(symbol, buildBookTickerDataCallback());
		log.error("Binance web socket add book ticker connection, symbol: " + symbol + ", ID: " + connectionId);
		bookTickerConnectionIdMap.put(keyBO, connectionId);
	}

	private WebSocketMessageCallback buildKLineDataCallback() {
		WebSocketMessageCallback kLineDataCallback = new WebSocketMessageCallback() {

			@Override
			public void onMessage(String message) {
				refreshLastActiveTime();

				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
//					TODO
//					cacheService.reciveData(dataBO);
				}
			}
		};
		return kLineDataCallback;
	}

	private WebSocketMessageCallback buildDepthDataCallback() {
		WebSocketMessageCallback bookeTickerDataCallback = new WebSocketMessageCallback() {

			@Override
			public void onMessage(String message) {
				refreshLastActiveTime();
				DepthCompleteDTO dto = buildDepthDTO(message);
				if (dto != null) {
					cacheDataService.getBinanceDepthMap().put(dto.getSymbol(), dto);
				}
			}
		};
		return bookeTickerDataCallback;
	}

	private WebSocketMessageCallback buildBookTickerDataCallback() {
		WebSocketMessageCallback bookeTickerDataCallback = new WebSocketMessageCallback() {

			@Override
			public void onMessage(String message) {
				refreshLastActiveTime();
//				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
//				if (dataBO != null) {
//					TODO
//					cacheService.reciveData(dataBO);
//				}

			}
		};
		return bookeTickerDataCallback;
	}

	public void killStream() {
		getWebSorkcetStreamClient();
		wsStreamClient.closeAllConnections();
	}

	private void refreshLastActiveTime() {
		constantService.setBinanceWebSocketLastActiveTime(LocalDateTime.now());
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
				log.error("binnace recive unknow: " + sourceMsg);
				return null;
			}
			bo = new CryptoCoinPriceCommonDataBO();

			JSONObject kDataJson = sourceMsgJson.getJSONObject("k");
			String symbol = sourceMsgJson.getString("s").toLowerCase();

			if (symbol.contains("usdt")) {
				bo.setCurrencyType(CurrencyTypeForCryptoCoin.USD.getCode());
				bo.setCoinType(symbol.replaceAll("usdt", ""));
			} else {
				return null;
			}

			bo.setStartPrice(new BigDecimal(kDataJson.getDouble("o")));
			bo.setEndPrice(new BigDecimal(kDataJson.getDouble("c")));
			bo.setHighPrice(new BigDecimal(kDataJson.getDouble("h")));
			bo.setLowPrice(new BigDecimal(kDataJson.getDouble("l")));
			bo.setVolume(new BigDecimal(kDataJson.getDouble("v")));

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

	public boolean getSocketLiveFlag() {
		LocalDateTime lastActiveTime = constantService.getBinanceWebSocketLastActiveTime();
		if (lastActiveTime == null) {
			return false;
		}
		long seconds = ChronoUnit.SECONDS.between(lastActiveTime, LocalDateTime.now());

		return CryptoCoinWebSocketConstant.BINANCE_SOCKET_INACTIVE_JUDGMENT_SECOND > seconds;
	}

	public void getKLineDataDemo() {
//		TODO
		SpotClient client = new SpotClientImpl();
//	     * symbol -- mandatory/string <br>
//	     * interval -- mandatory/string <br>
//	     * startTime -- optional/long <br>
//	     * endTime -- optional/long <br>
//	     * timeZone -- optional/string -- Default:0 (UTC) <br>
//	     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
		Map<String, Object> lineParamMap = new HashMap<String, Object>();
		lineParamMap.put("symbol", "BTCUSDT");
		lineParamMap.put("interval", IntervalType.DAY_1.getName());
		lineParamMap.put("limit", 10);

//		[
//		  [
//		    1591258320000,          // Open time
//		    "9640.7",               // Open
//		    "9642.4",               // High
//		    "9640.6",               // Low
//		    "9642.0",               // Close (or latest price)
//		    "206",                  // Volume
//		    1591258379999,          // Close time
//		    "2.13660389",           // Base asset volume
//		    48,                     // Number of trades
//		    "119",                  // Taker buy volume
//		    "1.23424865",           // Taker buy base asset volume
//		    "0"                     // Ignore.
//		  ]
//		]
		String response = client.createMarket().klines(lineParamMap);
		System.out.println(response);
	}

	private DepthCompleteDTO buildDepthDTO(String msg) {
		DepthCompleteDTO dto = new DepthCompleteDTO();
		JSONObject json = JSONObject.fromObject(msg);
		dto.setUpdateTime(localDateTimeHandler.dateToLocalDateTime(new Date(json.getLong("E"))));
		dto.setSymbol(json.getString("s"));

		DepthLevelDTO tmpOrderDTO = null;
		JSONArray tmpArray = null;
		List<DepthLevelDTO> sellerDtoList = new ArrayList<>();
		JSONArray sellerList = json.getJSONArray("b");
		for (int i = 0; i < sellerList.size(); i++) {
			tmpOrderDTO = new DepthLevelDTO();
			tmpArray = sellerList.getJSONArray(i);
			tmpOrderDTO.setPrice(new BigDecimal(tmpArray.getString(0)));
			tmpOrderDTO.setValue(new BigDecimal(tmpArray.getString(1)));
			sellerDtoList.add(tmpOrderDTO);
		}
		dto.setSellerList(sellerDtoList);

		List<DepthLevelDTO> buyerDtoList = new ArrayList<>();
		JSONArray buyerList = json.getJSONArray("a");
		for (int i = 0; i < buyerList.size(); i++) {
			tmpOrderDTO = new DepthLevelDTO();
			tmpArray = buyerList.getJSONArray(i);
			tmpOrderDTO.setPrice(new BigDecimal(tmpArray.getString(0)));
			tmpOrderDTO.setValue(new BigDecimal(tmpArray.getString(1)));
			buyerDtoList.add(tmpOrderDTO);
		}
		dto.setBuyerList(buyerDtoList);

		return dto;
	}

	public static void main(String[] args) {
		String proxyHost = "127.0.0.1";
		String proxyPort = "10809";

		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);

		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);

		BinanceWSClient2 t = new BinanceWSClient2();
		t.getKLineDataDemo();
	}
}
