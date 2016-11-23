package org.sagesource.rpc.manage.simple.finder;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.sagesource.common.Constants;
import org.sagesource.common.utils.ConfigUtils;
import org.sagesource.rpc.manage.exception.RpcManageException;
import org.sagesource.rpc.manage.simple.dto.SimpleServiceLocationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>简单服务发现,根据sage.service.location.xxx=schema:host:port:traget,...的格式,来发现服务的调用位置</p>
 * <pre>
 *     author      Sage XueQi
 *     date        2016/11/22
 *     email       job.xueqi@gmail.com
 * </pre>
 */
public class SimpleServiceFinder {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleServiceFinder.class);

	/**
	 * 发现服务位置地址
	 * <p>简单服务位置发现,指定服务名称,然后根据serviceName,在配置中随机查找一个服务位置返回
	 *
	 * @param serviceName
	 * @return
	 * @throws RpcManageException
	 */
	public static SimpleServiceLocationDto findServiceLocation(String serviceName) throws RpcManageException {
		LOGGER.debug("Begin Find Service Location,Service Name:[{}]", serviceName);

		String fullKey        = Constants.SAGE_SERVICE_LOCATION_PREFIX + serviceName;
		String locationValues = ConfigUtils.getProperty(fullKey);
		LOGGER.debug("Find Service Locations:[{}]", locationValues);

		if (Strings.isNullOrEmpty(locationValues))
			throw new RpcManageException("Service Location is Null", serviceName);

		List<String> locations = Splitter.on(",").omitEmptyStrings().splitToList(locationValues);
		String       location  = selectServiceLocation(locations);
		LOGGER.debug("Find Service:[{}] Location:[{}]", serviceName, location);

		SimpleServiceLocationDto dto = builtSimpleServiceLocationDto(location);
		return dto;
	}

	private static String selectServiceLocation(List<String> locations) {
		Long   position = Math.round(Math.random() * locations.size());
		String location = locations.get(position.intValue());

		return location;
	}

	private static SimpleServiceLocationDto builtSimpleServiceLocationDto(String location) {
		List<String>             result = Splitter.on(":").splitToList(location);
		SimpleServiceLocationDto dto    = new SimpleServiceLocationDto();

		int index = 0;
		dto.setSchema(result.get(index++));
		dto.setHost(result.get(index++));
		dto.setPort(result.get(index++));
		dto.setTarget(result.get(index++));

		return dto;
	}
}
