package demo.scriptCore.scheduleClawing.jobInfo.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.scriptCore.scheduleClawing.common.pojo.dto.CollectUrlHistoryDTO;
import demo.scriptCore.scheduleClawing.jobInfo.pojo.dto.V2exJobInfoOptionDTO;
import demo.selenium.service.impl.AutomationTestCommonService;
import toolPack.ioHandle.FileUtilCustom;

public abstract class JobInfoCollectionCommonService extends AutomationTestCommonService {

	protected void deleteOldUrls(String paramPathStr) {
		FileUtilCustom ioUtil = new FileUtilCustom();
		V2exJobInfoOptionDTO dto = null;

		int overloadCounting = 0;

		try {
			String content = ioUtil.getStringFromFile(paramPathStr);
			dto = buildObjFromJsonCustomization(content, V2exJobInfoOptionDTO.class);
		} catch (Exception e) {
			log.error("Read EducationInfoOptionDTO record error");
			return;
		}

		List<CollectUrlHistoryDTO> urlHistory = dto.getUrlHistory();
		if (urlHistory == null || urlHistory.size() < dto.getMaxHistorySize()) {
			return;
		}

		Collections.sort(urlHistory);

		overloadCounting = urlHistory.size() - dto.getMaxHistorySize();

		urlHistory = urlHistory.subList(overloadCounting, urlHistory.size());

		dto.setUrlHistory(urlHistory);

		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting()
				.create();
		String jsonString = gson.toJson(dto);
		ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), paramPathStr);
	}
}
