package com.nkang.kxmoment.controller;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.nkang.kxmoment.baseobject.Radar;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.util.MongoDBBasic;
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
	public @ResponseBody List<Object[]>  getOpenfooter(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
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
		
		MdmDataQualityView dqv = RestUtils.callGetDataQualityReportByParameter(userState,"","");
		a[1]=dqv.getNumberOfCustomer();
		b[1]=dqv.getNumberOfPartner();
		c[1]=dqv.getNumberOfCompetitor();
		d[1]=dqv.getNumberOfLeads();
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
		Object[] c = new Object[countOfCity+1];
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
	@RequestMapping("/getRadar")
	public @ResponseBody List<Radar[]> getRadar(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<Radar[]> radar=new ArrayList<Radar[]>();
	/*	Radar[] radars=new Radar[10];
		radars[0]=new Radar(" Automotive ",3.6345);
		radars[1]=new Radar(" Business Services ",15.367);
		radars[2]=new Radar(" Discrete - Machinery ",3.6345);
		radars[3]=new Radar(" Consumer Packaged Goods ",6.2055);
		radars[4]=new Radar(" Construction ",3.1815);
		radars[5]=new Radar(" Education: K-12 /School ",6.0675);
		radars[6]=new Radar(" Wholesale Trade ",16.3635);
		radars[7]=new Radar(" Retail ",10.1795);
		radars[8]=new Radar(" Transportation&Trans Services ",3.1595);
		radars[9]=new Radar(" Amusement and Recreation ",1.817);
		radar.add(radars);*/
	//	String totalOPSI=MongoDBBasic.getFilterTotalOPSIFromMongo("重庆市","","");
		List<String> listOfSegmentArea = RestUtils.CallGetJSFirstSegmentAreaFromMongo("120000", "重庆市");
        double m = Double.valueOf("120000");
		int upcnt = listOfSegmentArea.size();
		if(upcnt >= 1){
			upcnt = 1;
		}
		Radar[] radars=new Radar[upcnt];
			for (int i = 0; i < upcnt; i++) {
			double num;
			double n = Double.valueOf(RestUtils.CallgetFilterCountOnCriteriaFromMongo(listOfSegmentArea.get(i).trim(),"","重庆市",""));

			num = n/m;
			radars[i]=new Radar(listOfSegmentArea.get(i),num,n);
		}
			radar.add(radars);
			
		return radar;
	}
	/*
	 * author chang-zheng
	*/
	@RequestMapping("/getRadarda7")
	public @ResponseBody List<Radar[]>  getRadarda7(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<Radar[]> radar=new ArrayList<Radar[]>();
		String totalOPSI=MongoDBBasic.getFilterTotalOPSIFromMongo(userState,"","");
		List<String> listOfSegmentArea = new ArrayList<String>();
		listOfSegmentArea = MongoDBBasic.getFilterSegmentArea(userState);
		List<String> listArea = new ArrayList<String>();
		if(listOfSegmentArea!=null){
			if(listOfSegmentArea.size()>5){
				for(int i=0;i<5;i++){
					listArea.add(listOfSegmentArea.get(i));
				}
			}else{
				listArea = listOfSegmentArea;
			}
		}
		 double m = Double.valueOf(totalOPSI);
		Radar[] radars=new Radar[listArea.size()];
		Map<String,String> rdmap = MongoDBBasic.CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(listArea,"",userState,"");
		for(int i=0;i<listArea.size();i++){
			double num;
			double n =Double.valueOf(rdmap.get(listArea.get(i)));
			num = n/m;
			radars[i]=new Radar(listArea.get(i),num,n);
		}
		//radar.add(radars);
		if(radars!=null){
			//Radar temprada = null;
			  for (int i = 0; i < radars.length; i++)
	            {
	                for (int j = i; j < radars.length; j++)
	                {
	                    if (radars[i].count > radars[j].count)
	                    {
	                    	Radar temprada = radars[i];
	                    	radars[i] = radars[j];
	                    	radars[j] = temprada;
	                    }
	                }
	                
	            }
			  radar.add(radars);
		}
		return radar;
	}
	@RequestMapping("/getRadarda")
	public @ResponseBody List<Radar[]>  getRadarda(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<Radar[]> radar=new ArrayList<Radar[]>();
		String totalOPSI=MongoDBBasic.getFilterTotalOPSIFromMongo(userState,"","");
		List<String> listOfSegmentArea = new ArrayList<String>();
		listOfSegmentArea = MongoDBBasic.getFilterSegmentArea(userState);
		List<String> listArea = new ArrayList<String>();
		if(listOfSegmentArea!=null){
			if(listOfSegmentArea.size()>5){
				for(int i=0;i<5;i++){
					listArea.add(listOfSegmentArea.get(i));
				}
			}else{
				listArea = listOfSegmentArea;
			}
		}
		 double m = Double.valueOf(totalOPSI);
		Radar[] radars=new Radar[listArea.size()];
		Map<String,String> rdmap = MongoDBBasic.CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(listArea,"",userState,"");
		for(int i=0;i<listArea.size();i++){
			double num;
			double n =Double.valueOf(rdmap.get(listArea.get(i)));
			num = n/m;
			radars[i]=new Radar(listArea.get(i),num,n);
		}
		radar.add(radars);
		return radar;
	}
	@RequestMapping("/getRadarda2")
	public @ResponseBody String  getRadarda2(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		String totalOPSI=MongoDBBasic.getFilterTotalOPSIFromMongo(userState,"","");
		
		return totalOPSI;
	}
	@RequestMapping("/getRadarda3")
	public @ResponseBody String  getRadarda3(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		
		List<String> listOfSegmentArea = MongoDBBasic.getFilterSegmentArea(userState);
		return listOfSegmentArea.get(0)+"___2:"+listOfSegmentArea.get(1)+" _3: "+listOfSegmentArea.get(2)+" _4: "+listOfSegmentArea.get(3);
	}
	@RequestMapping("/getRadarda4")
	public @ResponseBody Map<String,String> getRadarda4(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		String totalOPSI=MongoDBBasic.getFilterTotalOPSIFromMongo(userState,"","");
		List<String> listOfSegmentArea = new ArrayList<String>();
		listOfSegmentArea = MongoDBBasic.getFilterSegmentArea(userState);
		List<String> listArea = new ArrayList<String>();
		if(listOfSegmentArea!=null){
			if(listOfSegmentArea.size()>5){
				for(int i=0;i<10;i++){
					listArea.add(listOfSegmentArea.get(i));
				}
			}else{
				listArea = listOfSegmentArea;
			}
		}
		Map<String,String> rdmap = MongoDBBasic.CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(listArea,"",userState,"");
		rdmap.put("opsi", totalOPSI);
		return rdmap;
	}
	@RequestMapping("/getRadarda5")
	public @ResponseBody String getRadarda5(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		
		List<String> listOfSegmentArea = MongoDBBasic.getFilterSegmentArea(userState);
		String rdmap = RestUtils.CallgetFilterCountOnCriteriaFromMongo(listOfSegmentArea.get(0),"",userState,"");
		return rdmap;
	}
	@RequestMapping("/getRadarda6")
	public @ResponseBody Map<String,String>  getRadarda6(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<String> listArea = new ArrayList<String>();
		listArea.add("Construction");
		//List<String> listOfSegmentArea = MongoDBBasic.getFilterSegmentArea(userState);
		Map<String,String> rdmap = MongoDBBasic.CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(listArea,"",userState,"");
		return rdmap;
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
	public @ResponseBody List<String> getCitys(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "country") String countryCode)
	{
		//List<String> citys=RestUtils.getAllStates();
		List<String> citys = MongoDBBasic.getAllStates(countryCode);
		return citys;
	}
}
