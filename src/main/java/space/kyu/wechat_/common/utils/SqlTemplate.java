package space.kyu.wechat_.common.utils;

public class SqlTemplate {
	private static final String INCOME_DETAIL_TABLE = "income_detail";
	private static final String OUTLAY_DETAIL_TABLE = "outlay_detail";
	private static final String MONTH_ACCOUNT_TABLE = "month_account";
	private static final String LOCATION_TABLE = "location";
	
	/********************************记账********************************/
	
	public static String INSERTINTO_INCOME_DETAIL = "insert into " + INCOME_DETAIL_TABLE 
			+ "(income,description,date,month_account_id,openid) values ('{income}','{description}','{date}','{month_account_id}','{openid}')";
	
	public static String INSERTINTO_OUTLAY_DETAIL = "insert into " + OUTLAY_DETAIL_TABLE 
			+ "(outlay,description,date,month_account_id,openid) values ('{outlay}','{description}','{date}','{month_account_id}','{openid}')";
	
	public static String CREATE_MONTH_ACCOUNT = "insert into " + MONTH_ACCOUNT_TABLE 
			+ "(openid,month,income,outlay) values ('{openid}','{month}','{income}','{outlay}')";
	
	public static String QUERY_MONTH_ACCOUNT = "select * from " + MONTH_ACCOUNT_TABLE
			+ " where openid = '{openid}' and month = '{month}'";
	
	public static String UPDATE_MONTH_ACCOUNT = "update " + MONTH_ACCOUNT_TABLE 
			+ " set income = '{income}',outlay = '{outlay}' where openid = '{openid}' and month = '{month}'";
	
	public static String QUERY_DAY_INCOME = "select * from " + INCOME_DETAIL_TABLE
			+ " where openid = '{openid}' and date = '{date}'";
	
	public static String QUERY_DAY_OUTLAY = "select * from " + OUTLAY_DETAIL_TABLE
			+ " where openid = '{openid}' and date = '{date}'";
	
	public static String DELETE_DAY_OUTLAY = "delete from " + OUTLAY_DETAIL_TABLE
			+ " where openid = '{openid}' and date = '{date}'";
	
	public static String DELETE_DAY_INCOME = "delete from " + INCOME_DETAIL_TABLE
			+ " where openid = '{openid}' and date = '{date}'";
	
	/*******************************map************************************/
	public static String ADD_LOCATION = "insert into " + LOCATION_TABLE
			+ "(openid,location_x,location_y,label) values ('{openid}','{location_x}','{location_y}','{label}')";
	
	public static String GET_LOCATION = "select * from " + LOCATION_TABLE
			+ " where openid = '{openid}'";
}
