package org.sagesource.rpc.serialize.format;

import org.sagesource.rpc.serialize.exception.RpcSerializeException;

/**
 * <p>报文消息格式化接口</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public interface Formater {

	/**
	 * 格式化方法
	 *
	 * @param object
	 * @return
	 */
	String format(Object object) throws RpcSerializeException;

}
