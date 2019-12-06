package demo.clawing.medicine.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.clawing.medicine.mapper.MedicineFactoryMapper;
import demo.clawing.medicine.pojo.po.MedicineFactory;
import demo.clawing.medicine.pojo.po.MedicineFactoryExample;
import demo.clawing.medicine.service.ClawingSinaMedicineFactoryService;

@Service
public class ClawingSinaMedicineFactoryServiceImpl extends CommonService implements ClawingSinaMedicineFactoryService {

	@Autowired
	private MedicineFactoryMapper factoryMapper;
	
	@Override
	public Long findFactoryId(String factoryName) {
		if(StringUtils.isBlank(factoryName)) {
			return null;
		}
		MedicineFactoryExample poe = new MedicineFactoryExample();
		poe.createCriteria().andFactoryNameLike(factoryName);
		List<MedicineFactory> f = factoryMapper.selectByExample(poe);
		
		if(f == null || f.size() < 1) {
			return createFatcory(factoryName);
		} else {
			return f.get(0).getId();
		}
	}
	
	private Long createFatcory(String factoryName) {
		Long newId = snowFlake.getNextId();
		MedicineFactory po = new MedicineFactory();
		po.setId(newId);
		po.setFactoryName(factoryName);
		factoryMapper.insertSelective(po);
		
		return newId;
	}
}
