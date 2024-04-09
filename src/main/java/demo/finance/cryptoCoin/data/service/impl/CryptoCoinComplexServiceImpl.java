package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import demo.config.costomComponent.BbtDynamicKey;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.binance.BinanceDataApiUnit;
import demo.finance.cryptoCoin.data.binance.BinanceDataWSClient;
import demo.finance.cryptoCoin.data.pojo.bo.BinanceKLineBO;
import demo.finance.cryptoCoin.data.pojo.type.CryptoBigMoveCounterType;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
import finance.common.pojo.bo.FilterPriceResult;
import finance.common.pojo.bo.KLineCommonDataBO;
import finance.common.pojo.type.IntervalType;
import finance.cryptoCoin.binance.pojo.dto.KLineKeyBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import net.sf.json.JSONObject;
import tool.pojo.constant.CxBbtInteractionUrl;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinComplexServiceImpl extends CryptoCoinCommonService implements CryptoCoinComplexService {

	@Autowired
	private CryptoCoinCacheDataService cacheDataServcie;
	@Autowired
	private BinanceDataWSClient binanceDataWSClient;
	@Autowired
	private BbtDynamicKey bbtDynamicKey;
	@Autowired
	private BinanceDataApiUnit binanceDataApiUnit;

	private static final String BIG_MOVE_REDIS_KEY_PERFIX = "cryptoCoinBigMove";
	private static final String BIG_RISE_REDIS_KEY_PERFIX = "Rise";
	private static final String BIG_FALL_REDIS_KEY_PERFIX = "Fall";
	private static final String BIG_MOVE_IN_1MIN_REDIS_KEY_PERFIX = "_1"
			+ CryptoBigMoveCounterType.IN_MINUTE.getRedisKeyTemplateKeyWord() + "_";
	private static final String BIG_MOVE_IN_5MIN_REDIS_KEY_PERFIX = "_5"
			+ CryptoBigMoveCounterType.IN_MINUTE.getRedisKeyTemplateKeyWord() + "_";
	private static final String BIG_MOVE_IN_10MIN_REDIS_KEY_PERFIX = "_10"
			+ CryptoBigMoveCounterType.IN_MINUTE.getRedisKeyTemplateKeyWord() + "_";
	private static final String BIG_MOVE_IN_24HOUR_REDIS_KEY_PERFIX = "_24"
			+ CryptoBigMoveCounterType.IN_HOUR.getRedisKeyTemplateKeyWord() + "_";

	@Override
	public void deleteOld1MinKLineDatas() {
		int oneMinDataListMaxSize = 70;
		Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> map = cacheDataServcie.getBinanceKLineCacheMap();
		for (KLineKeyBO key : map.keySet()) {
			if (IntervalType.MINUTE_1.getName().equals(key.getInterval())) {
				List<CryptoCoinPriceCommonDataBO> list = map.get(key);
				while (list != null && list.size() > oneMinDataListMaxSize) {
					list.remove(0);
				}
			}
		}
	}

	@Override
	public void checkBigMoveInMinutes() {
		int scaleForCalculate = 4;
		Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> map = cacheDataServcie.getBinanceKLineCacheMap();
		List<CryptoCoinPriceCommonDataBO> list = null;
		FilterPriceResult filterData = null;
		String timingKey = null;
		BigDecimal rate = null;
		boolean sendMsgFlag = false;
		CryptoCoinPriceCommonDataBO lastData = null;
		if (map.isEmpty()) {
			return;
		}
		int dataSize = 0;
		for (KLineKeyBO key : map.keySet()) {
			list = map.get(key);
			dataSize = 1;
			if (list.isEmpty() || list.size() < dataSize + 1) {
				continue;
			}
			filterData = kLineToolUnit.filterData(list.subList(list.size() - 2, list.size()));
			lastData = list.get(list.size() - 1);
			rate = filterData.getMaxPrice().divide(filterData.getMinPrice(), scaleForCalculate, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			oneMinTag: if (rate.doubleValue() > optionService.getBigMoveIn1min()) {
				timingKey = BIG_MOVE_IN_1MIN_REDIS_KEY_PERFIX;
				sendMsgFlag = sendNoticeMsgIfNecessary(timingKey, filterData, key.getSymbol(), rate, lastData,
						CryptoBigMoveCounterType.IN_MINUTE.getNoticeCacheLivingSeconds(), TimeUnit.SECONDS);
				if (sendMsgFlag) {
					break oneMinTag;
				}
			}

			dataSize = 5;
			if (list.size() < dataSize) {
				continue;
			}
			filterData = kLineToolUnit.filterData(list.subList(list.size() - dataSize, list.size()));
			rate = filterData.getMaxPrice().divide(filterData.getMinPrice(), scaleForCalculate, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			fiveMinTag: if (rate.doubleValue() > optionService.getBigMoveIn5min()) {
				timingKey = BIG_MOVE_IN_5MIN_REDIS_KEY_PERFIX;
				sendMsgFlag = sendNoticeMsgIfNecessary(timingKey, filterData, key.getSymbol(), rate, lastData,
						CryptoBigMoveCounterType.IN_MINUTE.getNoticeCacheLivingSeconds(), TimeUnit.SECONDS);
				if (sendMsgFlag) {
					break fiveMinTag;
				}
			}

			dataSize = 10;
			if (list.size() < dataSize) {
				continue;
			}
			filterData = kLineToolUnit.filterData(list.subList(list.size() - dataSize, list.size()));
			rate = filterData.getMaxPrice().divide(filterData.getMinPrice(), scaleForCalculate, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			tenMinTag: if (rate.doubleValue() > optionService.getBigMoveIn10min()) {
				timingKey = BIG_MOVE_IN_10MIN_REDIS_KEY_PERFIX;
				sendMsgFlag = sendNoticeMsgIfNecessary(timingKey, filterData, key.getSymbol(), rate, lastData,
						CryptoBigMoveCounterType.IN_MINUTE.getNoticeCacheLivingSeconds(), TimeUnit.SECONDS);
				if (sendMsgFlag) {
					break tenMinTag;
				}
			}
		}
	}

	@Override
	public void checkBigMoveInHours() {
		int scaleForCalculate = 4;
		Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> map = cacheDataServcie.getBinanceKLineCacheMap();
		List<CryptoCoinPriceCommonDataBO> hourCommonDataList = null;
		List<BinanceKLineBO> binanceDatalist = null;
		List<CryptoCoinPriceCommonDataBO> cacheDataList = null;
		FilterPriceResult filterDataFromHourData = null;
		String timingKey = null;
		BigDecimal rate = null;
		KLineCommonDataBO lastData;
		boolean sendMsgFlag = false;
		if (map.isEmpty()) {
			return;
		}
		for (KLineKeyBO key : map.keySet()) {
			binanceDatalist = binanceDataApiUnit.getKLineHourData(key.getSymbol());
			if (binanceDatalist.isEmpty() || binanceDatalist.isEmpty()) {
				continue;
			}

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime twentyFourHourAgo = now.minusHours(24).withSecond(0).withNano(0);

			hourCommonDataList = binanceDataConvertToCommonData(binanceDatalist, key.getSymbol(), IntervalType.HOUR_1);
			for (int i = 0; i < hourCommonDataList.size(); i++) {
				if (hourCommonDataList.get(i).getStartTime().isBefore(twentyFourHourAgo)) {
					hourCommonDataList.remove(i);
					i--;
				}
			}

			cacheDataList = map.get(key);
			if (cacheDataList != null && !cacheDataList.isEmpty()) {
				lastData = cacheDataList.get(cacheDataList.size() - 1);
				int cacheDataListMaxSize = 60;
				if (cacheDataList.size() < cacheDataListMaxSize) {
					hourCommonDataList.addAll(cacheDataList);
				} else {
					hourCommonDataList.addAll(
							cacheDataList.subList(cacheDataList.size() - cacheDataListMaxSize, cacheDataList.size()));
				}
			} else {
				lastData = hourCommonDataList.get(hourCommonDataList.size() - 1);
			}

			filterDataFromHourData = kLineToolUnit.filterData(hourCommonDataList);

			rate = filterDataFromHourData.getMaxPrice()
					.divide(filterDataFromHourData.getMinPrice(), scaleForCalculate, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			if (rate.doubleValue() > optionService.getBigMoveIn24hour()) {
				timingKey = BIG_MOVE_IN_24HOUR_REDIS_KEY_PERFIX;

				sendMsgFlag = sendNoticeMsgIfNecessary(timingKey, filterDataFromHourData, key.getSymbol(), rate,
						lastData, CryptoBigMoveCounterType.IN_HOUR.getNoticeCacheLivingSeconds(), TimeUnit.HOURS);
				if (sendMsgFlag) {
					continue;
				}
			}
		}
	}

	private boolean sendNoticeMsgIfNecessary(String timingKey, FilterPriceResult filterData, String symbol,
			BigDecimal rate, KLineCommonDataBO lastData, long timeout, TimeUnit timeUnit) {
		int scaleForPriceDisplay = 8;
		String redisKey = BIG_MOVE_REDIS_KEY_PERFIX;
		String directKey = null;
		String directStr = null;
		if (filterData.getMaxPriceDateTime().isAfter(filterData.getMinPriceDateTime())) {
			directKey = BIG_RISE_REDIS_KEY_PERFIX;
			directStr = directKey + "↗↗ 🟢";
		} else {
			directKey = BIG_FALL_REDIS_KEY_PERFIX;
			directStr = directKey + "↘↘ 🔴";
		}
		redisKey = redisKey + directKey + timingKey + symbol;
		if (redisTemplate.hasKey(redisKey)) {
			return false;
		}
		redisTemplate.opsForValue().set(redisKey, symbol, timeout, timeUnit);
		String msg = symbol + " " + directStr + ", " + rate + "% in " + timingKey + ", now: "
				+ lastData.getEndPrice().setScale(scaleForPriceDisplay, RoundingMode.HALF_UP);
		sendingMsg(msg);
		return true;
	}

	@Override
	public void checkBinanceKLineStreamAliveAndReconnect() {
		if (systemOptionService.isDev()) {
			return;
		}
		List<String> subscriptionSymbolList = optionService.getBinanceKLineSubscriptionSymbolSet();
		KLineKeyBO tmpKey = null;
		List<CryptoCoinPriceCommonDataBO> dataList = null;
		LocalDateTime now = null;
		CryptoCoinPriceCommonDataBO lastData = null;
		int maxReconnectCounter = optionService.getMaxReconnectCounterInOneTime();
		String symbol = null;
		for (int i = 0; i < subscriptionSymbolList.size() && maxReconnectCounter > 0; i++) {
			symbol = subscriptionSymbolList.get(i);
			tmpKey = new KLineKeyBO();
			tmpKey.setSymbol(symbol);
			tmpKey.setInterval(kLineDefaultInterval);
			dataList = cacheDataServcie.getBinanceKLineCacheMap().get(tmpKey);
			if (dataList == null || dataList.isEmpty()) {
				binanceDataWSClient.addNewKLineSubcript(symbol, kLineDefaultInterval);
				maxReconnectCounter--;
				continue;
			}
			now = LocalDateTime.now().withSecond(0).withNano(0).minusMinutes(1);
			lastData = dataList.get(dataList.size() - 1);
			if (lastData.getStartTime().isBefore(now)) {
				binanceDataWSClient.addNewKLineSubcript(symbol, kLineDefaultInterval);
				maxReconnectCounter--;
			}
		}

	}

	@Override
	public void getRecentBigMoveCounterBySymbol() {
		getRecentBigMoveCounterWithPatternBySymbol(CryptoBigMoveCounterType.IN_MINUTE);
		getRecentBigMoveCounterWithPatternBySymbol(CryptoBigMoveCounterType.IN_HOUR);
	}

	private void getRecentBigMoveCounterWithPatternBySymbol(CryptoBigMoveCounterType bingMoveCounterType) {
		String keyPattern = BIG_MOVE_REDIS_KEY_PERFIX + "*" + bingMoveCounterType.getRedisKeyTemplateKeyWord() + "*";
		Set<String> sourceKeySet = redisTemplate.keys(keyPattern);

		Set<String> targetKeySet = new HashSet<>();
		for (String key : sourceKeySet) {
			targetKeySet.add(key.replaceAll("_.*_", ""));
		}

		int riseCount = 0;
		int fallCount = 0;
		for (String key : targetKeySet) {
			if (key.contains(BIG_RISE_REDIS_KEY_PERFIX)) {
				riseCount++;
			} else if (key.contains(BIG_FALL_REDIS_KEY_PERFIX)) {
				fallCount++;
			}
		}
		String msg = "";
		if (riseCount > 0) {
			msg += "Big " + BIG_RISE_REDIS_KEY_PERFIX + " " + bingMoveCounterType.getName() + ": " + riseCount
					+ " in last " + bingMoveCounterType.getNoticeCacheLivingSeconds() + " seconds; ";
		}
		if (fallCount > 0) {
			msg += "Big " + BIG_FALL_REDIS_KEY_PERFIX + " " + bingMoveCounterType.getName() + ": " + fallCount
					+ " in last " + bingMoveCounterType.getNoticeCacheLivingSeconds() + " seconds; ";
		}

		if (msg.length() > 0 && ((riseCount + fallCount) > 5)) {
			if (redisTemplate.hasKey(msg)) {
				return;
			}
			sendingMsg(msg);
			redisTemplate.opsForValue().set(msg, "", bingMoveCounterType.getNoticeCacheLivingSeconds(),
					TimeUnit.SECONDS);
		}
	}

	@Override
	public void getCryptoCoinOptionFromCthulhu() {
		HttpUtil h = new HttpUtil();
		BaseStrDTO dto = new BaseStrDTO();
		dto.setStr(bbtDynamicKey.createKey());
		JSONObject json = JSONObject.fromObject(dto);
		try {
			String responseStr = h.sendPostRestful(systemOptionService.getCthulhuHostname() + CxBbtInteractionUrl.ROOT
					+ CxBbtInteractionUrl.GET_CRYPTO_COIN_OPTION, json.toString());
			optionService.refreshOption(responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<CryptoCoinPriceCommonDataBO> binanceDataConvertToCommonData(List<BinanceKLineBO> binanceDataList,
			String symbol, IntervalType intervalType) {
		List<CryptoCoinPriceCommonDataBO> list = new ArrayList<>();
		for (BinanceKLineBO binanceData : binanceDataList) {
			list.add(binanceDataConvertToCommonData__(binanceData, symbol, intervalType));
		}
		return list;
	}

	private CryptoCoinPriceCommonDataBO binanceDataConvertToCommonData__(BinanceKLineBO binanceData, String symbol,
			IntervalType intervalType) {
		// 未处理 coinType, currencyType
		CryptoCoinPriceCommonDataBO bo = new CryptoCoinPriceCommonDataBO();
		bo.setStartTime(binanceData.getOpenTime());
		bo.setEndTime(binanceData.getCloseTime());
		bo.setStartPrice(binanceData.getOpen());
		bo.setEndPrice(binanceData.getClose());
		bo.setHighPrice(binanceData.getHigh());
		bo.setLowPrice(binanceData.getLow());
		bo.setVolume(binanceData.getVolume());
		bo.setInterval(intervalType);
		return bo;
	}
}
