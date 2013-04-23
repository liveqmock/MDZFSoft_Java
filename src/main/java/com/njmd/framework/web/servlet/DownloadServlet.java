package com.njmd.framework.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

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
	private final String FROM_LOCAL = "1";// 下载文件来源-本地文件,参数传递路径是本地地址,类似"e://download/1.jpg"
	private final String FROM_RESOUCE = "2";// 下载文件来源-本应用文件,参数传递路径是相对于应用的url地址,类似"/download/1.jpg"
	private final String FROM_HTTP = "3";// 下载文件来源-url文件,参数传递路径是文件URL地址,类似"http://127.0.0.1/download/1.jpg"

	File sourceFile = null;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		InputStream in = null;
		OutputStream out = null;
		String sourceFileName = null;// 下载源文件文件名
		String downloadFileFrom = null;// 下载文件来源
		String zipName = "";
		String sourceFilePath = request.getParameter("sourceFilePath"); // 下载源文件路径
		String isDel = request.getParameter("isDel");// 下载后是否需要删除原文件
		String isCompress = request.getParameter("isCompress");// 是否需要压缩
		String targetFileName = request.getParameter("targetFileName"); // 目标文件名
		if (sourceFilePath != null && !sourceFilePath.equals(""))
		{

			sourceFilePath = URLDecoder.decode(sourceFilePath, "utf-8");
			if (sourceFilePath.startsWith("http"))
				downloadFileFrom = FROM_HTTP;
			else if (sourceFilePath.startsWith("/"))
				downloadFileFrom = FROM_RESOUCE;
			else
				downloadFileFrom = FROM_LOCAL;
		}

		// 判断文件是否存在
		boolean isFileExist = false;
		try
		{

			if (downloadFileFrom.equals(FROM_LOCAL))
			{
				sourceFile = new File(sourceFilePath);
				in = new FileInputStream(sourceFile);
				isFileExist = sourceFile.exists();
				sourceFileName = FilenameUtils.getName(sourceFilePath);
			}
			if (downloadFileFrom.equals(FROM_RESOUCE))
			{
				sourceFile = new File(this.getServletContext().getRealPath("/") + sourceFilePath);
				in = new FileInputStream(sourceFile);
				isFileExist = sourceFile.exists();
				sourceFileName = FilenameUtils.getName(sourceFilePath);
			}
			if (downloadFileFrom.equals(FROM_HTTP))
			{
				URL resourceUrl = new URL(sourceFilePath);
				HttpURLConnection urlconnection = (HttpURLConnection) resourceUrl.openConnection();
				urlconnection.connect();
				in = urlconnection.getInputStream();
				sourceFileName = FilenameUtils.getName(sourceFilePath);
				isFileExist = true;
			}

		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		if (!isFileExist)
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
			zipName = sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) + ".zip";// zip文件名
			File zipFile = new File(this.getServletContext().getRealPath("/") + "/temp/" + zipName);
			// 压缩文件不存在
			if (!zipFile.exists())
			{
				ZipUtil.zipFile(sourceFile, zipName);
			}
			targetFileName = zipName;
		}
		else
		{
			if (targetFileName == null)
				targetFileName = sourceFileName;
		}

		try
		{

			response.reset();
			response.setContentType("*/*;charset=utf-8");
			String realFileName = FilenameUtils.getName(targetFileName);
			response.setHeader("Content-disposition", "attachment; filename=" + realFileName);

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
							if (sourceFile.exists())
								sourceFile.delete();
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

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
