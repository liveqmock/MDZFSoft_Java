package com.njmd.zfms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

	/**
	 * 资源
	 * @return
	 */
	Resources resource();
	
	/**
	 * 操作
	 * @return
	 */
	Actions action();
	
	/**
	 * 
	 * @return
	 */
	boolean required() default true;
	
	static enum Resources{
		SYSLOGIN,
		SYSCORP,
		SYSFTP,
		SYSROLE,
		SYSPERMISSION,
		SYSLOG,
		SYSMONITOR,
		DEVTYPE,
		DEVFACTURER,
		DEVINFO,
		REPORTSTATISTICS,
		FILETYPE,
		FILE,
		NOTICE,
		INDEX,
		API
		
	}
	
	static enum Actions{
		LOGIN,
		LOGOUT,
		ADD,
		DELETE,
		BATCHDELETE,
		UPDATE,
		VIEW,
		LIST,
		SELECT,
		RESETPWD,
		UPDATEPWD,
		CORPSTATISTICS,
		USERSTATISTICS, 
		DETAIL,
		DOWNLOAD,
		MYFILELIST
	}
}
