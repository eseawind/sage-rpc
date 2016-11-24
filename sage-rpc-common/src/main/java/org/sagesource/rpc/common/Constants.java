package org.sagesource.rpc.common;

/**
 * <p>Framework Constant Value</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class Constants {

	//...........固定值...........//
	public static final String DEFAULT_SAGE_PROPERTIES           = "sagerpc.properties";
	public static final String DEFAULT_SAGE_JETTY_PORT           = "8080";
	public static final String DEFAULT_SAGE_JETTY_HOST           = "127.0.0.1";
	public static final String DEFAULT_SAGE_JETTY_TIMEOUT        = "3000";
	public static final String DEFAULT_SAGE_JETTY_MIN_THREADS    = "10";
	public static final String DEFAULT_SAGE_JETTY_MAX_THREADS    = "500";
	public static final String DEFAULT_SAGE_INVOKE_HTTP_MAXTOTAL = "200";
	public static final String DEFAULT_SAGE_INVOKE_HTTP_PERROUTE = "50";
	public static final String DEFAULT_SAGE_INVOKE_HTTP_RETRY    = "5";
	public static final String DEFAULT_SAGE_INVOKE_HTTP_MAXROUTE = "100";

	//..........配置Key值..........//
	public static final String SAGE_PROPERTIES_KEY       = "sage.properties.file";
	public static final String SAGE_JETTY_PORT           = "sage.jetty.port";
	public static final String SAGE_JETTY_TARGET         = "sage.jetty.target";
	public static final String SAGE_JETTY_HOST           = "sage.jetty.host";
	public static final String SAGE_JETTY_TIMEOUT        = "sage.jetty.timeout";
	public static final String SAGE_JETTY_MIN_THREADS    = "sage.jetty.minthread";
	public static final String SAGE_JETTY_MAX_THREADS    = "sage.jetty.maxthread";
	public static final String SAGE_INVOKE_HTTP_MAXTOTAL = "sage.invoke.http.maxtotal";
	public static final String SAGE_INVOKE_HTTP_PERROUTE = "sage.invoke.http.perroute";
	public static final String SAGE_INVOKE_HTTP_RETRY    = "sage.invoke.http.retry";
	public static final String SAGE_INVOKE_HTTP_MAXROUTE = "sage.invoke.http.maxroute";

	//..........服务相关配置Key值..........//
	public static final String SAGE_SERVICE_LOCATION_PREFIX = "sage.service.location.";

	public static final String SAGE_HTTP_INVOKE_DATA = "sage_http_invoke_data";
}
