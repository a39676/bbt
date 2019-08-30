package demo.mobile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.geographical.pojo.po.GeographicalArea;
import demo.geographical.service.GeographicalService;
import demo.mobile.mapper.MobileNumMapper;
import demo.mobile.pojo.po.MobileNum;
import demo.mobile.pojo.po.MobileNumExample;
import demo.mobile.pojo.po.MobileNumExample.Criteria;
import demo.mobile.service.MobileNumService;

@Service
public class MobileNumServiceImpl extends CommonService implements MobileNumService {

	@Autowired
	private MobileNumMapper mobileNumMapper;
	
	@Autowired
	private GeographicalService geoService;
	
	@Override
	public void getMobileGeo() {
//		MobileDataBO bo = new MobileDataBO();
		
		MobileNumExample mobileExample = new MobileNumExample();
		Criteria mobileCriteria = mobileExample.createCriteria();
		mobileCriteria.andPrefixEqualTo(135).andMidNumEqualTo(125);
		List<MobileNum> mobileNumList = mobileNumMapper.selectByExample(mobileExample);
		System.out.println(mobileNumList);
		
		GeographicalArea geoPO = geoService.findGeographical(mobileNumList.get(0).getGeographicalId());
		System.out.println(geoPO);
	}
}
