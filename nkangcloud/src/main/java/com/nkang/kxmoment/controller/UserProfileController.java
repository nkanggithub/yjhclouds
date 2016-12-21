package com.nkang.kxmoment.controller;

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
	 * chang-zheng
	 *  Congratulate
	 */
	@RequestMapping("/getRegisterUserByOpenID1")
	public @ResponseBody String getRegisterUserByOpenID1(HttpServletRequest request,
			HttpServletResponse response){
		String str = MongoDBBasic.getRegisterUserByOpenID("oqPI_xACjXB7pVPGi5KH9Nzqonj4");
		if(str!=null&&str!=""){
			return str;
		}
		return "oqPI_xACjXB7pVPGi5KH9Nzqonj4";
	}
	
	@RequestMapping("/getRegisterUserByOpenID")
	public @ResponseBody String getRegisterUserByOpenID(HttpServletRequest request,
			HttpServletResponse response){
		
		return MongoDBBasic.getRegisterUserByOpenID("oqPI_xACjXB7pVPGi5KH9Nzqonj4");
	}
	
	@RequestMapping("/userCongratulate")
	public @ResponseBody String updateUserCongratulateHistory(HttpServletRequest request,
			HttpServletResponse response ){
		String openid=request.getParameter("openID");
		CongratulateHistory conhis=new CongratulateHistory();
		conhis.setFrom(request.getParameter("from"));
		conhis.setTo(request.getParameter("to"));
		conhis.setType(request.getParameter("type"));
		conhis.setPoint(request.getParameter("points"));
		conhis.setComments(request.getParameter("comments"));
		conhis.setCongratulateDate(new Date().toLocaleString());
		MongoDBBasic.updateUserCongratulateHistory(openid,conhis);
		return "ok";
	} 
}
