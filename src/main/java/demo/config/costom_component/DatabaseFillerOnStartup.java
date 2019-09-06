package demo.config.costom_component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.base.user.pojo.constant.UserConstant;
import demo.base.user.pojo.po.Auth;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserRegistService;
import demo.base.user.service.UsersService;

@Component
public class DatabaseFillerOnStartup implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UsersService userService;
	@Autowired
	private UserRegistService userRegistService;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		if (event.getApplicationContext().getParent() == null) {
			roleService.__initBaseRole();
			
			/* 如无超级管理员角色, 初始化 */
			List<Auth> superAdminAuthList = authService.findSuperAdministratorAuth();
			Long superAdminAuthId = null;
			if(superAdminAuthList.size() < 1) {
				superAdminAuthId = authService.__createBaseSuperAdminAuth(UserConstant.noneUserId);
			} else {
				superAdminAuthId = superAdminAuthList.get(0).getId();
			}
			
			List<Long> superAdminUserIdList = userService.findUserIdListByAuthId(superAdminAuthId);
			Long superAdminId = null;
			if(superAdminUserIdList.size() < 1) {
				superAdminId = userRegistService.__baseSuperAdminRegist().getNewSuperAdminId();
			} else {
				superAdminId = superAdminUserIdList.get(0);
			}
			System.out.println(superAdminId);
		}
	}


}
