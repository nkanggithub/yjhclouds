package com.nkang.kxmoment.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@RequestMapping("/getChart2")
	public @ResponseBody List<Object[]> getChart2(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<String> listOfCities = RestUtils.CallGetFilterNonLatinCityFromMongo(userState);
		List<Object[]> finalString=new ArrayList<Object[]>();
		Object[] a = new Object[11];
		a[0]="客户";
		Object[] b = new Object[11];
		b[0]="竞争";
		Object[] c = new Object[11];
		c[0]="伙伴";
		Object[] d = new Object[10];
   	int countOfCity = 0;
    	if(listOfCities.size() <= 10){
    		countOfCity = listOfCities.size();
    	}
    	else{
    		countOfCity = 10;
//    		for(int i = 0; i < countOfCity ; i ++){
//    			tempOfCities.add(listOfCities.get(i));
//    		}
    	}
    	//Map<String, MdmDataQualityView> mapByStateCity = RestUtils.callGetDataQualityReportByParameter(userState,tempOfCities,"");
    	for(int i = 0; i < countOfCity ; i ++){
    		/* a[i+1]= mapByStateCity.get(tempOfCities.get(i)).getNumberOfCustomer();
    		 b[i+1] = mapByStateCity.get(tempOfCities.get(i)).getNumberOfCompetitor();
    		 c[i+1]=  mapByStateCity.get(tempOfCities.get(i)).getNumberOfPartner();
    		 d[i] =tempOfCities.get(i);
    		 */
    		 MdmDataQualityView mqvByStateCity = RestUtils.callGetDataQualityReportByParameter(userState,listOfCities.get(i),"");
    		 a[i+1]=mqvByStateCity.getNumberOfCustomer();
    		 b[i+1] =mqvByStateCity.getNumberOfCompetitor();
    		 c[i+1]= mqvByStateCity.getNumberOfPartner();
    		 d[i] =listOfCities.get(i);
  	
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
	
	/*
	 * author  chang-zheng
	 */
	@RequestMapping("/dq")
	public @ResponseBody List<String> getCitys(HttpServletRequest request, HttpServletResponse response)
	{
		return RestUtils.getAllStates();
	}
	
	/*
	 * author  chang-zheng
	 */
	@RequestMapping("/getChart22")
	public @ResponseBody List<Object[]> getChart22(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "userState") String userState)
	{
		List<String> listOfCities = RestUtils.CallGetFilterNonLatinCityFromMongo(userState);
		List<String> tempOfCities=new ArrayList<String>();    
		List<Object[]> finalString=new ArrayList<Object[]>();
		Object[] a = new Object[11];
		a[0]="客户";
		Object[] b = new Object[11];
		b[0]="竞争";
		Object[] c = new Object[11];
		c[0]="伙伴";
		Object[] d = new Object[10];
   	int countOfCity = 0;
    	if(listOfCities.size() <= 10){
    		countOfCity = listOfCities.size();
    	}
    	else{
    		countOfCity = 10;
    		for(int i = 0; i < countOfCity ; i ++){
    			tempOfCities.add(listOfCities.get(i));
    		}
    	}
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
	
}
