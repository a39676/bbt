package demo.testing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.pojo.po.Users;
import demo.baseCommon.service.CommonService;
import demo.util.BaseUtilCustom;
import net.sf.json.JSONObject;

@Service
public class TestService extends CommonService {
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;

	private static String testKey = "testKey";
	private static String testValue = "testValue";

	public void redisSet() {
		Users u = new Users();
		u.setUserName(testValue);
		JSONObject j = JSONObject.fromObject(u);
		redisTemplate.opsForValue().set(testKey, j.toString());
	}
	
	public String redisGet() {
		String testV = redisTemplate.opsForValue().get(testKey);
		testV = redisTemplate.opsForValue().get(testKey);
		return testV;
	}
	
	public void roleGetTest() {
		System.out.println(baseUtilCustom.getRoles());
		System.out.println(baseUtilCustom.getCurrentUserName());
	}
	
}
