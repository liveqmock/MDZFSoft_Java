package com.njmd.zfms.web.constants;

import com.njmd.framework.module.config.Config;
import com.njmd.framework.module.config.PropertiesConfigLoader;
import com.njmd.framework.module.config.Property;

/**
 * 系统配置常量文件
 * 
 * @author Yao
 * 
 */
@Config
@PropertiesConfigLoader(file = "config/config.properties", refreshTime = 60 * 60 * 8)
public class ConfigConstants
{
	@Property(key = "check_disk")
	public static String CHECK_DISK;

	@Property(key = "min_free_space")
	public static String MIN_FREE_SPACE;

	@Property(key = "support_file_extension")
	public static String SUPPORT_FILE_EXTENSION;

	@Property(key = "file_storage_root")
	public static String FILE_STORAGE_ROOT;

	@Property(key = "ffmpeg_home")
	public static String FFMPEG_HOME;

	@Property(key = "monitor.urls")
	public static String MONITOR_URLS;

}
