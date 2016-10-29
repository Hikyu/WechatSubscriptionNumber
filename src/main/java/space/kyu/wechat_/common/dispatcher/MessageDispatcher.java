package space.kyu.wechat_.common.dispatcher;

import java.util.Map;

import org.apache.log4j.Logger;

import space.kyu.wechat_.common.message.req.ReqLocationMessage;
import space.kyu.wechat_.common.message.req.ReqTextMessage;
import space.kyu.wechat_.common.service.BaseService;
import space.kyu.wechat_.common.service.TextServiceContext;
import space.kyu.wechat_.common.utils.MessageUtil;
import space.kyu.wechat_.common.utils.SpringUtil;
import space.kyu.wechat_.map.service.LocationService;

public class MessageDispatcher {
	private static Logger logger = Logger.getLogger(MessageDispatcher.class);

	public static String processMessage(Map<String, String> map) {
		logger.info("start Message Dispatch...");
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
			logger.info("文本消息...");
			ReqTextMessage textMessage = ReqMsgManager.getReqTextMsg(map);
			TextServiceContext context = new TextServiceContext();
			context.selectService(textMessage);
			return context.executeRequest();
		}

		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
			logger.info("图片消息...");
		}

		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
			logger.info("链接消息...");
		}

		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
			logger.info("位置消息...");
			BaseService<ReqLocationMessage, String> baseService = SpringUtil.getContext().getBean(LocationService.class);
			ReqLocationMessage msg = ReqMsgManager.getReqLocationMsg(map);
			return baseService.executeRequest(msg);
		}

		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
			logger.info("视频消息...");
		}

		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
			logger.info("语音消息...");
		}
		return "";

	}

}
