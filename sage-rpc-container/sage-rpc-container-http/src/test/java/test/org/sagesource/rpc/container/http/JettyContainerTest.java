package test.org.sagesource.rpc.container.http;

import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;
import org.sagesource.rpc.container.http.JettyContainer;

/**
 * <p></p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/20
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class JettyContainerTest {

	@Test
	public void test() throws InterruptedException {
		JettyContainer container = new JettyContainer(new AbstractHandler[0]);
		container.start();
		container.join();
	}

}
