package demo.scriptCore.scheduleClawing.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import finance.precious_metal.pojo.constant.PreciousMetalMQConstant;
import finance.precious_metal.pojo.dto.PreciousMetailPriceDTO;
import net.sf.json.JSONObject;

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
