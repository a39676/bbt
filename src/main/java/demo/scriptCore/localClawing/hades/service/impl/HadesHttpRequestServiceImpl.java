package demo.scriptCore.localClawing.hades.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.baseCommon.service.CommonService;
import demo.scriptCore.localClawing.hades.pojo.dto.parameter.QueryEvaluateAchievementsEvaluatePageDTO;
import demo.scriptCore.localClawing.hades.pojo.dto.response.HadeLoginResponseDTO;
import demo.scriptCore.localClawing.hades.pojo.dto.response.QueryEvaluateAchievementsEvaluatePageResponDTO;
import demo.scriptCore.localClawing.hades.service.HadesHttpRequestService;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class HadesHttpRequestServiceImpl extends CommonService implements HadesHttpRequestService {

	public HadeLoginResponseDTO login(String username, String pwd) throws IOException {
		HttpUtil h = new HttpUtil();
		String loginUrl = "https://srmdev.haid.com.cn/usercenter/login/loginByAccount";
		JSONObject parameter = new JSONObject();
		parameter.put("account", username);
		parameter.put("password", pwd);
		String responseStr = h.sendPostRestful(loginUrl, parameter.toString());

		HadeLoginResponseDTO dto = new Gson().fromJson(responseStr, HadeLoginResponseDTO.class);

		return dto;
	}

	public QueryEvaluateAchievementsEvaluatePageResponDTO queryEvaluateAchievementsEvaluatePage(String accessToken,
			QueryEvaluateAchievementsEvaluatePageDTO dto) {
		HttpUtil u = new HttpUtil();
		String queryUrl = "https://srmdev.haid.com.cn/supplier/evaluateAchievements/evaluatePage";
		JSONObject parameter = JSONObject.fromObject(dto);
		parameter = removeKeyIfValueNull(parameter);
		String responseStr = null;
		Map<String, String> propertyMap = new HashMap<>();
		propertyMap.put("Accesstoken", accessToken);
//		propertyMap.put("Cookie", "AccessToken=" + accessToken);
		propertyMap.put("Content-Type", "application/json");
		try {
			responseStr = u.sendPost(queryUrl, parameter.toString(), propertyMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		QueryEvaluateAchievementsEvaluatePageResponDTO response = new Gson().fromJson(responseStr,
				QueryEvaluateAchievementsEvaluatePageResponDTO.class);
		
		return response;
	}

	public static void main(String[] args) throws IOException {
		HadesHttpRequestServiceImpl t = new HadesHttpRequestServiceImpl();
		HadeLoginResponseDTO loginResponse = t.login("000676", "zaq12wsx@");

		String accessToken = loginResponse.getData().getAccessToken();

		QueryEvaluateAchievementsEvaluatePageDTO queryEvaluateAchievementsEvaluatePageDTO = new QueryEvaluateAchievementsEvaluatePageDTO();
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTimeHandler lh = new LocalDateTimeHandler();
//		String startDateStr = lh.dateToStr(now.minusMonths(1), LocalDateTimeHandler.normalDateFormat);
//		String endDateStr = lh.dateToStr(now, LocalDateTimeHandler.normalDateFormat);
//		queryEvaluateAchievementsEvaluatePageDTO.setCreateStartTime(startDateStr);
//		queryEvaluateAchievementsEvaluatePageDTO.setCreateEndTime(endDateStr);
//		queryEvaluateAchievementsEvaluatePageDTO.setCreateTime(new ArrayList<>());
//		queryEvaluateAchievementsEvaluatePageDTO.getCreateTime().add(startDateStr);
//		queryEvaluateAchievementsEvaluatePageDTO.getCreateTime().add(endDateStr);
		QueryEvaluateAchievementsEvaluatePageResponDTO queryEvaluateAchievementEvaluatePageResponse = t.queryEvaluateAchievementsEvaluatePage(accessToken, queryEvaluateAchievementsEvaluatePageDTO);
		System.out.println(queryEvaluateAchievementEvaluatePageResponse);
	}

	public JSONObject removeKeyIfValueNull(JSONObject json) {
		Set<?> keys = json.keySet();
		Set<Object> nullValueKeys = new HashSet<>();
		for (Object key : keys) {
			String value = String.valueOf(json.get(key));
			if (StringUtils.isBlank(value) || value.equals("[]")) {
				nullValueKeys.add(key);
			}
		}
		
		for(Object key : nullValueKeys) {
			json.remove(key);
		}

		return json;
	}
}
