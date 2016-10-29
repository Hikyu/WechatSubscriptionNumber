package space.kyu.wechat_.bus;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;

@Service
public class BusHelpService extends AbstractTextMsgService{

	@Override
	protected String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("公交线路查询语法如下:").append(lineFeed);
		respContent.append("输入 城市+线路名称 查询该线路，如：输入  天津312").append(lineFeed);
		return respContent.toString();
	}

}
