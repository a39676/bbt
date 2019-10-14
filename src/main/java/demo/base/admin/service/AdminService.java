package demo.base.admin.service;

import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface AdminService {

	CommonResultBBT deleteUserIpRecord(UserIpDeleteDTO param);

	void loadHomepageAnnouncementStr();

	void loadHomepageAnnouncementStr(String strContent);

}
