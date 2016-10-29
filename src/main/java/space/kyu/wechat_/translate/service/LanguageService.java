package space.kyu.wechat_.translate.service;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;

@Service
public class LanguageService extends AbstractTextMsgService{

	@Override
	protected String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("zh:中文").append(lineFeed);
		respContent.append("en:英语").append(lineFeed);
		respContent.append("cht:繁体中文").append(lineFeed);
		respContent.append("yue:粤语").append(lineFeed);
		respContent.append("wyw:文言文").append(lineFeed);
		respContent.append("jp:日语").append(lineFeed);
		respContent.append("kor:韩语").append(lineFeed);
		respContent.append("fra:法语").append(lineFeed);
		respContent.append("spa:西班牙语").append(lineFeed);
		respContent.append("th:泰语").append(lineFeed);
		respContent.append("ru:俄语").append(lineFeed);
		respContent.append("de:德语").append(lineFeed);
		return respContent.toString();
	}

}
