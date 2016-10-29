package space.kyu.wechat_.bill.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import space.kyu.wechat_.bill.dao.BaseDao;
import space.kyu.wechat_.bill.entity.MonthAccount;
import space.kyu.wechat_.bill.requestfilter.DateUtil;
import space.kyu.wechat_.common.service.AbstractTextMsgService;

/**
 * 查询月账单
 * 
 * @author yukai
 *
 */
@Service
public class QueryMonthBillService extends AbstractTextMsgService {

	@Autowired
	private BaseDao dao;
	
	@Override
	protected String dealwithRequest(String openid, String content) {
		String date = content;//2016-10
		MonthAccount monthAccount = getMonthAccount(openid, date);
		String income = monthAccount.getIncome();
		String outlay = monthAccount.getOutlay();

		// 判断是否是本月
		String dateStart = date;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateEnd = dateFormat.format(new Date()); // 2016-10-08
		String perOutlay = outlay;
		if (dateStart.equals(DateUtil.getMonthFromDate(dateEnd))) {
			// 查询本月
			dateStart = dateStart + "-01";// 2016-10-01
			// 相差天数
			int days = Integer.valueOf(DateUtil.getDaysBetweenTwoDays(dateStart, dateEnd));
			perOutlay = days <= 0 ? outlay : String.valueOf(Integer.valueOf(outlay) / days);
		} else {
			perOutlay = String.valueOf(Integer.valueOf(outlay) / 30);
		}

		StringBuilder resp = new StringBuilder();
		resp.append(date)
		    .append("收支情况:").append('\n')
		    .append("总收入:").append(income).append('\n')
		    .append("总支出:").append(outlay).append('\n')
		    .append("平均每天消费:").append(perOutlay);
		return resp.toString();
	}

	private MonthAccount getMonthAccount(String openid, String month) {
		MonthAccount account = new MonthAccount();
		account.setMonth(month);
		account.setOpenid(openid);
		dao.getMonthAccountID(account);
		return account;
	}

}
