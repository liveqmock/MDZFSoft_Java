package com.njmd.zfms.web.constants;


/**
 * 
 * @title:通用常量类
 * @description: 定义了系统常量信息
 * @author: Yao
 */
public class CommonConstants
{
	/** 每页显示记录数量 */
	public static final int MAX_PAGE_ITEMS = 10;

	/** 显示索引数量 */
	public static final int MAX_INDEX_PAGES = 3;

	/** 逗号分割符 */
	public static final String SPLIT_SYMBOL_COMMA = ",";

	/** 竖杆分割符 */
	public static final String SPLIT_SYMBOL_VERTICAL_LINE = "|";

	/** 横线分割符 */
	public static final String SPLIT_SYMBOL_TRANSVERSE_LINE = "-";

	/** 冒号分割符 */
	public static final String SPLIT_SYMBOL_COLON = ":";

	/** 下划线分割符 */
	public static final String SPLIT_SYMBOL_UNDERLINE = "_";

	/** 状态 1-有效 */
	public static final Integer STATUS_VALID = 1;

	/** 状态 0-无效 */
	public static final Integer STATUS_INVALID = 0;

	/** 新增用户默认密码 */
	public static final String DEFAULT_PWD = "888888";

	/** 无上级ID */
	public static final Long NO_PARENT_ID = 0l;

}
