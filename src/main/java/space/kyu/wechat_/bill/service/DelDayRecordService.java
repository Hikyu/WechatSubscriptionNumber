package space.kyu.wechat_.bill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import space.kyu.wechat_.bill.dao.BaseDao;
import space.kyu.wechat_.bill.entity.AccountDetail;
import space.kyu.wechat_.bill.entity.MonthAccount;
import space.kyu.wechat_.bill.requestfilter.DateUtil;
import space.kyu.wechat_.common.service.AbstractTextMsgService;
import space.kyu.wechat_.common.utils.InputMsgUtil;

@Service
public class DelDayRecordService extends AbstractTextMsgService{

	@Autowired
	private BaseDao dao;
	
	@Override
	protected String dealwithRequest(String openid, String content) {
		String date = InputMsgUtil.getDateFromDelRecordSrt(content);
		if (DateUtil.farFromToday(date)) {
			//输入日期大于当前日期
			return "输入日期大于当前日期~~请重输";
		}
		//先查询该日期的收入支出情况
		List<AccountDetail> incomes = dao.getIncomeDetailsByDay(openid, date);
		List<AccountDetail> outlays = dao.getOutLayDetailsByDay(openid, date);
		int dayIncomeNum = 0;
		int dayOutlayNum = 0;
		AccountDetail detail = null;
		if (incomes != null && incomes.size() > 0) {
			for(int i = 0; i < incomes.size(); i++) {
				detail = incomes.get(i);
				dayIncomeNum += Integer.valueOf(detail.getNum()); 
			}
		}
		
		if (outlays != null && outlays.size() > 0) {
			for(int i = 0; i < outlays.size(); i++) {
				detail = outlays.get(i);
				dayOutlayNum += Integer.valueOf(detail.getNum());
			}
		}
		
		//删除该日期收入支出记录
		dao.delDayIncomeRecord(openid, date);
		dao.delDayOutlayRecord(openid, date);
		//查询月度账单
		MonthAccount account = new MonthAccount();
		account.setMonth(DateUtil.getMonthFromDate(date));
		account.setOpenid(openid);
		dao.getMonthAccountID(account);
		int monthIncomeNum = Integer.valueOf(account.getIncome());
		int monthOutlayNum = Integer.valueOf(account.getOutlay());
		//更新月度账单
		monthIncomeNum-=dayIncomeNum;
		monthOutlayNum-=dayOutlayNum;
		account.setIncome(String.valueOf(monthIncomeNum));
		account.setOutlay(String.valueOf(monthOutlayNum));
		dao.updateMonthAccount(account);
		return "删除成功~~";
	}

}
