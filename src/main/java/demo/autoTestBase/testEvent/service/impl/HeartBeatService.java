package demo.autoTestBase.testEvent.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.pojo.constant.AutoTestUrl;
import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import toolPack.httpHandel.HttpUtil;

@Service
public class HeartBeatService extends CommonService {

	@Autowired
	private SystemOptionService systemOptionService;

	public void heartbeat() {
		HttpUtil h = new HttpUtil();
		String url = systemOptionService.getCthulhuHostname() + AutoTestUrl.ROOT + AutoTestUrl.BBT_HEART_BEAT;
		try {
			h.sendGet(url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
