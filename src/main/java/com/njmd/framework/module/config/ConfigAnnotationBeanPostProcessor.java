package com.njmd.framework.module.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import com.njmd.framework.utils.io.XMLUtil;

/**
 * @title: Config 配置文件类注释实现类
 * 
 * @description: 通过BeanPostProcessor接口，对所有标识@Config的类在其初始化的时候通过java反射机制对指定标识的方法及属性进行赋值
 * 
 *               并启动定时器对配置文件进行定时刷新
 * 
 * 
 * @author: Yao
 */
public class ConfigAnnotationBeanPostProcessor implements BeanPostProcessor
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());// 日志

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
	{
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
	{
		// 处理所有有@Config注释的类
		if (bean.getClass().getAnnotation(Config.class) != null)
		{
			PropertiesConfigLoader propertiesConfigLoader = bean.getClass().getAnnotation(PropertiesConfigLoader.class);
			XmlConfigLoader xmlConfigLoader = bean.getClass().getAnnotation(XmlConfigLoader.class);
			// 只处理属性配置文件和XML配置文件的加载

			if (propertiesConfigLoader != null || xmlConfigLoader != null)
			{
				logger.debug("初始化配置类" + bean.getClass().getName());
				ConfigLoadTimerTask task = new ConfigLoadTimerTask(bean);
				// 先直接执行初始化一下

				task.run();

				long refreshTime = task.getRefreshTime() * 1000;// task任务以秒为单位

				// 启动配置文件定时刷新任务
				if (refreshTime != 0)
				{
					Timer timer = new Timer();
					// timer.schedule(task, refreshTime, refreshTime);
				}
			}
		}

		return bean;
	}

	/**
	 * 
	 * @title:配置文件加载任务
	 * @description: 根据配置定时从刷新指定配置类
	 * 
	 * @author: Yao
	 * 
	 */
	class ConfigLoadTimerTask extends TimerTask
	{

		private Object bean;

		private long refreshTime;

		private ConfigLoadTimerTask()
		{

		}

		public ConfigLoadTimerTask(Object bean)
		{
			super();
			this.bean = bean;
		}

		@Override
		public void run()
		{
			// 属性配置文件加载处理

			this.propertiesLoaderHandler();
			// XML配置文件加载处理
			this.xmlLoaderHandler();
		}

		public long getRefreshTime()
		{
			return refreshTime;
		}

		public Object getBean()
		{
			return bean;
		}

		/**
		 * 属性配置文件Loader处理
		 * 
		 * @param bean
		 * @throws BeanInitializationException
		 */
		private void propertiesLoaderHandler() throws BeanInitializationException
		{
			final Method[] methods = bean.getClass().getDeclaredMethods();
			final Field[] fields = bean.getClass().getDeclaredFields();
			PropertiesConfigLoader propertiesConfigLoader = bean.getClass().getAnnotation(PropertiesConfigLoader.class);
			if (propertiesConfigLoader != null)
			{
				String file = propertiesConfigLoader.file();
				refreshTime = propertiesConfigLoader.refreshTime();
				try
				{

					Properties properties;

					properties = this.loadProperties(file);

					// 给相应方法绑定属性值

					for (Method method : methods)
					{
						// 给@PropertiesLoaded赋值

						Object para = null;
						if (method.getAnnotation(PropertiesLoaded.class) != null)
						{
							para = properties;
							para = ConvertUtils.convert(para, method.getParameterTypes()[0]);
							ReflectionUtils.invokeMethod(method, bean, new Object[] { para });
						}
						// 给@Property赋值

						final Property property = method.getAnnotation(Property.class);
						if (property != null)
						{
							// 参数类型转换
							para = properties.getProperty(property.key(), property.defaultValue());
							para = ConvertUtils.convert(para, method.getParameterTypes()[0]);
							ReflectionUtils.invokeMethod(method, bean, new Object[] { para });
						}

					}

					// 给相应字段绑定属性值

					for (Field field : fields)
					{
						// 给@PropertiesLoaded赋值

						Object value = null;
						if (field.getAnnotation(PropertiesLoaded.class) != null)
						{
							value = properties;
							value = ConvertUtils.convert(value, field.getType());
							ReflectionUtils.setField(field, bean, value);
						}
						// 给@Property赋值

						final Property property = field.getAnnotation(Property.class);
						if (property != null)
						{
							// 参数类型转换
							value = properties.getProperty(property.key(), property.defaultValue());
							value = ConvertUtils.convert(value, field.getType());
							ReflectionUtils.setField(field, bean, value);
						}

					}
				}
				catch (Exception e)
				{
					// throw new BeanInitializationException(e.getMessage(), e);
				}
			}
		}

		/**
		 * XML配置文件Loader处理
		 * 
		 * @param bean
		 * @throws BeanInitializationException
		 */
		@SuppressWarnings("unchecked")
		private void xmlLoaderHandler() throws BeanInitializationException
		{
			final Method[] methods = bean.getClass().getDeclaredMethods();
			final Field[] fields = bean.getClass().getDeclaredFields();
			XmlConfigLoader xmlConfigLoader = bean.getClass().getAnnotation(XmlConfigLoader.class);
			if (xmlConfigLoader != null)
			{
				String file = xmlConfigLoader.file();
				refreshTime = xmlConfigLoader.refreshTime();
				try
				{

					XMLUtil xmlUtils;
					xmlUtils = this.loadXml(file);

					// 给相应方法绑定属性值

					for (Method method : methods)
					{
						// 给@ElementText赋值

						Object para = null;
						final ElementText elementText = method.getAnnotation(ElementText.class);
						if (elementText != null)
						{
							// 参数类型转换
							para = xmlUtils.getSingleElementText(elementText.xpath());
							para = ConvertUtils.convert(para, method.getParameterTypes()[0]);
							ReflectionUtils.invokeMethod(method, bean, new Object[] { para });
						}
						// 给@ElementAttribute赋值

						final ElementAttribute elementAttribute = method.getAnnotation(ElementAttribute.class);
						if (elementAttribute != null)
						{
							// 参数类型转换
							para = xmlUtils.getElementAttributeValue(elementAttribute.xpath(), elementAttribute.attrName());
							para = ConvertUtils.convert(para, method.getParameterTypes()[0]);
							ReflectionUtils.invokeMethod(method, bean, new Object[] { para });
						}
						// 给@ElementTextCollection赋值

						final ElementTextCollection elementTextCollection = method.getAnnotation(ElementTextCollection.class);
						if (elementTextCollection != null)
						{

							String typeName = method.getParameterTypes()[0].getName();
							if (!("java.util.List").equals(typeName))
								throw new BeanInitializationException("@ElementTextCollection 只能用于注入List类型的字段或方法");
							// 参数类型转换
							List<Element> elements = xmlUtils.getAllElements(elementTextCollection.xpath());
							List<String> textList = new ArrayList<String>();
							for (Element element : elements)
							{
								textList.add(element.getText());
							}

							ReflectionUtils.invokeMethod(method, bean, new Object[] { textList });
						}
					}

					// 给相应字段绑定属性值

					for (Field field : fields)
					{

						// 给@ElementText赋值

						Object value = null;
						final ElementText elementText = field.getAnnotation(ElementText.class);
						if (elementText != null)
						{
							// 参数类型转换
							value = xmlUtils.getSingleElementText(elementText.xpath());
							value = ConvertUtils.convert(value, field.getType());
							ReflectionUtils.setField(field, bean, value);
						}
						// 给@ElementAttribute赋值

						final ElementAttribute elementAttribute = field.getAnnotation(ElementAttribute.class);
						if (elementAttribute != null)
						{
							// 参数类型转换
							value = xmlUtils.getElementAttributeValue(elementAttribute.xpath(), elementAttribute.attrName());
							value = ConvertUtils.convert(value, field.getType());
							ReflectionUtils.setField(field, bean, value);

						}
						// 给@ElementTextCollection赋值

						final ElementTextCollection elementTextCollection = field.getAnnotation(ElementTextCollection.class);
						if (elementTextCollection != null)
						{

							String typeName = field.getType().getName();
							if (!("java.util.List").equals(typeName))
								throw new BeanInitializationException("@ElementTextCollection 只能用于注入List类型的字段或方法");
							// 参数类型转换
							List<Element> elements = xmlUtils.getAllElements(elementTextCollection.xpath());
							List<String> textList = new ArrayList<String>();
							for (Element element : elements)
							{
								textList.add(element.getText());
							}

							ReflectionUtils.setField(field, bean, textList);
						}
					}
				}
				catch (Exception e)
				{
					throw new BeanInitializationException(e.getMessage(), e);
				}
			}
		}

		/**
		 * 加载属性配置文件
		 * 
		 * @param file
		 * @return
		 * @throws IOException
		 */
		private Properties loadProperties(String file) throws IOException
		{

			Properties properties = new Properties();
			String filePath = bean.getClass().getClassLoader().getResource("").getPath() + file;
			InputStream is = new FileInputStream(filePath);
			try
			{
				properties.load(is);
			}
			catch (IOException e)
			{
				throw e;
			}
			finally
			{
				if (is != null)
				{
					is.close();
				}
			}
			return properties;
		}

		/**
		 * 加载XML配置文件
		 * 
		 * @param file
		 * @return
		 * @throws XMLException
		 */
		private XMLUtil loadXml(String file) throws Exception
		{
			String filePath = bean.getClass().getClassLoader().getResource("").getPath() + file;
			InputStream is = new FileInputStream(filePath);
			return XMLUtil.getInsance(is);
		}
	}

}
