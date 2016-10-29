package space.kyu.wechat_.translate;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import space.kyu.wechat_.common.utils.Constants;
import space.kyu.wechat_.common.utils.HttpUtil;

public class BaiduTranslate {
	private static final String UTF8 = "utf-8";

	private static final String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";

	// 随机数，用于生成md5值
	private static final Random random = new Random();

	public static String translateToEn(String q) {
		BaiduTranslate baidu = new BaiduTranslate();
		String result = baidu.translate(q, "auto", "en");
		return result;
	}

	public static String translate(String q, String to) {
		BaiduTranslate baidu = new BaiduTranslate();
		String result = baidu.translate(q, "auto", to);
		return result;
	}

	@SuppressWarnings("finally")
	private String translate(String q, String from, String to) {
		List<NameValuePair> nvps = getQueryContent(q, from, to);
		String translateResult = null;
		try {
			String result = HttpUtil.sendPost(url, nvps);
			JSONObject resultJson = JSONObject.fromObject(result.toString());

			JSONArray array = (JSONArray) resultJson.get("trans_result");
			JSONObject dst = (JSONObject) array.get(0);
			String text = dst.getString("dst");
			text = URLDecoder.decode(text, UTF8);

			translateResult = text;
		} catch (Exception e) {
			e.printStackTrace();
			translateResult = "服务器异常：" + e.getMessage();
		} finally {
			return translateResult;
		}

	}

	private List<NameValuePair> getQueryContent(String q, String from, String to) {
		// 用于md5加密
		int salt = random.nextInt(10000);
		// 对appId+源文+随机数+token计算md5值
		StringBuilder md5String = new StringBuilder();
		md5String.append(Constants.TRANSLATE_APPID).append(q).append(salt).append(Constants.TRANSLATE_TOKEN);
		String md5 = DigestUtils.md5Hex(md5String.toString());

		// 使用Post方式，组装参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("q", q));
		nvps.add(new BasicNameValuePair("from", from));
		nvps.add(new BasicNameValuePair("to", to));
		nvps.add(new BasicNameValuePair("appid", Constants.TRANSLATE_APPID));
		nvps.add(new BasicNameValuePair("salt", String.valueOf(salt)));
		nvps.add(new BasicNameValuePair("sign", md5));
		return nvps;
	}

}
