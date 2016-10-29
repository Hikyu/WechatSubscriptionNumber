package space.kyu.wechat_.common.service;

import java.util.Date;

import space.kyu.wechat_.common.message.req.ReqTextMessage;
import space.kyu.wechat_.common.message.resp.RespTextMessage;
import space.kyu.wechat_.common.utils.MessageUtil;

public abstract class AbstractTextMsgService implements BaseService<ReqTextMessage,String> {

	@Override
	public String executeRequest(ReqTextMessage msg) {
		ReqTextMessage reqMsg = (ReqTextMessage) msg;
		String openid = msg.getFromUserName(); // 用户 openid
		String mpid = msg.getToUserName(); // 公众号原始 ID
		RespTextMessage respMsg = new RespTextMessage();
		String executeResult = dealwithRequest(openid, reqMsg.getContent());
		respMsg.setContent(executeResult);
		respMsg.setCreateTime((new Date()).getTime());
		respMsg.setFromUserName(mpid);
		respMsg.setToUserName(openid);
		respMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		return MessageUtil.textMessageToXml(respMsg);
	}

	abstract protected String dealwithRequest(String openid, String content);
}
