package space.kyu.wechat_.map.dao;

import space.kyu.wechat_.map.entity.Location;

public interface BaseDao {
	public void addLocation(String openid, Location localtion);

	public Location getLocation(String openid);
}
