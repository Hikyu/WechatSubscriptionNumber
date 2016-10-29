package space.kyu.wechat_.translate.service;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;
import space.kyu.wechat_.translate.BaiduTranslate;

@Service
public class TranslateService extends AbstractTextMsgService {

	@Override
	protected String dealwithRequest(String openid, String content) {
		int index = content.indexOf(":");
		String to = content.substring(0, index);
		String q = content.substring(index + 1, content.length());
		String result = null;
		if (to != null && !"".equals(to)) {
			result = BaiduTranslate.translate(q, to);
		} else {
			result = BaiduTranslate.translateToEn(q);
		}
		return result;
	}

}
