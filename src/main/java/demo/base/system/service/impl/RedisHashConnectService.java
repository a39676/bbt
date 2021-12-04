package demo.base.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class RedisHashConnectService extends RedisConnectCommonService {

	public String getValByName(String redisKey, String hashKey) {
		return String.valueOf(redisTemplate.opsForHash().get(redisKey, hashKey));
	}

	public List<String> getValsByName(String redisKey) {
		List<Object> list = redisTemplate.opsForHash().values(redisKey);
		return list.stream().map(obj -> String.valueOf(obj)).collect(Collectors.toList());
	}

	public void setValByName(String redisKey, String hashKey, String constantValue) {
		redisTemplate.opsForHash().put(redisKey, hashKey, constantValue);
	}

	public void setValsByName(String redisKey, HashMap<String, String> map) {
		redisTemplate.opsForHash().putAll(redisKey, map);
	}

	public void deleteValByName(String redisKey, String hashKey) {
		redisTemplate.opsForHash().delete(redisKey, hashKey);
	}

	public void deleteValsByName(String redisKey, List<String> hashKeys) {
		redisTemplate.opsForHash().delete(redisKey, hashKeys);
	}

	public Long getSize(String redisKey) {
		return redisTemplate.opsForHash().size(redisKey);
	}
}
