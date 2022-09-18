package demo.scriptCore.scheduleClawing.currencyExchangeRate.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.currencyExchangeRate.pojo.constant.CurrencyExchangeRateMQConstant;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import net.sf.json.JSONObject;

@Component
public class CurrencyExchangeRateDailyDataAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendCurrencyExchangeRateData(CurrencyExchageRateCollectResult exchangeRateDataDTO) {
		if (exchangeRateDataDTO == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(exchangeRateDataDTO);
		rabbitTemplate.convertAndSend(CurrencyExchangeRateMQConstant.CURRENCY_EXCHANGE_RATE_DAILY_DATA, json.toString());
	}

}
