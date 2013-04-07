package com.njmd.framework.module.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:已加载属性列表注入
 * @description: 将PropertiesConfigLoader中加载的所有资源注入到指定的字段或者方法中
 * 					字段或方法参数必需是Properties类型
 * 
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface PropertiesLoaded
{

}
