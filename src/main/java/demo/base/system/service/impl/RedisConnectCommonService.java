package demo.base.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import demo.baseCommon.service.CommonService;
import tool.pojo.bo.IpRecordBO;

public abstract class RedisConnectCommonService extends CommonService {

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	
	public void deleteValByName(String constantName) {
		redisTemplate.delete(constantName);
	}
	
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
	
	protected String buildRedisKeyPrefix(IpRecordBO record, String redisKeyPrefix) {
		return redisKeyPrefix + "_" + record.getForwardAddr() + "_" + record.getRemoteAddr();
	}
}
