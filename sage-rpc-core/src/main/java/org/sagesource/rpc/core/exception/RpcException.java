package org.sagesource.rpc.core.exception;

/**
 * <p>RPC Framework Exception</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class RpcException extends Exception {
	/**
	 * RPC Framework Exception Message.
	 */
	private String message;
	/**
	 * RPC Framework Exception Code.
	 */
	private String code;
	/**
	 * RPC Framework Exception Data.
	 */
	private Object data;

	public RpcException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcException(String message, Throwable cause, String code, Object data) {
		this(message, cause);
		this.code = code;
		this.data = data;
	}

	public RpcException(String message, Throwable cause, Object data) {
		this(message, cause);
		this.data = data;
	}

	public RpcException(String message, Object data) {
		super(message);
		this.data = data;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}
}
