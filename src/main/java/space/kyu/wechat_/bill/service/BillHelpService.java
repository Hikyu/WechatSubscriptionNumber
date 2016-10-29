package space.kyu.wechat_.bill.service;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;

/**
 * 返回记账帮助菜单
 * 
 * @author yukai
 *
 */
@Service
public class BillHelpService extends AbstractTextMsgService {

	@Override
	public String dealwithRequest(String openid, String content) {
		char lineFeed = '\n';
		StringBuilder respContent = new StringBuilder();
		respContent.append("记账语法如下:").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">1.收入</a>").append(lineFeed);
		respContent.append(" 描述+数值;。比如:工资+5000;表示工资收入5000元;以分号结尾").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">2.支出</a>").append(lineFeed);
		respContent.append(" 描述-数值;。比如:话费-100;表示支出话费100元;以分号结尾").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">3.多项收入支出</a>").append(lineFeed);
		respContent.append(" 多个收入支出项目以分号分隔。比如:工资+5000;话费-100;以分号结尾").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">4.日期</a>").append(lineFeed);
		respContent.append(" 记账开头设置账目日期,以冒号分隔,若不设置日期,则以当天日期为账目日期。比如:2016-10-07:工资+5000;话费-100;").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">5.查询</a>").append(lineFeed);
		respContent.append(" 输入日期查询该日收入支出情况。比如:2016-10-07").append(lineFeed);
		respContent.append(" 输入月份查询该月收入支出情况。比如:2016-10").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">6.删除某天收支记录</a>").append(lineFeed);
		respContent.append(" 删除+日期。比如:删除2016-10-01表示删除2016-10-01这一天的所有记录，同时月度账单作出相应的调整").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">7.提示</a>").append(lineFeed);
		respContent.append(" 收支描述中不要出现 \";\"|\":\"|\"+\"|\"-\"，否则无法正确解析~~");
		respContent.append(" 请在英文状态下输入\";\"|\":\"，插入收支记录记得以分号结尾~~").append(lineFeed);
		respContent.append(" <a color=\"#C0FF3E\">8.帮助</a>").append(lineFeed);
		respContent.append(" 回复\"记账\"可查看本菜单").append(lineFeed);
		return respContent.toString();

	}

}
