package org.sagesource.rpc.container.http;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.sagesource.rpc.common.Constants;
import org.sagesource.rpc.common.utils.ConfigUtils;
import org.sagesource.rpc.continer.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Jetty Container Implement</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class JettyContainer extends Container {
	private static final Logger LOGGER = LoggerFactory.getLogger(JettyContainer.class);

	//Jetty Handlers
	private AbstractHandler[] abstractHandlers;
	//Jetty Server 对象
	private Server            server;
	//Jetty Connector 对象
	private NetworkConnector  networkConnector;

	private String host;
	private String port;
	private String timeout;
	private String minThreads;
	private String maxThreads;

	public JettyContainer(AbstractHandler[] abstractHandlers) {
		this.abstractHandlers = abstractHandlers;
	}

	public void start() {
		try {
			server = new Server(createThreadPool());
			networkConnector = createNetworkConnector();

			server.addConnector(networkConnector);
			//设置在JVM退出时关闭Jetty的钩子
			server.setStopAtShutdown(true);
			//设置Jetty的Handlers
			server.setHandler(createHandlers());

			server.start();
		} catch (Exception e) {
			LOGGER.error("Jetty Container Start Error:host=[],port=[{}],timeout=[{}],min_threads=[{}],max_threads=[{}]",
					host, port, timeout, minThreads, maxThreads);
		}
	}

	public void join() throws InterruptedException {
		server.join();
	}

	public void stop() {
		try {
			if (networkConnector != null) {
				networkConnector.close();
				networkConnector = null;
			}
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	//..............................................//

	/**
	 * 创建Jetty ThreadPool
	 *
	 * @return
	 */
	private ThreadPool createThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();

		minThreads = ConfigUtils.getProperty(Constants.SAGE_JETTY_MIN_THREADS, Constants.DEFAULT_SAGE_JETTY_MIN_THREADS);
		maxThreads = ConfigUtils.getProperty(Constants.SAGE_JETTY_MAX_THREADS, Constants.DEFAULT_SAGE_JETTY_MAX_THREADS);

		threadPool.setMinThreads(Integer.valueOf(minThreads));
		threadPool.setMaxThreads(Integer.valueOf(maxThreads));

		return threadPool;
	}

	/**
	 * 创建NetWorkConnector
	 *
	 * @return
	 */
	private NetworkConnector createNetworkConnector() {
		ServerConnector networkConnector = new ServerConnector(server);

		host = ConfigUtils.getProperty(Constants.SAGE_JETTY_HOST, Constants.DEFAULT_SAGE_JETTY_HOST);
		port = ConfigUtils.getProperty(Constants.SAGE_JETTY_PORT, Constants.DEFAULT_SAGE_JETTY_PORT);
		timeout = ConfigUtils.getProperty(Constants.SAGE_JETTY_TIMEOUT, Constants.DEFAULT_SAGE_JETTY_TIMEOUT);

		networkConnector.setHost(host);
		networkConnector.setPort(Integer.valueOf(port));
		networkConnector.setIdleTimeout(Integer.valueOf(timeout));

		return networkConnector;
	}

	/**
	 * 创建Jetty的HandlerCollection
	 *
	 * @return
	 */
	private HandlerCollection createHandlers() {
		HandlerCollection collection = new ContextHandlerCollection();
		collection.setHandlers(abstractHandlers);

		return collection;
	}
}
