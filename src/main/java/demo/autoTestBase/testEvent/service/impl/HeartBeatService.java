package demo.autoTestBase.testEvent.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import demo.config.costomComponent.BbtDynamicKey;
import net.sf.json.JSONObject;
import tool.pojo.constant.BbtInteractionUrl;
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
		String url = systemOptionService.getCthulhuHostname() + BbtInteractionUrl.ROOT
				+ BbtInteractionUrl.BBT_HEART_BEAT;
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
