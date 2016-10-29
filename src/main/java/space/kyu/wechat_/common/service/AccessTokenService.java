package space.kyu.wechat_.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import space.kyu.wechat_.common.utils.Constants;
import space.kyu.wechat_.common.utils.HttpUtil;

/**
 * 获取AccessToken
 * 
 * @author Administrator
 *
 */
@Service
public class AccessTokenService {
	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	private static Logger logger = Logger.getLogger(AccessTokenService.class);
	private Integer count0 = 1;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final long FIXED_RATE = 100 * 60 * 1000;// 每隔100分钟获取一次token

	@Scheduled(fixedRate = FIXED_RATE)
	public void getAccessToken() throws InterruptedException {
		String info = String.format("%s: 第%s次获取AccessToken", dateFormat.format(new Date()), count0++);
		logger.info(info);
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", Constants.WECHAT_GRANT_TYPE);
		params.put("appid", Constants.WECHAT_APPID);
		params.put("secret", Constants.WECHAT_APP_SECRET);
		String jstoken = null;
		try {
			jstoken = HttpUtil.sendGet(GET_TOKEN_URL, params);
			String accessToken = JSONObject.fromObject(jstoken).getString("access_token");
			Constants.ACCESS_TOKEN = accessToken;
			logger.info("获取AccessToken成功:" + accessToken);
		} catch (Exception e) {
			logger.error("获取AccessToken失败" + e.getMessage());
			e.printStackTrace();
		}

	}
}
