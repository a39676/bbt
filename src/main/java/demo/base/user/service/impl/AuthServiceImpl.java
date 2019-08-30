package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.AuthMapper;
import demo.base.user.pojo.dto.FindAuthsConditionDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.type.AuthTypeType;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;
import demo.baseCommon.service.CommonService;

@Service
public class AuthServiceImpl extends CommonService implements AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private AuthRoleService authRoleService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public Long __createBaseSuperAdminAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.SUPER_ADMIN, RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_ADMIN);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseAdminAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.ADMIN, RolesType.ROLE_ADMIN);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseUserActiveAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.USER, RolesType.ROLE_USER, RolesType.ROLE_USER_ACTIVE);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseUserAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.USER, RolesType.ROLE_USER);
		return newAuthID;
	}
	
	@Override
	public Long __createBasePosterAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.POSTER, RolesType.ROLE_USER, RolesType.ROLE_POSTER);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseDelayPosterAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.DELAY_POSTER, RolesType.ROLE_USER, RolesType.ROLE_POSTER, RolesType.ROLE_DELAY_POSTER);
		return newAuthID;
	}
	
	private Long __createBaseAuth(Long supserAdminUserId, AuthType authType, RolesType... roleTypes) {
		Auth newBaseAuth = new Auth();
		Long newAuthID = snowFlake.getNextId();
		newBaseAuth.setId(newAuthID);
		newBaseAuth.setAuthName(authType.getName());
		newBaseAuth.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		newBaseAuth.setCreateBy(newAuthID);
		
		int count = authMapper.insertSelective(newBaseAuth);
		if(count < 1) {
			log.error("create base %s auth error", authType.getName());
			return null;
		} 
		
		Long newAuthRoleId = null;
		Roles role = null;
		
		for(RolesType roleType : roleTypes) {
			role = roleService.getRoleByNameFromRedis(roleType.getName());
			if(role == null) {
				log.error("bind base %s auth role error, role %s not exists", authType.getName(), roleType.getName());
				return null;
			}
			newAuthRoleId = authRoleService.insertAuthRole(newAuthID, role.getRoleId(), supserAdminUserId);
			if(newAuthRoleId == null) {
				log.error("bind base %s auth role error", authType.getName());
				return null;
			}
		}
		
		return newAuthID;
	}
	
	@Override
	public List<Auth> findSuperAdministratorAuth() {
		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_SUPER_ADMIN.getName());
		if(r == null) {
			return new ArrayList<Auth>();
		}
		
		FindAuthsConditionDTO dto = new FindAuthsConditionDTO();
		dto.setAuthName(AuthType.SUPER_ADMIN.getName());
		dto.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		dto.setRoleId(r.getRoleId());
		
		List<Auth> authList = findAuthsByCondition(dto);
		if(authList == null) {
			return new ArrayList<Auth>();
		}
		return authList;
	}
	
	@Override
	public List<Auth> findAuthsByCondition(FindAuthsConditionDTO dto) {
		List<Auth> result = authMapper.findAuthsByCondition(dto);
		return result;
	}
	
	@Override
	public List<Auth> findAuthsByCondition(AuthType authType) {
		FindAuthsConditionDTO dto = new FindAuthsConditionDTO();
		dto.setAuthName(authType.getName());
		dto.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		List<Auth> result = authMapper.findAuthsByCondition(dto);
		return result;
	}
}
