package space.kyu.wechat_.common.service;

import org.springframework.stereotype.Service;

/**
 * 不合法输入处理
 * @author yukai
 *
 */
@Service
public class ErrorInputService extends AbstractTextMsgService {

	@Override
	protected String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("输入的语法好像有点问题...").append(lineFeed);
		respContent.append("回复\"帮助\"查看语法说明").append(lineFeed);
		return respContent.toString();
	}

}
