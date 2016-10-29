package space.kyu.wechat_.map.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.message.req.ReqBaseMessage;
import space.kyu.wechat_.common.message.req.ReqLocationMessage;
import space.kyu.wechat_.common.message.resp.RespTextMessage;
import space.kyu.wechat_.common.service.BaseService;
import space.kyu.wechat_.common.utils.MessageUtil;

@Service
public class LocationService implements BaseService<ReqLocationMessage, String> {

	@Override
	public String executeRequest(ReqLocationMessage msg) {
		String location_x = msg.getLocation_X();
		String location_y = msg.getLocation_Y();
		String label = msg.getLabel();
		StringBuilder uri = new StringBuilder();
		uri.append("http://120646e4.nat123.net/wechat/map/showRestaurant?location_x=");
		uri.append(location_x);
		uri.append("&location_y=");
		uri.append(location_y);
		uri.append("&label=");
		uri.append(label);
		String respContent = "<a href='" + uri.toString() + "'> 搜索附近... </a>";
		return getResponseMsg(msg,respContent);
	}
	
	public String getResponseMsg(ReqBaseMessage msg,String resp){
		String openid = msg.getFromUserName(); // 用户 openid
		String mpid = msg.getToUserName(); // 公众号原始 ID
		RespTextMessage respMsg = new RespTextMessage();
		respMsg.setContent(resp);
		respMsg.setCreateTime((new Date()).getTime());
		respMsg.setFromUserName(mpid);
		respMsg.setToUserName(openid);
		respMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		return MessageUtil.textMessageToXml(respMsg);
	}
}
