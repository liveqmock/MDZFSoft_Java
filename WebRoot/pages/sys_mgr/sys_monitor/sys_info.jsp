 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
 import="com.njmd.zfms.web.commons.*"%>
 <%
 String letter=request.getParameter("letter");
 if(letter!=null){
	MonitorInfoBean bean= SysMonitorUtil.getMonitorInfoBean(letter);
 	out.print("{\"totalMemory\":"+bean.getTotalMemory());
 	out.print(",\"freeMemory\":"+bean.getFreeMemory());
 	out.print(",\"maxMemory\":"+bean.getMaxMemory());
 	out.print(",\"osName\":\""+bean.getOsName()+"\"");
 	out.print(",\"totalMemorySize\":"+bean.getTotalMemorySize());
 	out.print(",\"freePhysicalMemorySize\":"+bean.getFreePhysicalMemorySize());
 	out.print(",\"usedMemory\":"+bean.getUsedMemory());
 	out.print(",\"totalThread\":"+bean.getTotalThread());
 	out.print(",\"cpuRatio\":"+bean.getCpuRatio());
 	out.print(",\"harddiskTotal\":"+bean.getHarddiskTotal());
 	out.print(",\"letter\":\""+letter+"\"");
 	out.print(",\"ip\":\""+request.getLocalAddr()+"\"");
 	out.print(",\"harddiskFree\":"+bean.getHarddiskFree()+"}"); 
 }
 %>
 