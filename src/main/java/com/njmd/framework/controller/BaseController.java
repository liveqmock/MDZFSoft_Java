package com.njmd.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.RequestNameConstants;

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
	
	protected void savedObjectForLog(Object entity){
		WebContextHolder.savedObjectForLog(entity);
	}
}
