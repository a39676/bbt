package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.mapper.RolesMapper;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.service.RoleService;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONArray;

@Service
public class RoleServiceImpl extends CommonService implements RoleService {

	@Autowired
	private RolesMapper roleMapper;
	@Autowired
	private SystemConstantService constantService;
	
	@Override
	public void __initBaseRole() {
		Roles r = null;
		for(RolesType rt : RolesType.values()) {
			r = new Roles();
			r.setIsDelete(false);
			r.setRole(rt.getName());
			r.setRoleId(snowFlake.getNextId());
			roleMapper.insertOrUpdate(r);
		}
		setRoleListFromDBToRedis();
	}
	
	@Override
	public Roles getRoleByNameFromRedis(String roleName) {
		return getRoleByNameFromRedis(roleName, false);
	}
	
	@Override
	public Roles getRoleByNameFromRedis(String roleName, boolean refresh) {
		if(StringUtils.isBlank(roleName)) {
			return null;
		}
		List<Roles> roleList = getRoleListFromRedis(refresh);
		if(roleList.size() < 1) {
			return null;
		}
		Roles r = null;
		for(int i = 0; i < roleList.size() && r == null; i++) {
			if(roleName.equals(roleList.get(i).getRole())) {
				r = roleList.get(i);
				return r;
			}
		}
		return null;
	}
	
	@Override
	public List<Roles> getRoleListFromDB() {
		
		List<Roles> roleList = roleMapper.getRoleList();
		
		if(roleList == null || roleList.isEmpty()) {
			roleList = new ArrayList<Roles>();
		}
		
		return roleList;
		
	}
	
	private void setRoleListFromDBToRedis() {
		List<Roles> roleList = getRoleListFromDB();
		
		JSONArray ja = JSONArray.fromObject(roleList);
		constantService.setValByName(SystemConstantStore.roleList, ja.toString());
	}
	
	@Override
	public List<Roles> getRoleListFromRedis() {
		return getRoleListFromRedis(false);
	}
	
	@Override
	public List<Roles> getRoleListFromRedis(boolean refresh) {
		if(refresh) {
			setRoleListFromDBToRedis();
		}
		
		String roleListStr = constantService.getValByName(SystemConstantStore.roleList);
		if(StringUtils.isBlank(roleListStr)) {
			return new ArrayList<Roles>();
		}
		
		JSONArray ja = JSONArray.fromObject(roleListStr);
		if(ja.size() < 1) {
			return new ArrayList<Roles>();
		}
		
		Gson g = new Gson();
		Roles r = null;
		List<Roles> roleList = new ArrayList<Roles>();
		for(int i = 0; i < ja.size(); i++) {
			r = g.fromJson(ja.getString(i), Roles.class);
			roleList.add(r);
		}
		
		return roleList;
	}
}
