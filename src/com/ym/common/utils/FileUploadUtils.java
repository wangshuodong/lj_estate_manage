package com.ym.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ym.common.base.BaseAction;
import com.ym.common.base.BeansManager;
import com.ym.common.base.Constants;
import com.ym.common.base.FileUpload;
import com.ym.common.ehcache.ResCacheBean;

public class FileUploadUtils {

    private static Log log = LogFactory.getLog(BaseAction.class);
    private static ResCacheBean ehcacheBean = BeansManager.getBean(ResCacheBean.class);

    /**
     * 获取目录
     * 
     * @param path
     * @return
     */
    public static String getDir(String path) {
        if (StringUtils.isBlank(Constants.UPLOAD_PATH_PARENT)) {
            path = ehcacheBean.getRootPath() + path;
        } else {
            path = Constants.UPLOAD_PATH_PARENT + "/" + path;
        }
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
            log.debug("目录不存在，创建成功！[路径:" + path + "]");
        }
        return filePath.getAbsolutePath();
    }

    /**
     * 文件上传
     * 
     * @param request
     * @param path
     * @return
     */
    public static FileUpload uploadFile(MultipartFile patch, String path) {
        // MultipartFile patch = request.getFile(field);
        FileUpload upload = null;
        String newName = null, fileName = null, suffix = null;
        if (patch != null && !patch.isEmpty()) {
            try {
                fileName = patch.getOriginalFilename();
                int index = fileName.lastIndexOf('.');
                if (index > -1) {
                    suffix = fileName.substring(index);
                }
                newName = String.valueOf(System.currentTimeMillis()) + (suffix == null ? "" : suffix);
                patch.transferTo(new File(getDir(path), newName));
                log.debug("文件上传成功！path[" + path + "], name[" + fileName + "], size["
                        + Math.rint(patch.getSize() / 1024.0) + "]");

                upload = new FileUpload();
                upload.setLocalName(newName);
                upload.setLocalPath(path);
                upload.setRemoteName(fileName);
            } catch (IOException e) {
                log.error("文件上传失败！", e);
            }
        }
        return upload;
    }
    /**
     * 文件上传  v2.0
     * 
     * @param request
     * @param path
     * @return
     */
    public static FileUpload uploadFile_two(MultipartFile patch, String path) {
        // MultipartFile patch = request.getFile(field);
        FileUpload upload = null;
        String newName = null, fileName = null, suffix = null;
        try {
            fileName = patch.getOriginalFilename();
            int index = fileName.lastIndexOf('.');
            if (index > -1) {
                suffix = fileName.substring(index);
            } 
            newName = String.valueOf(getUUID()) + (suffix == null ? "" : suffix);
            patch.transferTo(new File(getDir(path), newName));
            log.debug("文件上传成功！path[" + path + "], name[" + fileName + "], size["
                    + Math.rint(patch.getSize() / 1024.0) + "]");

            upload = new FileUpload();
            upload.setLocalName(fileName);
            upload.setLocalPath(path);
            upload.setRemoteName(newName);
        } catch (IOException e) {
            log.error("文件上传失败！", e);
        }
        return upload;
    }
    @SuppressWarnings("static-access")
	private static String getUUID(){
    	 UUID uuid = new UUID(System.currentTimeMillis(), System.currentTimeMillis());
         return uuid.randomUUID()+"";
    }
}
