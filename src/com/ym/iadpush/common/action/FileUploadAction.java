package com.ym.iadpush.common.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ym.common.base.BaseAction;
import com.ym.common.base.FileUpload;
import com.ym.common.utils.FileDeleteUtil;
import com.ym.common.utils.FileUploadUtils;
import com.ym.common.utils.FileUtil;
import com.ym.common.utils.ReadZip;
import com.ym.iadpush.dev.service.user.DevUserService;
import com.ym.iadpush.manage.entity.NoiconAd;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.Upload;
import com.ym.iadpush.manage.entity.UserBalance;
import com.ym.iadpush.manage.services.balance.IBalanceService;
import com.ym.iadpush.manage.services.fileupload.IFileUploadService;
import com.ym.iadpush.manage.services.noicon.INoiconCountService;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

@Controller
public class FileUploadAction extends BaseAction {
		
	private @Autowired IFileUploadService uploadService;
	private @Autowired INoiconCountService noiconService;
	
	@Autowired
	private DevUserService devUserService;
	
	@Autowired
	private IBalanceService balanceService;
	
	@Autowired
	private IUserMgr userService;
	
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUpload.html", method = RequestMethod.POST)
	public Object fileUpload(@RequestParam(value = "file", required = false)MultipartFile file, MultipartHttpServletRequest multRequest,HttpServletRequest request, ModelMap model) {  
		Map<String, Object> paramMap = new HashMap<String, Object>();
	  
	    String path=request.getParameter("path");//保存路径
	
		String filemodule=request.getParameter("filemodule");//kvfile
		String moduleid=request.getParameter("moduleid");//46
		FileUpload fu= FileUploadUtils.uploadFile_two(file, path);
		 
        paramMap.put("realname", fu.getRemoteName());
		paramMap.put("filename", fu.getLocalName());
		paramMap.put("filemodule", filemodule);
		paramMap.put("moduleid", moduleid);
		//paramMap.put("path", fu.getLocalPath());
		paramMap.put("path", path);
		paramMap.put("uploadtime", System.currentTimeMillis());
		paramMap.put("filetype", file.getContentType());
		paramMap.put("filesize", file.getSize());
		paramMap.put("updatetime", new Date());
		Integer fileId=uploadService.insertFileUpload(paramMap);
		//如果上传的身份证则更新用户状态
		if(filemodule != null && filemodule.trim().equalsIgnoreCase("user")){
		    Map<String, Object> userparams = new HashMap<String, Object>();
		    //待审核
		    userparams.put("certification", 1);
		    userparams.put("updatetime", new Timestamp(System.currentTimeMillis()));
		    userparams.put("userId", this.getUser().getUserId());
		    devUserService.updateUserInfo(userparams);
		}
		model.put("fileId", fileId);
		model.put("id", moduleid);
		return model;  
	}  
	/**
	 * 文件上传  批量上传
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileAllUpload.html", method = RequestMethod.POST)
	public Object fileAllUpload(MultipartHttpServletRequest multRequest,HttpServletRequest request, ModelMap model) {  
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<MultipartFile> files = multRequest.getFiles("file"); 
		for (int i = 0; i < files.size(); i++) {
			System.out.println(files.get(i).getOriginalFilename());
		}
	    String path=request.getParameter("path");//保存路径
	
		String filemodule=request.getParameter("filemodule");//kvfile
		@SuppressWarnings("unused")
		String moduleid=request.getParameter("moduleid");//46
		return model;  
	}  
	 /**
     * 下载
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/download.html", method = RequestMethod.GET)
    public Object download(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	String noicon_id=request.getParameter("icon_id");
    	String flag=request.getParameter("flag");
    	String id=request.getParameter("id");
		String moduleid=request.getParameter("moduleid");
		String filemodule=request.getParameter("filemodule");
		try {
			if(id!=null&&!id.trim().equalsIgnoreCase("")){
				paramMap.put("id", Integer.parseInt(id));
			}
			else{
				paramMap.put("moduleid", Integer.parseInt(moduleid));
				paramMap.put("filemodule", filemodule);
			}
		} catch (Exception e) {
		}
		Upload upload = null;
		if(noicon_id!=null&&!noicon_id.trim().equalsIgnoreCase("")){
			NoiconAd icon=noiconService.select_noicon_config_data();
			upload=new Upload();
			String name="",path="";
			if(flag.trim().equalsIgnoreCase("img")){
				name=icon.getImgrealname();
				path=icon.getImgUrlpre();
			}
			else{
				name=icon.getApkrealname();
				path=icon.getApkUrlpre();
			}
			upload.setRealname(name);
			upload.setFilename(name);
			upload.setPath(path);
			
		}
		else{
			 upload=uploadService.selectFileUpload(paramMap);
			 try {
				 if(filemodule.trim().equalsIgnoreCase("user")){
					 Integer userId = Integer.valueOf(moduleid);
					 SysUsers user = userService.getByUserId(userId);
					 upload.setFilename(user.getBankUserName());
				 }
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
    	
    	if(upload == null){
    	    upload = new Upload();
    	    upload.setFilename("onlinenone.jpg");
    	    upload.setRealname("onlinenone.jpg");
    	    upload.setPath("/assets/biz-logic/images");
    	}
    	dirDownload(response,upload); 
        return null; 
    }
    
    /**
     * 批量下载身份证
     */
    @RequestMapping(value = "/batchDownload.html", method = RequestMethod.GET)
    public Object batchDownload(HttpServletResponse response, ModelMap model) {
    	Map<String, Object> params =  (Map<String, Object>) getSession().getAttribute("balances");
    	
    	if(params != null){
    		try {
				params.remove("begRow");
				params.remove("pageSize");
			} catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	
    	List<UserBalance> balances = balanceService.findUserBlances(params);
    	
    	if (balances != null && balances.size() > 0) {
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		File[] file = new File[balances.size()];
    		
    		try {
    			
    			int count = 0;
    			// 序号
				int sort = 0;
				String distPath = "";
				
    			for (UserBalance info : balances) {
    				sort++;
        			paramMap.put("moduleid", info==null?"0":info.getUserId());
        			paramMap.put("filemodule", "user");
        			try {
        				Upload upload = uploadService.selectFileUpload(paramMap);
        				if (upload != null) {
            				String downLoadPath = getDownloadFile(upload);
                			distPath = downLoadPath.substring(0, downLoadPath.lastIndexOf("/"));
                			File sourceFile = new File(downLoadPath);
                			if (sourceFile.exists()) {
                				file[count] = new File(downLoadPath);
                				StringBuffer buffername = new StringBuffer(sort + "_"
        								+ info.getBankUserName() +  ".jpg");
                				File renameFile = new File(distPath + "/" + buffername.toString());
                				FileUtil.copy(sourceFile, renameFile);
                				
                				file[count] = renameFile;
                				
                				count++;
                			}
            			}
					} catch (Exception e) {
						// TODO: handle exception
						LogFactory.getLog(FileUploadAction.class).info(e);
					}
        			
        			paramMap.clear();
        		}
    			
    			String tempFileName = String.valueOf(System.currentTimeMillis()) + ".zip";
    			if (count > 0) {
    				// 构建ZipFile
					ReadZip.getZip(tempFileName, distPath, file);
					// 下载
					download(tempFileName, distPath, response);
					
					return null;
    			} else {
    				response.setHeader("Pragma", "No-cache");
					response.setHeader("Cache-Control", "no-cache");
					response.setDateHeader("Expires", 0);
					response.setHeader("Content-Type",
							"text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.write("文件不存在");
					out.close();
					return null;
    			}
    			
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	return AD_HTML + "balance/balance";
    }
    
    /**
	 * 下载
	 * 
	 * @param fileName
	 * @param path
	 * @param response
	 */
	private void download(String fileName, String path,
			HttpServletResponse response) {
		String strZipName = fileName;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			File file = new File(path + "/" + strZipName);
			if (!file.exists()) {
				System.out
						.println("------------------------------------压缩包文件不存在");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setHeader("Content-Type", "text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write("压缩包文件不存在");
				out.close();
			} else {
				is = new FileInputStream(file);
				bis = new BufferedInputStream(is);
				os = response.getOutputStream();
				bos = new BufferedOutputStream(os);
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("application/x-msdownload;charset=utf-8");
				response.setHeader(
						"Content-disposition",
						"attachment;filename="
								+ URLEncoder.encode(strZipName, "utf-8"));

				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.flush();
				is.close();
				bis.close();
				os.close();
				bos.close();
				//删除临时文件
				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public String getDownloadFile(Upload upload) {
        return FileDeleteUtil.getrootPath()+upload.getPath()+ "/" + upload.getRealname();
    }
    public void dirDownload(HttpServletResponse response,Upload upload){
    	 BufferedInputStream bis = null;  
         BufferedOutputStream bos = null;  
   
         try {
			String downLoadPath = getDownloadFile(upload);  
   
			 long fileLength = new File(downLoadPath).length();  
			 
			 if(fileLength == 0){
			     upload = new Upload();
	             upload.setFilename("onlinenone.jpg");
	             upload.setRealname("onlinenone.jpg");
	             upload.setPath("/assets/biz-logic/images");
	             downLoadPath = getDownloadFile(upload);
	             fileLength = new File(downLoadPath).length();  
			 }
   
			 response.setHeader("Content-disposition", "attachment; filename="  
			         + new String(upload.getFilename().getBytes("utf-8"), "ISO8859-1"));  
			 response.setHeader("Content-Length", String.valueOf(fileLength));  
   
			 bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
			 bos = new BufferedOutputStream(response.getOutputStream());  
			 byte[] buff = new byte[2048];  
			 int bytesRead;  
			 while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
			     bos.write(buff, 0, bytesRead);  
			 }  
			 bis.close();  
			 bos.close();
		} catch (Exception e) {
			
		}
    }
    /** http下载 */
    public static boolean httpDownload(String httpUrl,String fileName) {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            return false;
        }
        String getfileName = "";
        try {
            getfileName = fileName;
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            File file = new File(getfileName);
            if (file.exists()) {
                System.out.println("文件已经存在,跳过下载：" + getfileName);
                return true;
            }
            FileOutputStream fs = new FileOutputStream(getfileName);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            File file1 = new File(getfileName);
            if (file1.exists()) {
                System.out.println("下载完成，文件存在：" + getfileName);
                return true;
            }
            return true;
        } catch (FileNotFoundException fe) {
            System.out.println("文件没有找到：" + getfileName);
            return false;
        } catch (Exception e) {
            return false;
        }
    }
	

}
