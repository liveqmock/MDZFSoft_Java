package com.njmd.framework.commons.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @title: 树形对象类
 * @description:对DhtmlxTree的封装,用于在后台构造出该对象后，使用前台控件进行树形展示
 * 
 * @author: Yao
 * 
 */
public class Tree
{
	private static final String DEFAULT_ROOT_ID = "0";

	protected Logger logger = LoggerFactory.getLogger(getClass());//日志
	/**
	 * 保存该树的所有节点
	 */
	private Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();;

	/**
	 * 保存该树的所有父节点与子节点的对应关系
	 * 用LinkedHashMap 保证该索引顺序与插入顺序一致
	 * Collections.synchronizedMap(）解决LinkedHashMap线程不安全问题
	 */
	private Map<String, ArrayList<String>> parentNodeMap = Collections
			.synchronizedMap(new LinkedHashMap<String, ArrayList<String>>());//new HashMap<String, ArrayList<String>>();

	/**
	 * 需要开始展现的根节点的编号
	 */
	private String showRootId = DEFAULT_ROOT_ID;

	public Tree()
	{
	}

	public Tree(List<TreeNode> nodeList)
	{

		addNodes(nodeList);
	}

	public Tree(List<TreeNode> nodeList, String showRootId)
	{
		setShowRootId(showRootId);
		addNodes(nodeList);
	}

	/**
	 * 设置开始展现的根节点
	 * 
	 * @param showRootId
	 * 
	 */
	public void setShowRootId(String showRootId)
	{
		this.showRootId = showRootId;
	}

	/**
	 * 增加单个树节点
	 * 
	 * @param treeNode
	 * 
	 */
	public void addNode(TreeNode treeNode)
	{
		String id = treeNode.getId();
		if (treeNode.getParentId() == null)
			treeNode.setParentId(DEFAULT_ROOT_ID);
		String pid = treeNode.getParentId();
		if (!nodeMap.containsKey(id))
		{
			ArrayList<String> childrenList = null;

			if (parentNodeMap.containsKey(pid))
			{
				childrenList = parentNodeMap.get(pid);
			} else
				childrenList = new ArrayList<String>();

			childrenList.add(id);
			parentNodeMap.put(pid, childrenList);
			nodeMap.put(id, treeNode);
		}
	}

	/**
	 * 批量增加树节点
	 * 
	 * @param treeNodeList
	 * 
	 */
	public void addNodes(List<TreeNode> treeNodeList)
	{
		if (treeNodeList != null)
		{
			for (TreeNode treeNode : treeNodeList)
			{
				addNode(treeNode);
			}
		}
	}

	/**
	 * 删除单个树节点
	 * 
	 * @param nodeId
	 * 
	 */
	public void removeNode(String nodeId)
	{
		if (nodeMap.containsKey(nodeId))
		{
			TreeNode treeNode = nodeMap.get(nodeId);

			String parentId = treeNode.getParentId();
			if (parentId != null && parentNodeMap.containsKey(parentId))
			{
				ArrayList<String> childrenList = parentNodeMap.get(parentId);
				if (childrenList.contains(nodeId))
				{
					childrenList.remove(nodeId);
					parentNodeMap.put(parentId, childrenList);
				}
			}
			nodeMap.remove(nodeId);
		}
	}

	/**
	 * 批量删除树节点
	 * 
	 * @param nodeIds
	 * 
	 */
	public void removeNodes(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (String id : nodeIds)
			{
				removeNode(id);
			}
		}
	}

	/**
	 * 根据父节点ID，移除该父节点下的所有子节点
	 * 
	 * @param parentId
	 */
	public void removeSubNodes(String parentId)
	{
		if (parentNodeMap.containsKey(parentId))
		{
			ArrayList<String> childrenList = parentNodeMap.get(parentId);
			for (String id : childrenList)
			{
				removeNode(id);
			}
		}
	}

	/**
	 * 设置一个节点的复选框为选中状态
	 * 
	 * @param nodeId
	 */
	public void setNodeChecked(String nodeId)
	{
		if (nodeMap.containsKey(nodeId))
		{
			TreeNode node = nodeMap.get(nodeId);
			node.setChecked(true);
			nodeMap.put(nodeId, node);
		}
	}

	/**
	 * 一次设置多个节点的复选框为选中状态
	 * 
	 * @param nodeIds
	 */
	public void setNodeChecked(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (String nodeId : nodeIds)
			{
				setNodeChecked(nodeId);
			}
		}
	}

	/**
	 * 设置一个节点为展开
	 * 
	 * @param nodeId
	 */
	public void setNodeOpen(String nodeId)
	{
		if (nodeMap.containsKey(nodeId))
		{
			TreeNode node = nodeMap.get(nodeId);
			node.setOpen(true);
			nodeMap.put(nodeId, node);
		}
	}

	/**
	 * 设置多个节点为展开
	 * 
	 * @param nodeIds
	 */
	public void setNodeOpen(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (String nodeId : nodeIds)
			{
				setNodeOpen(nodeId);
			}
		}
	}

	/**
	 * 设置节点隐藏
	 * 
	 * @param nodeId
	 */
	public void setNodeHidden(String nodeId)
	{
		if (nodeMap.containsKey(nodeId))
		{
			TreeNode node = nodeMap.get(nodeId);
			node.setHidden(true);
			nodeMap.put(nodeId, node);

			if (parentNodeMap.containsKey(nodeId))
			{
				for (String cid : parentNodeMap.get(nodeId))
				{
					setNodeHidden(cid);
				}
			}
		}
	}

	/**
	 * 设置多个节点隐藏
	 * 
	 * @param nodeIds
	 */
	public void setNodeHidden(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (String nodeId : nodeIds)
			{
				setNodeHidden(nodeId);
			}
		}
	}

	/**
	 * 设置节点隐藏，该节点隐藏后，是否隐藏该节点的父节点
	 * 
	 * @param nodeId
	 * @param isParentHidden
	 */
	public void setNodeHiddenWithParent(String nodeId)
	{
		setNodeHidden(nodeId);
		if (nodeMap.containsKey(nodeId))
		{
			TreeNode node = nodeMap.get(nodeId);
			String pid = node.getParentId();

			boolean isChildrenHidden = false;// 是否所有的子节点都隐藏了，默认是没有隐藏

			if (parentNodeMap.containsKey(pid))
			{
				ArrayList<String> hmChildren = parentNodeMap.get(pid);
				boolean hasDisplay = false;// 是否有子节点不隐藏，默认是都隐藏了

				for (String cid : hmChildren)
				{
					if (nodeMap.containsKey(cid))
					{
						TreeNode child = nodeMap.get(cid);
						if (!child.isHidden())
							hasDisplay = true;// 发现有子节点是没有隐藏的
						break;
					}
				}

				if (!hasDisplay)// 如果所有子节点都隐藏了
					isChildrenHidden = true;
			}

			if (isChildrenHidden)
			{
				if (nodeMap.containsKey(pid))
				{
					TreeNode parent = nodeMap.get(pid);
					parent.setHidden(true);
					nodeMap.put(pid, parent);
					setNodeHidden(parent.getId());
				}
			}
		}
	}

	/**
	 * 设置多个节点隐藏，该节点隐藏后，是否隐藏该节点的父节点
	 * 
	 * @param nodeIds
	 * @param isParentHidden
	 */
	public void setNodeHiddenWithParent(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (String nodeId : nodeIds)
			{
				setNodeHiddenWithParent(nodeId);
			}
		}
	}

	/**
	 * 设置除指定节点外的节点全部隐藏，该节点隐藏后，是否隐藏该节点的父节点
	 * 
	 * @param nodeId
	 * @param isParentHidden
	 */
	public void setExpectNodeHidden(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (TreeNode node : nodeMap.values())
			{
				setNodeHidden(node.getId());
			}

			for (String id : nodeIds)
			{
				setNodeDisplay(id);
			}
		}
	}

	/**
	 * 将某个节点设为可见
	 * 
	 * @param nodeId
	 * 
	 */
	private void setNodeDisplay(String nodeId)
	{
		if (nodeMap.containsKey(nodeId))
		{
			TreeNode node = nodeMap.get(nodeId);
			if (node.isHidden())
			{
				node.setHidden(false);
				nodeMap.put(nodeId, node);
				setNodeDisplay(node.getParentId());
			}
		}
	}

	/**
	 * 生成树形XML
	 * 
	 */
	public String getXml() throws IOException
	{
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GB18030");

		Element root = document.addElement("tree");
		root.addAttribute("id", showRootId);

		if (parentNodeMap.containsKey(showRootId))
		{
			for (String id : parentNodeMap.get(showRootId))
			{
				createXml(root, id);
			}
		}

		String treeXml = document.asXML();

		treeXml = treeXml.replaceAll("\n", "");

		return treeXml;
	}

	/**
	 * 通过递归的组织生成xml的element
	 * 
	 * @param element
	 * @param id
	 * 
	 */
	private void createXml(Element element, String id) throws IOException
	{
		if (nodeMap.containsKey(id))
		{
			TreeNode node = nodeMap.get(id);
			if (!node.isHidden())
			{
				Element item = node.addToParentElement(element);
				ArrayList<String> childList = parentNodeMap.get(id);
				if (childList != null)
				{
					for (String cid : childList)
					{
						createXml(item, cid);
					}
				}
			}
		}
	}

}
