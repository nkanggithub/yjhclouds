package com.nkang.kxmoment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.util.RestUtils;


@Controller
@RequestMapping("/userProfile")
public class UserProfileController {
	@RequestMapping(value="/getWeather",produces="text/html;charset=UTF-8")
	@ResponseBody  
	public String getWeather(HttpServletRequest request, HttpServletResponse response){
		String location=(String) request.getSession().getAttribute("location");
		//String location=request.getParameter("location");
		location=location.substring(0, 3);
		String weather= RestUtils.getWeatherInform(location);
		return weather;
	}
	
}
