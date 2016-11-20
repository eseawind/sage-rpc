package org.sagesource.rpc.continer;

/**
 * <p>Jetty Container</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public abstract class Container {
	public static volatile boolean   isStart   = false;
	public static volatile Container container = null;

	/**
	 * Jetty Container Start Method
	 */
	public abstract void start();

	/**
	 * Jetty Container Join Method
	 *
	 * @throws InterruptedException
	 */
	public abstract void join() throws InterruptedException;

	/**
	 * Jetty Container Stop Method
	 */
	public abstract void stop();
}
