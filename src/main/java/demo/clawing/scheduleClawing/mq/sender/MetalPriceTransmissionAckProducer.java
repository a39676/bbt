package demo.clawing.scheduleClawing.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;
import precious_metal.pojo.constant.PreciousMetalMQConstant;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;

@Component
public class MetalPriceTransmissionAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(PreciousMetailPriceDTO preciousMetailPriceDTO) {
		if (preciousMetailPriceDTO == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(preciousMetailPriceDTO);
		rabbitTemplate.convertAndSend(PreciousMetalMQConstant.transmissionMetalPriceData, json.toString());
	}

}
