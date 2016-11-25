package com.nkang.kxmoment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;


@Controller
@RequestMapping("/userProfile")
public class UserProfileController {
	@RequestMapping(value="/getWeather",produces="text/html;charset=UTF-8")
	@ResponseBody  
	public String getWeather(HttpServletRequest request, HttpServletResponse response){
		String location=(String) request.getSession().getAttribute("location");
		//String location=request.getParameter("location");
		if(location==null||"".equals(location))
		{
			location="重庆市";
		}else{
			location=location.substring(0, 3);
		}
		String weather= RestUtils.getWeatherInform(location);
		return weather;
	}
	@RequestMapping(value="/getLocation",produces="text/html;charset=UTF-8")
	@ResponseBody  
	public String getLocation(HttpServletRequest request, HttpServletResponse response){
		String uid=request.getParameter("uid");
		GeoLocation loc= RestUtils.callGetDBUserGeoInfo(uid);
		String curLoc = RestUtils.getUserCurLocWithLatLng(loc.getLAT() , loc.getLNG()); 
		if(curLoc!=null&&!"".equals(curLoc)){
			request.getSession().setAttribute("location",curLoc);
		}
		return curLoc;
	}
	@RequestMapping(value="/getMDLUserLists",produces="text/html;charset=UTF-8")
	@ResponseBody  
	public  String  getMDLUserLists(HttpServletRequest request, HttpServletResponse response){
		String openid = request.getParameter("UID");
		List<WeChatMDLUser> userList = MongoDBBasic.getWeChatUserFromMongoDB(openid);
		if(userList.size()>0){
			request.setAttribute("user",userList.get(0));
			return RestUtils.getMDLUserLists(openid);
		}
		return null;
	}
}
