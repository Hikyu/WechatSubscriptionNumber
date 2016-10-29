package space.kyu.wechat_.bus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import space.kyu.wechat_.common.service.AbstractTextMsgService;
import space.kyu.wechat_.common.utils.Constants;
import space.kyu.wechat_.common.utils.HttpUtil;

@Service
public class BusService extends AbstractTextMsgService {
	private static String BUS_URL = "http://op.juhe.cn/189/bus/busline";
	private static String JUHE_KEY = Constants.JEHU_BUS_KEY;
	@Override
	protected String dealwithRequest(String openid, String content) {
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher matcher = pattern.matcher(content);
		String city = "";
		String bus = "";
		if (matcher.find()) {
			int index = content.indexOf(matcher.group());
			city = content.substring(0, index);
			bus = content.substring(index,content.length());
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("city", city);
		params.put("key", JUHE_KEY);
		params.put("bus", bus);
		String result;
		StringBuilder resp = new StringBuilder();
		try {
			result = HttpUtil.sendGet(BUS_URL, params);
			JSONObject rout = JSONObject.fromObject(result);
			Map<String, String> map = new LinkedHashMap<String, String>();
			if (rout.getInt("error_code") == 0) {
				JSONArray data = rout.getJSONArray("result");
				for (int i = 0; i < data.size(); i++) {
					JSONObject come = data.getJSONObject(i);
					map.put("线路：", come.getString("key_name"));
					map.put("始发站：", come.getString("front_name"));
					map.put("终点站：", come.getString("terminal_name"));
					map.put("线路长度:", come.getString("length"));
					map.put("首班时间：", come.getString("start_time"));
					map.put("末班时间：", come.getString("end_time"));
					JSONArray stationdes = come.getJSONArray("stationdes");
					for (int j = 0; j < stationdes.size(); j++) {
						JSONObject station = stationdes.getJSONObject(j);
						map.put("站台"+station.getString("stationNum")+"：", station.getString("name"));
					}
					map.put("*********", "**********");
					resp.append(getResp(map));
				}
				
			} else {
				resp.append("错误：").append(rout.getString("reason"));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return resp.toString();
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
