package demo.finance.cryptoCoin.data.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinDailyDataQueryAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(CryptoCoinDailyDataQueryDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA_QUERY, json.toString());
	}

}