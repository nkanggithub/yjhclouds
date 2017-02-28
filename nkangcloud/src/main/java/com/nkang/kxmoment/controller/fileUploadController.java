package com.nkang.kxmoment.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nkang.kxmoment.baseobject.OrderNopay;
import com.nkang.kxmoment.util.BillOfSellPoi;
import com.nkang.kxmoment.util.FileOperateUtil;
import com.nkang.kxmoment.util.RestUtils;

@Controller
@RequestMapping("/fileUpload")
public class fileUploadController {
	@RequestMapping(value = "/uploadOrderNopay", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String readXlsOfOrderNopay(HttpServletRequest request){
		
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

}
