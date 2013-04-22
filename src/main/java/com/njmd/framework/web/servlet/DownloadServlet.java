package com.njmd.framework.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njmd.framework.utils.io.ZipUtil;

/**
 * 
 * @title: DownloadServlet.java
 * @description: 文件下载servlet
 * 
 * @author: Yao
 * 
 */
public class DownloadServlet extends HttpServlet
{
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	protected Logger logger = LoggerFactory.getLogger(getClass());// 日志
	private String pathUrl;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		FileInputStream in = null;
		OutputStream out = null;
		String targetFileNm = null;
		final String sourceFileNm;
		String zipName = "";
		String isDel = request.getParameter("isDel");
		String isCompress = request.getParameter("isCompress");
		sourceFileNm = request.getParameter("sourceFileNm"); // 源文件名
		targetFileNm = request.getParameter("targetFileNm"); // 目标文件名

		File file = new File(getServletContext().getRealPath("/") + "/downloads/" + sourceFileNm);
		if (!file.exists())
		{
			response.setContentType(CONTENT_TYPE);
			PrintWriter write = response.getWriter();
			write.println("<html>");
			write.println("<head><title>文件下载</title></head>");
			write.println("<body bgcolor=\"#ffffff\">");
			write.println("<script>alert('对不起，你要下载的文件不存在！');window.close();</script>");
			write.println("</body></html>");
			return;
		}
		// 是否压缩
		if (isCompress != null && "true".equals(isCompress))
		{
			zipName = sourceFileNm.substring(0, sourceFileNm.lastIndexOf(".")) + ".zip";// zip文件名
			File zipFile = new File(zipName);
			// 压缩文件不存在
			if (!zipFile.exists())
			{
				ZipUtil zu = new ZipUtil();
				ZipUtil.zipFile(new File(sourceFileNm), zipName);
				// delFile(sourceFileNm);
			}
			targetFileNm = zipName;
		}
		else
		{
			if (targetFileNm == null)
				targetFileNm = sourceFileNm;
		}

		try
		{

			response.reset();
			// System.out.println(fileName.substring(fileName.lastIndexOf("\\")+1));
			response.setContentType("*/*;charset=utf-8");
			String realFileName = FilenameUtils.getName(targetFileNm);
			response.setHeader("Content-disposition", "attachment; filename=" + realFileName);

			in = new FileInputStream(file);
			out = response.getOutputStream();

			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = in.read(buf)) != -1)
			{
				out.write(buf, 0, i);
			}
			out.flush();

		}
		catch (Exception e)
		{
			logger.error("文件下载出错：", e);
		}
		finally
		{
			if (in != null)
			{
				in.close();
			}
			if (out != null)
			{
				out.close();
			}
			// 是否要删除
			if (isDel != null && "true".equals(isDel))
			{
				Thread t = new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(1000 * 60 * 5);
							delFile(sourceFileNm);
						}
						catch (Exception e)
						{
						}

					}
				};
				t.start();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 */
	private void delFile(String fileName)
	{
		File file = new File(fileName);
		if (file.exists())
			file.delete();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	public String getPathUrl()
	{
		return pathUrl;
	}

	public void setPathUrl(String pathUrl)
	{
		this.pathUrl = pathUrl;
	}
}
