package space.kyu.wechat_.map.entity;

public class Location {
	// 地理位置维度
	private String Location_X;
	// 地理位置经度
	private String Location_Y;
	// 地理位置信息
	private String Label;

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}
}
