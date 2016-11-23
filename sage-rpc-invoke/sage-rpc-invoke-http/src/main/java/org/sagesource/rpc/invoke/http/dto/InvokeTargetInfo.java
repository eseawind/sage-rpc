package org.sagesource.rpc.invoke.http.dto;

/**
 * <p>调用目标信息</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/23
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class InvokeTargetInfo {

	private String schema;
	private String host;
	private String port;
	private String target;

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	//.......................................................................//
	public String builtUrl() {
		return String.format("%s://%s:%s/%s", schema, host, port, target);
	}
}
