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
 *	            	<config>111</config>
 *					<config>222</config>
 *					<config>333</config>
 *				</configs>
 *			用法如下：
 *			@Config
 *			@XmlConfigLoader(file=config.xml)
 *			public class XMLUtils{
 *
 *			@ElementText(xpath="/configs/config")
 *			public static List<String> configList1;
 *			
 *			public static List<String> configList2;
 *
 *			@ElementText(xpath="/configs/config)
 *			public static void setConfigList2(List<String> configList){this.configList2=configList};
 *			}
 *			以上两种方式注入后，configList1、congfig2的值都为config的值的集合;
 * 
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface ElementTextCollection
{
	String xpath();
}
