package demo.finance.cryptoCoin.technicalAnalysis.service.impl;

import java.math.BigDecimal;
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
import demo.finance.cryptoCoin.technicalAnalysis.service.CryptoCoinTechnicalAnalysisService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.technicalAnalysis.pojo.bo.BollBO;
import finance.technicalAnalysis.pojo.bo.KdjBO;
import finance.technicalAnalysis.service.TechnicalAnalysisUnit;

@Service
public class CryptoCoinTechnicalAnalysisServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinTechnicalAnalysisService {

	@Autowired
	private TechnicalAnalysisUnit technicalAnanlysisUnit;
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
			List<KdjBO> kdjBoList = technicalAnanlysisUnit.getKdjDataList(commonDataList);
			if (!filterByKDJ(kdjBoList)) {
				log.error(catalog.getCoinNameEnShort() + ", fail with KDJ");
				continue;
			}

			List<BollBO> bollBoList = technicalAnanlysisUnit.getBollDataList(commonDataList);
			if (!filterByBoll(bollBoList)) {
				log.error(catalog.getCoinNameEnShort() + ", fail with BOLL");
				continue;
			}

			coinNameList.add(catalog.getCoinNameEnShort());

		}

		if (coinNameList.isEmpty()) {
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
}
