package com.njmd.zfms.web.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.njmd.framework.utils.HttpUtil;
import com.njmd.framework.utils.mapper.JsonMapper;
import com.njmd.zfms.web.commons.MonitorInfoBean;
import com.njmd.zfms.web.constants.ConfigConstants;
import com.njmd.zfms.web.entity.sys.SysServerInfo;
import com.njmd.zfms.web.service.SysServerInfoService;

@Component
public class SysMonitorTask
{
	@Autowired
	private SysServerInfoService syServerInfoService;

	@Scheduled(fixedRate = 10 * 60 * 1000)
	void doSomethingWithDelay() throws Exception
	{
		String urls = ConfigConstants.MONITOR_URLS;
		if (urls != null)
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("m", Double.toString(Math.random()));
			for (String url : urls.split(","))
			{

				String retstr = HttpUtil.URLPost(url, params, HttpUtil.URL_PARAM_DECODECHARSET_UTF8);
				if (retstr != null)
				{
					if (retstr.trim().startsWith("{"))
					{
						JsonMapper jsonMapper = new JsonMapper();
						MonitorInfoBean mib = jsonMapper.fromJson(retstr, MonitorInfoBean.class);
						SysServerInfo serverInfo = new SysServerInfo();

						serverInfo.setRatioCpu(BigDecimal.valueOf(mib.getCpuRatio()));
						serverInfo.setRatioMemory(BigDecimal.valueOf((mib.getTotalMemory() - mib.getFreeMemory()) / mib.getTotalMemory() * 100));
						serverInfo.setUseMemory((mib.getTotalMemory() - mib.getFreeMemory()) + "");
						serverInfo
								.setRatioHarddisk(BigDecimal.valueOf(((Double.parseDouble(mib.getHarddiskTotal()) - Double.parseDouble(mib.getHarddiskFree()))
										/ Double.parseDouble(mib.getHarddiskTotal()) * 100)));
						serverInfo.setUseHarddisk(Double.parseDouble(mib.getHarddiskTotal()) - Double.parseDouble(mib.getHarddiskFree()) + "");
						serverInfo.setLetter(mib.getLetter());
						serverInfo.setSaveIp(mib.getIp());

						serverInfo.setCreateTime(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
						syServerInfoService.save(serverInfo);
					}

				}

			}

		}

	}

	public static void main(String args[])
	{

	}
}
