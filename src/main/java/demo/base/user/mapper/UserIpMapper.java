package demo.base.user.mapper;

import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.UserIp;

public interface UserIpMapper {
    int insert(UserIp record);

	int insertSelective(UserIp record);
	
	int deleteRecord(UserIpDeleteDTO param);
}