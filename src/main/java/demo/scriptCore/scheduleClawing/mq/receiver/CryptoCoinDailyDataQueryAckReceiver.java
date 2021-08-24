package demo.scriptCore.scheduleClawing.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import demo.baseCommon.service.CommonService;
import demo.scriptCore.scheduleClawing.service.CryptoCoinPriceService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA_QUERY)
public class CryptoCoinDailyDataQueryAckReceiver extends CommonService {

	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {

		try {
			CryptoCoinDailyDataQueryDTO dto = new Gson().fromJson(messageStr, CryptoCoinDailyDataQueryDTO.class);
			
			cryptoCoinPriceService.insertCryptoCoinDailyDataCollectEvent(dto);

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}

}
