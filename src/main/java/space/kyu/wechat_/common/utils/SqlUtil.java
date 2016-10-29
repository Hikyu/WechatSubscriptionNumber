package space.kyu.wechat_.common.utils;

import java.util.Iterator;
import java.util.Map;

public class SqlUtil {
	public static String substituteSql(String template, Map<String, String> map) {
		Iterator<Map.Entry<String, String>> itMap = map.entrySet().iterator();

		while (itMap.hasNext()) {
			Map.Entry<String, String> entry = itMap.next();
			template = template.replace("{" + entry.getKey() + "}", "" + entry.getValue());
		}
		return template;
	}
}
