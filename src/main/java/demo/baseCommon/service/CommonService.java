package demo.baseCommon.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.BaseResultType;
import demo.baseCommon.pojo.constant.SystemConstant;
import demo.baseCommon.pojo.param.PageParam;
import demo.config.costomComponent.SnowFlake;
import demo.tool.service.ReminderMessageService;
import net.sf.json.JSONObject;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;

public abstract class CommonService {

	@Autowired
	protected LocalDateTimeHandler localDateTimeHandler;
	@Autowired
	protected DateHandler dateHandler;
	@Autowired
	protected LocalDateTimeAdapter localDateTimeAdapter;

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected SnowFlake snowFlake;

	@Autowired
	protected NumericUtilCustom numericUtil;
	@Autowired
	protected FileUtilCustom ioUtil;

//	@Autowired
//	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;
	@Autowired
	private ReminderMessageService reminderMessageService;

	private static final int NORMAL_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 300;
	protected static final long THE_START_TIME = 946656000000L;
	protected static final String MAIN_FOLDER_PATH = SystemConstant.ROOT_PATH;

	protected PageParam setPageFromPageNo(Integer pageNo) {
		return setPageFromPageNo(pageNo, NORMAL_PAGE_SIZE);
	}

	protected PageParam setPageFromPageNo(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 1;
		}
		if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		PageParam pp = new PageParam();
		if (pageNo == 1) {
			pp.setPageStart(0);
			pp.setPageEnd(pageSize);
		} else if (pageNo > 1) {
			pp.setPageStart(pageSize * (pageNo - 1) + 1);
			pp.setPageEnd(pp.getPageStart() + pageSize);
		}
		pp.setPageSize(pageSize);
		return pp;
	}

	protected String createDateDescription(Date inputDate) {
		if (inputDate == null) {
			return "";
		}
		Long oneHourLong = 1000L * 60 * 60;
		Long timeDiff = System.currentTimeMillis() - inputDate.getTime();
		if (timeDiff < (oneHourLong / 2)) {
			return "a moment...";
		} else if (timeDiff <= oneHourLong) {
			return "not long ago";
		} else if (timeDiff <= (oneHourLong * 12)) {
			return String.valueOf(timeDiff / oneHourLong) + " hours ago";
		} else if (timeDiff <= (oneHourLong * 24)) {
			return "today";
		} else if (timeDiff <= (oneHourLong * 24 * 3)) {
			return String.valueOf(timeDiff / (oneHourLong * 24)) + " days ago";
		} else if (timeDiff <= (oneHourLong * 24 * 7)) {
			return "within a week";
		} else if (timeDiff <= (oneHourLong * 24 * 31)) {
			return "within a month";
		} else {
			return "long long ago...";
		}
	}

	protected CommonResult nullParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(BaseResultType.NULL_PARAM);
		return result;
	}

	protected CommonResult errorParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(BaseResultType.ERROR_PARAM);
		return result;
	}

	protected CommonResult serviceError() {
		CommonResult result = new CommonResult();
		result.fillWithResult(BaseResultType.SERVICE_ERROR);
		return result;
	}

	protected CommonResult normalSuccess() {
		CommonResult result = new CommonResult();
		result.normalSuccess();
		return result;
	}

	protected CommonResult notLogin() {
		CommonResult result = new CommonResult();
		result.failWithMessage("请登录后操作");
		return result;
	}

	protected boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.toLowerCase().contains("windows")) {
				return true;
			}
		}
		return false;
	}

	protected boolean isLinux() {
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.toLowerCase().contains("linux")) {
				return true;
			}
		}
		return false;
	}

	protected String getSuffixName(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return str.substring(str.lastIndexOf(".") + 1);
	}

	protected String pathChangeByDetectOS(String oldPath) {
		if (isWindows()) {
			return oldPath.replaceAll("/", "\\\\");
		} else {
			return oldPath.replaceAll("\\\\", "/");
		}
	}

	protected <T> T buildTestEventParamFromJsonCustomization(String jsonStr, Class<T> clazz) {
		String className = clazz.getSimpleName();

		try {
			JSONObject paramJson = JSONObject.fromObject(jsonStr);

			return buildObjFromJsonCustomization(paramJson.getString(className), clazz);

		} catch (Exception e) {
			String msg = String.format("Build gson error, param name: %s ", className);
			log.error(msg);
		}
		return null;

	}

	protected <T> T buildObjFromJsonCustomization(String jsonStr, Class<T> clazz) {
		String className = clazz.getSimpleName();

		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();

			return gson.fromJson(jsonStr, clazz);

		} catch (Exception e) {
			String msg = String.format("Build gson error, param name: %s ", className);
			log.error(msg);
		}
		return null;

	}

	protected void sendingMsg(String msg) {
//		TelegramBotNoticeMessageDTO dto = new TelegramBotNoticeMessageDTO();
//		dto.setId(TelegramStaticChatID.MY_ID);
//		dto.setBotName(TelegramBotType.BBT_MESSAGE.getName());
//		dto.setMsg(msg);
//		telegramMessageAckProducer.send(dto);
		log.error("Sending telegram message: " + msg);
		reminderMessageService.sendReminder(msg);
	}
}
