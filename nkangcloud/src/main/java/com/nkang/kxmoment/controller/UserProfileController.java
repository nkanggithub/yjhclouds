package com.nkang.kxmoment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.ClientMeta;
import com.nkang.kxmoment.baseobject.CongratulateHistory;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;
import com.nkang.kxmoment.util.ToolUtils;

@Controller
@RequestMapping("/userProfile")
public class UserProfileController {
	@RequestMapping(value = "/getWeather", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getWeather(HttpServletRequest request,
			HttpServletResponse response) {
		String city = (String) request.getSession()
				.getAttribute("city");
		// String location=request.getParameter("location");
		if (city == null || "".equals(city)) {
			city = "重庆市";
		}
		String weather = RestUtils.getWeatherInform(city);
		return weather;
	}

	@RequestMapping(value = "/getLocation", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getLocation(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		String uid = request.getParameter("uid");
		GeoLocation loc = RestUtils.callGetDBUserGeoInfo(uid);
		String message = RestUtils.getUserCurLocStrWithLatLng(loc.getLAT(),loc.getLNG());
		JSONObject demoJson = new JSONObject(message);
		String curLoc="";
        if(demoJson.has("result")){
     	    JSONObject JsonFormatedLocation = demoJson.getJSONObject("result");
     	 	curLoc = JsonFormatedLocation.getString("formatted_address");
     	 	String city = JsonFormatedLocation.getJSONObject("addressComponent").getString("city");
     	 	request.getSession().setAttribute("location", curLoc);
     	 	request.getSession().setAttribute("city", city);
        }
		return curLoc;
	}

	@RequestMapping(value = "/getMDLUserLists", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getMDLUserLists(HttpServletRequest request,
			HttpServletResponse response) {
		String openid = request.getParameter("UID");
		return RestUtils.getMDLUserLists(openid);
	}

	@RequestMapping(value = "/getTax", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getTax(HttpServletRequest request,
			HttpServletResponse response) {
		String taxIncomeStr = request.getParameter("taxIncome");
		String taxstartStr = request.getParameter("taxstart");
		String paymentStr = request.getParameter("payment");
		double taxIncome = new Double(taxIncomeStr);
		double taxstart = new Double(taxstartStr);
		double payment = new Double(paymentStr);
		HashMap<String, Double> result = new HashMap<String, Double>();
		double levelcalc= ToolUtils.getlevelcalc(taxIncome - taxstart-payment);
		double nolevelcalc= ToolUtils.getnolevelcalc(taxIncome - taxstart-payment);
		return "{\"levelcalc\":"+levelcalc+",\"nolevelcalc\":"+nolevelcalc+"}";
	}
	
	@RequestMapping(value = "/setSignature", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String setSignature(HttpServletRequest request,
			HttpServletResponse response) {
		String svg = request.getParameter("svg");
		String openid = (String) request.getSession()
				.getAttribute("UID");
		return RestUtils.getCallUpdateUserWithSignature(openid,svg);
	}
	@RequestMapping(value = "/updateUserWithLike", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserWithLike(
			@RequestParam(value="openid", required=false) String openid,
			@RequestParam(value="likeToName", required=false) String likeToName,
			@RequestParam(value="ToOpenId", required=false) String ToOpenId
			){
		boolean ret = false;
		try{
			ret = MongoDBBasic.updateUserWithLike(openid, likeToName, ToOpenId);
		}		
		catch(Exception e){
			ret = false;
		}
		return ret==true?"true":"false";
	}

	/*
	 * chang-zheng
	 *  Congratulate
	 */
	@RequestMapping("/getRegisterUsers")
	public @ResponseBody List<String> getRegisterUsers(HttpServletRequest request,
			HttpServletResponse response){
		return MongoDBBasic.getAllRegisterUsers();
	}
	

	/*
	 *Panda
	 *  getRecognitionInfoByOpenID
	 */
	@RequestMapping("/getRecognitionInfoByOpenID")
	public @ResponseBody List<CongratulateHistory>  getRecognitionInfoByOpenID(HttpServletRequest request,
			HttpServletResponse response ){
		List<CongratulateHistory> chList=MongoDBBasic.getRecognitionInfoByOpenID(request.getParameter("openID"));
/*		List<CongratulateHistory> chList=new ArrayList<CongratulateHistory>();
		CongratulateHistory ch=new CongratulateHistory();
		ch.setFrom("Panda");
		ch.setTo("Ning");
		ch.setComments("moving NoSQL and Solr match POC smoothly as part of our FY16 team goal. The progress you can your team made is promissing.");
		ch.setPoint("300");
		ch.setType("Innovators at Heart");
		String date="2016-12-22 16:52:07";
		ch.setCongratulateDate(date.substring(0,11));
		chList.add(ch);*/
		return chList;
		
	} 

	/*
	 * chang-zheng
	 *  Congratulate
	 */
	@RequestMapping("/getRegisterUserByOpenID1")
	public @ResponseBody String getRegisterUserByOpenID1(HttpServletRequest request,
			HttpServletResponse response){
		List<String> str = MongoDBBasic.getRegisterUserByOpenID("oqPI_xACjXB7pVPGi5KH9Nzqonj4");
		if(str!=null){
			return str.get(0);
		}
		return "oqPI_xACjXB7pVPGi5KH9Nzqonj4";
	}
	
	@RequestMapping("/getRegisterUserByOpenID")
	public @ResponseBody String getRegisterUserByOpenID(HttpServletRequest request,
			HttpServletResponse response){
		String openid=request.getParameter("openID");
		List<String> dbUser = MongoDBBasic.getRegisterUserByOpenID(openid);
		if(dbUser!=null){
			return dbUser.get(0).toString();
		}
		return "nullname";
	}
	
	@RequestMapping("/userCongratulate")
	public @ResponseBody String updateUserCongratulateHistory(HttpServletRequest request,
			HttpServletResponse response ){
		//String openid=request.getParameter("openID");
		String openid=MongoDBBasic.getRegisterUserByrealName(request.getParameter("to"));
		CongratulateHistory conhis=new CongratulateHistory();
		conhis.setFrom(request.getParameter("from"));
		conhis.setTo(request.getParameter("to"));
		conhis.setType(request.getParameter("type"));
		conhis.setPoint(request.getParameter("points"));
		conhis.setComments(request.getParameter("comments"));
		conhis.setCongratulateDate(new Date().toLocaleString());
		MongoDBBasic.updateUserCongratulateHistory(openid,conhis);
		RestUtils.sendNewsToUser(openid, conhis);
		return "ok";
	} 
	@RequestMapping("/getCompanyInfo")
	public @ResponseBody List<String> getCompanyInfo(HttpServletRequest request,
			HttpServletResponse response ){
		ClientMeta cm=MongoDBBasic.QueryClientMeta();
		List<String> companyInfo=new ArrayList<String>();
		companyInfo.add(cm.getClientLogo());
		companyInfo.add(cm.getClientCopyRight());
		return companyInfo;
		
	} 
	@RequestMapping("/getRealName")
	public @ResponseBody List<String> getRealName(HttpServletRequest request,
			HttpServletResponse response ){
		List<String> dbUser =  MongoDBBasic.getRegisterUserByOpenID(request.getParameter("openID"));
			return dbUser;
	} 
	@RequestMapping("/getAllRegisterUsers")
	public @ResponseBody List<String> getAllRegisterUsers(HttpServletRequest request,
			HttpServletResponse response ){
		List<String> str = MongoDBBasic.getAllRegisterUsers();
		return str;
		
	} 
}
