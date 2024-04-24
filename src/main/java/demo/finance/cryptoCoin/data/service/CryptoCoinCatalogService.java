package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;

public interface CryptoCoinCatalogService {

	CryptoCoinCatalog findCatalog(String coinName);

	CryptoCoinCatalog findCatalog(Long id);

	List<CryptoCoinCatalog> getAllCatalog();

	CommonResult addCatalog(String enShortName);

	List<CryptoCoinCatalog> findCatalog(List<String> coinNameList);

}
