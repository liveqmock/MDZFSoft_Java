package com.njmd.framework.commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njmd.framework.utils.mapper.JsonMapper;

/**
 * 
 * @title: 树形对象类
 * 
 * @description:对DhtmlxTree的封装,用于在后台构造出该对象后，使用前台控件进行树形展示
 * 
 * 
 * @author: Yao
 * 
 */
public class Tree implements Iterable<TreeNode>
{
	public static final String DEFAULT_ROOT_ID = "0";

	protected Logger logger = LoggerFactory.getLogger(getClass());// 日志

	/**
	 * 保存该树的所有节点
	 */
	private final List<TreeNode> nodeList = new ArrayList<TreeNode>();

	public Tree()
	{
	}

	public Tree(List<TreeNode> nodeList)
	{

		addNodes(nodeList);
	}

	/**
	 * 增加单个树节点
	 * 
	 * 
	 * @param treeNode
	 * 
	 */
	public void addNode(TreeNode treeNode)
	{
		if (treeNode.getpId() == null || treeNode.getpId().equals(""))
			treeNode.setpId(DEFAULT_ROOT_ID);
		nodeList.add(treeNode);
	}

	/**
	 * 批量增加树节点
	 * 
	 * 
	 * @param treeNodeList
	 * 
	 */
	public void addNodes(List<TreeNode> treeNodeList)
	{
		if (treeNodeList != null && !treeNodeList.isEmpty())
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
	 * 
	 * @param nodeId
	 * 
	 */
	public void removeNode(String nodeId)
	{
		for (int i = 0; i < nodeList.size(); i++)
		{
			TreeNode treeNode = nodeList.get(i);
			if (treeNode.getId().equals(nodeId))
			{
				nodeList.remove(i);
				return;
			}
		}
	}

	/**
	 * 批量删除树节点
	 * 
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
		for (int i = nodeList.size() - 1; i >= 0; i--)
		{
			TreeNode treeNode = nodeList.get(i);
			if (treeNode.getpId().equals(parentId))
			{
				nodeList.remove(i);
			}
		}
	}

	/**
	 * 设置一个节点的复选框为选中状态
	 * 
	 * 
	 * @param nodeId
	 */
	public void setNodeChecked(String nodeId)
	{
		for (int i = 0; i < nodeList.size(); i++)
		{
			TreeNode treeNode = nodeList.get(i);
			if (treeNode.getId().equals(nodeId))
			{
				treeNode.setChecked(true);
				return;
			}
		}

	}

	/**
	 * 一次设置多个节点的复选框为选中状态
	 * 
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
		for (int i = 0; i < nodeList.size(); i++)
		{
			TreeNode treeNode = nodeList.get(i);
			if (treeNode.getId().equals(nodeId))
			{
				treeNode.setOpen(true);
				return;
			}
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
		for (int i = 0; i < nodeList.size(); i++)
		{
			TreeNode treeNode = nodeList.get(i);
			if (treeNode.getId().equals(nodeId))
			{
				treeNode.setHidden(true);
				return;
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
	 * 设置除指定节点外的节点全部隐藏，该节点隐藏后，是否隐藏该节点的父节点
	 * 
	 * @param nodeId
	 * @param isParentHidden
	 */
	public void setExpectNodeHidden(List<String> nodeIds)
	{
		if (nodeIds != null)
		{
			for (TreeNode node : nodeList)
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
	 * 
	 * @param nodeId
	 * 
	 */
	private void setNodeDisplay(String nodeId)
	{
		for (int i = 0; i < nodeList.size(); i++)
		{
			TreeNode treeNode = nodeList.get(i);
			if (treeNode.getId().equals(nodeId))
			{
				treeNode.setHidden(false);
				return;
			}
		}
	}

	/**
	 * 生成树形XML
	 * 
	 */
	public String getJson()
	{
		return JsonMapper.nonEmptyMapper().toJson(this.nodeList);
	}

	public List<TreeNode> getNodeList()
	{
		return nodeList;
	}

	/**
	 * 树形节点是否为空
	 * 
	 * @return
	 */
	public boolean isEmptyNodes()
	{
		return nodeList.isEmpty();
	}

	@Override
	public Iterator<TreeNode> iterator()
	{
		return nodeList.iterator();
	}

}
