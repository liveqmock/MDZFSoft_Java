package com.njmd.framework.utils.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title:一个使用Dom4j读取XML文件的工具类。
 * 
 * @description: <pre>
 * 基本示例：<br/>
 *      String filePath = "c:/xx/xxx.xml";
 *      XMLUtil util = XMLUtil.getInsance(filePath);
 *      Element element = util.getSingleElement("/root/elemA/elemB");
 *      String text = util.getSingleElementText("/root/elemA/elemB");
 * </pre>
 * 
 * @author Yao
 */
public class XMLUtil
{

	protected Logger log = LoggerFactory.getLogger(this.getClass());// 日志

	private Document doc = null;

	// 用于快速查询的cache
	private final Map<String, Element> lookupCache = new HashMap<String, Element>();

	/**
	 * 仅供测试使用
	 * 
	 * @param doc
	 */
	XMLUtil(Document doc)
	{
		this.doc = doc;
		if (log.isDebugEnabled())
		{
			log.debug("使用了仅供测试的Constructor。");
		}
	}

	/**
	 * 私有的Constructor
	 * 
	 * @param is
	 *            流文件
	 * 
	 * @param validate
	 *            是否需要验证
	 * 
	 * @throws XMLException
	 */
	private XMLUtil(InputStream is, boolean validate) throws Exception
	{
		SAXReader builder = null;
		if (validate)
		{
			if (log.isInfoEnabled())
			{
				log.info("需要使用文档验证，可以使用Schema或者DTD。");
			}
			// 为了支持Schema，必须进行下面的处理
			builder = new SAXReader("org.apache.xerces.parsers.SAXParser", true);
			builder.setFeature("http://apache.org/xml/features/validation/schema", true);
			builder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",
					"http://www.w3.org/2001/XMLSchema-instance");
		}
		else
		{
			builder = new SAXReader();
		}

		try
		{
			doc = builder.read(is);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 得到一个工具类的实例。默认不需要验证。
	 * 
	 * @param filePath
	 *            需要读取的文件路径
	 * @return XMLUtil
	 * @throws XMLException
	 */
	public static XMLUtil getInsance(String filePath) throws Exception
	{
		return getInsance(filePath, false);
	}

	/**
	 * 得到一个工具类的实例。
	 * 
	 * <br/>
	 * <b>如果指明需要Schema验证，XML文件中指明Schema的路径分隔符号
	 * 
	 * 必须使用左斜线</b>
	 * 
	 * @param filePath
	 *            需要读取的文件路径
	 * @param validate
	 *            是否需要验证
	 * 
	 * @return XMLUtil
	 * @throws XMLException
	 */
	public static XMLUtil getInsance(String filePath, boolean validate) throws Exception
	{
		try
		{
			return getInsance(new FileInputStream(filePath), validate);
		}
		catch (FileNotFoundException e)
		{
			throw e;
		}
	}

	/**
	 * 得到一个工具类的实例。默认不需要验证。
	 * 
	 * @param is
	 *            流文件
	 * 
	 * @return XMLUtil
	 * @throws XMLException
	 */
	public static XMLUtil getInsance(InputStream is) throws Exception
	{
		return getInsance(is, false);
	}

	/**
	 * 得到一个工具类的实例。
	 * 
	 * <br/>
	 * <b>如果指明需要验证，只能使用DTD</b>
	 * 
	 * @param is
	 *            流文件
	 * 
	 * @param validate
	 * @return XMLUtil
	 * @throws Exception
	 */
	public static XMLUtil getInsance(InputStream is, boolean validate) throws Exception
	{
		return new XMLUtil(is, validate);
	}

	/**
	 * 把XML文档的内容输出到一个给定的流对象中。默认编码GB2312
	 * 
	 * @param stream
	 *            给定的输出流对象
	 * @throws Exception
	 */
	public void writeToStream(OutputStream stream) throws Exception
	{
		this.writeToStream(stream, "gb2312");
	}

	/**
	 * 把XML文档的内容输出到一个给定的流对象中。
	 * 
	 * @param stream
	 *            给定的输出流对象
	 * @param encoding
	 *            指定字符编码。
	 * 
	 * @throws Exception
	 */
	public void writeToStream(OutputStream stream, String encoding) throws Exception
	{

		try
		{
			XMLWriter out = new XMLWriter(stream);
			out.write(doc);
			out.close();
		}
		catch (Exception e)
		{
			// log.error("输出文件到指定的流对象发生异常！", e);
			throw e;
		}
	}

	/**
	 * 返回所有满足XPath条件的节点元素集合。
	 * 
	 * @param xpath
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getAllElements(String xpath) throws Exception
	{
		List<Element> elements = null;
		elements = doc.selectNodes(xpath);
		return elements;
	}

	/**
	 * 返回满足XPath条件的第一个节点元素。
	 * 
	 * @param xpath
	 * @return Element
	 * @throws Exception
	 */
	public Element getSingleElement(String xpath) throws Exception
	{
		// 所有查询单个元素的方法都调用了这个方法，所以只在这里使用cache
		if (lookupCache.containsKey(xpath))
			return lookupCache.get(xpath);
		else
		{
			Element element = null;
			element = (Element) doc.selectSingleNode(xpath);
			lookupCache.put(xpath, element);
			return element;
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的内容，字符串格式
	 * 
	 * @param xpath
	 * @return String。如果指定的元素不存在，返回null。
	 * 
	 * @throws Exception
	 */
	public String getSingleElementText(String xpath) throws Exception
	{
		Element element = this.getSingleElement(xpath);
		if (element == null)
			return null;
		else
			return element.getTextTrim();
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的指定属性。
	 * 
	 * @param xpath
	 * @param attrName
	 * @return Attribute
	 * @throws Exception
	 */
	public Attribute getElementAttribute(String xpath, String attrName) throws Exception
	{
		if (this.getSingleElement(xpath) == null)
			return null;
		else
			return this.getSingleElement(xpath).attribute(attrName);
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的指定属性的内容值。
	 * 
	 * @param xpath
	 * @param attrName
	 * @return String 属性的内容值，如果指定的属性不存在，返回null
	 * @throws Exception
	 */
	public String getElementAttributeValue(String xpath, String attrName) throws Exception
	{
		Attribute attr = this.getElementAttribute(xpath, attrName);
		if (attr == null)
			return null;
		else
			return attr.getValue().trim();
	}

	/**
	 * 在指定的元素下面增加一个元素。
	 * 
	 * @param xpath
	 *            指定的元素
	 * 
	 * @param elemName
	 *            增加元素的名称
	 * 
	 * @param elemText
	 *            增加元素的内容
	 * 
	 * @throws Exception
	 */
	public void addElement(String xpath, String elemName, String elemText) throws Exception
	{
		Element parent = this.getSingleElement(xpath);
		parent.addElement(elemName).setText(elemText);
	}

	/**
	 * 使指定位置的元素从他的上级脱离。并且返回这个元素。如果没有上级，不作任何删除 的操作。
	 * 
	 * @param xpath
	 * @return 被修改的元素
	 * @throws Exception
	 */
	public Node removeElement(String xpath) throws Exception
	{
		lookupCache.remove(xpath);
		Element element = this.getSingleElement(xpath);
		if (element.isRootElement())
			return element;
		else
			return element.detach();
	}

	/**
	 * 改变指定元素的文本内容。
	 * 
	 * @param xpath
	 *            指定元素
	 * @param elemText
	 *            需要设置的文本
	 * @throws Exception
	 *             如果指定的元素不存在
	 */
	public void setElementText(String xpath, String elemText) throws Exception
	{
		Element element = this.getSingleElement(xpath);
		if (element == null)
			throw new IllegalArgumentException("指定的元素XPath:" + xpath + "不存在！");
		else
		{
			element.setText(elemText);
		}
	}

	/**
	 * 在指定路径的元素上增加一个属性。如果同名属性已经存在，重新设置这个属性的值。
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue
	 * @throws Exception
	 */
	public void setAttribute(String xpath, String attrName, String attrValue) throws Exception
	{
		Element element = this.getSingleElement(xpath);
		try
		{
			element.addAttribute(attrName, attrValue);
		}
		catch (Exception e)
		{
			log.error("设置节点元素的属性发生异常！", e);
			throw e;
		}
	}

	/**
	 * 删除指定元素的指定属性。
	 * 
	 * @param xpath
	 * @param attrName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean removeAttribute(String xpath, String attrName) throws Exception
	{
		Element element = this.getSingleElement(xpath);
		if (element == null)
			return false;
		else
			return element.remove(element.attribute(attrName));
	}
}
