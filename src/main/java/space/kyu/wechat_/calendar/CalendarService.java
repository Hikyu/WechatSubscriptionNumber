package space.kyu.wechat_.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import space.kyu.wechat_.common.service.AbstractTextMsgService;
import space.kyu.wechat_.common.utils.Constants;
import space.kyu.wechat_.common.utils.HttpUtil;

@Service
public class CalendarService extends AbstractTextMsgService {
	private static String CALENDAR_URL = "http://japi.juhe.cn/calendar/day";
	private static String JUHE_KEY = Constants.JEHU_CALENDAR_KEY;

	@Override
	protected String dealwithRequest(String openid, String content) {
		if ("今天".equals(content)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			content = dateFormat.format(new Date());
		}
		Map<String, String> params = new HashMap<String, String>();
		String date = getQueryDate(content);
		params.put("date", date);
		params.put("key", JUHE_KEY);
		String result = null;
		String resp = null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			result = HttpUtil.sendGet(CALENDAR_URL, params);
			JSONObject calendar = JSONObject.fromObject(result);
			if (calendar.getInt("error_code") == 0) {
				JSONObject data = calendar.getJSONObject("result").getJSONObject("data");
				map.put("日期：", date);
				String weekday = data.getString("weekday");
				map.put("星期：", weekday);
				String lunar = data.getString("lunar");
				map.put("农历：", lunar);
				if (data.has("holiday")) {
					String holiday = data.getString("holiday");
					map.put("节日：", holiday);
				}
				if (data.has("desc")) {
					String desc = data.getString("desc");
					map.put("节日描述：", desc);
				}

				String lunarYear = data.getString("lunarYear");
				map.put("纪年：", lunarYear);
				String animalsYear = data.getString("animalsYear");
				map.put("属相：", animalsYear);
				String avoid = data.getString("avoid");
				map.put("忌：", avoid);
				String suit = data.getString("suit");
				map.put("宜：", suit);
				resp = getResp(map);
			} else {
				resp = "服务器错误：" + calendar.getString("reason");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	private String getQueryDate(String content) {
		String year = content.substring(0, 4);
		String month = content.substring(4, 6);
		String day = content.substring(6, 8);
		if (month.startsWith("0")) {
			month = month.substring(1, month.length());
		}
		if (day.startsWith("0")) {
			day = day.substring(1, day.length());
		}
		return new StringBuilder().append(year).append("-").append(month).append("-").append(day).toString();

	}

	private String getResp(Map<String, String> map) {
		StringBuilder resp = new StringBuilder();
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			resp.append(key).append(value).append('\n');
		}

		return resp.toString();
	}
}
