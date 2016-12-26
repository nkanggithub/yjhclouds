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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.FaceObj;
import com.nkang.kxmoment.service.CoreService;
import com.nkang.kxmoment.util.CommenJsonUtil;
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
		String str="";
		FaceRecognition faceRecognition = new FaceRecognition();
		List<FaceObj> ls = new ArrayList<FaceObj>();
		
		JSONArray jsonArraGoface;
		JSONArray jsonArraEmotion;
		try {
			String URL = CoreService.picURL;
			if(URL==null || URL ==""){
				URL = "http://mmbiz.qpic.cn/mmbiz_jpg/WB6qdHS5xfEEO8tSzLHxG7nvFB0yQzqPdm8iafdluCIq6EOt4sDRyItQJVvQFbb785C3ic3Bbz468ibOS0ibSFBJIg/0";
			}
			jsonArraGoface = new JSONArray(faceRecognition.goface(URL).toString());
			jsonArraEmotion = new JSONArray(faceRecognition.gofaceEmotion(URL).toString());
			for(int i=0 ; i < jsonArraGoface.length() ;i++)
			  {
			  FaceObj fo = new FaceObj();
			  JSONObject myjObject = jsonArraGoface.getJSONObject(i);
			  JSONObject myjObjectEmotion = jsonArraEmotion.getJSONObject(i);
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("anger").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("contempt").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("disgust").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("fear").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("happiness").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("neutral").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("sadness").toString());
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("surprise").toString());
			  
			  fo.setAge(CommenJsonUtil.jsonToObject(myjObject.get("faceAttributes").toString()).get("age").toString());
			  fo.setBeard(CommenJsonUtil.jsonToObject(CommenJsonUtil.jsonToObject(myjObject.get("faceAttributes").toString()).getString("facialHair").toString()).get("beard").toString());
			  fo.setGender(CommenJsonUtil.jsonToObject(myjObject.get("faceAttributes").toString()).get("gender").toString());
			  fo.setGlasses(CommenJsonUtil.jsonToObject(myjObject.get("faceAttributes").toString()).get("glasses").toString());
			  fo.setMoustache(CommenJsonUtil.jsonToObject(CommenJsonUtil.jsonToObject(myjObject.get("faceAttributes").toString()).getString("facialHair").toString()).get("moustache").toString());
			  fo.setSmile(CommenJsonUtil.jsonToObject(myjObject.get("faceAttributes").toString()).get("smile").toString());
			  ls.add(fo);
			  }
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 for(int i=0 ; i < ls.size() ;i++){
			 str = str+"\n\n" + ls.get(i).Info()+ "\n\n";
		 }
		return str;
	}
}
 
