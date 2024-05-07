package demo.finance.cryptoCoin.data.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.mq.producer.CryptoCoinDailyDataQueryAckProducer;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
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
	private CryptoCoinDailyDataQueryAckProducer cryptoCoinDailyDataQueryAckProducer;

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
	public void sendDailyDataQueryInSteps() {
		List<String> symbolList = getSymbolListFromOptionFile();
		String symbol = null;
		CryptoCoinCatalogExample catalogExample = null;
		List<CryptoCoinCatalog> catalogList = null;
		CryptoCoinCatalog catalog = null;
		CryptoCoinPrice1day lastData = null;
		LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
		String coinName = null;

		Set<String> keys = redisTemplate.keys(DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX + "_*");

		int counting = 0;
		int symbolIndex = 0;

		for (; symbolIndex < symbolList.size()
				&& counting < optionService.getDailyDataQueryInOneTime(); symbolIndex++, counting++) {
			symbol = symbolList.get(symbolIndex);
			coinName = symbol.replaceAll(defaultCyrrencyTypeForCryptoCoin.getName(), "");
			if (keys.contains(DAILY_DATA_QUERY_SYMBOL_KEY_PREFIX + "_" + symbol)) {
				counting--;
				continue;
			}
			catalogExample = new CryptoCoinCatalogExample();
			catalogExample.createCriteria().andCoinNameEnShortEqualTo(coinName);
			catalogList = cryptoCoinCatalogMapper.selectByExample(catalogExample);
			if (catalogList == null || catalogList.isEmpty()) {
				counting--;
				continue;
			}
			catalog = catalogList.get(0);
			lastData = cryptoCoinPrice1dayMapper.selectLastDataByCoinTypeAndCurrencyType(catalog.getId(),
					defaultCyrrencyTypeForCryptoCoin.getCode().longValue());

			if (lastData != null && !lastData.getStartTime().isBefore(todayStart)) {
				counting--;
				continue;
			}
			sendDailyDataQuery(symbol);
			setDailyDataQuerySymbolKey(symbol);
		}
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

}
