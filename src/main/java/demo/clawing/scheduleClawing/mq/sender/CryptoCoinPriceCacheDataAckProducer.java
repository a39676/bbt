package demo.clawing.scheduleClawing.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinPriceCacheDataAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendPriceCacheData(CryptoCoinPriceCommonDataBO bo) {
		if (bo == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(bo);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_PRICE_CACHE_QUEUE, json.toString());
	}

}
