package space.kyu.wechat_.bill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import space.kyu.wechat_.bill.entity.AccountDetail;
import space.kyu.wechat_.bill.entity.MonthAccount;
import space.kyu.wechat_.common.utils.SqlTemplate;
import space.kyu.wechat_.common.utils.SqlUtil;

@Configurable
@Service
public class DaoImpl implements BaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertIncomeDetail(AccountDetail detail) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("income", detail.getNum());
		map.put("description", detail.getDescription());
		map.put("date", detail.getDate());
		map.put("month_account_id", detail.getMonthAccountID());
		map.put("openid", detail.getOpenid());
		
		String sql = SqlUtil.substituteSql(SqlTemplate.INSERTINTO_INCOME_DETAIL, map);
		jdbcTemplate.execute(sql);
	}

	@Override
	public void insertOutLayDetail(AccountDetail detail) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("outlay", detail.getNum());
		map.put("description", detail.getDescription());
		map.put("date", detail.getDate());
		map.put("month_account_id", detail.getMonthAccountID());
		map.put("openid", detail.getOpenid());
		String sql = SqlUtil.substituteSql(SqlTemplate.INSERTINTO_OUTLAY_DETAIL, map);
		jdbcTemplate.execute(sql.toString());
	}

	@Override
	public String getMonthAccountID(MonthAccount month) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", month.getOpenid());
		map.put("month", month.getMonth());
		String sql = SqlUtil.substituteSql(SqlTemplate.QUERY_MONTH_ACCOUNT, map);
		String id = null;
		List<MonthAccount> list = jdbcTemplate.query(sql, new RowMapper<MonthAccount>(){

			@Override
			public MonthAccount mapRow(ResultSet arg0, int arg1) throws SQLException {
				MonthAccount account = new MonthAccount();
				account.setId(arg0.getString("id"));
				account.setIncome(arg0.getString("income"));
				account.setMonth(arg0.getString("month"));
				account.setOpenid(arg0.getString("openid"));
				account.setOutlay(arg0.getString("outlay"));
				return account;
			}
			
		});
		
		if (list != null && list.size() != 0) {
			//月账单已经存在
			MonthAccount account = list.get(0);
			month.setIncome(account.getIncome());
			month.setOutlay(account.getOutlay());
			month.setId(account.getId());
			id = account.getId();
			return id;
		} else {
			//月账单不存在，创建
			month.setIncome("0");
			month.setOutlay("0");
			return createMonthAccount(month);
		}
	}
	
	/**
	 * 创建月账单，返回账单id
	 * @param account
	 * @return
	 */
	private String createMonthAccount(MonthAccount account){
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", account.getOpenid());
		map.put("month", account.getMonth());
		map.put("income", account.getIncome());
		map.put("outlay", account.getOutlay());
		final String sql = SqlUtil.substituteSql(SqlTemplate.CREATE_MONTH_ACCOUNT, map);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection arg0)
                    throws SQLException {
                PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                
                return ps;
            }
        },keyHolder);
		
		String id = keyHolder.getKey().toString();
		account.setId(id);
		return id;
		
	}

	@Override
	public List<AccountDetail> getIncomeDetailsByDay(String openid, String date) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("date", date);
		String sql = SqlUtil.substituteSql(SqlTemplate.QUERY_DAY_INCOME, map);
		List<AccountDetail> list = jdbcTemplate.query(sql, new RowMapper<AccountDetail>(){

			@Override
			public AccountDetail mapRow(ResultSet arg0, int arg1) throws SQLException {
				AccountDetail detail = new AccountDetail();
				detail.setId(arg0.getString("id"));
				detail.setDate(arg0.getString("date"));
				detail.setDescription(arg0.getString("description"));
				detail.setMonthAccountID(arg0.getString("month_account_id"));
				detail.setNum(arg0.getString("income"));
				detail.setOpenid(arg0.getString("openid"));
				return detail;
			}
			
		});
		return list;
	}

	@Override
	public List<AccountDetail> getOutLayDetailsByDay(String openid, String date) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("date", date);
		String sql = SqlUtil.substituteSql(SqlTemplate.QUERY_DAY_OUTLAY, map);
		List<AccountDetail> list = jdbcTemplate.query(sql, new RowMapper<AccountDetail>(){

			@Override
			public AccountDetail mapRow(ResultSet arg0, int arg1) throws SQLException {
				AccountDetail detail = new AccountDetail();
				detail.setId(arg0.getString("id"));
				detail.setDate(arg0.getString("date"));
				detail.setDescription(arg0.getString("description"));
				detail.setMonthAccountID(arg0.getString("month_account_id"));
				detail.setNum(arg0.getString("outlay"));
				detail.setOpenid(arg0.getString("openid"));
				return detail;
			}
			
		});
		return list;
	}

	@Override
	public void updateMonthAccount(MonthAccount account) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", account.getOpenid());
		map.put("month", account.getMonth());
		map.put("income", account.getIncome());
		map.put("outlay", account.getOutlay());
		String sql = SqlUtil.substituteSql(SqlTemplate.UPDATE_MONTH_ACCOUNT, map);
		jdbcTemplate.execute(sql);
	}

	@Override
	public void delDayOutlayRecord(String openid, String date) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("date", date);
		String sql = SqlUtil.substituteSql(SqlTemplate.DELETE_DAY_OUTLAY, map);
		jdbcTemplate.execute(sql);
	}

	@Override
	public void delDayIncomeRecord(String openid, String date) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("date", date);
		String sql = SqlUtil.substituteSql(SqlTemplate.DELETE_DAY_INCOME, map);
		jdbcTemplate.execute(sql);
	}


}
