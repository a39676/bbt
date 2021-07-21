package demo.base.system.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.bo.SystemConstant;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class RedisConnectService extends CommonService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public String getValByName(String constantName) {
		if (StringUtils.isBlank(constantName)) {
			log.error("constant name was empty");
			return "";
		}
		if (redisTemplate.hasKey(constantName)) {
			return String.valueOf(redisTemplate.opsForValue().get(constantName));
		} else {
			return "";
		}
	}

	public void setValByName(String cosntantName, String constantValue) {
		redisTemplate.opsForValue().set(cosntantName, constantValue);
	}

	public void setValByName(String cosntantName, String constantValue, Long validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}

	public void setValByName(String cosntantName, String constantValue, int validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}

	public void setValByName(SystemConstant systemConstant) {
		redisTemplate.opsForValue().set(systemConstant.getConstantName(), systemConstant.getConstantValue());
	}

	public void setValsByName(List<SystemConstant> systemConstants) {
		Map<String, String> values = systemConstants.stream()
				.collect(Collectors.toMap(SystemConstant::getConstantName, SystemConstant::getConstantValue));
		redisTemplate.opsForValue().multiSet(values);
	}

	public void deleteValByName(String constantName) {
		redisTemplate.delete(constantName);
	}

	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	public CommonResult refreshRedisValueFromFile(String filePath) {
		CommonResult result = new CommonResult();
		try {
			if (StringUtils.isBlank(filePath)) {
				result.failWithMessage("path error");
				return result;
			}

			File file = new File(filePath);
			if (!file.exists()) {
				result.failWithMessage("file not exists");
				return result;
			}

			FileUtilCustom ioUtil = new FileUtilCustom();
			String fileStr = ioUtil.getStringFromFile(filePath);
			JSONObject json = JSONObject.fromObject(fileStr);
			@SuppressWarnings("rawtypes")
			Set keys = json.keySet();
			String tmpKey = null;
			String tmpValue = null;
			for (Object key : keys) {
				tmpKey = String.valueOf(key);
				tmpValue = json.getString(tmpKey);
				if (StringUtils.isNotBlank(tmpKey)) {
					if (redisTemplate.hasKey(tmpKey)) {
						result.addMessage("refresh key:" + tmpKey + " , set: " + tmpValue + "\n");
					} else {
						result.addMessage("add key:" + tmpKey + " , set: " + tmpValue + "\n");
					}
					setValByName(tmpKey, tmpValue);
				} else {
					result.addMessage("detect an empty key, has value: " + tmpValue + "\n");
				}
			}

			result.setIsSuccess();
			return result;
		} catch (Exception e) {
			result.failWithMessage(e.getMessage());
			return result;
		}
	}

}
