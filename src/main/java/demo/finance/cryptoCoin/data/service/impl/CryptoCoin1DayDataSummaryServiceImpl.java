package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import finance.common.pojo.bo.KLineCommonDataBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

@Service
public class CryptoCoin1DayDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1DayDataSummaryService {

	private final int DAY_STEP_LONG = 1;

	@Autowired
	private CryptoCoinPrice1dayMapper dataMapper;
	@Autowired
	private CryptoCoinCatalogService coinCatalogService;

	@Override
	public CommonResult receiveDailyData(CryptoCoinDataDTO dto) {
		CommonResult r = new CommonResult();
		List<CryptoCoinPriceCommonDataBO> dataList = dto.getPriceHistoryData();
		CryptoCoinDataSourceType dataSourceType = CryptoCoinDataSourceType.getType(dto.getDataSourceCode());
		if (dataSourceType == null) {
			dataSourceType = CryptoCoinDataSourceType.CRYPTO_COMPARE;
		}

		String symbol = dto.getSymbol();
		CryptoCoinCatalog coinType = coinCatalogService
				.findCatalog(symbol.replaceAll(defaultCyrrencyTypeForCryptoCoin.getName(), ""));
		CurrencyTypeForCryptoCoin currencyType = defaultCyrrencyTypeForCryptoCoin;
		if (coinType == null || currencyType == null) {
			return r;
		}

		if (!isValidData(dataList)) {
//			telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE,
//					dto.getSymbol() + ", get error data(all zero) from: " + dataSourceType.getName(),
//					TelegramStaticChatID.MY_ID);
			sendingMsg(dto.getSymbol() + ", get error data(all zero) from: " + dataSourceType.getName());
			return r;
		}

		updateSummaryData(dataList, coinType, currencyType);

		r.setIsSuccess();
		return r;
	}

	/*
	 * 2021-04-14 crypto compare 有时会返回全0数据 暂不处理此类数据 dto 应该附带数据源
	 */
	private <E extends KLineCommonDataBO> boolean isValidData(List<E> dataList) {
		if (dataList == null || dataList.isEmpty()) {
			return false;
		}
		int zeroDataCountDown = dataList.size();
		KLineCommonDataBO tmpData = null;
		for (int i = 0; i < dataList.size() && zeroDataCountDown > 0; i++) {
			tmpData = dataList.get(i);
			if (tmpData.getStartPrice().equals(BigDecimal.ZERO) && tmpData.getEndPrice().equals(BigDecimal.ZERO)
					&& tmpData.getHighPrice().equals(BigDecimal.ZERO)
					&& tmpData.getLowPrice().equals(BigDecimal.ZERO)) {
				zeroDataCountDown--;
			}
		}
		return zeroDataCountDown > 0;
	}

	private <E extends KLineCommonDataBO> void updateSummaryData(List<E> dataList, CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType) {

		LocalDateTime dataStartTime = dataList.get(0).getStartTime();
		dataStartTime = dataStartTime.with(LocalTime.MIN);
		LocalDateTime dataEndime = dataList.get(dataList.size() - 1).getStartTime();

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1day> poList = dataMapper.selectByExample(example);

		boolean dataTimeMatchFlag = false;
		KLineCommonDataBO tmpNewData = null;
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			tmpNewData = dataList.get(dataIndex);
			for (int i = 0; i < poList.size() && !dataTimeMatchFlag; i++) {
				CryptoCoinPrice1day po = poList.get(i);
				if (po.getStartTime().isEqual(tmpNewData.getStartTime().with(LocalTime.MIN))) {
					dataTimeMatchFlag = true;
					mergeDataPair(po, tmpNewData);
				}
			}
			if (!dataTimeMatchFlag) {
				insertNewData(tmpNewData, coinType, currencyType);
			}

			dataTimeMatchFlag = false;
		}

	}

	private CryptoCoinPrice1day mergeDataPair(CryptoCoinPrice1day target, KLineCommonDataBO data) {
		target.setStartPrice(data.getStartPrice());
		target.setEndPrice(data.getEndPrice());
		target.setHighPrice(data.getHighPrice());
		target.setLowPrice(data.getLowPrice());
		if (data.getVolume() != null && data.getVolume().compareTo(BigDecimal.ZERO) > 0) {
			target.setVolume(data.getVolume());
		}

		dataMapper.updateByPrimaryKeySelective(target);
		return target;
	}

	@SuppressWarnings("unused")
	private CryptoCoinPrice1day mergeDataPair(CryptoCoinPrice1day target, CryptoCoinPrice1day source) {
		if (target.getStartTime() == null || target.getStartTime().isAfter(source.getStartTime())) {
			target.setStartTime(source.getStartTime());
			target.setStartPrice(source.getStartPrice());
		}
		if (target.getEndTime() == null || target.getEndTime().isBefore(source.getEndTime())) {
			target.setEndTime(source.getEndTime());
			target.setEndPrice(source.getEndPrice());
		}
		target.setVolume(source.getVolume());
		if (target.getHighPrice() == null || target.getHighPrice().doubleValue() < source.getHighPrice().byteValue()) {
			target.setHighPrice(source.getHighPrice());
		}
		if (target.getLowPrice() == null || target.getLowPrice().doubleValue() > source.getLowPrice().byteValue()) {
			target.setLowPrice(source.getLowPrice());
		}

		dataMapper.deleteByPrimaryKey(source.getId());
		return target;
	}

	private void insertNewData(KLineCommonDataBO data, CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType) {
		CryptoCoinPrice1day po = new CryptoCoinPrice1day();
		po.setId(snowFlake.getNextId());
		po.setStartTime(data.getStartTime().with(LocalTime.MIN));
		po.setEndTime(po.getStartTime().plusDays(DAY_STEP_LONG));
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(data.getStartPrice());
		po.setEndPrice(data.getEndPrice());
		po.setHighPrice(data.getHighPrice());
		po.setLowPrice(data.getLowPrice());
		po.setVolume(data.getVolume());

		dataMapper.insertSelective(po);
	}

	@Override
	public List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);

		return dataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime, LocalDateTime endTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime).andEndTimeLessThanOrEqualTo(endTime);

		return dataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime) {
		List<CryptoCoinPrice1day> poList = getDataList(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1day po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime, LocalDateTime endTime) {
		List<CryptoCoinPrice1day> poList = getDataList(coinType, currencyType, startTime, endTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1day po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}

}
