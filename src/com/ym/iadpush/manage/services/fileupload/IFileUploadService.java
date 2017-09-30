/**
 * 
 */
package com.ym.iadpush.manage.services.fileupload;

import java.util.Map;

import com.ym.iadpush.manage.entity.Upload;

public interface IFileUploadService {

	/**
	 * 插入文件上传数据记录
	 * @param paramMap  Upload 
	 * @return int 
	 */
	Integer insertFileUpload(Map<String, Object> paramMap);
	
	/**
	 * 查询记录
	 * @param paramMap 可配置参数：moduleid filemodule id
	 * @return Upload
	 */
	Upload selectFileUpload(Map<String, Object> paramMap);
	/**
	 * 根据删除记录
	 * @param paramMap 可配置参数：moduleid filemodule id
	 * @return Upload
	 */
	Integer deleteFileUpload(Map<String, Object> paramMap);

	
}
