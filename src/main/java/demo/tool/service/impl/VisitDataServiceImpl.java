package demo.tool.service.impl;

import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.tool.service.VisitDataService;
import jakarta.servlet.http.HttpServletRequest;
import tool.pojo.bo.IpRecordBO;

@Service
public class VisitDataServiceImpl extends CommonService implements VisitDataService {

	@Override
	public IpRecordBO getIp(HttpServletRequest request) {
		IpRecordBO record = new IpRecordBO();
		record.setRemoteAddr(request.getRemoteAddr());
		record.setForwardAddr(request.getHeader("X-FORWARDED-FOR"));

		return record;
	}

}
