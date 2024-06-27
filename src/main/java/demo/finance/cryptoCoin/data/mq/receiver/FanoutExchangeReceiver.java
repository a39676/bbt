//package demo.finance.cryptoCoin.data.mq.receiver;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FanoutExchangeReceiver {
//
//	@RabbitListener(queues = { "CryptoCoinMonitorFanout" })
//	public void receiveMessageFromFanout1(String message) {
//		System.out.println("Received fanout 1 message: " + message);
//	}
//
//}
