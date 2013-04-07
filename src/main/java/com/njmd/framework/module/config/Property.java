package com.njmd.framework.module.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:属性值注入
 * @description:  根据属性Key将PropertiesConfigLoader中属性值注入到指定的字段或者方法中
 * 
 * @author: Yao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface Property
{
	/**资源key名称*/
	String key();

	/**默认值*/
	String defaultValue() default "";
}
