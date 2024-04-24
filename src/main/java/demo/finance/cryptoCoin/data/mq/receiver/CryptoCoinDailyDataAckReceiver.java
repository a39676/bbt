package demo.finance.cryptoCoin.data.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.baseCommon.service.CommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA)
public class CryptoCoinDailyDataAckReceiver extends CommonService {

	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinDataDTO dto = buildObjFromJsonCustomization(messageStr, CryptoCoinDataDTO.class);
			cryptoCoin1DayDataService.receiveDailyData(dto);
		} catch (Exception e) {
			log.error("mq error, " + CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA + ", e:" + e.getLocalizedMessage());
		}
	}
}
