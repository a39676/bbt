package demo.finance.cryptoCoin.technicalAnalysis.service;

import java.util.List;

import demo.finance.cryptoCoin.technicalAnalysis.pojo.bo.KdjBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public interface CryptoCoinTechnicalAnalysisService {

	List<KdjBO> getKdjDataList(List<CryptoCoinPriceCommonDataBO> priceDataList);

	List<String> filter();

}
