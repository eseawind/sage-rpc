package test.org.sagesource.common.utils;

import org.junit.Test;
import org.sagesource.rpc.common.utils.ConfigUtils;

/**
 * <p></p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class ConfigUtilsTest {

	@Test
	public void getPropertyTest() {
		String val = ConfigUtils.getProperty("sage");
		System.out.println(val);
	}

}
