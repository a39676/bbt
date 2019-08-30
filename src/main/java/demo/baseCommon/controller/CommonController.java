package demo.baseCommon.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.po.UserIp;
import demo.util.BaseUtilCustom;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public abstract class CommonController {
	
	@Autowired
	private UserIpMapper userIpMapper;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
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
	
	protected tmpIpRecord getIp(HttpServletRequest request) {
        tmpIpRecord record = new tmpIpRecord();
        record.remoteAddr = request.getRemoteAddr();
        record.forwardAddr = request.getHeader("X-FORWARDED-FOR");

        return record;
	}
	
	protected void insertVisitIp(HttpServletRequest request, String customInfo) {
		tmpIpRecord record = getIp(request);
		UserIp ui = new UserIp();
		ui.setIp(NumericUtilCustom.ipToLong(record.remoteAddr));
		ui.setForwardIp(NumericUtilCustom.ipToLong(record.forwardAddr));
		ui.setServerName(request.getServerName());
		ui.setUri(request.getRequestURI() + "/?customInfo=" + customInfo);
		ui.setUserId(baseUtilCustom.getUserId());
		
		userIpMapper.insertSelective(ui);
	}
	
	protected void insertVisitIp(HttpServletRequest request) {
		tmpIpRecord record = getIp(request);
		UserIp ui = new UserIp();
		ui.setIp(NumericUtilCustom.ipToLong(record.remoteAddr));
		ui.setForwardIp(NumericUtilCustom.ipToLong(record.forwardAddr));
		ui.setServerName(request.getServerName());
		ui.setUri(request.getRequestURI());
		ui.setUserId(baseUtilCustom.getUserId());
		
		userIpMapper.insertSelective(ui);
	}
	
	class tmpIpRecord {
		public String remoteAddr;
		public String forwardAddr;
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
