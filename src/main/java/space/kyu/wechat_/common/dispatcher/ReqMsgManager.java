package space.kyu.wechat_.common.dispatcher;

import java.util.Map;

import space.kyu.wechat_.common.message.req.ReqBaseMessage;
import space.kyu.wechat_.common.message.req.ReqLocationMessage;
import space.kyu.wechat_.common.message.req.ReqTextMessage;
import space.kyu.wechat_.common.utils.MessageUtil;

public class ReqMsgManager {
	public static ReqTextMessage getReqTextMsg(Map<String, String> map) {
		ReqTextMessage textMessage = new ReqTextMessage();
		initReqBaseMsg(textMessage, map);
		String content = map.get("Content");
		textMessage.setContent(content);
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		return textMessage;
	}
	
	private static void initReqBaseMsg(ReqBaseMessage message,Map<String, String> map){
		String openid = map.get("FromUserName"); // 用户 openid
		String mpid = map.get("ToUserName"); // 公众号原始 ID
		message.setFromUserName(openid);
		message.setToUserName(mpid);
	}
	public static ReqLocationMessage getReqLocationMsg(Map<String, String> map){
		ReqLocationMessage message = new ReqLocationMessage();
		initReqBaseMsg(message, map);
		
		String location_X = map.get("Location_X");
		String location_Y = map.get("Location_Y");
		String label = map.get("Label");
		String scale = map.get("Scale");
		message.setLocation_X(location_X);
		message.setLocation_Y(location_Y);
		message.setLabel(label);
		message.setScale(scale);
		return message;
		
	}
}
