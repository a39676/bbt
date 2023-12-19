package demo.tool.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import demo.base.system.service.impl.SystemOptionService;
import demo.config.costomComponent.BbtDynamicKey;
import demo.tool.service.ReminderMessageService;
import net.sf.json.JSONObject;
import tool.pojo.constant.BbtInteractionUrl;
import toolPack.httpHandel.HttpUtil;

@Service
public class ReminderMessageServiceImpl implements ReminderMessageService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BbtDynamicKey bbtDynamicKey;
	@Autowired
	private SystemOptionService systemOptionService;

	@Override
	public void sendReminder(String msg) {
		HttpUtil h = new HttpUtil();
		ServiceMsgDTO dto = new ServiceMsgDTO();
		dto.setKey(bbtDynamicKey.createKey());
		dto.setMsg(msg);
		JSONObject json = JSONObject.fromObject(dto);
		try {
			h.sendPost(systemOptionService.getCthulhuHostname() + BbtInteractionUrl.ROOT
					+ BbtInteractionUrl.TEXT_MESSAGE_FORWARD, json.toString());
		} catch (Exception e) {
			log.error("Send reminder error, msg: " + msg);
			log.error(e.getLocalizedMessage());
		}
	}
}
