package demo.base.admin.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.admin.pojo.constant.AdminUrlConstant;
import demo.base.admin.pojo.constant.AdminViewConstants;
import demo.base.admin.service.AdminService;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.Users;
import demo.base.user.service.UsersService;
import demo.baseCommon.controller.CommonController;
import net.sf.json.JSONObject;

/**
 * @author Acorn 2017年4月15日
 * 
 */
@Controller
@RequestMapping(value = AdminUrlConstant.root)
public class AdminController extends CommonController {

	@Autowired
	private UsersService userDetailDao;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SystemConstantService systemConstantService;
	
	/**
	 * 解锁/锁定用户
	 * 
	 * @param formData
	 * @return
	 */
	@GetMapping(value = AdminUrlConstant.userManager, produces = "text/html;charset=UTF-8")
	public ModelAndView userManager() {
		ModelAndView view = new ModelAndView();

		view.addObject("title", "User manger site");
		view.addObject("message", "Nothing yet");
		view.setViewName(AdminViewConstants.userManager);

		return view;
	}

	/**
	 * 解锁/锁定用户 请求处理
	 * 
	 * @param formData
	 * @return
	 */
	@PostMapping(value = AdminUrlConstant.userManager, produces = "text/html;charset=UTF-8")
	public ModelAndView userEdit(@RequestBody MultiValueMap<String, String> formData) {
		ModelAndView view = new ModelAndView();

		Users tmpUser = new Users();
		tmpUser.setUserName(formData.get("userName").get(0));
		tmpUser.setEnable(Boolean.parseBoolean(formData.get("enable").get(0)));
		tmpUser.setAccountNonLocked(Boolean.parseBoolean(formData.get("accountNonLocked").get(0)));
		tmpUser.setAccountNonExpired(Boolean.parseBoolean(formData.get("accountNonExpired").get(0)));
		tmpUser.setCredentialsNonExpired(Boolean.parseBoolean(formData.get("credentialsNonExpired").get(0)));
		userDetailDao.setLockeds(tmpUser);

		view.addObject("message", tmpUser.getUserName());

		view.setViewName(AdminViewConstants.userManager);

		return view;
	}

	@GetMapping(value = AdminUrlConstant.dba)
	public ModelAndView dbaPage() {
		ModelAndView view = new ModelAndView();
		view.addObject("title", "Spring Security Hello World");
		view.addObject("message", "This is protected page - Database Page!");
		view.setViewName(AdminViewConstants.adminView);

		return view;
	}

	@PostMapping(value = AdminUrlConstant.deleteUserIpRecord)
	public void deleteUserIpRecord(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		UserIpDeleteDTO param = new UserIpDeleteDTO().fromJson(getJson(data));
		CommonResult result = adminService.deleteUserIpRecord(param);
		outputJson(response, JSONObject.fromObject(result));
	}

	@PostMapping(value = AdminUrlConstant.refreshSystemConstant)
	public void refreshSystemConstant(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		List<String> keys = Arrays.asList(jsonInput.getString("keys").split(" "));
		HashMap<String, String> result = systemConstantService.getValsByName(keys, true);
		outputJson(response, JSONObject.fromObject(result));
	}
	
}