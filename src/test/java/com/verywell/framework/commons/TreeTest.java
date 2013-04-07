package com.verywell.framework.commons;

import org.junit.Before;
import org.junit.Test;

import com.njmd.framework.utils.MD5Utils;

public class TreeTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetJsonStr()
	{
		System.out.println(MD5Utils.toMD5("111111"));
		/*
		 * System.out.println(MD5Utils.toMD5("111111")); List<TreeNode> list = new ArrayList<TreeNode>(); for (int i = 0; i < 10;
		 * i++) { TreeNode node = new TreeNode("" + i); node.setName("XXXX"); node.setChecked(false); // Tree tree = new Tree();
		 * // tree.addNode(node); // tree.getJsonStr(); list.add(node); }
		 * 
		 * String nodeJson = JsonMapper.nonEmptyMapper().toJson(list); System.out.println(nodeJson);
		 */
	}

}
