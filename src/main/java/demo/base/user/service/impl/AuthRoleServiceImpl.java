package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.AuthRoleMapper;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.AuthRoleExample;
import demo.base.user.service.AuthRoleService;
import demo.baseCommon.service.CommonService;

@Service
public class AuthRoleServiceImpl extends CommonService implements AuthRoleService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthRoleServiceImpl.class);

	@Autowired
	private AuthRoleMapper authRoleMapper;
	

	@Override
	public Long insertAuthRole(Long authId, Long roleId, Long creatorId) {
		if(authId == null || roleId == null || creatorId == null) {
			return null;
		}
		Long newAuthRoleId = snowFlake.getNextId();
		AuthRole ar = new AuthRole();
		ar.setId(newAuthRoleId);
		ar.setAuthId(authId);
		ar.setRoleId(roleId);
		ar.setCreateBy(creatorId);
		ar.setCreateTime(LocalDateTime.now());
		int count = authRoleMapper.insertSelective(ar);
		if(count < 1) {
			log.error("insert auth role error authId :" + authId + " roleId: " + roleId + "creatorId: " + creatorId);
			return null;
		}
		return newAuthRoleId;
	}
	
	@Override
	public List<AuthRole> selectByExample(AuthRoleExample example) {
		return authRoleMapper.selectByExample(example);
	}
	
	@Override
	public int deleteById(Long id) {
		AuthRole record = new AuthRole();
		record.setId(id);
		record.setIsDelete(true);
		return authRoleMapper.updateByPrimaryKeySelective(record);
	}
	
}
