package demo.clawing.scheduleClawing.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinMinuteDataAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendHistoryPrice(CryptoCoinDataDTO cryptoCoinPriceDTO) {
		if (cryptoCoinPriceDTO == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(cryptoCoinPriceDTO);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_MINUTE_DATA, json.toString());
	}

}
