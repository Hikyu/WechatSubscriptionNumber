package space.kyu.wechat_.map.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import space.kyu.wechat_.common.utils.SqlTemplate;
import space.kyu.wechat_.common.utils.SqlUtil;
import space.kyu.wechat_.map.entity.Location;

public class DaoImpl implements BaseDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addLocation(String openid, Location localtion) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location_x", localtion.getLocation_X());
		map.put("location_y", localtion.getLocation_Y());
		map.put("label", localtion.getLabel());
		map.put("openid", openid);
		String sql = SqlUtil.substituteSql(SqlTemplate.ADD_LOCATION, map);
		jdbcTemplate.execute(sql);
	}

	@Override
	public Location getLocation(String openid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid",openid);
		String sql = SqlUtil.substituteSql(SqlTemplate.GET_LOCATION, map);
		List<Location> list = jdbcTemplate.query(sql, new RowMapper<Location>(){

			@Override
			public Location mapRow(ResultSet arg0, int arg1) throws SQLException {
				Location location = new Location();
				location.setLabel(arg0.getString("label"));
				location.setLocation_X(arg0.getString("location_x"));
				location.setLocation_Y(arg0.getString("location_y"));
				return location;
			}
			
		});
		
		if (list != null && list.size() != 0) {
			//月账单已经存在
			Location location = list.get(0);
			return location;
		}
		return null;
	}

}
