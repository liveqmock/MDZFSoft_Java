package com.njmd.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;

/**
 * Controller基类
 * 
 * @author yao
 * 
 */
public class BaseController
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());// 日志

	protected LoginToken getLoginToken()
	{
		return WebContextHolder.getCurrLoginToken();
	}
}
