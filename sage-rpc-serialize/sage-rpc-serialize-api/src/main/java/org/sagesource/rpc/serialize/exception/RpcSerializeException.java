package org.sagesource.rpc.serialize.exception;

import org.sagesource.rpc.core.exception.RpcException;

/**
 * <p></p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class RpcSerializeException extends RpcException {

	public RpcSerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcSerializeException(String message, Throwable cause, Object data) {
		super(message, cause, data);
	}
}
