package com.ym.common.utils;

import java.io.File;

import com.ym.common.base.BeansManager;
import com.ym.common.ehcache.ResCacheBean;

public class FileDeleteUtil {
	 private static ResCacheBean ehcacheBean = BeansManager.getBean(ResCacheBean.class);
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    路径
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
		sPath=FileDeleteUtil.getrootPath() + sPath;
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	public static String getrootPath(){
		return ehcacheBean.getRootPath();
	}
}
