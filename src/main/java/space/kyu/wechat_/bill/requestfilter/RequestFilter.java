package space.kyu.wechat_.bill.requestfilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 微信5秒内服务器不响应则重新发送请求 这样会造成用户数据的重复输入，造成重复计算收支错误
 * 将用户每次的请求保存在一个全局的map中，key为openid,value为RequestInfo实例
 * 在处理请求之前先检查这个map中是否已存在相同的请求，若无则正常处理，有则直接返回
 * map每隔10秒执行一次清除操作，比对RequestInfo中的时间与当前时间间隔超过5秒则清除 。map中每条记录的最大存活时间不超过15s
 * 只针对插入请求
 * 
 * @author yukai
 *
 */
@Service
public class RequestFilter {
	public static Map<String, RequestInfo> requests = new HashMap<String, RequestInfo>();

	private static final long FIXED_RATE = 10 * 1000;// 每隔10sec
	
	private static Logger logger = Logger.getLogger(RequestFilter.class);

	@Scheduled(fixedRate = FIXED_RATE)
	public void checkRequests() throws InterruptedException {
		Date now = new Date();
		List<String> requestList = new ArrayList<String>();
		Iterator<Map.Entry<String, RequestInfo>> itMap = requests.entrySet().iterator();
		RequestInfo requestInfo;
		while (itMap.hasNext()) {
			Map.Entry<String, RequestInfo> entry = itMap.next();
			requestInfo = entry.getValue();
			long elapsedSeconds = DateUtil.diffInSecBetweenTwoDate(now, requestInfo.getDate());
			if (elapsedSeconds >= 5) {
				requestList.add(entry.getKey());
			}
		}
		removeRequest(requestList);

	}

	private void removeRequest(List<String> removeList) {
		String openid;
		for (int i = 0; i < removeList.size(); i++) {
			openid = removeList.get(i);
			logger.info("删除请求:"+requests.get(openid));
			requests.remove(openid);
		}
	}

	/**
	 * 是否存在重复请求
	 * 
	 * @param openid
	 * @param content
	 * @return
	 */
	public static boolean checkRequest(String openid, String content) {
		boolean result = false;
		synchronized (requests) {
			if (requests.containsKey(openid)) {
				// map中存在该用户最近的请求
				RequestInfo requestExist = requests.get(openid);
				String contentExist = requestExist.getContent();
				if (content.equals(contentExist)) {
					// 请求内容也相同
					logger.info("重复请求:");
					result = true;
				}
			}
			RequestInfo request = new RequestInfo();
			request.setContent(content);
			request.setDate(new Date());
			requests.put(openid, request);
			logger.info("加入请求:" + content);
			return result;
		}
	}
}
