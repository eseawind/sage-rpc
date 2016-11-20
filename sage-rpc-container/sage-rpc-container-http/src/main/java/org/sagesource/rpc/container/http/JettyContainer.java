package org.sagesource.rpc.container.http;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.sagesource.common.utils.ConfigUtils;
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

	private static final String JETTY_PORT        = "sage.jetty.port";
	private static final String JETTY_TARGET      = "sage.jetty.target";
	private static final String JETTY_HOST        = "sage.jetty.host";
	private static final String JETTY_TIMEOUT     = "sage.jetty.timeout";
	private static final String JETTY_MIN_THREADS = "sage.jetty.minthread";
	private static final String JETTY_MAX_THREADS = "sage.jetty.maxthread";

	private static final String DEFAULT_JETTY_PORT        = "8080";
	private static final String DEFAULT_JETTY_HOST        = "127.0.0.1";
	private static final String DEFAULT_JETTY_TIMEOUT     = "3000";
	private static final String DEFAULT_JETTY_MIN_THREADS = "10";
	private static final String DEFAULT_JETTY_MAX_THREADS = "500";

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

		minThreads = ConfigUtils.getProperty(JETTY_MIN_THREADS, DEFAULT_JETTY_MIN_THREADS);
		maxThreads = ConfigUtils.getProperty(JETTY_MAX_THREADS, DEFAULT_JETTY_MAX_THREADS);

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

		host = ConfigUtils.getProperty(JETTY_HOST, DEFAULT_JETTY_HOST);
		port = ConfigUtils.getProperty(JETTY_PORT, DEFAULT_JETTY_PORT);
		timeout = ConfigUtils.getProperty(JETTY_TIMEOUT, DEFAULT_JETTY_TIMEOUT);

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
