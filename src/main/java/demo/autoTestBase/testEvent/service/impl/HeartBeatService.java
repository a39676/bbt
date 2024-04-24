package demo.autoTestBase.testEvent.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import demo.config.customComponent.BbtDynamicKey;
import net.sf.json.JSONObject;
import tool.pojo.constant.CxBbtInteractionUrl;
import toolPack.httpHandel.HttpUtil;

@Service
public class HeartBeatService extends CommonService {

	@Autowired
	private SystemOptionService systemOptionService;
	@Autowired
	private BbtDynamicKey bbtDynamicKey;

	public void heartbeat() {
		if (systemOptionService.isDev()) {
			return;
		}
		HttpUtil h = new HttpUtil();
		String url = systemOptionService.getCthulhuHostname() + CxBbtInteractionUrl.ROOT
				+ CxBbtInteractionUrl.BBT_HEART_BEAT;
		String key = bbtDynamicKey.createKey();
		BaseStrDTO dto = new BaseStrDTO();
		dto.setStr(key);
		JSONObject json = JSONObject.fromObject(dto);
		try {
			h.sendPostRestful(url, json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
