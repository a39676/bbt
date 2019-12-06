package demo.base.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.constant.UsersUrlConstant;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.baseCommon.controller.CommonController;
import demo.config.costom_component.BaseUtilCustom;
import demo.tool.pojo.MailRecord;
import demo.tool.service.MailService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = UsersUrlConstant.root)
public class UsersController extends CommonController {
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private MailService mailService;
	@Autowired
	private SystemConstantService systemConstantService;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@PostMapping(value = UsersUrlConstant.userInfo)
	public ModelAndView userInfo() {
		ModelAndView view = new ModelAndView("userJSP/userInfo");
		
		if(!baseUtilCustom.isLoginUser()) {
			view.setViewName(LoginUrlConstant.login);
			return view;
		}
		
		Long userId = baseUtilCustom.getUserId();
		UsersDetailVO ud = usersService.findUserDetail(userId);
		
		view.addObject("nickName", ud.getNickName());
		view.addObject("email", ud.getEmail());
		view.addObject("qq", ud.getQq());
		view.addObject("gender", ud.getGender());
		view.addObject("mobile", ud.getMobile());
		view.addObject("reservationInformation", ud.getReservationInformation());
		
		if(!baseUtilCustom.getRoles().contains(RolesType.ROLE_USER_ACTIVE.getName())) {
			MailRecord mail = mailService.findRegistActivationUnusedByUserId(userId);
			view.addObject("mailKey", mail.getMailKey());
			view.addObject("adminMail", systemConstantService.getValByName(SystemConstantStore.adminMailName));
			if(!baseUtilCustom.getAuthDetail().containsKey("modifyRegistMail")) {
				view.addObject("notActive", "notActive");
			}
		}
		
		return view;
	}
	
	@PostMapping(value = UsersUrlConstant.otherUserInfo)
	public ModelAndView otherUserInfo(@RequestBody OtherUserInfoDTO param, HttpServletRequest request, HttpServletResponse response) {
//		TODO
		ModelAndView view = new ModelAndView("userJSP/otherUserInfo");
		
		UsersDetailVO ud = usersService.findOtherUserDetail(param);
		
		view.addObject("userDetail", ud);
		view.addObject("pk", param.getPk());
		
		return view;
	}
	
	public Long getCurrentUserId() {
		String userName = baseUtilCustom.getCurrentUserName();
		if(userName == null || userName.length() == 0) {
			return null;
		} 
		
		Users currentUser = usersService.getUserbyUserName(userName);
		if(currentUser == null) {
			return null;
		}
		return currentUser.getUserId();
	}
	
	@PostMapping(value = UsersUrlConstant.isLogin)
	public void isLogin(HttpServletResponse response) {
		CommonResult result = new CommonResult();
		if(baseUtilCustom.isLoginUser()) {
			result.setIsSuccess();
		}
		outputJson(response, JSONObject.fromObject(result));
	}
	
	public String findHeadImageUrl(Long userId) {
		return usersService.findHeadImageUrl(userId);
	}

}
