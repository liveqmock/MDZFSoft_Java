package com.njmd.framework.commons;

import java.io.Serializable;

/**
 * 
 * @title: 树形节点类
 * 
 * @description: 与Tree类相关，定义了树形的节点
 * 
 * @author: Yao
 * 
 */
public class TreeNode implements Serializable
{
	// 树节点ID
	private String id;
	// 父节点ID
	private String pId;
	// 节点名称
	private String name;
	// 树的ID path
	private String path;
	// 超链接URL
	private String url;

	// urltarget
	private String target;

	// 图标
	private String icon;

	// 打开图标
	private String iconOpen;

	// 关闭图标
	private String iconClose;

	// 是否为checkbox选中状态
	private boolean isChecked;
	// 该节点是否展开
	private boolean isOpen = true;
	// 该节点是否隐藏
	private boolean isHidden;
	//是否显示checkBox
	private boolean isNocheck;
	
	public TreeNode()
	{
	}

	public TreeNode(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getpId()
	{
		return pId;
	}

	public void setpId(String pId)
	{
		this.pId = pId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getIconOpen()
	{
		return iconOpen;
	}

	public void setIconOpen(String iconOpen)
	{
		this.iconOpen = iconOpen;
	}

	public String getIconClose()
	{
		return iconClose;
	}

	public void setIconClose(String iconClose)
	{
		this.iconClose = iconClose;
	}

	public boolean isChecked()
	{
		return isChecked;
	}

	public void setChecked(boolean isChecked)
	{
		this.isChecked = isChecked;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	public boolean isHidden()
	{
		return isHidden;
	}

	public void setHidden(boolean isHidden)
	{
		this.isHidden = isHidden;
	}
	
	public boolean isNocheck() {
		return isNocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.isNocheck = nocheck;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
