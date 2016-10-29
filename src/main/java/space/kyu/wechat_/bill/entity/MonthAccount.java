package space.kyu.wechat_.bill.entity;

/**
 * 月账单
 * 
 * @author yukai
 *
 */
public class MonthAccount {
	private String id;
	
	private String openid;
	
	private String month;
	
	private String income;
	
	private String outlay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getOutlay() {
		return outlay;
	}

	public void setOutlay(String outlay) {
		this.outlay = outlay;
	}

}
