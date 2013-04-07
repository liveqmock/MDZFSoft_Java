package com.njmd.framework.module.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:配置类	   
 * @description: 标识一个类是一个配置类
 * 				  该类可以通过PropertiesConfigLoader，XmlConfigLoader加载所需配置数据，
 * 				  并进行定时的数据刷新
 * 
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config
{
}
