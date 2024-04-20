package demo.tool.service;



import jakarta.servlet.http.HttpServletRequest;
import tool.pojo.bo.IpRecordBO;

public interface VisitDataService {

	IpRecordBO getIp(HttpServletRequest request);

}
