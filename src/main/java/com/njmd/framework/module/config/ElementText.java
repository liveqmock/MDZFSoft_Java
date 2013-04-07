package com.njmd.framework.module.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:XML元素内容注入
 * @description:  根据Xpath将XmlConfigLoader中XML元素内容注入到指定的字段或者方法中
 * 
 * 			例：xml配置文件如下
 * 				<?xml version="1.0" encoding="UTF-8"?>
 *				<configs>
 *	            	<config name="a">111</config>
 *					<config name="b">111</config>
 *				</configs>
 *			用法如下：
 *			@Config
 *			@XmlConfigLoader(file=config.xml)
 *			public class XMLUtils{
 *
 *			@ElementText(xpath="/configs/config[@name=a]")
 *			public static String CONFIG1;
 *			
 *			public static String CONFIG2;
 *
 *			@ElementText(xpath="/configs/config[@name=b])
 *			public static void setConfig2(String config2){CONFIG2=config2};
 *			}
 *			以上两种方式注入后，CONFIG1的值为111,CONFIG2的值为222;
 * 
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface ElementText
{
	String xpath();
}
