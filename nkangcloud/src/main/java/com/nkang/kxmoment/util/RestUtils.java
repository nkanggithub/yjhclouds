package com.nkang.kxmoment.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nkang.kxmoment.baseobject.WeChatUser;

public class RestUtils {
	private static Logger log=Logger.getLogger(RestUtils.class);
	private static final  double EARTH_RADIUS = 6371000; 
	public static String getAccessKey() {
			//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ Constants.APP_ID+ "&secret=" + Constants.APPSECRET;
			String accessToken = null;
			String expires_in = null;
			try {
		           URL urlGet = new URL(url);
		           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
		           http.setRequestMethod("GET"); //must be get request
		           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		           http.setDoOutput(true);
		           http.setDoInput(true);
		           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
		           http.connect();
		           InputStream is = http.getInputStream();
		           int size = is.available();
		           byte[] jsonBytes = new byte[size];
		           is.read(jsonBytes);
		           String message = new String(jsonBytes, "UTF-8");
		           JSONObject demoJson = new JSONObject(message);
		           accessToken = demoJson.getString("access_token");
		           expires_in = demoJson.getString("expires_in");
		           log.info("Get New Access Key :" + accessToken);
		           DBUtils.updateAccessKey(accessToken, expires_in);
		           is.close();
		       } catch (Exception e) {
		    	   e.printStackTrace();
		       } 
			return accessToken;
	}

	public static WeChatUser getWeChatUserInfo(String akey, String openID){
		WeChatUser wcu = new WeChatUser();
		
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+ akey +"&openid="+ openID;
		try {
	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("GET"); //must be get request
	           http.setRequestProperty("Content-Type","application/json");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
	           http.connect();
	           InputStream is = http.getInputStream();
	           int size = is.available();
	           byte[] jsonBytes = new byte[size];
	           is.read(jsonBytes);
	           String message = new String(jsonBytes, "UTF-8");
	           JSONObject demoJson = new JSONObject(message);
	           String subscribe 	= demoJson.getString("subscribe");
	           String openid 		= demoJson.getString("openid");
	           String nickname		= demoJson.getString("nickname");
	           String sex			= demoJson.getString("sex");
	           String city			= demoJson.getString("city");
	           String language		= demoJson.getString("language");
	           String province		= demoJson.getString("province");
	           String headimgurl	= demoJson.getString("headimgurl");
	           String subscribe_time= demoJson.getString("subscribe_time");
	           String remark		= demoJson.getString("remark");
	           String groupid		= demoJson.getString("groupid");
	           wcu.setCity(city);
	           wcu.setGroupid(groupid);
	           wcu.setHeadimgurl(headimgurl);
	           wcu.setLanguage(language);
	           wcu.setNickname(nickname);
	           wcu.setOpenid(openid);
	           wcu.setProvince(province);
	           wcu.setRemark(remark);
	           wcu.setSex(sex);
	           wcu.setSubscribe(subscribe);
	           wcu.setSubscribe_time(subscribe_time);
	           
	           is.close();
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       } 
		return wcu;
	}

	public static String getLocationDetailsforOppt(String Query, String Region, String OpptID, String OpptAddr, String OpsiId, String CityRegion) throws Exception{
		String StatusMessage = "";
		if(Query != null){
			Query = URLEncoder.encode(Query, "UTF-8");
		}
		else{
			Query = "Query";
		}
		if(Region != null){
			Region = URLEncoder.encode(Region, "UTF-8");
		}
		else{
			Region = "China";
		}
		log.info("Get Started 2: " + OpsiId + " City " + Region + " Query " + Query);
		
		String url =  "http://api.map.baidu.com/place/v2/search?query=" + Query + "&region=" + Region + "&output=json&ak=" + Constants.BAIDU_APPKEY;
		log.info(url);
		try {
	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("GET"); //must be get request
	           http.setRequestProperty("Content-Type","application/json");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
	           http.connect();
	           InputStream is = http.getInputStream();
	           int size = is.available();
	           byte[] jsonBytes = new byte[size];
	           is.read(jsonBytes);
	           String message = new String(jsonBytes, "UTF-8");
	           log.info(message);
	           JSONObject demoJson = new JSONObject(message);
	           if(demoJson.has("results")){
		           JSONArray ResultJA = demoJson.getJSONArray("results");
		           if(ResultJA.length() > 0){
		        	   JSONObject placeJO = ResultJA.getJSONObject(0);
		        	   if(placeJO != null &&placeJO.has("location")){
		        		   try{
					           JSONObject locationJO = placeJO.getJSONObject("location");
					           String lng = locationJO.getString("lng");
					           String lat = locationJO.getString("lat");
					           if(lng !="" && lat != ""){
					        	   DBUtils.updateOppt(lat, lng, OpptID, String.valueOf(ResultJA.length()), OpptAddr, OpsiId, CityRegion);
					           }
		        		   }
		        		   catch(Exception e){
		        			   DBUtils.updateOppt("", "", OpptID, String.valueOf(0), OpptAddr, OpsiId, CityRegion);
		        		   }
		        	   }
		        	   else{
		        		   DBUtils.updateOppt("", "", OpptID, String.valueOf(0), OpptAddr, OpsiId, CityRegion);
		        	   }
		           }
		           else{
		        	   DBUtils.updateOppt("", "", OpptID, String.valueOf(0), OpptAddr, OpsiId, CityRegion);
		           }
	           }
	           else{
	        	   StatusMessage = demoJson.getString("status");
	           }

	           is.close();
	           //log.info("--Organization ID :" + OpptID + " Updated");
	       } catch (Exception e) {
	    	   System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()) + "Exception Occurs in getLocationDetailsforOppt with OPSI: "+ OpsiId +"---" + e.toString());
	    	   e.printStackTrace();
	       }
		return StatusMessage;
	}
	
    public static void getEnv() {  
        System.getProperties().list(System.out);//得到当前的系统属性。并将属性列表输出到控制台  
        String encoding = System.getProperty("file.encoding");  
        log.info("Encoding:" + encoding);  
    }
    
    public static String getUserCurLocWithLatLng(String lat, String lng){
    	String ret = "";
    	String url =  "http://api.map.baidu.com/geocoder/v2/?ak=" + Constants.BAIDU_APPKEY + "&location=" + lat + "," + lng +"&output=json";
		try {
	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("GET"); //must be get request
	           http.setRequestProperty("Content-Type","application/json");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
	           http.connect();
	           InputStream is = http.getInputStream();
	           int size = is.available();
	           byte[] jsonBytes = new byte[size];
	           is.read(jsonBytes);
	           String message = new String(jsonBytes, "UTF-8");
	           log.info(message);
	           JSONObject demoJson = new JSONObject(message);
	           if(demoJson.has("result")){
	        	   JSONObject JsonFormatedLocation = demoJson.getJSONObject("result");
		           ret = JsonFormatedLocation.getString("formatted_address");
	           }
	           is.close();
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
		return ret;
    }
    
    public static List<String> getUserCityInfoWithLatLng(String lat, String lng){
    	String ret = "";
    	List<String> CityComponent =  new ArrayList<String>();
    	String url =  "http://api.map.baidu.com/geocoder/v2/?ak=" + Constants.BAIDU_APPKEY + "&location=" + lat + "," + lng +"&output=json";
		try {
	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("GET"); //must be get request
	           http.setRequestProperty("Content-Type","application/json");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
	           http.connect();
	           InputStream is = http.getInputStream();
	           int size = is.available();
	           byte[] jsonBytes = new byte[size];
	           is.read(jsonBytes);
	           String message = new String(jsonBytes, "UTF-8");
	           log.info(message);
	           JSONObject demoJson = new JSONObject(message);
	           if(demoJson.has("result")){
	        	   JSONObject JsonFormatedLocation = demoJson.getJSONObject("result");
		           ret = JsonFormatedLocation.getString("formatted_address");
		           JSONObject JsonAddressComponent = JsonFormatedLocation.getJSONObject("addressComponent");
		           CityComponent.add(JsonAddressComponent.getString("province"));
		           CityComponent.add(JsonAddressComponent.getString("city"));
		           CityComponent.add(JsonAddressComponent.getString("district"));
	           }
	           is.close();
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
		return CityComponent;
    }
    
    public static String createMenu(String access_token) {
        String menu = "{\"button\":[{\"name\":\"Near By\",\"sub_button\":[{\"type\":\"click\",\"name\":\"Opportunity\",\"key\":\"nboppt\"},{\"type\":\"click\",\"name\":\"Customer\",\"key\":\"nbcust\"},{\"type\":\"click\",\"name\":\"Competitor\",\"key\":\"nbcompe\"},{\"type\":\"click\",\"name\":\"Partner\",\"key\":\"nbpartner\"}]},{\"name\":\"MDG\",\"sub_button\":[{\"type\":\"click\",\"name\":\"Data Lake\",\"key\":\"MDLAKE\"},{\"type\":\"click\",\"name\":\"Customer Care\",\"key\":\"05_SIGN\"}]}]}";
        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
          try {
             URL url = new URL(action);
             HttpURLConnection http =   (HttpURLConnection) url.openConnection();    
             http.setRequestMethod("POST");        
             http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
             http.setDoOutput(true);        
             http.setDoInput(true);
             System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
             System.setProperty("sun.net.client.defaultReadTimeout", "30000");
             http.connect();
             OutputStream os= http.getOutputStream();    
             os.write(menu.getBytes("UTF-8"));
             os.flush();
             os.close();
             InputStream is =http.getInputStream();
             int size =is.available();
             byte[] jsonBytes =new byte[size];
             is.read(jsonBytes);
             String message=new String(jsonBytes,"UTF-8");
             return "Create Menu Success";
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }    
          return "Create Menu Failed";
     }

      /**
       * 删除当前Menu
      * @Title: deleteMenu
      * @Description: 删除当前Menu
      * @param @return    设定文件
      * @return String    返回类型
      * @throws
       */
     public static String deleteMenu(String access_token)
     {
         String action = "https://api.weixin.qq.com/cgi-bin/menu/delete? access_token="+access_token;
         try {
            URL url = new URL(action);
            HttpURLConnection http =   (HttpURLConnection) url.openConnection();    

            http.setRequestMethod("GET");        
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            http.setDoOutput(true);        
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒 
             http.connect();
            OutputStream os= http.getOutputStream();    
            os.flush();
            os.close();

            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            String message=new String(jsonBytes,"UTF-8");
            return "deleteMenu:"+message;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
         return "deleteMenu Failed";   
     }
    
    private static double rad(double d)
    {
       return d * Math.PI / 180.0;
    }
    public static double GetDistance(double lon1,double lat1,double lon2, double lat2)
    {
       double radLat1 = rad(lat1);
       double radLat2 = rad(lat2);
       double a = radLat1 - radLat2;
       double b = rad(lon1) - rad(lon2);
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       s = s * EARTH_RADIUS;
       s = Math.round(s * 10000) / 10000;
       return s/1000;
    }

}


