package com.nkang.kxmoment.controller;


import java.io.IOException;
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
	
	@RequestMapping("/register")
	@ResponseBody
	public boolean registUser(HttpServletRequest request, HttpServletResponse response){
		String openId = request.getParameter("uid");
		String suppovisor = request.getParameter("suppovisor");
		String role = request.getParameter("role");
		String registerDate = request.getParameter("registerDate");
		String selfIntro = request.getParameter("selfIntro");
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("email");
		
		WeChatMDLUser user = new WeChatMDLUser();
		user.setOpenid(openId);
		user.setSuppovisor(suppovisor);
		user.setRole(role);
		user.setRegisterDate(registerDate);
		user.setSelfIntro(selfIntro);
		user.setPhone(telephone);
		user.setEmail(email);
		
		request.setAttribute("form", user);
		/*Map<String,String> errors = validateRegist(user, request.getSession());
		if(errors.size() > 0) {
			
			return "redirect:/mdm/profile.jsp";
		}*/
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

//	private boolean validateWehcatUser(String openId) {
//		WeChatUser user = MongoDBBasic.queryWeChatUser(openId);
//		if(user != null){
//			String telephone = user.getPhone();
//			String email = user.getEmail();
//			if(!StringUtils.isNullOrEmpty(telephone) || StringUtils.isNullOrEmpty(email)){
//				return true;
//			}
//		}
//		return false;
//	}
	
}
