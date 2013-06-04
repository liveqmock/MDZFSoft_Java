package com.njmd.framework.commons;

import javax.servlet.http.HttpServletRequest;

/**
 * 消息提示对象
 * 
 * @author Yao
 * 
 */
public class Message
{ 
	private final static String MESSAGE_REQUEST_NAME = "";

	/** 消息类型－默认 */
	public static int MESSAGE_TYPE_DEFAULT = 0;

	/** 消息类型－成功 */
	public static int MESSAGE_TYPE_SUCCESS = 1;

	/** 消息类型－失败 */
	public static int MESSAGE_TYPE_ERROR = 2;

	/** 消息类型 */
	private int messageType;

	/** 消息框标题 */
	private final String title = "消息提示";

	/** 消息内容 */
	private String messageInfo;

	public int getMessageType()
	{
		return this.messageType;
	}

	public void setMessageType(int messageType)
	{
		this.messageType = messageType;
	}

	public String getMessageInfo()
	{
		return this.messageInfo;
	}

	public void setMessageInfo(String message)
	{
		this.messageInfo = message;
	}

	public String getTitle()
	{
		return this.title;
	}

	/**
	 * 获得一个成功消息对象
	 * 
	 * @param msg
	 * @return
	 */
	public synchronized static void addSuccessMessage(String msg, HttpServletRequest request)
	{
		Message message = new Message();
		message.setMessageInfo(msg);
		message.setMessageType(Message.MESSAGE_TYPE_SUCCESS);
		request.setAttribute(MESSAGE_REQUEST_NAME, message);
	}

	/**
	 * 获得一个失败消息对象
	 * 
	 * @param msg
	 * @return
	 */
	public synchronized static void addErrorMessage(String msg, HttpServletRequest request)
	{
		Message message = new Message();
		message.setMessageInfo(msg);
		message.setMessageType(Message.MESSAGE_TYPE_ERROR);
		request.setAttribute(MESSAGE_REQUEST_NAME, message);
	}
}
