package demo.base.system.mq.producer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auxiliaryCommon.pojo.constant.ServiceMQConstant;
import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;

@Component
public class CxMsgAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 
	 * @param mqQueueName {@link ServiceMQConstant}
	 * @param msg
	 */
	public void sendMsgThroughMq(String mqQueueName, String msg) {
		if (StringUtils.isBlank(msg)) {
			return;
		}

		log.error("Sending telegram message through MQ, queue name: " + mqQueueName + ", msg:" + msg);

		ServiceMsgDTO dto = new ServiceMsgDTO();
		dto.setMsg(msg);
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(mqQueueName, json.toString());
	}

}
