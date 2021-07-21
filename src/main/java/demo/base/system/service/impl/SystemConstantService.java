package demo.base.system.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.SystemConstantMapper;
import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.DebugStatusConstant;
import demo.baseCommon.service.CommonService;

@Scope("singleton")
@Service
public class SystemConstantService extends CommonService {

	@Autowired
	private SystemConstantMapper systemConstantMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private String envName = null;
	private Boolean isDebuging = null;
	private String shutdownKey = null;

	public String getEnvName() {
		if (StringUtils.isBlank(envName)) {
			envName = getSysValByName(SystemConstantStore.ENV_NAME);
		}
		return envName;
	}
	
	public String getEnvNameRefresh() {
		envName = getSysValByName(SystemConstantStore.ENV_NAME, true);
		return envName;
	}
	
	public boolean getIsDebuging() {
		if(isDebuging == null) {
			isDebuging = DebugStatusConstant.debuging.equals(getSysValByName(SystemConstantStore.DEBUG_STATUS));
		}
		return isDebuging;
	}
	
	public String getShutdownKey() {
		if (StringUtils.isBlank(shutdownKey)) {
			shutdownKey = getSysValByName(SystemConstantStore.SHUTDOWN_KEY);
		}
		return shutdownKey;
	}

	private String getSysValByName(String constantName) {
		String val = redisConnectService.getValByName(constantName);

		if (!val.equals("")) {
			return val;
		} else {
			SystemConstant tmpConstant = systemConstantMapper.getValByName(constantName);
			if (tmpConstant == null || StringUtils.isBlank(tmpConstant.getConstantValue())) {
				log.error("can NOT get constant: " + constantName + " from database");
				return "";
			}
			redisTemplate.opsForValue().set(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			log.error("refresh " + constantName + ", ---> " + tmpConstant.getConstantValue());
			return tmpConstant.getConstantValue();
		}
	}
	
	public String getSysValByName(String constantName, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantName);
		}
		return getSysValByName(constantName);
	}
}
