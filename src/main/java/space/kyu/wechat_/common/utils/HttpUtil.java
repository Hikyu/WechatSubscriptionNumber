package space.kyu.wechat_.common.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

	public static String sendGet(String reqUrl, Map<String, String> params) throws Exception {
		InputStream inputStream = null;
		HttpGet request = new HttpGet();
		try {
			String url = buildUrl(reqUrl, params);
			CloseableHttpClient client = HttpClientBuilder.create().build();

			request.setHeader("Accept-Encoding", "gzip");
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			inputStream = response.getEntity().getContent();
			String result = getStringFromGZIP(inputStream);
			return result;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			request.releaseConnection();
		}

	}

	/**
	 * 发送表单
	 * @param reqUrl
	 * @param nvps
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String reqUrl,List<NameValuePair> nvps) throws Exception{
		InputStream inputStream = null;
		HttpPost request = new HttpPost();
		try {
			request.setURI(new URI(reqUrl));
			request.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			request.setHeader("Accept-Encoding", "gzip");
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(request);

			inputStream = response.getEntity().getContent();
			String result = getStringFromGZIP(inputStream);
			return result;

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			request.releaseConnection();
		}
		
	}
	
	private static String getStringFromGZIP(InputStream is) {
		String jsonString = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(is);
			bis.mark(2);
			// 取前两个字节
			byte[] header = new byte[2];
			int result = bis.read(header);
			// reset 输入流到开始位置
			bis.reset();
			// 判断是否是 GZIP 格式
			int headerData = getShort(header);
			// Gzip 流 的前两个字节是 0x1f8b
			if (result != -1 && headerData == 0x1f8b) {
				// LogUtil.i("HttpTask", " use GZIPInputStream ");
				is = new GZIPInputStream(bis);
			} else {
				// LogUtil.d("HttpTask", " not use GZIPInputStream");
				is = bis;
			}
			InputStreamReader reader = new InputStreamReader(is, "utf-8");
			char[] data = new char[100];
			int readSize;
			StringBuffer sb = new StringBuffer();
			while ((readSize = reader.read(data)) > 0) {
				sb.append(data, 0, readSize);
			}
			jsonString = sb.toString();
			bis.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	private static int getShort(byte[] data) {
		return (data[0] << 8) | data[1] & 0xFF;
	}

	/**
	 * 构建查询参数
	 * 
	 * @param reqUrl
	 * @param params
	 * @return
	 */
	public static String buildUrl(String reqUrl, Map<String, String> params) {
		StringBuilder query = new StringBuilder();
		Set<String> set = params.keySet();
		for (String key : set) {
			query.append(String.format("%s=%s&", key, params.get(key)));
		}
		return reqUrl + "?" + query;
	}
}
