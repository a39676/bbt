package demo.base.admin.service;

import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.baseCommon.pojo.result.CommonResult;

public interface AdminService {

	CommonResult deleteUserIpRecord(UserIpDeleteDTO param);

	void loadHomepageAnnouncementStr();

	void loadHomepageAnnouncementStr(String strContent);

}
