package org.sagesource.rpc.common.utils;

/**
 * <p>Class 操作类</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/19
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class ClassHelper {

	public static ClassLoader getClassLoader() {
		return getClassLoader(ClassHelper.class);
	}
	//................................................................//

	/**
	 * 获取类的Classloader
	 * <p>先获取当前线程的Classloader,如果不存在,就获取制定类的ClassLoader
	 *
	 * @param cls
	 * @return
	 */
	public static ClassLoader getClassLoader(Class<?> cls) {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {

		}
		if (cl == null) {
			cl = cls.getClassLoader();
		}
		return cl;
	}
}
