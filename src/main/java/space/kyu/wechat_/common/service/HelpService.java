package space.kyu.wechat_.common.service;

import org.springframework.stereotype.Service;

@Service
public class HelpService extends AbstractTextMsgService{

	@Override
	protected String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("1.输入\"翻译\"查看翻译功能语法").append(lineFeed);
		respContent.append("2.输入\"记账\"查看记账功能语法").append(lineFeed);
		respContent.append("3.输入\"万年历\"查看万年历功能语法").append(lineFeed);
		respContent.append("4.输入\"公交\"查看公交线路查询功能语法").append(lineFeed);
		return respContent.toString();
	}
	
}
