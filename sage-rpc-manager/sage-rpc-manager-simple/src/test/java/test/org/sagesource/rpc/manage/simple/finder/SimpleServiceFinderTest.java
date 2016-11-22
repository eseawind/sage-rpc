package test.org.sagesource.rpc.manage.simple.finder;

import org.junit.Test;
import org.sagesource.rpc.manage.exception.RpcManageException;
import org.sagesource.rpc.manage.simple.dto.SimpleServiceLocationDto;
import org.sagesource.rpc.manage.simple.finder.SimpleServiceFinder;

/**
 * <p></p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/22
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class SimpleServiceFinderTest {

	@Test
	public void findServiceLocationTest() throws RpcManageException {
		SimpleServiceLocationDto result = SimpleServiceFinder.findServiceLocation("passport");
		System.out.println(String.format("result: host=[%s],port=[%s],target=[%s]", result.getHost(), result.getPort(), result.getTarget()));


	}
}
