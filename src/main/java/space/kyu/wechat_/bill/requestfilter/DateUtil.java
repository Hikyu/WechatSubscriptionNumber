package space.kyu.wechat_.bill.requestfilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import space.kyu.wechat_.common.utils.InputMsgUtil;

public class DateUtil {
	public static long diffInSecBetweenTwoDate(Date dateOne, Date dateTwo) {
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;
		long different;
		if (dateOne.after(dateTwo)) {
			different = dateOne.getTime() - dateTwo.getTime();
		} else {
			different = dateTwo.getTime() - dateOne.getTime();
		}

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;
		return elapsedSeconds;
	}
	
	/**
	 * 获取当天日期
	 * 
	 * @return
	 */
	public static String thisDay() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获取记账日期
	 * 
	 * @param content
	 * @return
	 */
	public static String getDate(String content) {
		int dateEnd = content.indexOf(":");
		String date = DateUtil.thisDay();
		if (dateEnd != -1) {
			String subDate = content.substring(0, dateEnd);
			if (InputMsgUtil.isDayBillQuery(subDate)) {
				date = subDate;
			}
		}
		return date;
	}

	/**
	 * 截取除用户输入日期以外内容
	 * 
	 * @param content
	 * @return
	 */
	public static String getContentWithOutDate(String content) {
		String newContent = content;
		int dateEnd = content.indexOf(":");
		if (dateEnd != -1) {
			String subDate = content.substring(0, dateEnd);
			if (InputMsgUtil.isDayBillQuery(subDate)) {
				newContent = content.substring(dateEnd + 1, content.length());
			}
		}
		return newContent;
	}

	/**
	 * 截取年月
	 * 
	 * @param date
	 *            2016-10-09
	 * @return 2016-10
	 */
	public static String getMonthFromDate(String date) {
		int index = date.lastIndexOf("-");
		return date.substring(0, index);
	}

	public static String getDaysBetweenTwoDays(String dateFrom, String dateEnd) {
		Date dtFrom = null;
		Date dtEnd = null;
		dtFrom = toDate(dateFrom, "yyyy-MM-dd");
		dtEnd = toDate(dateEnd, "yyyy-MM-dd");
		long begin = dtFrom.getTime();
		long end = dtEnd.getTime();
		long inter = end - begin;
		if (inter < 0) {
			inter = inter * (-1);
		}
		long dateMillSec = 24 * 60 * 60 * 1000;

		long dateCnt = inter / dateMillSec;

		long remainder = inter % dateMillSec;

		if (remainder != 0) {
			dateCnt++;
		}
		return String.valueOf(dateCnt);
	}

	/**
	 * 字符串(yyyy-MM-dd)转换成为java.util.Date
	 * 
	 * @param sDate
	 *            字符串(yyyy-MM-dd)
	 * @param sFmt
	 *            format
	 * @return Date java.util.Date日期
	 */
	public static Date toDate(String sDate, String sFmt) {
		Date dt = null;
		try {
			dt = new SimpleDateFormat(sFmt).parse(sDate);
		} catch (ParseException e) {
			return dt;
		}
		return dt;
	}

	/**
	 * 输入日期是否在今天之后
	 * 
	 * @param date
	 * @return true 输入日期在大于当前日期
	 */
	public static boolean farFromToday(String date) {
		String thisDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date today = toDate(thisDay, "yyyy-MM-dd");
		Date inputDate = toDate(date, "yyyy-MM-dd");
		return today.before(inputDate);
	}
}
