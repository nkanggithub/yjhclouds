package com.nkang.kxmoment.controller;


import java.util.ArrayList;
import java.util.List;










import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nkang.kxmoment.baseobject.ClientInformation;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.util.RestUtils;
@Controller
public class DQMenuController {
	
	public DQMenuController()
	{
		System.out.println("start to load dqmenu class......");
	}
	@RequestMapping("/getOpenMes")
	public @ResponseBody List<ClientInformation> getOpenMes(HttpServletRequest request, HttpServletResponse response)
	{
		List<ClientInformation> clientList = new ArrayList<ClientInformation>();
		clientList = RestUtils.CallGetClientFromMongoDB();
/*		ClientInformation ci=new ClientInformation();
		ci.setClientDescription("test...");
		ci.setClientID("1223sd");
		ci.setClientIdentifier("sfsfsgf");
		clientList.add(ci);
		ci.setClientDescription("test2...");
		clientList.add(ci);*/
		return clientList;
	}
	
	@RequestMapping("/getChart2")
	public @ResponseBody List<String> getChart2(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<String> listOfCities = RestUtils.CallGetFilterNonLatinCityFromMongo(userState);
		List<String> finalString=new ArrayList<String>();
    	String a = "['客户'";
    	String b = "['竞争'";
    	String c = "['伙伴'";
    	String d = "";
   	int countOfCity = 0;
    	if(listOfCities.size() <= 10){
    		countOfCity = listOfCities.size();
    	}
    	else{
    		countOfCity = 10;
    	}
    	for(int i = 0; i < countOfCity ; i ++){
    		 MdmDataQualityView mqvByStateCity = RestUtils.callGetDataQualityReportByParameter(userState,listOfCities.get(i),"");
    		 a = a + "," + mqvByStateCity.getNumberOfCustomer();
    		 b = b + "," + mqvByStateCity.getNumberOfCompetitor();
    		 c = c + "," + mqvByStateCity.getNumberOfPartner();
    		 d = d + "'"+ listOfCities.get(i) +"',";
    	}
    	
    	a = a + "]";
    	b = b + "]";
    	c = c + "]";
    	d = d.substring(0, d.length()-1);
    	d = d + "";
    	finalString.add(a);
    	finalString.add(b);
    	finalString.add(c);
    	finalString.add(d);
    	return finalString;
	}
	
	@RequestMapping("/DQMenu")
	public String DQMenu(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "UID") String uid)
	{
		
		String AccessKey =RestUtils.callGetValidAccessKey();
		WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
		String totalOPSI=RestUtils.CallgetFilterTotalOPSIFromMongo("null","null","null");
		List<String > addressInfo = RestUtils.getUserCurLocWithLatLngV2(wcu.getLat(),wcu.getLng());
		
		if(addressInfo != null && addressInfo.size()>0){
		wcu.setAddressInfo(addressInfo);
		}
		wcu.setProvince("上海市");
		wcu.setCity("上海市");
		List<String> lst = RestUtils.CallGetJSFirstSegmentAreaListFromMongo(wcu.getProvince());
		if(wcu.getNickname() == null && wcu.getNickname() == ""){
			wcu.setNickname("Vistitor");
		}
		if(wcu.getHeadimgurl() ==null && wcu.getHeadimgurl() == ""){
			wcu.setHeadimgurl("MetroStyleFiles/gallery.jpg");
		}
		request.getSession().setAttribute("userInfo", wcu);
		request.getSession().setAttribute("userState", wcu.getProvince());
		request.getSession().setAttribute("radarSize", lst.size());
		request.getSession().setAttribute("uid", uid);
		request.getSession().setAttribute("totalOPSI", totalOPSI);
		return "DQMenu";
	}
}
