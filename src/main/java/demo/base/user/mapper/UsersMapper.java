package demo.base.user.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.dto.ResetFailAttemptDTO;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;

public interface UsersMapper {

	int insert(Users record);

	int insertSelective(Users record);

	int insertFailAttempts(String userName);

	int resetFailAttempts(ResetFailAttemptDTO param);

	ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayDTO param);

	int cleanAttempts(@Param("dateInput") Date dateInput);

	int lockUserWithAttempts(String userName);

	Users findUserByUserName(String userName);

	int setLockeds(Users user);

	Long getUserIdByUserName(String userName);

	Long getUserIdByUserNameOrEmail(String inputUserName);

	UsersDetail getUserDetailByUserName(String userName);

	Users findUser(Long userId);

	int countAttempts(String userName);

	int matchUserPassword(@Param("userId") Long userId, @Param("pwd") String pwd);

	int atLeastOneSuperAdministratorUser(
			@Param("authType") Integer authType,
			@Param("authName") String authName
	);

}