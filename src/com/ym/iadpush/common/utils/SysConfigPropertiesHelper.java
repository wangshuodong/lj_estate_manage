/**
 * www.yuitat.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.ym.iadpush.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysConfigPropertiesHelper {
	
	private static final Logger							logger			= LoggerFactory
																			.getLogger(SysConfigPropertiesHelper.class);
	
	private String[]									fileNames		= { "/SystemConfig.properties","/prop-jdbc.properties"};
	
	private volatile static SysConfigPropertiesHelper	propertiesHelps	= null;
	
	public static Map<Object, Object>					map				= null;
	
	private SysConfigPropertiesHelper() {
		InputStream in = null;
		
		try {
			map = new HashMap<Object, Object>();
			for (String name : fileNames) {
				in = SysConfigPropertiesHelper.class.getResourceAsStream(name);
				Properties props = new Properties();
				props.load(in);
				Set<Object> keys = props.keySet();
				for (Object o : keys) {
					Object t = props.get(o);
					map.put(o, t);
				}
				in.close();
			}
		} catch (IOException e) {
			logger.error(Arrays.toString(fileNames) + "读取失败！", e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				logger.error(Arrays.toString(fileNames) + "关闭失败！", e);
			}
		}
	}
	
	/**
	 *	根据key 获取值
	 *	@param	String key
	 *	@return String 
	 */
	public static String getProperty(String key) {
		
		if (null == propertiesHelps) {
			synchronized (SysConfigPropertiesHelper.class) {
				if (null == propertiesHelps) {
					propertiesHelps = new SysConfigPropertiesHelper();
				}
			}
		}
		return (String) map.get(key);
	}
	
	public static String getFileRoot() {
		
		return getProperty("fileroot");
	}
}
