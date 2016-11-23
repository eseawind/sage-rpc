package org.sagesource.common.utils;

import org.sagesource.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Framework Config Utils</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class ConfigUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);


	public static String getProperty(String key) {
		return getProperty(key, null);
	}

	//...........................................................//

	/**
	 * 获取配置参数的值,如果不存在,返回默认值
	 *
	 * @param key    参数Key
	 * @param defVal 默认值
	 * @return
	 */
	public static String getProperty(String key, String defVal) {
		String value = System.getProperty(key);
		if (value != null && value.length() > 0) return value;

		Properties properties = getProperties();
		return replaceProperty(properties.getProperty(key, defVal), (Map) properties);
	}

	/**
	 * 加载Properties文件
	 *
	 * @param fileName       properties文件名,例如:<code>sagerpc.properties</code>,<code>/METE-INF/conf/foo.properties</code>
	 * @param allowMultiFile 是否支持MultiFile,如果是<code>false</code>,在找到MultiFile时,会抛出异常{@IllegalStateException}
	 * @param optional       如果没有加载到配置 是否打出warn的日志
	 * @return
	 */
	public static Properties loadProperties(String fileName, boolean allowMultiFile, boolean optional) {
		Properties properties = new Properties();

		//从本地Classloader读取
		if (fileName.startsWith("/")) {
			try {
				FileInputStream input = new FileInputStream(fileName);
				try {
					properties.load(input);
				} finally {
					input.close();
				}
			} catch (Exception e) {
				LOGGER.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
			}
			return properties;
		}

		//从非本地Classloader位置读取配置
		List<URL> list = new ArrayList<>();
		try {
			Enumeration<URL> urls = ClassHelper.getClassLoader().getResources(fileName);
			list = new ArrayList<>();
			while (urls.hasMoreElements()) {
				list.add(urls.nextElement());
			}
		} catch (Throwable t) {
			LOGGER.warn("Fail to load " + fileName + " file: " + t.getMessage(), t);
		}
		if (list.size() == 0) {
			if (!optional) {
				LOGGER.warn("No " + fileName + " found on the class path.");
			}
			return properties;
		}

		//判断是否支持非本地读取
		if (!allowMultiFile) {
			if (list.size() > 1) {
				String errMsg = String.format("only 1 %s file is expected, but %d dubbo.properties files found on class path: %s",
						fileName, list.size(), list.toString());
				LOGGER.warn(errMsg);
			}
			try {
				properties.load(ClassHelper.getClassLoader().getResourceAsStream(fileName));
			} catch (Exception e) {
				LOGGER.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
			}
			return properties;
		}

		LOGGER.info("load " + fileName + " properties file from " + list);

		for (java.net.URL url : list) {
			try {
				Properties  p     = new Properties();
				InputStream input = url.openStream();
				if (input != null) {
					try {
						p.load(input);
						properties.putAll(p);
					} finally {
						try {
							input.close();
						} catch (Exception e) {
						}
					}
				}
			} catch (Exception e) {
				LOGGER.warn("Fail to load " + fileName + " file from " + url + "(ingore this file): " + e.getMessage(), e);
			}
		}

		return properties;
	}

	public static volatile Properties PROPERTIES;

	/**
	 * 加载{@Properties}配置文件
	 * <p>Properties文件名的查找顺序:JVM参数 -> 系统环境变量 sage.properties.file的值
	 * <p>如果都没有查找到,则默认:sagerpc.properties
	 *
	 * @return
	 */
	public static Properties getProperties() {
		if (PROPERTIES == null) {
			synchronized (ConfigUtils.class) {
				if (PROPERTIES == null) {
					String path = System.getProperty(Constants.SAGE_PROPERTIES_KEY);
					if (path == null || path.length() == 0) {
						path = System.getenv(Constants.SAGE_PROPERTIES_KEY);
						if (path == null || path.length() == 0) {
							path = Constants.DEFAULT_SAGE_PROPERTIES;
						}
					}
					PROPERTIES = loadProperties(path, false, true);
				}
			}
		}
		return PROPERTIES;
	}

	private static Pattern VARIABLE_PATTERN = Pattern.compile(
			"\\$\\s*\\{?\\s*([\\._0-9a-zA-Z]+)\\s*\\}?");

	/**
	 * 替换Properties的占位符
	 *
	 * @param expression
	 * @param params
	 * @return
	 */
	public static String replaceProperty(String expression, Map<String, String> params) {
		if (expression == null || expression.length() == 0 || expression.indexOf('$') < 0) {
			return expression;
		}
		Matcher      matcher = VARIABLE_PATTERN.matcher(expression);
		StringBuffer sb      = new StringBuffer();
		while (matcher.find()) { // 逐个匹配
			String key   = matcher.group(1);
			String value = System.getProperty(key);
			if (value == null && params != null) {
				value = params.get(key);
			}
			if (value == null) {
				value = "";
			}
			matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
