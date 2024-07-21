package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mq.producer.CxMsgAckProducer;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinMaxVolumeMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.mq.producer.CryptoCoinDailyDataQueryAckProducer;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinMaxVolume;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinMaxVolumeKey;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

@Service
public class CryptoCoinComplexServiceImpl extends CryptoCoinCommonService implements CryptoCoinComplexService {

	@Autowired
	private CryptoCoinCatalogMapper cryptoCoinCatalogMapper;
	@Autowired
	private CryptoCoinPrice1dayMapper cryptoCoinPrice1dayMapper;
	@Autowired
	private CryptoCoinMaxVolumeMapper cryptoCoinMaxVolumeMapper;
	@Autowired
	private CryptoCoinDailyDataQueryAckProducer cryptoCoinDailyDataQueryAckProducer;
	@Autowired
	private CxMsgAckProducer cxMsgAckProducer;

	private static final String DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX = "dailyDataQuerying";

	@Override
	public void sendDailyDataQuery(String symbol) {
		CryptoCoinPrice1day lastData = findLastData(symbol);
		Long defaultStartTime = null;
		if (lastData == null) {
			defaultStartTime = localDateTimeHandler
					.localDateTimeToDate(LocalDateTime.now().minusYears(3).with(LocalTime.MIN)).getTime();
		} else {
			defaultStartTime = localDateTimeHandler.localDateTimeToDate(lastData.getStartTime().with(LocalTime.MIN))
					.getTime();
		}
		CryptoCoinDailyDataQueryDTO dto = new CryptoCoinDailyDataQueryDTO();
		dto.setDataSourceCode(CryptoCoinDataSourceType.BINANCE.getCode());
		dto.setCurrencyName(optionService.getDefaultCurrency());
		dto.setCoinName(symbol.replaceAll(optionService.getDefaultCurrency(), ""));
		dto.setStartTime(defaultStartTime);
		dto.setEndTime(new Date().getTime());
		cryptoCoinDailyDataQueryAckProducer.send(dto);
	}

	@Override
	public void sendDailyDataQuerys() {
		List<String> symbolList = getSymbolListFromOptionFile();
		String symbol = null;
		CryptoCoinCatalogExample catalogExample = null;
		List<CryptoCoinCatalog> catalogList = null;
		CryptoCoinCatalog catalog = null;
		CryptoCoinPrice1day lastData = null;
		LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
		String coinName = null;

		Set<String> keys = redisTemplate.keys(DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX + "_*");

//		int counting = 0;
		int symbolIndex = 0;

		for (; symbolIndex < symbolList.size()
//				&& counting < optionService.getDailyDataQueryInOneTime()
		; symbolIndex++) {
			symbol = symbolList.get(symbolIndex);
			coinName = symbol.replaceAll(defaultCyrrencyTypeForCryptoCoin.getName(), "");
			if (keys.contains(DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX + "_" + symbol)) {
				continue;
			}
			catalogExample = new CryptoCoinCatalogExample();
			catalogExample.createCriteria().andCoinNameEnShortEqualTo(coinName);
			catalogList = cryptoCoinCatalogMapper.selectByExample(catalogExample);
			if (catalogList == null || catalogList.isEmpty()) {
				continue;
			}
			catalog = catalogList.get(0);
			lastData = cryptoCoinPrice1dayMapper.selectLastDataByCoinTypeAndCurrencyType(catalog.getId(),
					defaultCyrrencyTypeForCryptoCoin.getCode().longValue());

			if (lastData != null && !lastData.getStartTime().isBefore(todayStart)) {
				continue;
			}
			sendDailyDataQuery(symbol);
			setDailyDataQuerySymbolKey(symbol);
//			counting++;
		}
	}

	@Override
	public void reSendDailyDataQuerys() {
		Set<String> keys = redisTemplate.keys(DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX + "_*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
		sendDailyDataQuerys();
	}

	private void setDailyDataQuerySymbolKey(String symbol) {
		long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.now().with(LocalTime.MAX));
		redisTemplate.opsForValue().set(DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX + "_" + symbol, "", seconds,
				TimeUnit.SECONDS);
	}

	private List<String> getSymbolListFromOptionFile() {
		optionService.refreshOption();
		return optionService.getSymbolList();
	}

	private CryptoCoinPrice1day findLastData(String symbol) {
		String coinName = symbol.replaceAll(optionService.getDefaultCurrency(), "");
		CryptoCoinCatalogExample catalogExample = new CryptoCoinCatalogExample();
		catalogExample.createCriteria().andCoinNameEnShortEqualTo(coinName);
		List<CryptoCoinCatalog> catalogList = cryptoCoinCatalogMapper.selectByExample(catalogExample);
		if (catalogList == null || catalogList.isEmpty()) {
			CryptoCoinCatalog newCatalog = new CryptoCoinCatalog();
			newCatalog.setId(snowFlake.getNextId());
			newCatalog.setCoinNameEnShort(coinName);
			cryptoCoinCatalogMapper.insertSelective(newCatalog);
			return null;
		}
		CryptoCoinCatalog catalog = catalogList.get(0);

		CryptoCoinPrice1day lastData = cryptoCoinPrice1dayMapper.selectLastDataByCoinTypeAndCurrencyType(
				catalog.getId(), CurrencyTypeForCryptoCoin.USDT.getCode().longValue());
		return lastData;
	}

	@Override
	public void checkMaxVolume() {
		List<String> symbolList = optionService.getSymbolList();
		for (String symbol : symbolList) {
			checkMaxVolumeOfDay(symbol);
		}
	}

	private void checkMaxVolumeOfDay(String symbol) {
		String coinName = symbol.replaceAll("_", "").replaceAll("USDT", "");
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andCoinNameCnEqualTo(coinName);
		List<CryptoCoinCatalog> catalogList = cryptoCoinCatalogMapper.selectByExample(example);
		if (catalogList == null || catalogList.isEmpty()) {
			return;
		}
		CryptoCoinCatalog catalog = catalogList.get(0);
		CryptoCoinMaxVolumeKey key = new CryptoCoinMaxVolumeKey();
		key.setCoinType(catalog.getId());
		key.setCurrencyType(defaultCyrrencyTypeForCryptoCoin.getCode());
		CryptoCoinMaxVolume po = cryptoCoinMaxVolumeMapper.selectByPrimaryKey(key);
		if (po == null) {
			return;
		}

		LocalDateTime now = LocalDateTime.now().with(LocalTime.MIN);
		LocalDateTime tenDaysAgo = now.minusDays(10);
		CryptoCoinPrice1dayExample dailyDataExample = new CryptoCoinPrice1dayExample();
		dailyDataExample.createCriteria().andCoinTypeEqualTo(catalog.getId())
				.andCurrencyTypeEqualTo(defaultCyrrencyTypeForCryptoCoin.getCode())
				.andStartTimeGreaterThanOrEqualTo(tenDaysAgo).andStartTimeLessThanOrEqualTo(now);
		List<CryptoCoinPrice1day> dataList = cryptoCoinPrice1dayMapper.selectByExample(dailyDataExample);
		if (dataList == null || dataList.isEmpty()) {
			return;
		}

		CryptoCoinPrice1day lastData = dataList.get(dataList.size() - 1);
		if (po.getMaxVolume().compareTo(lastData.getVolume()) < 0) {
			String msg = catalog.getCoinNameEnShort() + ", reach max volume";
			cxMsgAckProducer.sendMsgThroughMq(CryptoCoinMQConstant.BIG_MOVE_DATA, msg);
		}

		if (dataList.size() < 5) {
			return;
		}
		BigDecimal totalVolume = BigDecimal.ZERO;
		for (int i = 1; i <= 5; i++) {
			totalVolume = totalVolume.add(dataList.get(dataList.size() - i).getVolume());
		}
		if (po.getMaxAvg5Volume().multiply(new BigDecimal(5)).compareTo(totalVolume) < 0) {
			String msg = catalog.getCoinNameEnShort() + ", reach max 5 days volume";
			cxMsgAckProducer.sendMsgThroughMq(CryptoCoinMQConstant.BIG_MOVE_DATA, msg);
		}

		if (dataList.size() < 10) {
			return;
		}
		totalVolume = BigDecimal.ZERO;
		if (po.getMaxAvg5Volume().multiply(new BigDecimal(10)).compareTo(totalVolume) < 0) {
			String msg = catalog.getCoinNameEnShort() + ", reach max 10 days volume";
			cxMsgAckProducer.sendMsgThroughMq(CryptoCoinMQConstant.BIG_MOVE_DATA, msg);
		}
	}

	@Override
	public CryptoCoinMaxVolume refreshMaxVolumeRecord(Long coinTypeId) {
		CryptoCoinMaxVolumeKey key = new CryptoCoinMaxVolumeKey();
		key.setCoinType(coinTypeId);
		key.setCurrencyType(defaultCyrrencyTypeForCryptoCoin.getCode());
		CryptoCoinMaxVolume po = cryptoCoinMaxVolumeMapper.selectByPrimaryKey(key);

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinTypeId)
				.andCurrencyTypeEqualTo(defaultCyrrencyTypeForCryptoCoin.getCode());
		List<CryptoCoinPrice1day> dataList = cryptoCoinPrice1dayMapper.selectByExample(example);
		if (dataList == null || dataList.isEmpty()) {
			return po;
		}

		boolean newRecordFlag = false;
		if (po == null) {
			newRecordFlag = true;
			po = new CryptoCoinMaxVolume();
			po.setCoinType(coinTypeId);
			po.setCurrencyType(defaultCyrrencyTypeForCryptoCoin.getCode());
		}

		BigDecimal maxVolume = BigDecimal.ZERO;
		BigDecimal maxAvg5Volume = BigDecimal.ZERO;
		BigDecimal maxAvg10Volume = BigDecimal.ZERO;
		LocalDateTime maxVolumeDate = null;
		LocalDateTime maxAvg5VolumeDate = null;
		LocalDateTime maxAvg10VolumeDate = null;

		List<BigDecimal> tmp5VolumeList = new ArrayList<>();
		List<BigDecimal> tmp10VolumeList = new ArrayList<>();

		for (int i = 0; i < dataList.size(); i++) {
			CryptoCoinPrice1day data = dataList.get(i);
			if (data.getVolume().compareTo(maxVolume) > 0) {
				maxVolume = data.getVolume();
				maxVolumeDate = data.getStartTime();
			}
			if (tmp5VolumeList.size() >= 5) {
				tmp5VolumeList.remove(0);
			}
			tmp5VolumeList.add(data.getVolume());
			BigDecimal tmp5Volume = BigDecimal.ZERO;
			for (int i5 = 0; i5 < tmp5VolumeList.size(); i5++) {
				tmp5Volume = tmp5Volume.add(tmp5VolumeList.get(i5));
			}
			tmp5Volume = tmp5Volume.divide(new BigDecimal(5), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP);
			if (maxAvg5Volume.compareTo(tmp5Volume) > 0) {
				maxAvg5Volume = tmp5Volume;
				maxAvg5VolumeDate = data.getStartTime();
			}

			if (tmp10VolumeList.size() >= 10) {
				tmp10VolumeList.remove(0);
			}
			tmp10VolumeList.add(data.getVolume());
			BigDecimal tmp10Volume = BigDecimal.ZERO;
			for (int i10 = 0; i10 < tmp10VolumeList.size(); i10++) {
				tmp10Volume = tmp10Volume.add(tmp10VolumeList.get(i10));
			}
			tmp10Volume = tmp10Volume.divide(new BigDecimal(10), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP);
			if (maxAvg10Volume.compareTo(tmp10Volume) > 0) {
				maxAvg10Volume = tmp10Volume;
				maxAvg10VolumeDate = data.getStartTime();
			}
		}

		po.setMaxVolume(maxVolume);
		po.setMaxVolumeTime(maxVolumeDate);
		po.setMaxAvg5Volume(maxAvg5Volume);
		po.setMaxAvg5VolumeTime(maxAvg5VolumeDate);
		po.setMaxAvg10Volume(maxAvg10Volume);
		po.setMaxAvg10VolumeTime(maxAvg10VolumeDate);

		if (newRecordFlag) {
			cryptoCoinMaxVolumeMapper.insertSelective(po);
		}

		return po;
	}
}
