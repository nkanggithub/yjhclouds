package com.nkang.kxmoment.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;

@Controller
public class RegisterController {
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/regist")
	@ResponseBody
	public boolean regist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8"); 
		String openId = request.getParameter("uid");
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String registerDate = dateFormat.format(now);
		//String selfIntro = URLDecoder.decode(request.getParameter("selfIntro"),"UTF-8");
		String selfIntro = request.getParameter("selfIntro");
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("email");
		String group = request.getParameter("group");
		
		
		WeChatMDLUser user = new WeChatMDLUser();
		user.setOpenid(URLEncoder.encode(openId, "UTF-8"));
		user.setRealName(URLEncoder.encode(name, "UTF-8"));
		user.setRole(URLEncoder.encode(role, "UTF-8"));
		user.setRegisterDate(registerDate);
		user.setSelfIntro(URLEncoder.encode(selfIntro, "UTF-8"));
		user.setPhone(URLEncoder.encode(telephone, "UTF-8"));
		user.setEmail(URLEncoder.encode(email, "UTF-8"));
		user.setGroupid(URLEncoder.encode(group, "UTF-8"));;
		return Boolean.parseBoolean(RestUtils.regist(user));
	}
	
	@RequestMapping("/ajaxValidateTelephone")
	@ResponseBody
	private boolean ajaxValidateTelephone(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String telephone = request.getParameter("telephone");
		return MongoDBBasic.queryWeChatUserTelephone(telephone);
	}
	
	@RequestMapping("/ajaxValidateEmail")
	@ResponseBody
	public boolean ajaxValidateEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		return MongoDBBasic.queryWeChatUserEmail(email);
	}
	
	private Map<String,String> validateRegist(WeChatMDLUser formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
//		String openId = formUser.getOpenid();
//		if(openId == null){
//			errors.put("uid", "uid is null");
//		} else if(validateWehcatUser(openId)){
//			errors.put("uid", "user was registed");
//		}
		
		String telephone = formUser.getPhone();
		if(telephone == null || telephone.trim().isEmpty()){
			errors.put("telephone", "Telephone can't be empty!");
		} else if(!telephone.matches("^1[34578]\\d{9}$")){
			errors.put("telephone", "Telephone format is wrong!");
		} else if(!MongoDBBasic.queryWeChatUserTelephone(telephone)){
			errors.put("telephone", "Telephone was existed!");
		}
		
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email can't be empty!");
		} else if(!email.matches("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$")) {
			errors.put("email", "Email format is wrong!");
		} else if(!MongoDBBasic.queryWeChatUserEmail(email)) {
			errors.put("email", "Email was existed!");
		}
		return errors;
	}
	
}
