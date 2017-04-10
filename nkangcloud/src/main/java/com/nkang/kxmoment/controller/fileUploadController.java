package com.nkang.kxmoment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.nkang.kxmoment.util.FileOperateUtil;


@Controller
@RequestMapping("/fileUpload")
public class fileUploadController {
	private static Logger log=Logger.getLogger(fileUploadController.class);
	@RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
	//@ResponseBody
	public ModelAndView readXlsOfOrderNopay(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mView = new ModelAndView("upload");
		DiskFileItemFactory factory = new DiskFileItemFactory();
	    factory.setSizeThreshold(1024 * 1024);
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    upload.setFileSizeMax(1024 * 1024 * 2);
	    upload.setHeaderEncoding("utf-8");
	    upload.setSizeMax(1024 * 1024 * 4);

		 List<FileItem> fileList = null;
		 	String message = "文件导入失败，请重新导入..";
		    try {
		    	
		        fileList = upload.parseRequest(new ServletRequestContext(request));
		        if(fileList != null){
		            for(FileItem item:fileList){
		            	//String filename="";
		            	   if(!item.isFormField() && item.getSize() > 0){
		                	InputStream is = item.getInputStream();
		                	/*  int dot = item.getName().lastIndexOf('.');   
				            if ((dot >-1) && (dot < (item.getName().length()))) {   
				            	filename=item.getName().substring(0, dot); 
				            }  
		                    if("OrderNopay".equals(filename)){
		                    	 message=FileOperateUtil.DBOperateOrderNopay(is);
		                    }
		                     if("OnDelivery".equals(filename)){
		                    	 message=FileOperateUtil.DBOperateOnDelivery(is);
		                    }
		                    if("Inventory".equals(filename)){
		                    	 message=FileOperateUtil.DBOperateInventory(is);
		                    }*/
		                	message=FileOperateUtil.DBOperateOnExcl(is);
		                	 request.getSession().setAttribute("processSuccMsg", message);
		                    if(is!=null){
		                    	is.close();
		                    }
		                }
		            }
		        }
		           
		    } catch (Exception e) {
		        e.printStackTrace();
		        log.info("fileurl-===--"+e.getMessage());
		        message = "fail--"+e.toString()+"  fileList-size="+ fileList.size() +" message="+ message+" item.isFormField() ="+fileList.get(0).isFormField()+" && item.getSize()="+ fileList.get(0).getSize();
		    
		    }

			return mView;

	}
	
	
	
/*	private WebApplicationContext getApplicationContext(
			HttpServletRequest request) {
		return (WebApplicationContext) request
				.getSession()
				.getServletContext()
				.getAttribute(
            WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }
*/

}
