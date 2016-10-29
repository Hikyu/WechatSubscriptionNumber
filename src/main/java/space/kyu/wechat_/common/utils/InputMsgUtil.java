package space.kyu.wechat_.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析过滤用户输入数据
 * 
 * @author yukai
 *
 */
public class InputMsgUtil {

	/**
	 * 是否查询月账单
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isMonthBillQuery(String content) {
		// String regex = "\\d{4,4}-\\d{2,2}";
		String regex = "^[0-9]{4}-(0[1-9]|(10|11|12))$";
		return isMatch(content, regex);

	}

	/**
	 * 是否删除某天记录
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isRecordDel(String content) {
		if (content.startsWith("删除")) {
			String date = getDateFromDelRecordSrt(content);
			return isDayBillQuery(date);
		}
		return false;

	}

	public static String getDateFromDelRecordSrt(String delRecordStr) {
		return delRecordStr.substring("删除".length(), delRecordStr.length());
	}

	/**
	 * 是否查询某日账单
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isDayBillQuery(String content) {
		// String regex = "\\d{4,4}-\\d{2,2}-\\d{2,2}";
		String regex = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
		return isMatch(content, regex);
	}

	/**
	 * 是否查询万年历
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isCalendarQuery(String content) {
		String regex = "^[0-9]{4}(((0[13578]|(10|12))(0[1-9]|[1-2][0-9]|3[0-1]))|(02(0[1-9]|[1-2][0-9]))|((0[469]|11)(0[1-9]|[1-2][0-9]|30)))$";
		return isMatch(content, regex);
	}

	/**
	 * 是否记账
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isBillCreate(String content) {
		String regex = "([0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30))):){0,1}(([^\\+-;]+)(\\+|-)\\d+;)+";
		return isMatch(content, regex);
	}

	/**
	 * 判断是否为翻译请求
	 * 
	 * @return
	 */
	public static boolean isTranslate(String content) {
		String regex = "[a-z]{0,3}:.*";
		return isMatch(content, regex);
	}

	/**
	 * 是否查看公交线路
	 * 
	 * @return
	 */
	public static boolean isBusQuery(String content) {
		String regex = "^[\u4E00-\\u9FA5]{2,10}\\d{1,4}$";
		return isMatch(content, regex);
	}

	private static boolean isMatch(String content, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}

}
