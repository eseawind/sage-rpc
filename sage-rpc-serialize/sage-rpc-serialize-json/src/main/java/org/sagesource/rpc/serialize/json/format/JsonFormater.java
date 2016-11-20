package org.sagesource.rpc.serialize.json.format;

import com.alibaba.fastjson.JSON;
import org.sagesource.rpc.serialize.exception.RpcSerializeException;
import org.sagesource.rpc.serialize.format.Formater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * <p>Json消息格式化,将传递的消息格式化为JSON</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class JsonFormater implements Formater {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonFormater.class);

	private JsonFormater() {
	}

	@Override
	public String format(Object object) throws RpcSerializeException {
		LOGGER.debug("Begin Format Message To Json, message=[{}]", Objects.toString(object));

		if (object == null) LOGGER.warn("Format Message To Json, Object is Null");

		try {
			return JSON.toJSONString(object);
		} catch (Exception e) {
			throw new RpcSerializeException("Format Message To Json Exception", e, object);
		}
	}

	/**
	 * 属于懒汉式单例,
	 * 因为Java机制规定，内部类SingletonHolder只有在getInstance()方法第一次调用的时候才会被加载(实现了lazy),
	 * 而且其加载过程是线程安全的。内部类加载的时候实例化一次instance
	 */
	private static class SingletonHolder {
		private static final JsonFormater INSTANCE = new JsonFormater();
	}

	public static JsonFormater getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
