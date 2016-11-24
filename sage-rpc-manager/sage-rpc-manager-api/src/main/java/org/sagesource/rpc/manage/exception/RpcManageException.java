package org.sagesource.rpc.manage.exception;

import org.sagesource.rpc.common.exception.RpcException;

/**
 * <p></p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/22
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class RpcManageException extends RpcException {

	public RpcManageException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcManageException(String message, Throwable cause, Object data) {
		super(message, cause, data);
	}

	public RpcManageException(String message, Object data) {
		super(message, data);
	}
}