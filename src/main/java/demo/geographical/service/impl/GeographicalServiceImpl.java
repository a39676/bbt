package demo.geographical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.geographical.mapper.GeographicalAreaMapper;
import demo.geographical.pojo.po.GeographicalArea;
import demo.geographical.pojo.po.example.GeographicalAreaExample;
import demo.geographical.pojo.po.example.GeographicalAreaExample.Criteria;
import demo.geographical.service.GeographicalService;

@Service
public class GeographicalServiceImpl extends CommonService implements GeographicalService {

	@Autowired
	private GeographicalAreaMapper geoMapper;
	
	@Override
	public GeographicalArea findGeographical(Long geographicalId) {
		if(geographicalId == null) {
			return new GeographicalArea();
		}
		GeographicalAreaExample example = new GeographicalAreaExample();
		Criteria c = example.createCriteria();
		c.andIdEqualTo(geographicalId);
		List<GeographicalArea> g = geoMapper.selectByExample(example);
		
		if(g == null || g.size() < 1) {
			return new GeographicalArea();
		}
		return g.get(0);
	}
}
