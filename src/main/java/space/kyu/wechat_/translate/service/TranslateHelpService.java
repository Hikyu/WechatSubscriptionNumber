package space.kyu.wechat_.translate.service;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;

@Service
public class TranslateHelpService extends AbstractTextMsgService{

	@Override
	protected String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("翻译语法如下:").append(lineFeed);
		respContent.append("目标语种:要翻译的内容，比如").append(lineFeed);
		respContent.append("<a color=\"#C0FF3E\">en:我是祖国的花朵</a>").append(lineFeed);
		respContent.append("其中，en代表目标语种为英语，我是祖国的花朵 为要翻译的内容").append(lineFeed);
		respContent.append("目标语种可以不填写，默认为英语，比如").append(lineFeed);
		respContent.append("<a color=\"#C0FF3E\">:我是祖国的花朵</a>").append(lineFeed);
		respContent.append("输入 \"语种\"查看语种列表");
		return respContent.toString();
	}
	
}
