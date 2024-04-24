package demo.finance.cryptoCoin.data.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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

	@Override
	public void sendAllDailyDataQuery() {
		List<String> symbolList = getSymbolListFromOptionFile();
		for (String symbol : symbolList) {
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
