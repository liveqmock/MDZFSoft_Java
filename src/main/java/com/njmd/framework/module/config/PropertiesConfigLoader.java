package com.njmd.framework.module.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:属性配置文件加载	  
 * @description: 根据文件名，从属性文件中加载数据
 * 					该属性文件必需放至与src下
 * 
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertiesConfigLoader
{
	/**
	 * 资源文件路径
	 * @return
	 */
	String file();

	/**
	 * 刷新时间（单位：秒）
	 * @return
	 */
	long refreshTime() default 0;
}
