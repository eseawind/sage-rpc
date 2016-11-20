package org.sagesource.rpc.serialize.json.parse;

import com.alibaba.fastjson.JSON;
import org.sagesource.rpc.serialize.exception.RpcSerializeException;
import org.sagesource.rpc.serialize.parse.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>JSON消息反序列化</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class JsonParser implements Parser {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);

	private JsonParser() {
	}

	@Override
	public <T> T parse(String message) throws RpcSerializeException {
		LOGGER.debug("Begin Parse Json Message, message=[{}]", message);

		if (message == null) LOGGER.warn("Parse Json Message, Message is Null");

		try {
			return (T) JSON.parse(message);
		} catch (Exception e) {
			throw new RpcSerializeException("Parse Json Message Exception", e, message);
		}
	}

	/**
	 * 属于懒汉式单例,
	 * 因为Java机制规定，内部类SingletonHolder只有在getInstance()方法第一次调用的时候才会被加载(实现了lazy),
	 * 而且其加载过程是线程安全的。内部类加载的时候实例化一次instance
	 */
	private static class SingletonHolder {
		private static final JsonParser INSTANCE = new JsonParser();
	}

	public static JsonParser getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
