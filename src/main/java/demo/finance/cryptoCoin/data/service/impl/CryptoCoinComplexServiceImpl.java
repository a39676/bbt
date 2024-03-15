package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
import finance.common.pojo.bo.FilterPriceResult;
import finance.common.pojo.type.IntervalType;
import finance.cryptoCoin.binance.pojo.dto.KLineKeyBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoCoinComplexServiceImpl extends CryptoCoinCommonService implements CryptoCoinComplexService {

	@Autowired
	private CryptoCoinCacheDataService cacheDataServcie;

	private static final String BIG_MOVE_REDIS_KEY_PERFIX = "cryptoCoinBigMove";
	private static final String BIG_RISE_REDIS_KEY_PERFIX = "Rise";
	private static final String BIG_FALL_REDIS_KEY_PERFIX = "Fall";
	private static final String BIG_MOVE_IN_1MIN_REDIS_KEY_PERFIX = "1Min";
	private static final String BIG_MOVE_IN_5MIN_REDIS_KEY_PERFIX = "5Min";
	private static final String BIG_MOVE_IN_10MIN_REDIS_KEY_PERFIX = "10Min";
	private static final Integer BIG_MOVES_MAX_LIVING_SECONDS = 620;

	@Override
	public void deleteOldKLineDatas() {
		int oneMinDataListMaxSize = 30;
		Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> map = cacheDataServcie.getBinanceKLineCacheMap();
		for (KLineKeyBO key : map.keySet()) {
			if (IntervalType.MINUTE_1.name().equals(key.getInterval())) {
				List<CryptoCoinPriceCommonDataBO> list = map.get(key);
				while (list != null && list.size() > oneMinDataListMaxSize) {
					list.remove(0);
				}
			}
		}
	}

	@Override
	public void checkBigMoveInMinutes() {
		double maxPercentFor1Min = 1;
		double maxPercentFor5Min = 3D;
		double maxPercentFor10Min = 5D;
		Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> map = cacheDataServcie.getBinanceKLineCacheMap();
		List<CryptoCoinPriceCommonDataBO> list = null;
		FilterPriceResult filterData = null;
		String redisKey = null;
		String directKey = null;
		String timingKey = null;
		String msg = null;
		BigDecimal rate = null;
		for (KLineKeyBO key : map.keySet()) {
			list = map.get(key);
			if (list.isEmpty()) {
				continue;
			}
			filterData = filterData(list.subList(list.size() - 2, list.size() - 1));
			rate = filterData.getMaxPrice().divide(filterData.getMinPrice(), 2, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			oneMinTag: if (rate.doubleValue() > maxPercentFor1Min) {
				redisKey = BIG_MOVE_REDIS_KEY_PERFIX;
				timingKey = BIG_MOVE_IN_1MIN_REDIS_KEY_PERFIX;
				if (filterData.getMaxPriceDateTime().isAfter(filterData.getMinPriceDateTime())) {
					directKey = BIG_RISE_REDIS_KEY_PERFIX;
				} else {
					directKey = BIG_FALL_REDIS_KEY_PERFIX;
				}
				redisKey = redisKey + directKey + timingKey + key.getSymbol();
				if (redisTemplate.hasKey(redisKey)) {
					break oneMinTag;
				}
				redisTemplate.opsForValue().set(redisKey, key.getSymbol(), BIG_MOVES_MAX_LIVING_SECONDS,
						TimeUnit.SECONDS);
				msg = key.getSymbol() + " " + directKey + ", " + rate + "% in " + timingKey;
				sendingMsg(msg);
			}

			if (list.size() < 5) {
				continue;
			}
			filterData = filterData(list.subList(list.size() - 5, list.size() - 1));
			rate = filterData.getMaxPrice().divide(filterData.getMinPrice(), 2, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			fiveMinTag: if (rate.doubleValue() > maxPercentFor5Min) {
				redisKey = BIG_MOVE_REDIS_KEY_PERFIX;
				timingKey = BIG_MOVE_IN_5MIN_REDIS_KEY_PERFIX;
				if (filterData.getMaxPriceDateTime().isAfter(filterData.getMinPriceDateTime())) {
					directKey = BIG_RISE_REDIS_KEY_PERFIX;
				} else {
					directKey = BIG_FALL_REDIS_KEY_PERFIX;
				}
				redisKey = redisKey + directKey + timingKey + key.getSymbol();
				if (redisTemplate.hasKey(redisKey)) {
					break fiveMinTag;
				}
				redisTemplate.opsForValue().set(redisKey, key.getSymbol(), BIG_MOVES_MAX_LIVING_SECONDS,
						TimeUnit.SECONDS);
				msg = key.getSymbol() + " " + directKey + ", " + rate + "% in " + timingKey;
				sendingMsg(msg);
			}

			if (list.size() < 10) {
				continue;
			}
			filterData = filterData(list.subList(list.size() - 10, list.size() - 1));
			rate = filterData.getMaxPrice().divide(filterData.getMinPrice(), 2, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
			fiveMinTag: if (rate.doubleValue() > maxPercentFor10Min) {
				redisKey = BIG_MOVE_REDIS_KEY_PERFIX;
				timingKey = BIG_MOVE_IN_10MIN_REDIS_KEY_PERFIX;
				if (filterData.getMaxPriceDateTime().isAfter(filterData.getMinPriceDateTime())) {
					directKey = BIG_RISE_REDIS_KEY_PERFIX;
				} else {
					directKey = BIG_FALL_REDIS_KEY_PERFIX;
				}
				redisKey = redisKey + directKey + timingKey + key.getSymbol();
				if (redisTemplate.hasKey(redisKey)) {
					break fiveMinTag;
				}
				redisTemplate.opsForValue().set(redisKey, key.getSymbol(), BIG_MOVES_MAX_LIVING_SECONDS,
						TimeUnit.SECONDS);
				msg = key.getSymbol() + " " + directKey + ", " + rate + "% in " + timingKey;
				sendingMsg(msg);
			}
		}
	}
}
