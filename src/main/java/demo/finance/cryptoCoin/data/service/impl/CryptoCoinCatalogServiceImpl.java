package demo.finance.cryptoCoin.data.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;

@Service
public class CryptoCoinCatalogServiceImpl extends CryptoCoinCommonService implements CryptoCoinCatalogService {

	@Autowired
	private CryptoCoinCatalogMapper mapper;

	@Override
	public CryptoCoinCatalog findCatalog(String coinName) {
		if (StringUtils.isBlank(coinName)) {
			return null;
		}
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andCoinNameEnShortEqualTo(coinName.toUpperCase()).andIsDeleteEqualTo(false);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);
		if (poList.isEmpty()) {
			return null;
		}
		return poList.get(0);
	}

	@Override
	public List<CryptoCoinCatalog> findCatalog(List<String> coinNameList) {
		if (coinNameList == null || coinNameList.isEmpty()) {
			return new ArrayList<>();
		}

		Set<String> paramNameSet = new HashSet<>();
		for (String coinName : coinNameList) {
			if (StringUtils.isNotBlank(coinName)) {
				paramNameSet.add(coinName.toUpperCase());
			}
		}

		if (paramNameSet.isEmpty()) {
			return new ArrayList<>();
		}

		coinNameList.clear();
		coinNameList.addAll(paramNameSet);

		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andCoinNameEnShortIn(coinNameList).andIsDeleteEqualTo(false);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);
		return poList;
	}

	@Override
	public CryptoCoinCatalog findCatalog(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CryptoCoinCatalog> getAllCatalog() {
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		return mapper.selectByExample(example);
	}

	@Override
	public CommonResult addCatalog(String enShortName) {
		CommonResult r = new CommonResult();
		CryptoCoinCatalog po = new CryptoCoinCatalog();
		po.setId(snowFlake.getNextId());
		po.setCoinNameEnShort(enShortName);
		try {
			int count = mapper.insertSelective(po);
			if (count > 0) {
				r.setIsSuccess();
			}
		} catch (Exception e) {
		}
		return r;
	}

}
