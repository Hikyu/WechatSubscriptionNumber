package space.kyu.wechat_.bill.dao;

import java.util.List;

import space.kyu.wechat_.bill.entity.AccountDetail;
import space.kyu.wechat_.bill.entity.MonthAccount;

public interface BaseDao {

	/**
	 * 插入一条收入明细
	 * 
	 * @param detail
	 */
	public void insertIncomeDetail(AccountDetail detail);

	/**
	 * 插入一条支出明细
	 * 
	 * @param detail
	 */
	public void insertOutLayDetail(AccountDetail detail);

	/**
	 * 获取月账单id，月账单不存在，则创建
	 * 
	 * @param month
	 * @return
	 */
	public String getMonthAccountID(MonthAccount month);

	/**
	 * 获取某日收入账单
	 * 
	 * @param openid
	 * @param date
	 * @return
	 */
	public List<AccountDetail> getIncomeDetailsByDay(String openid, String date);

	/**
	 * 获取某日支出账单
	 * 
	 * @param openid
	 * @param date
	 * @return
	 */
	public List<AccountDetail> getOutLayDetailsByDay(String openid, String date);

	/**
	 * 更新某月账单
	 * @param account
	 */
	public void updateMonthAccount(MonthAccount account);
	
	/**
	 * 删除某日支出记录
	 * @param openid
	 * @param date
	 */
	public void delDayOutlayRecord(String openid, String date);
	
	/**
	 * 删除某日收入记录
	 * @param openid
	 * @param date
	 */
	public void delDayIncomeRecord(String openid, String date);
}
