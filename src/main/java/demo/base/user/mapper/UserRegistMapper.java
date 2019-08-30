package demo.base.user.mapper;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.po.Users;

public interface UserRegistMapper {
	
	int isUserExists(String userName);
	
	int resetPassword(@Param("pwd")String pwd, @Param("pwdd")String pwdd, @Param("userId")Long userId);
	
	int insertNewUser(Users user);

}
