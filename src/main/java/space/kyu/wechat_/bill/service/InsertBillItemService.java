package space.kyu.wechat_.bill.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import space.kyu.wechat_.bill.dao.BaseDao;
import space.kyu.wechat_.bill.entity.AccountDetail;
import space.kyu.wechat_.bill.entity.MonthAccount;
import space.kyu.wechat_.bill.requestfilter.DateUtil;
import space.kyu.wechat_.bill.requestfilter.RequestFilter;
import space.kyu.wechat_.common.service.AbstractTextMsgService;

/**
 * 记录收入支出明细
 * 
 * @author yukai
 *
 */
@Service
public class InsertBillItemService extends AbstractTextMsgService {

	@Autowired
	private BaseDao dao;

	@Override
	protected String dealwithRequest(String openid, String content) {
		String date = DateUtil.getDate(content);
		if (DateUtil.farFromToday(date)) {
			//输入日期大于当前日期
			return "输入日期大于当前日期~~请重输";
		}
		
		if (RequestFilter.checkRequest(openid, content)) {
			//存在相同请求，返回空
			return "相同内容的请求刚刚处理过了~~为防止重复提交的内容导致记录错乱，请10s后重试...";
		}
		
		MonthAccount monthAccount = getMonthAccount(openid, date);
		content = DateUtil.getContentWithOutDate(content);
		String[] items = content.split(";");
		List<AccountDetail> incomes = new ArrayList<AccountDetail>();
		List<AccountDetail> outlays = new ArrayList<AccountDetail>();
		//此次操作收入总金额
		int incomeNum = 0;
		//此次操作支出总金额
		int outlayNum = 0;
		for (int i = 0; i < items.length; i++) {
			String item = items[i];
			int index = -1;
			AccountDetail detail = new AccountDetail();
			detail.setDate(date);
			detail.setOpenid(openid);
			detail.setMonthAccountID(monthAccount.getId());
			if ((index = item.indexOf("+")) != -1) {
				// 收入
				String description = item.substring(0, index);
				String income = item.substring(index + 1, item.length());
				detail.setNum(income);
				detail.setDescription(description);
				incomes.add(detail);
				incomeNum += Integer.valueOf(income);
			} else if ((index = item.indexOf("-")) != -1) {
				// 支出
				String description = item.substring(0, index);
				String outlay = item.substring(index + 1, item.length());
				detail.setNum(outlay);
				detail.setDescription(description);
				outlays.add(detail);
				outlayNum += Integer.valueOf(outlay);
			}
		}

		insertBillDetail(incomes, outlays);
		updateMonthAccount(monthAccount, incomeNum, outlayNum);
		
		return "操作成功~~";
	}

	private void updateMonthAccount(MonthAccount monthAccount, int incomeNum, int outlayNum) {
		int incomeNow = Integer.valueOf(monthAccount.getIncome());
		int outlayNow = Integer.valueOf(monthAccount.getOutlay());
		monthAccount.setIncome(String.valueOf(incomeNow+incomeNum));
		monthAccount.setOutlay(String.valueOf(outlayNow+outlayNum));
		dao.updateMonthAccount(monthAccount);
	}

	private void insertBillDetail(List<AccountDetail> incomes, List<AccountDetail> outlays) {
		for (int i = 0; i < incomes.size(); i++) {
			dao.insertIncomeDetail(incomes.get(i));
		}

		for (int i = 0; i < outlays.size(); i++) {
			dao.insertOutLayDetail(outlays.get(i));
		}
	}

	private MonthAccount getMonthAccount(String openid, String date) {
		MonthAccount account = new MonthAccount();
		String month = DateUtil.getMonthFromDate(date);
		account.setMonth(month);
		account.setOpenid(openid);
	    dao.getMonthAccountID(account);
	    return account;
	}

}
