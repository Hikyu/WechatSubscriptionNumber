package space.kyu.wechat_.calendar;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;

@Service
public class CalendarHelpService extends AbstractTextMsgService{

	@Override
	protected String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("万年历语法如下:").append(lineFeed);
		respContent.append("输入日期查看指定日期万年历，如：输入 20161001").append(lineFeed);
		respContent.append("输入\"今天\"即可查看今日万年历，如：输入 今天").append(lineFeed);
		return respContent.toString();
	}

}
