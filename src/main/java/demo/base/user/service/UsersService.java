package demo.base.user.service;

import java.util.ArrayList;
import java.util.List;

import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.vo.UsersDetailVO;


/**
 * @author Acorn
 * 2017年4月13日
 */
public interface UsersService {

	int insertFailAttempts(String userName);

	int setLockeds(Users user);
	
	int resetFailAttempts(String userName);

	Long getUserIdByUserName(String userName);
	
	/**
	 * 可输入 userName || userId(better)
	 * 2017年4月13日
	 * @param userName
	 * @param userId
	 * @return
	 * ArrayList<UserAttempts>
	 */
	ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayDTO param);
	
	Users getUserbyUserName(String userName);

	UsersDetailVO findUserDetail(Long userId);
	String findHeadImageUrl(Long userId);

	int countAttempts(String userName);

	UsersDetailVO findOtherUserDetail(OtherUserInfoDTO param);

//	/** 查找持有某种权限的用户ID 2019-06-24 增加角色/机构逻辑后,将完全删除此逻辑 */
//	List<Long> findUserIdListByRoleId(Integer roleId);
	
	/** 查找某种角色的所有userId */
	List<Long> findUserIdListByAuthId(Long authId);

	MyUserPrincipal buildMyUserPrincipalByUserName(String userName);

	List<Auth> findAuthsByUserId(Long userId);

	List<Roles> findRolesByAuthId(Long authId);

	List<Roles> findRolesByAuthIdList(List<Long> authIdList);


}
