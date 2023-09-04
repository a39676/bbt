package demo.scriptCore.localClawing.hades.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.hades.pojo.dto.HadesUserDTO;
import demo.selenium.service.impl.AutomationTestCommonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class HadesOptionFile extends AutomationTestCommonService {

	private List<HadesUserDTO> userList;

	public List<HadesUserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<HadesUserDTO> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "HadesOptionFile [userList=" + userList + "]";
	}

	@PostConstruct
	public void refreshOption() {
		String optionFilePath = MAIN_FOLDER_PATH + "/optionFile/automationTest/hadesOption.json";

		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			JSONObject json = JSONObject.fromObject(jsonStr);
//			HadesOptionFile tmp = new Gson().fromJson(jsonStr, HadesOptionFile.class);
//			BeanUtils.copyProperties(tmp, this);

			JSONArray userListArray = json.getJSONArray("userList");
			HadesUserDTO tmpUserDTO = null;
			for (int i = 0; i < userListArray.size(); i++) {
				JSONObject tmpJson = userListArray.getJSONObject(i);
				tmpUserDTO = new HadesUserDTO();
				tmpUserDTO.setUsername(tmpJson.getString("username"));
				tmpUserDTO.setPwd(tmpJson.getString("pwd"));
				if (this.userList == null) {
					this.userList = new ArrayList<>();
				}
				this.userList.add(tmpUserDTO);
			}

			log.error("Automation test, Hades option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Automation test, Hades option loading error: " + e.getLocalizedMessage());
		}
	}

}
