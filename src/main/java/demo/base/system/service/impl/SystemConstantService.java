package demo.base.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.SystemConstantMapper;
import demo.base.system.pojo.bo.SystemConstant;
import demo.baseCommon.service.CommonService;

@Service
public class SystemConstantService extends CommonService {

	@Autowired
	private SystemConstantMapper systemConstantMapper;
	
	public String getValByName(String constantName) {
		if(StringUtils.isBlank(constantName)) {
			return "";
		}
		
		if(redisTemplate.hasKey(constantName)) {
			return String.valueOf(redisTemplate.opsForValue().get(constantName));
		} else {
			SystemConstant tmpConstant = systemConstantMapper.getValByName(constantName);
			if(tmpConstant == null || StringUtils.isBlank(tmpConstant.getConstantValue())) {
				return "";
			}
			redisTemplate.opsForValue().set(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			return tmpConstant.getConstantValue();
		}
	}
	
	public String getValByName(String constantName, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantName);
		}
		return getValByName(constantName);
	}
	
	public HashMap<String, String> getValsByName(List<String> constantNames, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantNames);
			return getValsByName(constantNames);
		} else {
			List<String> realConstantNames = constantNames.stream().filter(name -> !redisTemplate.hasKey(name)).collect(Collectors.toList());
			return getValsByName(realConstantNames);
		}
		
	}

	public HashMap<String, String> getValsByName(List<String> constantNames) {
		if(constantNames == null || constantNames.isEmpty()) {
			return new HashMap<String, String>();
		}
		List<SystemConstant> queryResult = systemConstantMapper.getValsByName(constantNames);

		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(queryResult != null && queryResult.size() > 0) {
			queryResult.stream().forEach(tmpConstant -> {
				result.put(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			});
		}
		redisTemplate.opsForValue().multiSet(result);
		
		return result;
	}

	public void setValByName(String cosntantName, String constantValue) {
		redisTemplate.opsForValue().set(cosntantName, constantValue);
	}
	
	public void setValByName(String cosntantName, String constantValue, Long validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}
	
	public void setValByName(String cosntantName, String constantValue, Integer validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}
	
	public void setValByName(SystemConstant systemConstant) {
		redisTemplate.opsForValue().set(systemConstant.getConstantName(), systemConstant.getConstantValue());
	}
	
	public void setValsByName(List<SystemConstant> systemConstants) {
		Map<String, String> values = systemConstants.stream().collect(Collectors.toMap(SystemConstant::getConstantName, SystemConstant::getConstantValue));
		redisTemplate.opsForValue().multiSet(values);
	}
	
	public void deleteValByName(String constantName) {
		redisTemplate.delete(constantName);
	}

	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
}
