package org.sagesource.rpc.invoke.exception;

import org.sagesource.rpc.common.exception.RpcException;

/**
 * <p></p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/23
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class RpcInvokeException extends RpcException {

	public RpcInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcInvokeException(String message, Throwable cause, Object data) {
		super(message, cause, data);
	}

	public RpcInvokeException(String message, Object data) {
		super(message, data);
	}
}
