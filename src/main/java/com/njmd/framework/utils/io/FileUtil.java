package com.njmd.framework.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 
 * @title:文件操作工具类
 * @description: 直接从commons-lang包中的FileUtils集成，基本可以满足要求
 * 
 *               避免开发人员不知道commons-lang包FileUtils
 * @author: Yao
 */
public final class FileUtil extends org.apache.commons.io.FileUtils {
	/**
	 * 将字符串追加到文本文件，如果文件已存在，追加到原来的内容后
	 * 
	 * 
	 * @param str
	 *            String, 要写出的内容
	 * @param fileName
	 *            String, 文件名
	 * 
	 * @throws Exception
	 */
	public static void appendTxt(String str, String fileNane) throws Exception {
		write(str, fileNane, true);
	}

	/**
	 * 文件复制
	 * 
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public static final void copy(String from, String to) throws Exception {
		makeParentDir(to);
		FileInputStream fis = new FileInputStream(from);
		try {
			FileOutputStream fos = new FileOutputStream(to, false);
			try {
				byte[] buf = new byte[1024 * 16];
				int size = 0;
				while ((size = fis.read(buf)) != -1)
					fos.write(buf, 0, size);
			} catch (Exception ex) {
				throw ex;
			} finally {
				fos.flush();
				fos.close();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			fis.close();
		}
	}

	/**
	 * 文件复制目录
	 * 
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public static final void copyDir(String from, String to, boolean recursive) throws Exception {
		if (from.endsWith("/"))
			from = from.substring(0, from.length() - 1);
		if (to.endsWith("/"))
			to = to.substring(0, to.length() - 1);
		File file = new File(from);
		if (file.isDirectory()) {
			String[] fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				String tmp = from + "/" + fileList[i];
				if (new File(tmp).isFile() || recursive)
					copyDir(tmp, to + "/" + fileList[i], recursive);
			}
		} else
			copy(from, to);
	}

	/**
	 * 创建指定文件所在的目录
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static final void makeParentDir(String filename) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			String parent = file.getParent();
			if (parent != null)
				new File(parent).mkdirs();
		}
	}

	/**
	 * 用来创建目录
	 * 
	 * @param dirName
	 *            String, 目录名（或绝对路径文件名）
	 * 
	 * @return bool, 是否创建成功
	 */
	public static boolean mkDir(String dirName) {
		File file = new File(dirName);
		if (file.exists()) // 判断文件或目录是否存在

		{
			if (file.canWrite() == false) // 判断文件或目录是否可写

			{
				return false;
			} else {
				return true;
			}
		} else
		// 如果不存在就创建目录
		{
			String path = null;
			// 确保windows系统下两种分割符兼容
			int firstSlash1 = dirName.indexOf("/");
			int finalSlash1 = dirName.lastIndexOf("/");

			int firstSlash2 = dirName.indexOf("\\");
			int finalSlash2 = dirName.lastIndexOf("\\");

			firstSlash1 = firstSlash1 < firstSlash2 ? firstSlash1 : firstSlash2;
			finalSlash1 = finalSlash1 > finalSlash2 ? finalSlash1 : finalSlash2;

			// if (firstSlash1 < 0 && finalSlash1 < 0)
			// {
			// firstSlash1 = dirName.indexOf("\\");
			// finalSlash1 = dirName.lastIndexOf("\\");
			// }

			if (finalSlash1 == 0) // 非法路径
			{
				return false;
			} else if (finalSlash1 == 1) // UNIX系统根目录

			{
				path = File.separator;
			} else if (firstSlash1 == finalSlash1) // 确保文件路径分隔符是路径的一部分
			{
				path = dirName.substring(0, finalSlash1 + 1);
			} else {
				path = dirName.substring(0, finalSlash1);
			}

			File dir = new File(path);
			return dir.mkdirs();
		}
	}

	/**
	 * 从文件中读取二进制字节流
	 * 
	 * @param fileName
	 *            String, 要读取的文件名
	 * 
	 * @return byte[], 读取的内容
	 */
	public static byte[] read(String fileName) throws Exception {
		if (fileName == null) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			return bytes;
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
	}

	/**
	 * 从文件中读取第一行文本
	 * 
	 * 
	 * @param fileName
	 *            String, 文件名
	 * 
	 * @return String, 文件第一行内容
	 */
	public static String readLine(String fileName) throws Exception {
		if (fileName == null) {
			return null;
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			String str = in.readLine();
			if (str != null) {
				str = str.trim();
			}
			return str;
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
	}

	/**
	 * 从文件中读取文本到缓冲
	 * 
	 * 
	 * @param fileName
	 *            String, 文件名
	 * 
	 * @return String, 所读文件内容
	 * 
	 * @throws Exception
	 */
	public static String readTxt(String fileName) throws Exception {
		if (fileName == null) {
			return null;
		}
		BufferedReader in = null;
		String lineIn = null;
		StringBuffer buffer = new StringBuffer();
		try {
			in = new BufferedReader(new FileReader(fileName));
			while ((lineIn = in.readLine()) != null) {
				buffer.append(lineIn.trim());
				buffer.append("\r\n"); // 行号不可少

			}
			return buffer.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
	}

	/**
	 * 把二进制字节流写入文件
	 * 
	 * 
	 * @param bytes
	 *            byte[], 要写出的字节数组
	 * @param fileName
	 *            String, 文件名
	 */
	public static void write(byte[] bytes, String fileName) throws Exception {
		if (bytes == null || fileName == null) {
			return;
		}
		mkDir(fileName); // 写文件需要检查相关目录是否存在，没有的话需要创建

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(bytes);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();
		}
	}

	/**
	 * 把字符串分割后写入文件
	 * 
	 * 
	 * @param str
	 * @param regex
	 * @param fileName
	 * @throws Exception
	 */
	public static void write(String str, String regex, String fileName) throws Exception {
		if (str == null || fileName == null) {
			return;
		}
		mkDir(fileName); // 写文件需要检查相关目录是否存在，没有的话需要创建

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			String[] lines = str.split(regex);
			if (lines != null && lines.length > 0) {
				for (int i = 0; i < lines.length; i++) {
					writer.write(lines[i] + "\n");
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			writer.close();
		}
	}

	/**
	 * 写文件操作
	 * 
	 * 
	 * @param str
	 *            String, 要写出的内容
	 * @param fileName
	 *            String, 文件名
	 * 
	 * @param isAppend
	 *            bool, 是否追加，如果值为false，则覆盖掉以前的内容
	 * @throws Exception
	 */
	private static void write(String str, String fileName, boolean isAppend) throws Exception {
		if (fileName == null || str == null) {
			return;
		}
		mkDir(fileName); // 写文件需要检查相关目录是否存在，没有的话需要创建

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(fileName, isAppend));
			out.write(str);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();
		}
	}

	/**
	 * 将字符串写出到文本文件，如果文件已存在，将覆盖原来的内容
	 * 
	 * @param str
	 *            String, 要写出的内容
	 * @param fileName
	 *            String, 文件名
	 * 
	 * 
	 * @throws Exception
	 */
	public static void writeTxt(String str, String fileName) throws Exception {
		write(str, fileName, false);
	}
}
