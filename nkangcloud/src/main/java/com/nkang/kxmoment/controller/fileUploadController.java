package com.nkang.kxmoment.controller;

import java.io.File;
import java.util.List;

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

import com.nkang.kxmoment.util.FileOperateUtil;


@Controller
@RequestMapping("/fileUpload")
public class fileUploadController {
	private static Logger log=Logger.getLogger(fileUploadController.class);
	@RequestMapping(value = "/uploadOrderNopay", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String readXlsOfOrderNopay(HttpServletRequest request,HttpServletResponse response){
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
	    factory.setSizeThreshold(1024 * 1024);
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    upload.setFileSizeMax(1024 * 1024 * 2);
	    upload.setHeaderEncoding("utf-8");
	    upload.setSizeMax(1024 * 1024 * 4);

		 List<FileItem> fileList = null;
		 	String url = null;
		 	String message = "fail";
		    try {
		    	
		        fileList = upload.parseRequest(new ServletRequestContext(request));
		        if(fileList != null){
		        	 String fileurl=request.getSession().getServletContext().getRealPath("/");
		        	 log.info("fileurl------"+fileurl);
		            for(FileItem item:fileList){
		            	String filename="";
		            	System.out.println(item.getName());
		                if(!item.isFormField() && item.getSize() > 0){
		                	url = fileurl+item.getName();
		                    item.write(new File(url));
		                    int dot = item.getName().lastIndexOf('.');   
				            if ((dot >-1) && (dot < (item.getName().length()))) {   
				            	filename=item.getName().substring(0, dot); 
				            }  
		                    if("OrderNopay".equals(filename)){
		                    	 message=FileOperateUtil.DBOperateOrderNopay(url);
		                    }
		                    if("OnDelivery".equals(filename)){
		                    	 message=FileOperateUtil.DBOperateOnDelivery(url);
		                    }
		                    if("Inventory".equals(filename)){
		                    	 message=FileOperateUtil.DBOperateInventory(url);
		                    }
		                }
		            }
		        }
		           
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

			return message;

	}
/*//		, @RequestParam("file") CommonsMultipartFile file
//		System.out.println(file.getOriginalFilename());
		try {
			 init(request);
			FileOperateUtil.upload(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}
	private void init(HttpServletRequest request) {
	        if(FileOperateUtil.FILEDIR == null){
	            FileOperateUtil.FILEDIR = request.getSession().getServletContext().getRealPath("/") + "file/";
	        }
	    }
*/
}
