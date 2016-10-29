package space.kyu.wechat_.common.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.kyu.wechat_.common.dispatcher.EventDispatcher;
import space.kyu.wechat_.common.dispatcher.MessageDispatcher;
import space.kyu.wechat_.common.service.WeChatVerifyService;
import space.kyu.wechat_.common.utils.MessageUtil;

/**
 * 微信开放平台 https://mp.weixin.qq.com
 * 
 * @author yukai
 *
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {
	private static Logger logger = Logger.getLogger(WeChatController.class);

	@Autowired
	private WeChatVerifyService service;

	/**
	 * 微信开放平台验证url有效性
	 * 
	 * @param request
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@GET
	@RequestMapping(value = "verify", method = RequestMethod.GET)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void verify(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		logger.info("start wechat verify...");
		if (service.verify(signature, timestamp, nonce)) {
			try {
				response.getWriter().write(echostr);
				logger.info("wechat verify success!");
			} catch (IOException e) {
				logger.error("wechat verify failed!" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("wechat verify failed!");
		}
	}

	/**
	 * 接收微信用户post消息
	 * 
	 * @param request
	 * @param response
	 */
	@POST
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	public void receiveRequest(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		if (service.verify(signature, timestamp, nonce)) {
			String resp = null;
			try {
				Map<String, String> map = MessageUtil.parseXml(request);
				String msgtype = map.get("MsgType");
				if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)) {
					resp = EventDispatcher.processEvent(map); // 进入事件处理
				} else {
					resp = MessageDispatcher.processMessage(map); // 进入消息处理
				}
				logger.debug("resp:" + resp);
				response.getWriter().write(resp);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		} else {
			logger.error("错误的请求!");
		}
	}
}
