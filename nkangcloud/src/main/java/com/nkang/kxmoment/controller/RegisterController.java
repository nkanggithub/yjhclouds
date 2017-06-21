package com.nkang.kxmoment.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.nkang.kxmoment.baseobject.Role;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;

@Controller
public class RegisterController {
	
	@RequestMapping("/regist")
	@ResponseBody
	public boolean regist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8"); 
		String openId = request.getParameter("uid");
		String name = request.getParameter("name");
		//String role = request.getParameter("role");
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String registerDate = dateFormat.format(now);
		//String selfIntro = URLDecoder.decode(request.getParameter("selfIntro"),"UTF-8");
		//String selfIntro = request.getParameter("selfIntro");
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("email");
		String selfIntro=request.getParameter("selfIntro");
		/*String group = request.getParameter("group");
		String java = request.getParameter("javatag");
		String html = request.getParameter("htmltag");
		String webservice = request.getParameter("webservicetag");
		String etl = request.getParameter("etltag");*/
		/*ArrayList list = new ArrayList();
		Map map = new HashMap();
		
		if(!StringUtils.isNullOrEmpty(java) && Integer.parseInt(java)>0 && Integer.parseInt(java)<=100){
			map.put("java", java);
		}
		
		if(!StringUtils.isNullOrEmpty(html) && Integer.parseInt(html)>0 && Integer.parseInt(html)<=100){
			map.put("html", html);
		}
		
		if(!StringUtils.isNullOrEmpty(webservice) && Integer.parseInt(webservice)>0 && Integer.parseInt(webservice)<=100){
			map.put("webservice", webservice);
		}
		
		if(!StringUtils.isNullOrEmpty(etl) && Integer.parseInt(etl)>0 && Integer.parseInt(etl)<=100){
			map.put("etl", etl);
		}
		list.add(map);
		System.out.println(list);*/
		WeChatMDLUser user = new WeChatMDLUser();
		user.setSelfIntro(URLEncoder.encode(selfIntro, "UTF-8"));
		user.setOpenid(URLEncoder.encode(openId, "UTF-8"));
		user.setRealName(URLEncoder.encode(name, "UTF-8"));
		//user.setRole(URLEncoder.encode(role, "UTF-8"));
		user.setRegisterDate(registerDate);
		//user.setSelfIntro(URLEncoder.encode(selfIntro, "UTF-8"));
		user.setPhone(URLEncoder.encode(telephone, "UTF-8"));
		user.setEmail(URLEncoder.encode(email, "UTF-8"));
		//user.setGroupid(URLEncoder.encode(group, "UTF-8"));
		//user.setTag(list);
		//if(validateRegist(user)){
			return Boolean.parseBoolean(RestUtils.regist(user));
//		}
//		return false;
	}
	

	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public boolean updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8"); 
		String openId = request.getParameter("uid");
		String isActived = request.getParameter("isActived");
		String isAuthenticated = request.getParameter("isAuthenticated");
		String isRegistered = request.getParameter("isRegistered");
		String registerDate = request.getParameter("registerDate");
		String realName=request.getParameter("realName");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String companyName=request.getParameter("companyName");
		String companyRole=request.getParameter("companyRole");
		String selfIntro=request.getParameter("selfIntro");
		Role role=new Role();
		if(request.getParameterValues("role")!=null){
			String[] checkbox= request.getParameterValues("role");
			for(int i =0;i<checkbox.length;i++) //对checkbox进行遍历  
			{  
				if(checkbox[i].equals("isExternalUpStream")){
					role.setExternalUpStream(true);
				}else if(checkbox[i].equals("isExternalCustomer")){
					role.setExternalCustomer(true);
				}else if(checkbox[i].equals("isExternalPartner")){
					role.setExternalPartner(true);
				}else if(checkbox[i].equals("isExternalCompetitor")){
					role.setExternalCompetitor(true);
				}else if(checkbox[i].equals("isInternalSeniorMgt")){
					role.setInternalSeniorMgt(true);
				}else if(checkbox[i].equals("isInternalImtMgt")){
					role.setInternalImtMgt(true);
				}else if(checkbox[i].equals("isInternalBizEmp")){
					role.setInternalBizEmp(true);
				}else if(checkbox[i].equals("isInternalNonBizEmp")){
					role.setInternalNonBizEmp(true);
				}else if(checkbox[i].equals("isInternalQuoter")){
					role.setInternalQuoter(true);
				}else if(checkbox[i].equals("isITOperations")){
					role.setITOperations(true);
				}
			}  												
		}
		WeChatMDLUser user = new WeChatMDLUser();
		user.setRole(companyRole);
		user.setOpenid(URLEncoder.encode(openId, "UTF-8"));
		user.setRealName(realName);
		user.setPhone(phone);
		user.setEmail(email);
		user.setCompanyName(companyName);
		user.setIsActive(isActived);
		user.setIsAuthenticated(isAuthenticated);
		user.setIsRegistered(isRegistered);
		user.setRoleObj(role);
		user.setSelfIntro(selfIntro);
		if(!StringUtils.isNullOrEmpty(registerDate)){
			user.setRegisterDate(registerDate);
		}
		return MongoDBBasic.updateUserWithManageStatus(user);
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
	
	private boolean validateRegist(WeChatMDLUser formUser) {
		String openId = formUser.getOpenid();
		if(StringUtils.isNullOrEmpty(openId)){
			return false;
		} 
		
		String telephone = formUser.getPhone();
		if(telephone == null || telephone.trim().isEmpty()){
			return false;
		} else if(!telephone.matches("^1[34578]\\d{9}$")){
			return false;
		} else if(!MongoDBBasic.queryWeChatUserTelephone(telephone)){
			return false;
		}
		
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			return false;
		} else if(!email.matches("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$")) {
			return false;
		} else if(!MongoDBBasic.queryWeChatUserEmail(email)) {
			return false;
		}
		
		String role = formUser.getRole();
		if(StringUtils.isNullOrEmpty(role)){
			return false;
		} 
		
		String groupid = formUser.getGroupid();
		if(StringUtils.isNullOrEmpty(groupid)){
			return false;
		} 
		return true;
	}
	
}
