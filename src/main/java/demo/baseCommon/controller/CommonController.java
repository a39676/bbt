package demo.baseCommon.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;

public abstract class CommonController {
	
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
