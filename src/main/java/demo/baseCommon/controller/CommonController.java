package demo.baseCommon.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tool.pojo.bo.IpRecordBO;

public abstract class CommonController {

	protected final Logger log = LoggerFactory.getLogger(getClass());

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
		if (m.find()) {
			return m.group(0);
		} else {
			return "";
		}
	}
}
