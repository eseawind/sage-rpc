package org.sagesource.rpc.invoke.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.sagesource.rpc.common.Charsets;
import org.sagesource.rpc.common.Constants;
import org.sagesource.rpc.invoke.exception.RpcInvokeException;
import org.sagesource.rpc.invoke.http.client.InvokeHttpClient;
import org.sagesource.rpc.invoke.http.dto.InvokeTargetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Http传输层实现</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class HttpInvoker {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpInvoker.class);

	/**
	 * 请求远程服务
	 *
	 * @param requestMessage   消息
	 * @param invokeTargetInfo 远程服务调用信息
	 * @return
	 */
	public String request(String requestMessage, InvokeTargetInfo invokeTargetInfo) throws RpcInvokeException {
		String   serviceUrl = invokeTargetInfo.builtUrl();
		HttpPost post       = new HttpPost(serviceUrl);
		post.setHeader("Connection", "Keep-Alive");
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair(Constants.SAGE_HTTP_INVOKE_DATA, requestMessage));

		LOGGER.debug("Begin Remote Processor Call,message=[{}],url=[{}]", requestMessage, serviceUrl);

		try {
			post.setEntity(new UrlEncodedFormEntity(params, Charsets.UTF_8));
			CloseableHttpResponse response = InvokeHttpClient.getHttpClient(invokeTargetInfo).execute(post);

			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
			} else {
				throw new RpcInvokeException("Remote Processor Call Response Error", requestMessage + "[" + serviceUrl + "]");
			}
		} catch (Exception e) {
			throw new RpcInvokeException("Remote Processor Call Exception", e, requestMessage + "[" + serviceUrl + "]");
		}
	}

	/**
	 * 远程服务响应
	 *
	 * @param responseMessage
	 * @param outputStream
	 * @throws RpcInvokeException
	 */
	public void response(String responseMessage, OutputStream outputStream) throws RpcInvokeException {
		LOGGER.debug("Remote Processor Call Service Response:[{}]", responseMessage);

		try {
			outputStream.write(responseMessage.getBytes(Charsets.UTF_8));
		} catch (IOException e) {
			throw new RpcInvokeException("Remote Processor Call Service Exception", e, responseMessage);
		}
	}

	//.......................................................................//

	/**
	 * 单例类
	 */
	private static class SingletonHolder {
		private static final HttpInvoker INSTANCE = new HttpInvoker();
	}

	public static HttpInvoker getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
