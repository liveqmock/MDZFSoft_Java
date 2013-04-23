package com.njmd.zfms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.Page;
import com.njmd.zfms.web.entity.sys.SysServerInfo;
import com.njmd.zfms.web.service.SysServerInfoService;

/**
 * 系统监控
 * 
 * @author CFG
 * 
 */
@Controller
@RequestMapping("/sysMonitor")
public class SysMonitorController extends BaseController
{
	private final String[] INFORMATION_PARAMAS = { "用户", "用户账号" };
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/sys_monitor/";
 

	@Autowired
	private SysServerInfoService sysServerInfoService;
 

	/** 主页面 */
	@RequestMapping 
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		 
		return BASE_DIR + "sys_monitor";
	}
	/** 图标*/
	@RequestMapping(value = "/chart")
	public String chart(HttpServletRequest request, Page page, Model model) throws Exception
	{
		int queryType = request.getParameter("queryType")==null?1:Integer.parseInt(request.getParameter("queryType"));
		String saveIp = request.getParameter("saveIp");
		
		String chartTitle="";
		StringBuffer categories=new StringBuffer("");
		//CPU占用率
		StringBuffer ratioCpuData=new StringBuffer("");
		//内存占用率
		StringBuffer ratioMemoryData=new StringBuffer("");
		//硬盘占用率
		StringBuffer ratioHarddiskData=new StringBuffer("");
		switch(queryType)
    	{
			case 1: 
				chartTitle="按天统计";
				List<SysServerInfo> list=sysServerInfoService.serverInfoListByDay(request.getParameter("date1"), saveIp);
				if(list!=null){
					for(SysServerInfo s:list){
						if(!categories.toString().equals("")){
							categories.append(",");
							ratioCpuData.append(",");
							ratioMemoryData.append(",");
							ratioHarddiskData.append(",");
						}
						categories.append(s.getCreateTime().substring(8, 10));
						ratioCpuData.append(s.getRatioCpu());
						ratioMemoryData.append(s.getRatioMemory());
						ratioHarddiskData.append(s.getRatioHarddisk());
					}
				}
				
			break;
			case 2: 
				chartTitle="按周统计";
				List<Object[]> list1=sysServerInfoService.serverInfoListByWeek(request.getParameter("date2"), saveIp);
				if(list1!=null){
					for(Object[] obj :list1){
						if(!categories.toString().equals("")){
							categories.append(",");
							ratioCpuData.append(",");
							ratioMemoryData.append(",");
							ratioHarddiskData.append(",");
						}
						categories.append(obj[0]);
						ratioCpuData.append(obj[1]);
						ratioMemoryData.append(obj[2]);
						ratioHarddiskData.append(obj[3]);
					}
				}
			break;
			case 3: 
				chartTitle="按月统计";
				List<Object[]> list2=sysServerInfoService.serverInfoListByMonth(request.getParameter("year"), request.getParameter("month"),   saveIp);
				if(list2!=null){
					for(Object[] obj :list2){
						if(!categories.toString().equals("")){
							categories.append(",");
							ratioCpuData.append(",");
							ratioMemoryData.append(",");
							ratioHarddiskData.append(",");
						}
						categories.append(obj[0]);
						ratioCpuData.append(obj[1]);
						ratioMemoryData.append(obj[2]);
						ratioHarddiskData.append(obj[3]);
					}
				}
			break;
			case 4: 
				chartTitle="按年统计";
				List<Object[]> list3=sysServerInfoService.serverInfoListByYear(request.getParameter("year"), saveIp);
				if(list3!=null){
					for(Object[] obj :list3){
						if(!categories.toString().equals("")){
							categories.append(",");
							ratioCpuData.append(",");
							ratioMemoryData.append(",");
							ratioHarddiskData.append(",");
						}
						categories.append(obj[0]);
						ratioCpuData.append(obj[1]);
						ratioMemoryData.append(obj[2]);
						ratioHarddiskData.append(obj[3]);
					}
				}
			break;
			default : ;break;
    	}
		
		model.addAttribute("saveIp", saveIp);
		model.addAttribute("chartTitle", chartTitle);
		model.addAttribute("categories", categories);
		model.addAttribute("ratioCpuData", ratioCpuData);
		model.addAttribute("ratioMemoryData", ratioMemoryData);
		model.addAttribute("ratioHarddiskData", ratioHarddiskData);
		return BASE_DIR + "chart";
	}
}
