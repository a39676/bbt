package demo.scriptCore.cryptoCoin.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import demo.scriptCore.cryptoCoin.service.OtherInformationCollectorService;
import demo.selenium.service.impl.AutomationTestCommonService;
import toolPack.httpHandel.HttpUtil;

@Service
public class OtherInformationCollectorServiceImpl extends AutomationTestCommonService
		implements OtherInformationCollectorService {

	@Override
	public void getFearAndGreedIndexAndSend() {
		String url = "https://alternative.me/crypto/fear-and-greed-index/";
		HttpUtil h = new HttpUtil();
		String text = "";
		String response = null;
		try {
			response = h.sendGet(url);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			text = "Can NOT get fear and greed index, E: " + e.getLocalizedMessage();
			sendingMsg(text);
			return;
		}

		Element doc = Jsoup.parse(response);
		Elements newsListDiv = doc.select("div.fng-value");
		Element tmpEle = null;
		for (int i = 0; i < newsListDiv.size(); i++) {
			tmpEle = newsListDiv.get(i);
			text += tmpEle.text() + "\n";
		}
		if (StringUtils.isBlank(text)) {
			text = "Can NOT get fear and greed index";
		}
		sendingMsg(text);
	}

}
