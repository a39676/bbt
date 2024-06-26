package demo.scriptCore.scheduleClawing.currencyExchangeRate.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import finance.currencyExchangeRate.pojo.constant.CurrencyExchangeRateMQConstant;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import net.sf.json.JSONObject;
import tool.pojo.constant.CxBbtInteractionUrl;
import toolPack.httpHandel.HttpUtil;

@Component
public class CurrencyExchangeRateDailyDataAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private SystemOptionService systemOptionService;

	public void sendCurrencyExchangeRateData(CurrencyExchageRateCollectResult exchangeRateDataDTO) {
		if (exchangeRateDataDTO == null) {
			return;
		}

		JSONObject json = JSONObject.fromObject(exchangeRateDataDTO);

		log.error("sendCurrencyExchangeRateData, env: " + systemOptionService.getEnvName());
		if (systemOptionService.isRaspberry()) {
			HttpUtil h = new HttpUtil();
			json.remove("fail");
			try {
				h.sendPostRestful(systemOptionService.getCthulhuHostname() + CxBbtInteractionUrl.ROOT
						+ CxBbtInteractionUrl.RECEIVE_CURRENCY_EXCHANGE_RATE_DAILY_DATA, json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			rabbitTemplate.convertAndSend(CurrencyExchangeRateMQConstant.CURRENCY_EXCHANGE_RATE_DAILY_DATA,
					json.toString());
		}

	}

}
