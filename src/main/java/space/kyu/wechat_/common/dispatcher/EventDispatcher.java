package space.kyu.wechat_.common.dispatcher;

import java.util.Map;

import org.apache.log4j.Logger;

import space.kyu.wechat_.common.message.req.ReqTextMessage;
import space.kyu.wechat_.common.service.BaseService;
import space.kyu.wechat_.common.service.HelpService;
import space.kyu.wechat_.common.utils.MessageUtil;

public class EventDispatcher {
	private static Logger logger = Logger.getLogger(EventDispatcher.class);

	public static String processEvent(Map<String, String> map) {
		logger.info("start Event Dispatch...");
		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
			logger.info("关注事件...");
			BaseService<ReqTextMessage,String> service = new HelpService();
			ReqTextMessage textMessage = ReqMsgManager.getReqTextMsg(map);
			return service.executeRequest(textMessage);
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { // 取消关注事件
			logger.info("取消关注事件...");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { // 扫描二维码事件
			logger.info("扫描二维码事件...");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { // 位置上报事件
			logger.info("位置上报事件...");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
			logger.info("自定义菜单点击事件...");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { // 自定义菜单
																	// View 事件
			logger.info("自定义菜单VIEW事件...");
		}
		return "";
	}

}
