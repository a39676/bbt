package demo.finance.cryptoCoin.technicalAnalysis.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.technicalAnalysis.pojo.bo.BollBO;
import demo.finance.cryptoCoin.technicalAnalysis.pojo.bo.KdjBO;
import demo.finance.cryptoCoin.technicalAnalysis.service.CryptoCoinTechnicalAnalysisService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoCoinTechnicalAnalysisServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinTechnicalAnalysisService {

	@SuppressWarnings("unused")
	@Autowired
	private CryptoCoinOptionService optionService;
	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataSummaryService;
	@Autowired
	private CryptoCoinCatalogMapper catalogMapper;

	private static final int DEFAULT_DAILY_DATA_SIZE = 60;

	@Override
	public List<String> filter() {
		List<String> coinNameList = new ArrayList<>();
		LocalDateTime defaultStartTime = LocalDateTime.now().minusDays(DEFAULT_DAILY_DATA_SIZE).with(LocalTime.MIN);
		CryptoCoinCatalogExample catalogExample = new CryptoCoinCatalogExample();
		catalogExample.createCriteria().andIsDeleteEqualTo(false);
		List<CryptoCoinCatalog> catalogList = catalogMapper.selectByExample(catalogExample);
		for (CryptoCoinCatalog catalog : catalogList) {
			List<CryptoCoinPriceCommonDataBO> commonDataList = cryptoCoin1DayDataSummaryService
					.getCommonDataList(catalog, defaultCyrrencyTypeForCryptoCoin, defaultStartTime);
			if (commonDataList == null || commonDataList.isEmpty()) {
				continue;
			}
			List<KdjBO> kdjBoList = getKdjDataList(commonDataList);
			if (!filterByKDJ(kdjBoList)) {
				log.error(catalog.getCoinNameEnShort() + ", fail with KDJ");
				continue;
			}

			List<BollBO> bollBoList = getBollDataList(commonDataList);
			if (!filterByBoll(bollBoList)) {
				log.error(catalog.getCoinNameEnShort() + ", fail with BOLL");
				continue;
			}

			coinNameList.add(catalog.getCoinNameEnShort());

		}
		
		if(coinNameList.isEmpty()) {
			coinNameList.add("Can NOT found any symbol match this condition");
		}
		
		return coinNameList;
	}

	private boolean filterByKDJ(List<KdjBO> kdjBoList) {
		if (kdjBoList.isEmpty()) {
			return false;
		}
		KdjBO lastKDJ = kdjBoList.get(kdjBoList.size() - 1);
		return lastKDJ.getJ().compareTo(lastKDJ.getD()) > 0;
	}

	@Override
	public List<KdjBO> getKdjDataList(List<CryptoCoinPriceCommonDataBO> priceDataList) {
		List<KdjBO> resultList = new ArrayList<>();
		if (priceDataList.isEmpty()) {
			return resultList;
		}
		BigDecimal rsv = null;
		BigDecimal rsvDividend = null;
		BigDecimal rsvDivisor = null;
		BigDecimal k = new BigDecimal(50);
		BigDecimal d = new BigDecimal(50);
		BigDecimal j = k.multiply(new BigDecimal(3)).subtract(d.multiply(new BigDecimal(2)));
		for (int i = 0; i < priceDataList.size(); i++) {
			CryptoCoinPriceCommonDataBO data = priceDataList.get(i);
			KdjBO bo = new KdjBO();
			rsvDividend = data.getEndPrice().subtract(data.getLowPrice());
			rsvDivisor = data.getHighPrice().subtract(data.getLowPrice());
			rsv = rsvDividend.divide(rsvDivisor, SCALE_FOR_CALCULATE, RoundingMode.HALF_UP);
			k = k.multiply(new BigDecimal(2)).divide(new BigDecimal(3), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP)
					.add(rsv.divide(new BigDecimal(3), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP));
			d = d.multiply(new BigDecimal(2)).divide(new BigDecimal(3), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP)
					.add(k.divide(new BigDecimal(3), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP));
			j = k.multiply(new BigDecimal(3)).add(d.multiply(new BigDecimal(2)));
			bo.setRsv(rsv);
			bo.setK(k);
			bo.setD(d);
			bo.setJ(j);
			resultList.add(bo);
		}

		return resultList;
	}

	private boolean filterByBoll(List<BollBO> bollBoList) {
		int bollMinSize = 5;
		if (bollBoList.size() < bollMinSize) {
			return false;
		}
		List<BollBO> subBoList = bollBoList.subList(bollBoList.size() - 4, bollBoList.size() - 1);
		boolean bollGettingWildAndRising = true;
		BollBO tmpBO = null;
		BigDecimal lastGap = BigDecimal.ZERO;
		BigDecimal lastMA = BigDecimal.ZERO;
		BigDecimal newGap = null;
		BigDecimal newMA = null;
		for (int i = 0; i < subBoList.size() && bollGettingWildAndRising; i++) {
			tmpBO = subBoList.get(i);
			newGap = tmpBO.getUpper().subtract(tmpBO.getLower());
			newMA = tmpBO.getMa();
			bollGettingWildAndRising = (lastGap.compareTo(newGap) < 0) && (lastMA.compareTo(newMA) < 0);
			if (bollGettingWildAndRising) {
				lastGap = newGap;
				lastMA = newMA;
			} else {
				continue;
			}
		}
		return bollGettingWildAndRising;
	}

	@Override
	public List<BollBO> getBollDataList(List<CryptoCoinPriceCommonDataBO> priceDataList) {
		int defaultMaSize = 20;
		BigDecimal defaultK = new BigDecimal(2);
		List<BollBO> bollDataList = new ArrayList<>();
		if (priceDataList == null || priceDataList.size() < defaultMaSize) {
			return bollDataList;
		}
		BollBO bollData = null;

		for (int i = 0; i < priceDataList.size() - defaultMaSize; i++) {
			List<CryptoCoinPriceCommonDataBO> subDataList = priceDataList.subList(i, i + defaultMaSize);
			List<BigDecimal> closePriceList = getClosePriceList(subDataList);
			BigDecimal avgClose = getAveOfClosePrice(closePriceList);
			BigDecimal variance = getVarianceOfClosePrice(closePriceList, avgClose);
			BigDecimal standardDeviation = new BigDecimal(Math.sqrt(variance.doubleValue()));

			bollData = new BollBO();
			bollData.setMa(avgClose);
			bollData.setUpper(avgClose.add(standardDeviation.multiply(defaultK)));
			bollData.setLower(avgClose.subtract(standardDeviation.multiply(defaultK)));
			bollDataList.add(bollData);
		}

		return bollDataList;
	}

	private List<BigDecimal> getClosePriceList(List<CryptoCoinPriceCommonDataBO> dataList) {
		List<BigDecimal> closePriceList = new ArrayList<>();
		for (CryptoCoinPriceCommonDataBO data : dataList) {
			closePriceList.add(data.getEndPrice());
		}
		return closePriceList;
	}

	private BigDecimal getAveOfClosePrice(List<BigDecimal> dataList) {
		BigDecimal total = BigDecimal.ZERO;
		for (BigDecimal data : dataList) {
			total = total.add(data);
		}
		BigDecimal avg = total.divide(new BigDecimal(dataList.size()), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP);
		return avg;
	}

	private BigDecimal getVarianceOfClosePrice(List<BigDecimal> dataList, BigDecimal avg) {
		BigDecimal variance = BigDecimal.ZERO;
		for (int i = 0; i < dataList.size(); i++) {
			variance = variance.add(new BigDecimal(Math.pow(dataList.get(i).subtract(avg).doubleValue(), 2)));
		}
		variance = variance.divide(new BigDecimal(dataList.size()), SCALE_FOR_CALCULATE, RoundingMode.HALF_UP);

		return variance;
	}
}
