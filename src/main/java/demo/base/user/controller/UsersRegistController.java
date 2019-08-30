package demo.base.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.base.system.pojo.constant.BaseStatusCode;
import demo.base.user.UserViewConstants;
import demo.base.user.pojo.constant.UsersUrlConstant;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.service.UserRegistService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.util.BaseUtilCustom;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = UsersUrlConstant.root)
public class UsersRegistController extends CommonController {
	
	@Autowired
	private UserRegistService userRegistService;
//	@Autowired
//	private MailService mailService;
//	@Autowired
//	private SystemConstantService systemConstantService;
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@GetMapping(value = {UsersUrlConstant.userNameExistCheck})
	public void userNameExistCheck(
			@RequestBody String jsonStrInput, 
			HttpServletResponse response
			) throws IOException {
		// 建输出流
		PrintWriter out = response.getWriter();
		
		JSONObject jsonInput = JSONObject.fromObject(jsonStrInput);
		String userName = jsonInput.getString("userName");
		JSONObject json = new JSONObject();
		boolean result = userRegistService.isUserExists(userName);
		if(result) {
			json.put("result", BaseStatusCode.fail);
		} else {
			json.put("exception", "user name not exist");
		}
		
		out.print(json);
	}
	
	@PostMapping(value = UsersUrlConstant.modifyRegistMail)
	public void modifyRegistMail(@RequestBody String data, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		
		CommonResult result = new CommonResult();
		JSONObject jsonOutput;
		if(!baseUtilCustom.isLoginUser() || !jsonInput.containsKey("modifyRegistMail")) {
			result.fillWithResult(ResultType.errorParam);
			jsonOutput = JSONObject.fromObject(result);
			outputJson(response, jsonOutput);
			return;
		}
		
		result = userRegistService.modifyRegistEmail(baseUtilCustom.getUserId(), jsonInput.getString("modifyRegistMail"));
		if(result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			baseUtilCustom.modifyAuthDetail("modifyRegistMail", "modifyRegistMail");
		}
		jsonOutput = JSONObject.fromObject(result);
		outputJson(response, jsonOutput);
	}
	
	@ApiOperation(value="创建用户页面", notes="返回创建用户页面")
	@GetMapping(value = UsersUrlConstant.userRegist)
	public ModelAndView userRegistView(HttpServletRequest request) {
		insertVisitIp(request, "registGet");
		ModelAndView view = new ModelAndView();
		view.setViewName(UserViewConstants.userRegist);
		
		return view;
	}
	
	@ApiOperation(value="用户注册", notes="用户注册请求")
	@ApiImplicitParam(name = "user", value = "用户注册UserRegistParam", required = true, dataType = "UserRegistParam")
	@PostMapping(value = UsersUrlConstant.userRegist)
	public void userRegistHandler(@RequestBody UserRegistDTO param, HttpServletResponse response, HttpServletRequest request) {
		insertVisitIp(request, "registPost");
		String ip = request.getHeader("X-FORWARDED-FOR");
		
		CommonResult result = userRegistService.newUserRegist(param, ip);
		
		JSONObject jsonOutput = JSONObject.fromObject(result);
		
		try {
			response.getWriter().println(jsonOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 2018-06-28 不再向用户发送激活邮件,改为用户发送激活码到指定邮箱.
	 */
	/*
//	@GetMapping(value = UsersUrlConstant.registActivation)
//	public ModelAndView registActivation(@RequestParam(value = "mailKey", defaultValue = "")String mailKey) {
//		CommonResult serviceResult = usersService.registActivation(mailKey);
//		ModelAndView view = new ModelAndView("userJSP/userRegistActivationResult");
//		view.addObject("message", serviceResult.getMessage());
//		view.addObject("hostName", SystemConstantStore.store.get(SystemConstantStore.hostName));
//		return view;
//	}
 * */

	/* 2018-06-28 暂停向注册用户发送激活邮件,改由用户发回激活码. */
	/*
//	@PostMapping(value = UsersUrlConstant.resendRegistMail)
//	public void resendRegistMail(HttpServletResponse response) {
//		CommonResult result = new CommonResult();
//		if(!baseUtilCustom.isLoginUser() 
//				|| baseUtilCustom.getRoles().contains(RolesType.ROLE_USER_ACTIVE.getRoleName())
//				) {
//			result.fillWithResult(ResultType.notLoginUser);
//			outputJson(response, JSONObject.fromObject(result));
//			return;
//		}
//		
//		Long userId = baseUtilCustom.getUserId();
//		
//		result = usersService.resendRegistMail(userId);
//		
//		if(!result.getResult().equals(ResultType.success.getCode())) {
//			logger.debug(result.message + "userId : " + userId + " Time: " + LocalDateTime.now());
//			result.fillWithResult(ResultType.errorParam);
//			outputJson(response, JSONObject.fromObject(result));
//			return;
//		}
//		
//		result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
//		baseUtilCustom.modifyAuthDetail("modifyRegistMail", "modifyRegistMail");
//		outputJson(response, JSONObject.fromObject(result));
//		return;
//	}
 */
	
	@GetMapping(value = UsersUrlConstant.forgotPasswordOrUsername)
	public ModelAndView forgotPasswordOrUsername() {
		return new ModelAndView("userJSP/forgotPasswordOrUsername");
	}
	
	@PostMapping(value = UsersUrlConstant.forgotPassword)
	public void forgotPassword(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
		CommonResult result = new CommonResult();
		JSONObject jsonOutput;
		JSONObject jsonInput = getJson(data);

		if(!jsonInput.containsKey("email")) {
			result.fillWithResult(ResultType.errorParam);
			outputJson(response, JSONObject.fromObject(result));
			return;
		}
		
		result = userRegistService.sendForgotPasswordMail(jsonInput.getString("email"), foundHostNameFromRequst(request));
		if(result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			outputJson(response, JSONObject.fromObject(result));
			return;
		}
		
		jsonOutput = JSONObject.fromObject(result);
		outputJson(response, jsonOutput);
		return;
	}
	
	@PostMapping(value = UsersUrlConstant.forgotUsername)
	public void forgotUsername(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
		CommonResult result = new CommonResult();
		JSONObject jsonOutput;
		JSONObject jsonInput = getJson(data);

		if(!jsonInput.containsKey("email")) {
			result.fillWithResult(ResultType.errorParam);
			outputJson(response, JSONObject.fromObject(result));
			return;
		}
		
		result = userRegistService.sendForgotUsernameMail(jsonInput.getString("email"), foundHostNameFromRequst(request));
		if(result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			outputJson(response, JSONObject.fromObject(result));
			return;
		}
		
		jsonOutput = JSONObject.fromObject(result);
		outputJson(response, jsonOutput);
		return;
	}

	@GetMapping(value = UsersUrlConstant.resetPassword)
	public ModelAndView resetPassword(@RequestParam(value = "mailKey", defaultValue = "")String mailKey, RedirectAttributes redirectAttr) {
		/**
		 * 仅接受通过重置密码邮件url的访问,
		 * 用户登录后重设密码不经过此处
		 */
		ModelAndView view = new ModelAndView("userJSP/resetPassword");
		
		if(StringUtils.isBlank(mailKey)) {
			view.addObject("errorMessage", ResultType.errorParam.getName());
			return view;
		} 
		
		view.addObject("mailKey", mailKey);
		
		return view;
	}
	
	@PostMapping(value = UsersUrlConstant.resetPassword)
	public void resetPassword(@RequestBody String data, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		CommonResult result = new CommonResult();
		
		if(!jsonInput.containsKey("newPassword") || !jsonInput.containsKey("newPasswordRepeat")) {
			result.fillWithResult(ResultType.nullParam);
			outputJson(response, JSONObject.fromObject(result));
			return;
		}
		
		if(jsonInput.containsKey("mailKey") && StringUtils.isNotBlank(String.valueOf(jsonInput.getString("mailKey")))) {
			result = userRegistService.resetPasswordByMailKey(jsonInput.getString("mailKey"), jsonInput.getString("newPassword"), jsonInput.getString("newPasswordRepeat"));
		} else if (baseUtilCustom.isLoginUser()) {
			if(!jsonInput.containsKey("oldPassword")) {
				result.fillWithResult(ResultType.nullParam);
				outputJson(response, JSONObject.fromObject(result));
				return;
			}
			result = userRegistService.resetPasswordByLoginUser(baseUtilCustom.getUserId(), jsonInput.getString("oldPassword"), jsonInput.getString("newPassword"), jsonInput.getString("newPasswordRepeat"));
		} else {
			result.successWithMessage("");
			outputJson(response, JSONObject.fromObject(result));
			return;
		}
		
		outputJson(response, JSONObject.fromObject(result));
	}
	
}
