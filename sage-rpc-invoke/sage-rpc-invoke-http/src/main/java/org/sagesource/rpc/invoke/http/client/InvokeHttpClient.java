package org.sagesource.rpc.invoke.http.client;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.sagesource.rpc.common.Constants;
import org.sagesource.rpc.common.utils.ConfigUtils;
import org.sagesource.rpc.invoke.http.dto.InvokeTargetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * <p>Invoke Http客户端</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/23
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class InvokeHttpClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvokeHttpClient.class);

	private static volatile CloseableHttpClient closeableHttpClient;

	public static CloseableHttpClient getHttpClient(InvokeTargetInfo invokeTargetInfo) {
		if (closeableHttpClient == null) {
			synchronized (InvokeHttpClient.class) {
				if (closeableHttpClient == null) {
					int maxTotal = Integer.valueOf(ConfigUtils.getProperty(Constants.SAGE_INVOKE_HTTP_MAXTOTAL,
							Constants.DEFAULT_SAGE_INVOKE_HTTP_MAXTOTAL));
					int maxPerRoute = Integer.valueOf(ConfigUtils.getProperty(Constants.SAGE_INVOKE_HTTP_PERROUTE,
							Constants.DEFAULT_SAGE_INVOKE_HTTP_PERROUTE));
					int retryTimes = Integer.valueOf(ConfigUtils.getProperty(Constants.SAGE_INVOKE_HTTP_RETRY,
							Constants.DEFAULT_SAGE_INVOKE_HTTP_RETRY));
					int maxRoute = Integer.valueOf(ConfigUtils.getProperty(Constants.SAGE_INVOKE_HTTP_MAXROUTE,
							Constants.DEFAULT_SAGE_INVOKE_HTTP_MAXROUTE));

					closeableHttpClient = createHttpClient(maxTotal, maxPerRoute, retryTimes, maxRoute,
							invokeTargetInfo.getHost(), Integer.valueOf(invokeTargetInfo.getPort()));
				}
			}
		}
		return closeableHttpClient;
	}

	/**
	 * 创建HttpClient对象
	 *
	 * @param maxTotal    连接池最大连接数
	 * @param maxPerRoute 每个路由的最大连接数
	 * @param retryTimes  重试次数
	 * @param maxRoute    目标主机最大连接数
	 * @param hostname    目标主机
	 * @param port        目标主机端口
	 * @return
	 */
	private static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, final int retryTimes,
	                                                    int maxRoute, String hostname, int port) {
		//添加HTTPS支持
		ConnectionSocketFactory        plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf   = SSLConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainsf).register("https", sslsf).build();

		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
		manager.setMaxTotal(maxTotal);                              //连接池最大连接数
		manager.setDefaultMaxPerRoute(maxPerRoute);                 //将每个路由基础的连接增加到20

		HttpHost httpHost = new HttpHost(hostname, port);
		manager.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);  //将目标主机的最大连接数增加

		//请求重试机制
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				LOGGER.debug("createHttpClient retryRequest executionCount:[{}]", executionCount);

				//重试5次,放弃
				if (executionCount >= retryTimes) return false;
				//如果服务器丢掉了连接,那么重试
				if (exception instanceof NoHttpResponseException) return true;
				//不要重试SSL握手异常
				if (exception instanceof SSLHandshakeException) return false;
				//超时
				if (exception instanceof InterruptedIOException) return false;
				//目标服务器不可达
				if (exception instanceof UnknownHostException) return false;
				//连接被拒绝
				if (exception instanceof ConnectTimeoutException) return false;
				//SSL握手异常
				if (exception instanceof SSLException) return false;

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest       request       = clientContext.getRequest();
				//如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager).setRetryHandler(httpRequestRetryHandler).build();

		return httpClient;
	}
}
