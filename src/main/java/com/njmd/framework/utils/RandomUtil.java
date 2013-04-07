package com.njmd.framework.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomUtil
{
	private static SecureRandom random = new SecureRandom();

	/**
	 * 随机生成Long.
	 */
	public static int randomInt()
	{
		return random.nextInt();
	}

	/**
	 * 随机生成Long.
	 */
	public static int randomInt(int i)
	{
		return random.nextInt(i);
	}

	/**
	 * 随机生成Long.
	 */
	public static boolean randomBoolean()
	{
		return random.nextBoolean();
	}

	/**
	 * 根据成功率进行随机计算本次调用是否成功
	 * 
	 * @param successRate
	 * @return
	 */
	public static boolean isSuccess(int successRate)
	{
		boolean isSuccess = false;
		if (successRate >= 100)
			isSuccess = true;
		else if (successRate <= 0)
			isSuccess = false;
		else
		{
			int radom = randomInt(100) + 1;
			if (radom <= successRate)
				isSuccess = true;
			else
				isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 根据随机项的产生概率，随机获取一个随机项
	 * 
	 * @param itemList
	 * @return
	 */
	public static RandomItem getRadomItem(List<RandomItem> itemList)
	{
		List<RandomItem> radomList = new ArrayList<RandomItem>();
		int index = 0;
		for (RandomItem radomItem : itemList)
		{
			Integer probabity = radomItem.getProbabity();
			for (int i = 0; i < probabity; i++)
			{
				radomList.add(index++, radomItem);
			}
		}
		return radomList.get(random.nextInt(index));
	}
}
