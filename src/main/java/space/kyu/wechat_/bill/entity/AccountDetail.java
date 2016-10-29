package space.kyu.wechat_.bill.entity;

/**
 * 账单明细
 * 
 * @author yukai
 *
 */
public class AccountDetail {
	private String id;
	
	private String description;

	private String date;

	private String num;

	private String monthAccountID;

	private String openid;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMonthAccountID() {
		return monthAccountID;
	}

	public void setMonthAccountID(String monthAccountID) {
		this.monthAccountID = monthAccountID;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	
}
