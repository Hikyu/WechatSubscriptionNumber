package space.kyu.wechat_.common.service;

import space.kyu.wechat_.common.message.req.ReqBaseMessage;

public interface BaseServiceContext<T extends ReqBaseMessage, R> {
	public void selectService(T reqMeg);

	public R executeRequest();
}
