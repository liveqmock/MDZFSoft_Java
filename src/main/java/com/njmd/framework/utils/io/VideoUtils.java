package com.njmd.framework.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 视频处理工具类
 * 
 * @author Yao
 * 
 */
public class VideoUtils
{
	protected static Logger logger = LoggerFactory.getLogger(VideoUtils.class);// 日志

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param imgfilepath
	 * @return
	 */
	public static boolean makeImgbyMP4(String ffmpegpath, String videofilepath, String imgfilepath)
	{
		// System.out.println(videofilepath + "->" + imgfilepath);
		ffmpegpath = ffmpegpath.replaceAll("/", "\\\\");
		videofilepath = videofilepath.replaceAll("/", "\\\\");
		imgfilepath = imgfilepath.replaceAll("/", "\\\\");
		// System.out.println(videofilepath + "->" + imgfilepath);
		List<String> commend = new java.util.ArrayList<String>();
		commend.add("\"" + ffmpegpath + "\\bin\\ffmpeg" + "\"");
		commend.add("-i");
		commend.add("\"" + videofilepath + "\"");
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("1");
		commend.add("-qscale");
		commend.add("1");
		commend.add("-s");
		commend.add("640*360");
		commend.add("-vframes");
		commend.add("1");
		commend.add("\"" + imgfilepath + "\"");
		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try
		{
			builder.command(commend);
			builder.redirectErrorStream(true);
			Process p = builder.start();
			byte[] b = new byte[1024];
			int readbytes = -1;
			StringBuffer output = new StringBuffer();
			// 读取进程输出值
			InputStream in = p.getInputStream();
			try
			{
				while ((readbytes = in.read(b)) != -1)
				{
					output.append(new String(b, 0, readbytes));
				}
				logger.debug(output.toString());
			}
			catch (IOException e1)
			{
			}
			finally
			{
				try
				{
					in.close();
				}
				catch (IOException e2)
				{
				}
			}

		}
		catch (Exception e)
		{
			mark = false;
			e.printStackTrace();
		}
		return mark;
	}

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param flvfilepath
	 * @return
	 */
	public static void makeFlashbyvideo(String ffmpegpath, String videofilepath, String flvfilepath)
	{
		System.out.println(videofilepath + "->" + flvfilepath);
		List<String> commend = new java.util.ArrayList<String>();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(videofilepath);
		commend.add("-sameq");
		commend.add("-ar");
		commend.add("44100");
		commend.add(flvfilepath);
		try
		{
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			Process p = builder.start();
			p.waitFor();
			System.out.println("over");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param flvfilepath
	 * @return
	 */
	public static void makeFlashbyMP4(String ffmpegpath, String videofilepath, String flvfilepath)
	{
		System.out.println(videofilepath + "->" + flvfilepath);
		List<String> commend = new java.util.ArrayList<String>();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(videofilepath);
		commend.add("-qscale");
		commend.add("10");
		commend.add("-ar");
		commend.add("44100");
		commend.add("-s");
		commend.add("640*360");
		commend.add(flvfilepath);
		try
		{
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			Process p = builder.start();
			p.waitFor();
			System.out.println("over");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		makeImgbyMP4("d:/ffmpeg-20121125-git-26c531c-win64-static",
				"D:/Program Files/apache-tomcat-7.0.32/webapps/ftp_resource/1/0/2013-05-03/20130503_153802_212.mp4",
				"D:/Program Files/apache-tomcat-7.0.32/webapps/ftp_resource/1/0/2013-05-03/20130503_153802_212.jpg");
	}
}
