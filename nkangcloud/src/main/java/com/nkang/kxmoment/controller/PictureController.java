package com.nkang.kxmoment.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.service.CoreService;
import com.nkang.kxmoment.util.FaceRecognition;

@Controller
public class PictureController{
	/**
    * <p>Description: 上传文件</p>
    * @param request
    */
	@RequestMapping("/uploadPicture")
	@ResponseBody
   public String upload(HttpServletRequest request) {
		FaceRecognition faceRecognition = new FaceRecognition();
		 return faceRecognition.gofaceEmotion("http://mmbiz.qpic.cn/mmbiz_jpg/WB6qdHS5xfEEO8tSzLHxG7nvFB0yQzqPdm8iafdluCIq6EOt4sDRyItQJVvQFbb785C3ic3Bbz468ibOS0ibSFBJIg/0");
	}
}
 
