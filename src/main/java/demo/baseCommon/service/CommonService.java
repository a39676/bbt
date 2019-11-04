package demo.baseCommon.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.BaseResultType;
import demo.baseCommon.pojo.param.PageParam;
import demo.config.costom_component.SnowFlake;
import numericHandel.NumericUtilCustom;

public abstract class CommonService {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected SnowFlake snowFlake;
	
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	protected NumericUtilCustom numericUtil;

	private static final int normalPageSize = 10;
	private static final int maxPageSize = 300;
	protected static final long theStartTime = 946656000000L;

	protected PageParam setPageFromPageNo(Integer pageNo) {
		return setPageFromPageNo(pageNo, normalPageSize);
	}

	protected PageParam setPageFromPageNo(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 1;
		}
		if (pageSize > maxPageSize) {
			pageSize = maxPageSize;
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
		result.fillWithResult(BaseResultType.nullParam);
		return result;
	}
	
	protected CommonResult errorParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(BaseResultType.errorParam);
		return result;
	}
	
	protected CommonResult serviceError() {
		CommonResult result = new CommonResult();
		result.fillWithResult(BaseResultType.serviceError);
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
		if(os != null) {
			if(os.toLowerCase().contains("windows")) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean isLinux() {
		String os = System.getProperty("os.name");
		if(os != null) {
			if(os.toLowerCase().contains("linux")) {
				return true;
			}
		}
		return false;
	}

	protected String getSuffixName(String str) {
		if(StringUtils.isBlank(str)) {
			return "";
		}
		return str.substring(str.lastIndexOf(".") + 1);
	}

	protected String pathChangeByDetectOS(String oldPath) {
		if(isWindows()) {
			return oldPath.replaceAll("/", "\\\\");
		} else {
			return oldPath.replaceAll("\\\\", "/");
		}
	}
}
