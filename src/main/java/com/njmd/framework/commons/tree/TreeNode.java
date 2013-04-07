package com.njmd.framework.commons.tree;

import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;

/**
 * 
 * @title: 树形节点类
 * @description: 与Tree类相关，定义了树形的节点
 * 
 * @author: Yao
 * 
 */
public class TreeNode
{

	private String id;

	private String text;

	private String parentId;// 父节点编号

	private boolean selected = false;// 是否为光标选中状态

	private boolean checked = false;// 是否为checkbox选中状态

	private boolean open = false;// 该节点是否展开

	private boolean hidden = false;// 该节点是否隐藏

	private HashMap<String, String> userData = new HashMap<String, String>();

	public TreeNode(String id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getId()
	{
		return id;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public void addUserData(String key, String value)
	{
		userData.put(key, value);
	}

	public HashMap<String, String> getUserData()
	{
		return userData;
	}

	public void setUserData(HashMap<String, String> userData)
	{
		this.userData = userData;
	}

	public Element addToParentElement(Element eParent)
	{
		Element item = eParent.addElement("item");

		item.addAttribute("id", getId());
		item.addAttribute("text", getText());

		if (isSelected())
			item.addAttribute("select", "yes");

		if (isChecked())
			item.addAttribute("checked", "1");

		if (isOpen())
			item.addAttribute("open", "1");

		if (userData != null)
		{
			Iterator<String> it = userData.keySet().iterator();
			while (it.hasNext())
			{
				Element userEle = item.addElement("userdata");
				String key = (String) it.next();
				userEle.addAttribute("name", key);
				String value = userData.get(key);
				userEle.addText(value);
			}
		}
		return item;
	}

	public boolean isHidden()
	{
		return hidden;
	}

	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}

	public boolean isOpen()
	{
		return open;
	}

	public void setOpen(boolean open)
	{
		this.open = open;
	}
}
