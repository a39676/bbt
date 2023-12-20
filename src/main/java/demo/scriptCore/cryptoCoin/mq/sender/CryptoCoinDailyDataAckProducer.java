package demo.scriptCore.cryptoCoin.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;

@Component
public class CryptoCoinDailyDataAckProducer extends CommonService {

	@SuppressWarnings("unused")
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendHistoryPrice(CryptoCoinDataDTO cryptoCoinPriceDTO) {
		if (cryptoCoinPriceDTO == null) {
			return;
		}
		
		// 2023-12-20 NOT used after deploy on local machine
//		JSONObject json = JSONObject.fromObject(cryptoCoinPriceDTO);
//		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA, json.toString());
	}

}
