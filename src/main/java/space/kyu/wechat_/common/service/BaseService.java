package space.kyu.wechat_.common.service;

import space.kyu.wechat_.common.message.req.ReqBaseMessage;

public interface BaseService<Msg extends ReqBaseMessage,Response> {
	/**
	 * 执行请求，返回请求结果
	 * 
	 * @param map
	 * @return
	 */
	public Response executeRequest(Msg msg);
}
