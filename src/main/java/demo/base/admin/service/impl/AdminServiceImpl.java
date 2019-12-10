package demo.base.admin.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.BaseResultType;
import demo.base.admin.service.AdminService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.baseCommon.pojo.result.CommonResultBBT;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserIpMapper userIpMapper;
	@Autowired
	private SystemConstantService systemConstantService;
	@Autowired
	private FileUtilCustom ioUtil;
	
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
	
	@Override
	public void loadHomepageAnnouncementStr() {
		String filePath = systemConstantService.getValByName(SystemConstantStore.homepageAnnouncement);
		File f = new File(filePath);
		if(f.exists()) {
			String strContent = ioUtil.getStringFromFile(filePath);
			systemConstantService.setValByName(SystemConstantStore.homepageAnnouncementStr, strContent);
		}
	}
	
	@Override
	public void loadHomepageAnnouncementStr(String strContent) {
		systemConstantService.setValByName(SystemConstantStore.homepageAnnouncementStr, strContent);
	}
}
