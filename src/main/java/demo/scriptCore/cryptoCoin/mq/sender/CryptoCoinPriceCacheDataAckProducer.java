package demo.scriptCore.cryptoCoin.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Component
public class CryptoCoinPriceCacheDataAckProducer extends CommonService {

	@SuppressWarnings("unused")
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendPriceCacheData(CryptoCoinPriceCommonDataBO bo) {
		if (bo == null) {
			return;
		}
		
//		2023-12-20 NOT used after deploy on local machine
//		JSONObject json = JSONObject.fromObject(bo);
//		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_PRICE_CACHE_QUEUE, json.toString());
	}

}
