//package demo.socket.controller;
//
/*
 * 2019-05-25
 * 项目引用jar包转换---->从原配springFramework 转换到 springBoot
 * socket通讯相关暂时注释
 * socket dev mark
 */
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//
//import demo.common.controller.CommonController;
//import net.sf.json.JSONObject;
//
//@Controller
//public class SocketController extends CommonController {
//	
//	@Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//	
//	@Scheduled(fixedRate = 1000 * 60)//每隔60秒向客户端发送一次数据
//    public void sendTemplate() {
//		JSONObject json = new JSONObject();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		json.put("content", "服务器定时消息" + sdf.format(new Date()));
//        simpMessagingTemplate.convertAndSend("/topic/public", json.toString());//将消息推送给 /topic/greetings 的客户端
//    }
//    
//    @MessageMapping("/public")
//    @SendTo("/topic/public")
//    public String topicPublic(String jsonStr) throws Exception {
//    	JSONObject json = null;
//    	try {
//			json = JSONObject.fromObject(jsonStr);
//		} catch (Exception e) {
//			json = new JSONObject();
//			json.put("inputContent", "somethingWrong");
//		}
//    	
//    	json.put("content", "Hello, " + json.getString("inputContent"));
//    	
//    	return json.toString();
//    }
//    
//    @MessageMapping("/single")
//    @SendTo("/topic/single/{single}")
//    public String topicSingle(String jsonStr) throws Exception {
//    	JSONObject json = null;
//    	try {
//			json = JSONObject.fromObject(jsonStr);
//		} catch (Exception e) {
//			json = new JSONObject();
//			json.put("inputContent", "somethingWrong");
//		}
//    	
//    	json.put("content", "Hello, " + json.getString("inputContent"));
//    	
//    	return json.toString();
//    }
//
//}
