package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.po.Roles;

public interface RoleService {

	List<Roles> getRoleListFromDB();

	void __initBaseRole();

	List<Roles> getRoleListFromRedis();

	List<Roles> getRoleListFromRedis(boolean refresh);

	Roles getRoleByNameFromRedis(String roleName, boolean refresh);

	Roles getRoleByNameFromRedis(String roleName);

}
