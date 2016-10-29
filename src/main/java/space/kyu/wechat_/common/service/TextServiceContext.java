package space.kyu.wechat_.common.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import space.kyu.wechat_.common.message.req.ReqTextMessage;
import space.kyu.wechat_.common.utils.SpringUtil;

/**
 * 文本消息   回复文本
 * @author yukai
 *
 */
public class TextServiceContext implements BaseServiceContext<ReqTextMessage,String> {
	private static Properties keysHadleService;
	private static Properties regexHandleService;
	private BaseService<ReqTextMessage,String> service;
	ReqTextMessage message;

	static {
		try {
			keysHadleService = loadProperties("/key.properties");
			regexHandleService = loadProperties("/regex.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Properties loadProperties(String filePath) throws IOException {
		Properties properties = new Properties();
		InputStreamReader is = null;
		try {
			is = new InputStreamReader(BaseServiceContext.class.getResourceAsStream(filePath),"UTF-8");
			properties.load(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return properties;
	}

	@Override
	public void selectService(ReqTextMessage reqMeg) {
		this.message = reqMeg;
		String content = reqMeg.getContent();
		String serviceName = null;
		serviceName = keysHadleService.getProperty(content);
		if (serviceName != null) {
			// 根据输入内容匹配到service
			service = getServiceByName(serviceName);
			return;
		}
		// 对输入内容进行正则匹配
		Enumeration<?> e = regexHandleService.propertyNames();
		while (e.hasMoreElements()) {
			String regex = (String) e.nextElement();
			String name = regexHandleService.getProperty(regex);
			if (isRegex(content, regex)) {
				service = getServiceByName(name);
				break;
			}
		}
		if (service == null) {
			//输入内容无法识别
			service = (BaseService<ReqTextMessage,String>) SpringUtil.getContext().getBean(ErrorInputService.class);
		}
	}

	private boolean isRegex(String content, String regex) {
		int index = regex.lastIndexOf(".");
		String className = regex.substring(0, index);
		String methodName = regex.substring(index+1, regex.length());
		try {
			Class<?> regexClass = Class.forName(className);
			Method regexMethod = regexClass.getMethod(methodName, String.class);
			boolean isRegex = (Boolean) regexMethod.invoke(null, content);
			return isRegex;
		} catch (ReflectiveOperationException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private BaseService<ReqTextMessage,String> getServiceByName(String serviceName) {
		BaseService<ReqTextMessage,String> baseService = null;
		try {
			Class<?> regexClass = Class.forName(serviceName);
			baseService = (BaseService<ReqTextMessage,String>) SpringUtil.getContext().getBean(regexClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseService;
		
	}

	@Override
	public String executeRequest() {
		return service.executeRequest(message);
	}
}
