package com.nkang.kxmoment.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
			String URL = CoreService.picURL;// "http://mmbiz.qpic.cn/mmbiz_jpg/WB6qdHS5xfEEO8tSzLHxG7nvFB0yQzqPdm8iafdluCIq6EOt4sDRyItQJVvQFbb785C3ic3Bbz468ibOS0ibSFBJIg/0";
			if(URL==null || URL ==""){
				URL = "http://mmbiz.qpic.cn/mmbiz_jpg/WB6qdHS5xfEEO8tSzLHxG7nvFB0yQzqPdm8iafdluCIq6EOt4sDRyItQJVvQFbb785C3ic3Bbz468ibOS0ibSFBJIg/0";
			}
			jsonArraGoface = new JSONArray(faceRecognition.goface(URL).toString());
			jsonArraEmotion = new JSONArray(faceRecognition.gofaceEmotion(URL).toString());
			for(int i=0 ; i < jsonArraEmotion.length() ;i++)
			  {
			  FaceObj fo = new FaceObj();
			  JSONObject myjObject = jsonArraGoface.getJSONObject(i);
			  JSONObject myjObjectEmotion = jsonArraEmotion.getJSONObject(i);
			  fo.setAnger(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("anger").toString());
			  fo.setContempt(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("contempt").toString());
			  fo.setDisgust(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("disgust").toString());
			  fo.setFear(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("fear").toString());
			  fo.setHappiness(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("happiness").toString());
			  fo.setNeutral(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("neutral").toString());
			  fo.setSadness(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("sadness").toString());
			  fo.setSurprise(CommenJsonUtil.jsonToObject(myjObjectEmotion.get("scores").toString()).get("surprise").toString());
			  
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
 
