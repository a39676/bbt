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
			if (kdjBoList.isEmpty()) {
				continue;
			}
			KdjBO lastKDJ = kdjBoList.get(kdjBoList.size() - 1);
			if (lastKDJ.getJ().compareTo(lastKDJ.getD()) > 0) {
				coinNameList.add(catalog.getCoinNameEnShort());
			}
		}
		return coinNameList;
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
}
