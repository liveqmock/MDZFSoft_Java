package com.njmd.zfms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njmd.framework.commons.Tree;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.Page;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.zfms.web.entity.file.FileTypeInfo;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.service.FileTypeInfoService;
import com.njmd.zfms.web.service.FileUploadInfoService;
import com.njmd.zfms.web.service.SysCorpService;
import com.njmd.zfms.web.service.SysLoginService;

/**
 * 报表统计
 * 
 * @author CFG
 * 
 */
@Controller
@RequestMapping("/reportStatistics")
public class ReportStatisticsController  extends BaseController{
	// 基础目录
	private final String BASE_DIR = "/data_statistics/report_statistics/";
	
	@Autowired
	private FileUploadInfoService fileUploadInfoService;
	
	@Autowired
	private FileTypeInfoService fileTypeInfoService;
	
	@Autowired
	private SysCorpService sysCorpService;
	@Autowired
	private SysLoginService sysLoginService;

	
	/** 主页面 */
	@RequestMapping 
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		return BASE_DIR + "corp_statistics";
	}
	/** 部门统计 */
	@RequestMapping(value = "/corpStatistics")
	public String corpStatistics(HttpServletRequest request, Page page, Model model) throws Exception
	{
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		return BASE_DIR + "corp_statistics";
	}
	/** 警员统计 */
	@RequestMapping(value = "/userStatistics")
	public String userStatistics(HttpServletRequest request, Page page, Model model) throws Exception
	{
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		return BASE_DIR + "user_statistics";
	}
	
	/** 机构报表 */
	@RequestMapping(value = "/corpChart")
	public String corpChart(HttpServletRequest request, Model model) throws Exception
	{
		//横坐标
		List<String> categoriesList;
		//图表数据
		Map<String,Integer> chartData;
		//表格数据
		Map<String,Integer> detialData;
		//部门
		List<SysCorp> corpList;
		//文件类型
		List<FileTypeInfo> fileTypeInfoList;
		
		Map<String,String> corpCategoriesMap;
		
		
		int queryType = request.getParameter("queryType")==null?1:Integer.parseInt(request.getParameter("queryType"));
		String startDate = request.getParameter("startDate") ;
		String endDate= request.getParameter("endDate") ;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		Long parentId=request.getParameter("corpId")==null?1:Long.parseLong(request.getParameter("corpId"));
		categoriesList=new ArrayList<String>();
		corpList=sysCorpService.findByParentId(parentId);
		fileTypeInfoList= fileTypeInfoService.findAll();
		FileTypeInfo fileType=new FileTypeInfo();
		fileType.setTypeName("未分类");
		fileTypeInfoList.add(fileType);
		
		if(queryType==1){
			//按年统计
			
			for(int i=1;i<=12;i++){
				if(i<10) {
					categoriesList.add(year+"0"+i);
				}else
					categoriesList.add(year+Integer.toString(i));
			} 
			
		}
		if(queryType==2){
			//按月统计 
			
			 int lastDay=DateTimeUtil.lastDay(Integer.parseInt(year),Integer.parseInt( month));
			 for(int i=1;i<=lastDay;i++){
				 if(i<10) {
					 categoriesList.add("0"+i);
					}else
						categoriesList.add( Integer.toString(i));
				
			 }
			
			
		}
		if(queryType==3){
			//按天统计
			startDate=startDate.replaceAll("-", "");
			endDate=endDate.replaceAll("-","");
			String startDate1=startDate.toString();
			while(Integer.parseInt(startDate1)<=Integer.parseInt(endDate)){
				categoriesList.add(startDate1);
				startDate1=	DateTimeUtil.rollDate(startDate1, 1);
			}
		}  
		
		StringBuffer categories=new StringBuffer("");
		for(String s:categoriesList){
			if(!categories.toString().equals("")){
				categories.append(",");
			}
			categories.append(s);
		}
		
		
		
		model.addAttribute("categories", categories.toString());
		chartData=fileUploadInfoService.getCorpChartData(queryType, parentId, year, month, startDate, endDate);
		corpCategoriesMap=new HashMap<String,String>();
	    for(SysCorp corp:corpList){
	    	StringBuffer sb=new StringBuffer("");
	    	for(String date:categoriesList){
	    		if(!sb.toString().equals("")){
	    			sb.append(",");
	    		}
	    		sb.append(chartData.get(corp.getCorpId()+"_"+date));
	    	}
	    	corpCategoriesMap.put(corp.getCorpName(), sb.toString());
	    }
	    detialData=fileUploadInfoService.getCorpDetialData(queryType, parentId, year, month, startDate, endDate);
	    
	    model.addAttribute("detialData", detialData);
	    model.addAttribute("corpList", corpList);
	    model.addAttribute("fileTypeInfoList",fileTypeInfoList);
	    model.addAttribute("categoriesList", categoriesList);
	    model.addAttribute("corpCategoriesMap", corpCategoriesMap);
	    SysCorp sysCorp= sysCorpService.findById(parentId);
	    model.addAttribute("sysCorp", sysCorp);
	    model.addAttribute("corpList", corpList);
	    
		return BASE_DIR + "corp_chart";
	}

	/** 个人报表 */
	@RequestMapping(value = "/userChart")
	public String userChart(HttpServletRequest request, Model model) throws Exception
	{
		//横坐标
		List<String> categoriesList;
		//图表数据
		Map<String,Integer> chartData;
		//表格数据
		Map<String,Integer> detialData;
		//部门
		List<SysCorp> corpList;
		//文件类型
		List<FileTypeInfo> fileTypeInfoList;
		
		Map<String,String> corpCategoriesMap;
		
		
		int queryType = request.getParameter("queryType")==null?1:Integer.parseInt(request.getParameter("queryType"));
		String startDate = request.getParameter("startDate") ;
		String endDate= request.getParameter("endDate") ;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		Long uploadUserId= Long.parseLong(request.getParameter("uploadUserId"));
		categoriesList=new ArrayList<String>(); 
		
		 
		if(queryType==1){
			//按年统计
			
			for(int i=1;i<=12;i++){
				if(i<10) {
					categoriesList.add(year+"0"+i);
				}else
					categoriesList.add(year+Integer.toString(i));
			} 
			
		}
		if(queryType==2){
			//按月统计 
			
			 int lastDay=DateTimeUtil.lastDay(Integer.parseInt(year),Integer.parseInt( month));
			 for(int i=1;i<=lastDay;i++){
				 if(i<10) {
					 categoriesList.add("0"+i);
					}else
						categoriesList.add( Integer.toString(i));
			 }
		}
		if(queryType==3){
			//按天统计
			startDate=startDate.replaceAll("-", "");
			endDate=endDate.replaceAll("-","");
			String startDate1=startDate.toString();
			while(Integer.parseInt(startDate1)<=Integer.parseInt(endDate)){
				categoriesList.add(startDate1);
				startDate1=	DateTimeUtil.rollDate(startDate1, 1);
			}
		}  
		
		
		
		StringBuffer categories=new StringBuffer("");
		for(String s:categoriesList){
			if(!categories.toString().equals("")){
				categories.append(",");
			}
			categories.append(s);
		}
		
		
		//获取分类数据 及文件数量
		
		chartData=fileUploadInfoService.getUserChartData(queryType, uploadUserId, year, month, startDate, endDate);
		corpCategoriesMap=new HashMap<String,String>();
		 
		fileTypeInfoList= fileTypeInfoService.findAll();
		FileTypeInfo fileType=new FileTypeInfo();
		fileType.setTypeName("未分类");
		fileTypeInfoList.add(fileType);
	    for(FileTypeInfo type:fileTypeInfoList){
	    	StringBuffer sb=new StringBuffer("");
	    	for(String date:categoriesList){
	    		if(!sb.toString().equals("")){
	    			sb.append(",");
	    		}
	    		sb.append(chartData.get(type.getTypeId()+"_"+date));
	    	}
	    	corpCategoriesMap.put(type.getTypeName(), sb.toString());
	    }
	     
	    SysLogin uploadUser= sysLoginService.findById(uploadUserId);
	    model.addAttribute("chartData", chartData);
	    model.addAttribute("categories", categories.toString());
	    model.addAttribute("fileTypeInfoList",fileTypeInfoList);
	    model.addAttribute("categoriesList", categoriesList);
	    model.addAttribute("corpCategoriesMap", corpCategoriesMap);
	    model.addAttribute("uploadUser", uploadUser); 
		return BASE_DIR + "user_chart";
	}
  
 
	
}
