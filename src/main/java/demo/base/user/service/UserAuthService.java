package demo.base.user.service;

import demo.base.user.pojo.type.AuthType;

public interface UserAuthService {

	Long insertUserAuth(Long userId, Long authId);

	Long insertBaseUserAuth(Long userId, AuthType authType);

	int deleteUserAuth(Long userId, Long authId);

	int deleteUserAuth(Long userId, AuthType authType);

}
