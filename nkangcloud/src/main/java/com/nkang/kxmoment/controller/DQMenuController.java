package com.nkang.kxmoment.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.ClientInformation;
import com.nkang.kxmoment.baseobject.GeoLocation;
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
	@RequestMapping("/getOpenfooter")
	public @ResponseBody List<Object[]>  getOpenfooter(HttpServletRequest request, HttpServletResponse response)
	{
		List<Object[]> finalString=new ArrayList<Object[]>();
		
		Object[] a = new Object[2];
		a[0]="Customer";
		Object[] b = new Object[2];
		b[0]="Partner";
		Object[] c = new Object[2];
		c[0]="Competitor";
		Object[] d = new Object[2];
		d[0]="Lead";
		a[1]=60;
		b[1]=30;
		c[1]=5;
		d[1]=5;
		finalString.add(a);
    	finalString.add(b);
    	finalString.add(c);
    	finalString.add(d);
		return finalString;
	}
	/*
	 * author  chang-zheng
	 */
	@RequestMapping("/getChart2")
	public @ResponseBody List<Object[]> getChart2(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<String> listOfCities = RestUtils.CallGetFilterNonLatinCityFromMongo(userState);
		List<String> tempOfCities=new ArrayList<String>();    
		List<Object[]> finalString=new ArrayList<Object[]>();
	
		
   	    int countOfCity = 0;
    	if(listOfCities.size() <= 10){
    		countOfCity = listOfCities.size();
    		tempOfCities = listOfCities;
    	}
    	else{
    		countOfCity = 10;
    		for(int i = 0; i < countOfCity ; i ++){
    			tempOfCities.add(listOfCities.get(i));
    		}
    	}
    	Object[] d = new Object[countOfCity];
    	Object[] a = new Object[countOfCity+1];
		a[0]="客户";
		Object[] b = new Object[countOfCity+1];
		b[0]="竞争";
		Object[] c = new Object[countOfCity];
		c[0]="伙伴";
    	Map<String, MdmDataQualityView> mapByStateCity = RestUtils.callGetDataQualityReportByParameter(userState,tempOfCities,"");
    	for(int i = 0; i < countOfCity ; i ++){
    		 a[i+1]= mapByStateCity.get(tempOfCities.get(i)).getNumberOfCustomer();
    		 b[i+1] = mapByStateCity.get(tempOfCities.get(i)).getNumberOfCompetitor();
    		 c[i+1]=  mapByStateCity.get(tempOfCities.get(i)).getNumberOfPartner();
    		 d[i] =tempOfCities.get(i);
    		 
    		// MdmDataQualityView mqvByStateCity = RestUtils.callGetDataQualityReportByParameter(userState,listOfCities.get(i),"");
    		/* a[i+1]=mqvByStateCity.getNumberOfCustomer();
    		 b[i+1] =mqvByStateCity.getNumberOfCompetitor();
    		 c[i+1]= mqvByStateCity.getNumberOfPartner();
    		 d[i] =listOfCities.get(i);*/
  	
    	}
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
		GeoLocation loc= RestUtils.callGetDBUserGeoInfo(uid);
		String curLoc = RestUtils.getUserCurLocWithLatLng(loc.getLAT() , loc.getLNG()); 
	//	List<String > addressInfo =RestUtils.getUserCurLocWithLatLngV2(loc.getLAT() , loc.getLNG()); 
		List<String > addressInfo = new ArrayList<String>();
		if(addressInfo != null && addressInfo.size()>0){
		wcu.setAddressInfo(addressInfo);
		}
		String state=curLoc.substring(0, 3);
	//	List<String> lst = RestUtils.CallGetJSFirstSegmentAreaListFromMongo("上海市");
		if(wcu.getNickname() == null && wcu.getNickname() == ""){
			wcu.setNickname("Vistitor");
		}
		if(wcu.getHeadimgurl() ==null && wcu.getHeadimgurl() == ""){
			wcu.setHeadimgurl("MetroStyleFiles/gallery.jpg");
		}
		request.getSession().setAttribute("userInfo", wcu);
		request.getSession().setAttribute("userState", state);
	//	request.getSession().setAttribute("radarSize", lst.size());
		request.getSession().setAttribute("uid", uid);
		request.getSession().setAttribute("curLoc", curLoc);
		return "DQMenu";
	}
	
	
	@RequestMapping("/getGraphic")
	public @ResponseBody List<String> getGraphic(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "city") String city)
	{
		List<String> citys=new ArrayList<String>();
		citys.add("北京市");
		citys.add("上海市");
		citys.add("天津市");
		citys.add("深圳市");
		citys.add("重庆市");
	return citys;
	}
	
	@RequestMapping("/getCitys")
	public @ResponseBody Set<String> getCitys(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "country") String countryCode)
	{
		//List<String> citys=RestUtils.getAllStates();
		Set<String> citys = RestUtils.getAllStates(countryCode);
		return citys;
	}
}
