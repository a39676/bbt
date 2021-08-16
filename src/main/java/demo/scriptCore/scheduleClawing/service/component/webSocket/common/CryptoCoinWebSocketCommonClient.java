package demo.scriptCore.scheduleClawing.service.component.webSocket.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.constant.ServerHost;
import demo.base.system.mq.producer.CxMsgAckProducer;
import demo.scriptCore.scheduleClawing.mq.sender.CryptoCoinPriceCacheDataAckProducer;
import demo.selenium.service.impl.SeleniumCommonService;
import finance.cryptoCoin.pojo.constant.CryptoCoinPriceCommonUrl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

public abstract class CryptoCoinWebSocketCommonClient extends SeleniumCommonService {

	@Autowired
	private HttpUtil httpUtil;

	@Autowired
	protected CryptoCoinPriceCacheDataAckProducer croptoCoinPriceCacheDataAckProducer;
	@Autowired
	protected CxMsgAckProducer cxMsgAckProducer;

	protected List<String> getSubscriptionList() {
		List<String> result = new ArrayList<>();
		try {
			String url = ServerHost.localHost10001 + CryptoCoinPriceCommonUrl.ROOT + CryptoCoinPriceCommonUrl.GET_SUBSCRIPTION_CATALOG;
			String response = String.valueOf(httpUtil.sendGet(url));
			JSONArray responseJ = JSONArray.fromObject(response);
			JSONObject tmpJ = null;
			for(int i = 0; i < responseJ.size(); i++) {
				try {
					tmpJ = responseJ.getJSONObject(i);
					result.add(tmpJ.getString("enShortname"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			log.error("get crypto coin subcription list error: " + e.getLocalizedMessage());
		}
		
		return result;
	}
	
}
