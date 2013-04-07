package com.verywell.framework.utils;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class DateTimeTest
{
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetJsonStr()
	{
		DateTime time = new DateTime();
		System.out.println(time.dayOfWeek().withMaximumValue().toString("yyyyMMdd"));
		System.out.println(time.dayOfWeek().withMinimumValue().toString("yyyyMMdd"));
		System.out.println(time.dayOfMonth().withMaximumValue().toString("yyyyMMdd"));
		System.out.println(time.dayOfMonth().withMinimumValue().toString("yyyyMMdd"));
	}
}
