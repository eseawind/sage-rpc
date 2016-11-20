package org.sagesource.rpc.serialize.parse;

import org.sagesource.rpc.serialize.exception.RpcSerializeException;

/**
 * <p>报文消息反序列化接口</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public interface Parser {

	/**
	 * 消息反序列化
	 *
	 * @param message
	 * @param <T>
	 * @return
	 */
	<T> T parse(String message) throws RpcSerializeException;

}
