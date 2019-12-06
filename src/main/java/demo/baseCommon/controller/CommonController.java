package demo.baseCommon.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.po.UserIp;
import demo.config.costom_component.BaseUtilCustom;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;
import tool.pojo.bo.IpRecordBO;

public abstract class CommonController {
	
	@Autowired
	private UserIpMapper userIpMapper;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	@Autowired
	private NumericUtilCustom numericUtil;
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	protected JSONObject getJson(String data) {
		JSONObject json;
		try {
			json = JSONObject.fromObject(data);
		} catch (Exception e) {
			e.printStackTrace();
			json = JSONObject.fromObject("{\"data\":\"wrong data\"}");
		}
		return json;
	}
	
	protected void outputStr(HttpServletResponse response, String str) {
		try {
			response.getWriter().println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void outputJson(HttpServletResponse response, JSONObject json) {
		try {
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void outputJson(HttpServletResponse response, JSONArray jsonArray) {
		try {
			response.getWriter().println(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected IpRecordBO getIp(HttpServletRequest request) {
		IpRecordBO record = new IpRecordBO();
        record.setRemoteAddr(request.getRemoteAddr());
        record.setForwardAddr(request.getHeader("X-FORWARDED-FOR"));

        return record;
	}
	
	protected void insertVisitIp(HttpServletRequest request, String customInfo) {
		IpRecordBO record = getIp(request);
		UserIp ui = new UserIp();
		ui.setIp(numericUtil.ipToLong(record.getRemoteAddr()));
		ui.setForwardIp(numericUtil.ipToLong(record.getForwardAddr()));
		ui.setServerName(request.getServerName());
		if(StringUtils.isNotBlank(customInfo)) {
			ui.setUri(request.getRequestURI());
		} else {
			ui.setUri(request.getRequestURI() + "/?customInfo=" + customInfo);
		}
		ui.setUserId(baseUtilCustom.getUserId());
		
		userIpMapper.insertSelective(ui);
	}
	
	protected void insertVisitIp(HttpServletRequest request) {
		insertVisitIp(request, null);
	}
	
	protected String foundHostNameFromRequst(HttpServletRequest request) {
		String url = request.getServerName();
		Pattern p = Pattern.compile("(?!:http://)(www\\.[0-9a-zA-Z_]+\\.[a-z]{1,8})(?!:/.*)");
		Matcher m = p.matcher(url);
		if(m.find()) {
			return m.group(0);
		} else {
			return "";
		}
	}
}
