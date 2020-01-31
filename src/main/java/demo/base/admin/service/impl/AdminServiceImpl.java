package demo.base.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.BaseResultType;
import demo.base.admin.service.AdminService;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.baseCommon.pojo.result.CommonResultBBT;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserIpMapper userIpMapper;
	
	@Override
	public CommonResultBBT deleteUserIpRecord(UserIpDeleteDTO param) {
		CommonResultBBT result = new CommonResultBBT();
		if(param.getStartDate() == null || param.getEndDate() == null) {
			result.fillWithResult(BaseResultType.errorParam);
			return result;
		}
		
		int deleteCount = userIpMapper.deleteRecord(param);
		result.fillWithResult(BaseResultType.success);
		result.setMessage(String.valueOf(deleteCount));
		return result;
	}
	
}
