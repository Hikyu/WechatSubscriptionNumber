package space.kyu.wechat_.bill.requestfilter;

import java.util.Date;

/**
 * 保存请求的时间和内容
 * 
 * @author yukai
 *
 */
public class RequestInfo {
	private Date date;
	private String content;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
