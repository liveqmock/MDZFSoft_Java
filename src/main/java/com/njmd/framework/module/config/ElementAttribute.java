package com.njmd.framework.module.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:XML属性值注入
 * @description:  根据Xpath，属性名称将XmlConfigLoader中XML属性值注入到指定的字段或者方法中
 * 
 * 			例：xml配置文件如下
 * 				<?xml version="1.0" encoding="UTF-8"?>
 *				<configs>
 *	            	<config1 value="111"/>
 *					<config2 value="222"/>
 *				</configs>
 *			用法如下：
 *		 	@Config
 *			@XmlConfigLoader(file=config.xml)
 *			public class XMLUtils{
 *			@ElementAttribute(xpath="/configs/config1",attrName="value")
 *			public static String CONFIG1;
 *			
 *			public static String CONFIG2;
 *
 *			@ElementAttribute(xpath="/configs/config2",attrName="value")
 *			public static void setConfig2(String config2){CONFIG2=config2};
 *			}
 *
 *			以上两种方式注入后，CONFIG1的值为111,CONFIG2的值为222;
 *
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface ElementAttribute
{
	String xpath();

	String attrName();
}
