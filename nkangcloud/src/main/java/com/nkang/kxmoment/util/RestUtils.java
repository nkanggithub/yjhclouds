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

import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
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
        System.getProperties().list(System.out);//??????????????????????  
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
       * ????Menu
      * @Title: deleteMenu
      * @Description: ????Menu
      * @param @return    ????
      * @return String    ????
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
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//????30?
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //????30? 
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
    
    
	public static String callMasterDataUpsert(OrgOtherPartySiteInstance opsi) throws UnsupportedEncodingException {
		String url = "http://shenan.duapp.com/masterdataupsert?";
		if(opsi != null){
			if(!StringUtils.isEmpty(opsi.getSiteInstanceId())){
				url = url + "siteInstanceId="+URLEncoder.encode(opsi.getSiteInstanceId(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getAddressType())){
				url = url + "&addressType="+URLEncoder.encode(opsi.getAddressType(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getBranchIndicator())){
				url = url + "&branchIndicator="+URLEncoder.encode(opsi.getBranchIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCharScriptCode())){
				url = url + "&charScriptCode="+URLEncoder.encode(opsi.getCharScriptCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCityRegion())){
				url = url + "&cityRegion="+URLEncoder.encode(opsi.getCityRegion(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCountOfEmployee())){
				url = url + "&countOfEmployee="+URLEncoder.encode(opsi.getCountOfEmployee(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCountryCode())){
				url = url + "&countryCode="+URLEncoder.encode(opsi.getCountryCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCountryName())){
				url = url + "&countryName="+URLEncoder.encode(opsi.getCountryName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCountryRegionCode())){
				url = url + "&countryRegionCode="+URLEncoder.encode(opsi.getCountryRegionCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getCountryRegionName())){
				url = url + "&countryRegionName="+URLEncoder.encode(opsi.getCountryRegionName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getDeletionIndicator())){
				url = url + "&deletionIndicator="+URLEncoder.encode(opsi.getDeletionIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getDomesticDuns())){
				url = url + "&domesticDuns="+URLEncoder.encode(opsi.getDomesticDuns(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getDuns())){
				url = url + "&duns="+URLEncoder.encode(opsi.getDuns(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getFocusAccountIndicator())){
				url = url + "&focusAccountIndicator="+URLEncoder.encode(opsi.getFocusAccountIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getGlobalAccountIndicator())){
				url = url + "&globalAccountIndicator="+URLEncoder.encode(opsi.getGlobalAccountIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getGlobalDuns())){
				url = url + "&globalDuns="+URLEncoder.encode(opsi.getGlobalDuns(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getGlobalDunsName())){
				url = url + "&globalDunsName="+URLEncoder.encode(opsi.getGlobalDunsName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getHeadDuns())){
				url = url + "&headDuns="+URLEncoder.encode(opsi.getHeadDuns(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getHeadDunsName())){
				url = url + "&headDunsName="+URLEncoder.encode(opsi.getHeadDunsName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getHyperscaleAccountIndicator())){
				url = url + "&HyperscaleAccountIndicator="+URLEncoder.encode(opsi.getHyperscaleAccountIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIncludePartnerOrgIndicator())){
				url = url + "&includePartnerOrgIndicator="+URLEncoder.encode(opsi.getIncludePartnerOrgIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIndicatorBags())){
				url = url + "&indicatorBags="+URLEncoder.encode(opsi.getIndicatorBags(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIndustrySegmentNames())){
				url = url + "&industrySegmentNames="+URLEncoder.encode(opsi.getIndustrySegmentNames(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIndustryVerticalNames())){
				url = url + "&industryVerticalNames="+URLEncoder.encode(opsi.getIndustryVerticalNames(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIsCompetitor())){
				url = url + "&isCompetitor="+URLEncoder.encode(opsi.getIsCompetitor(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIsGlobalAccount())){
				url = url + "&isGlobalAccount="+URLEncoder.encode(opsi.getIsGlobalAccount(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getIsOutOfBusiness())){
				url = url + "&isOutOfBusiness="+URLEncoder.encode(opsi.getIsOutOfBusiness(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getLanguageCode())){
				url = url + "&languageCode="+URLEncoder.encode(opsi.getLanguageCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getLatinCity())){
				url = url + "&latinCity="+URLEncoder.encode(opsi.getLatinCity(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getLatinStreet1LongName())){
				url = url + "&latinStreet1LongName="+URLEncoder.encode(opsi.getLatinStreet1LongName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getMailingSiteDuns())){
				url = url + "&mailingSiteDuns="+URLEncoder.encode(opsi.getMailingSiteDuns(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getNamedAccountIndicator())){
				url = url + "&namedAccountIndicator="+URLEncoder.encode(opsi.getNamedAccountIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getNonlatinCity())){
				url = url + "&nonlatinCity="+URLEncoder.encode(opsi.getNonlatinCity(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getNonlatinStreet1LongName())){
				url = url + "&nonlatinStreet1LongName="+URLEncoder.encode(opsi.getNonlatinStreet1LongName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOnlyPresaleCustomer())){
				url = url + "&onlyPresaleCustomer="+URLEncoder.encode(opsi.getOnlyPresaleCustomer(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationExtendedName())){
				url = url + "&organizationExtendedName="+URLEncoder.encode(opsi.getOrganizationExtendedName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationId())){
				url = url + "&organizationId="+URLEncoder.encode(opsi.getOrganizationId(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationLegalName())){
				url = url + "&organizationLegalName="+URLEncoder.encode(opsi.getOrganizationLegalName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationNonLatinExtendedName())){
				url = url + "&organizationNonLatinExtendedName="+URLEncoder.encode(opsi.getOrganizationNonLatinExtendedName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationNonLatinLegalName())){
				url = url + "&organizationNonLatinLegalName="+URLEncoder.encode(opsi.getOrganizationNonLatinLegalName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationNonLatinReportingName())){
				url = url + "&organizationNonLatinReportingName="+URLEncoder.encode(opsi.getOrganizationNonLatinReportingName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrganizationReportingName())){
				url = url + "&organizationReportingName="+URLEncoder.encode(opsi.getOrganizationReportingName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrgCountryCode())){
				url = url + "&orgCountryCode="+URLEncoder.encode(opsi.getOrgCountryCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getOrgCountryName())){
				url = url + "&orgCountryName="+URLEncoder.encode(opsi.getOrgCountryName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getParentCountryCode())){
				url = url + "&parentCountryCode="+URLEncoder.encode(opsi.getParentCountryCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getParentOrganizationId())){
				url = url + "&parentOrganizationId="+URLEncoder.encode(opsi.getParentOrganizationId(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getParentOrganizationName())){
				url = url + "&parentOrganizationName="+URLEncoder.encode(opsi.getParentOrganizationName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getPostalCode())){
				url = url + "&postalCode="+URLEncoder.encode(opsi.getPostalCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getPostalCode2())){
				url = url + "&postalCode2="+URLEncoder.encode(opsi.getPostalCode2(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getPresalesId())){
				url = url + "&presalesId="+URLEncoder.encode(opsi.getPresalesId(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getRad())){
				url = url + "&rad="+URLEncoder.encode(opsi.getRad(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getRadBags())){
				url = url + "&radBags="+URLEncoder.encode(opsi.getRadBags(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getRplStatusCode())){
				url = url + "&rplStatusCode="+URLEncoder.encode(opsi.getRplStatusCode(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getRplStatusTime())){
				url = url + "&rplStatusTime="+URLEncoder.encode(opsi.getRplStatusTime(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getSalesCoverageSegments())){
				url = url + "&salesCoverageSegments="+URLEncoder.encode(opsi.getSalesCoverageSegments(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getSiteDuns())){
				url = url + "&siteDuns="+URLEncoder.encode(opsi.getSiteDuns(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getSiteId())){
				url = url + "&siteId="+URLEncoder.encode(opsi.getSiteId(),"UTF-8");
			}

			
			
			
/*			if(!StringUtils.isEmpty(opsi.getSiteInstanceId())){
				url = url + "&siteInstanceId="+URLEncoder.encode(opsi.getSiteInstanceId(),"UTF-8");
			}*/
			
			
			
			
			if(!StringUtils.isEmpty(opsi.getSiteName())){
				url = url + "&siteName="+URLEncoder.encode(opsi.getSiteName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getSlsCrgSegmtNameBags())){
				url = url + "&slsCrgSegmtNameBags="+URLEncoder.encode(opsi.getSlsCrgSegmtNameBags(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getState())){
				url = url + "&state="+URLEncoder.encode(opsi.getState(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getStreetAddress1())){
				url = url + "&streetAddress1="+URLEncoder.encode(opsi.getStreetAddress1(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getStreetAddress2())){
				url = url + "&streetAddress2="+URLEncoder.encode(opsi.getStreetAddress2(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getStreetAddress3())){
				url = url + "&streetAddress3="+URLEncoder.encode(opsi.getStreetAddress3(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTargetSegmentNames())){
				url = url + "&targetSegmentNames="+URLEncoder.encode(opsi.getTargetSegmentNames(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTargetSubSegmentNames())){
				url = url + "&targetSubSegmentNames="+URLEncoder.encode(opsi.getTargetSubSegmentNames(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTaxIds())){
				url = url + "&taxIds="+URLEncoder.encode(opsi.getTaxIds(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTgtSegmtNameBags())){
				url = url + "&tgtSegmtNameBags="+URLEncoder.encode(opsi.getTgtSegmtNameBags(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTopAccountIndicator())){
				url = url + "&topAccountIndicator="+URLEncoder.encode(opsi.getTopAccountIndicator(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTopParentOrganizationId())){
				url = url + "&topParentOrganizationId="+URLEncoder.encode(opsi.getTopParentOrganizationId(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getTopParentOrganizationName())){
				url = url + "&topParentOrganizationName="+URLEncoder.encode(opsi.getTopParentOrganizationName(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getWorldRegion())){
				url = url + "&worldRegion="+URLEncoder.encode(opsi.getWorldRegion(),"UTF-8");
			}
			if(!StringUtils.isEmpty(opsi.getWorldRegionPath())){
				url = url + "&worldRegionPath="+URLEncoder.encode(opsi.getWorldRegionPath(),"UTF-8");
			}
			
		}
		try {
	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("PUT"); //must be get request
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("http.proxyHost", "web-proxy.atl.hp.com");  
	           System.setProperty("http.proxyPort", "8080");  
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
	           http.connect();
	           InputStream is = http.getInputStream();
	           int size = is.available();
	           byte[] jsonBytes = new byte[size];
	           is.read(jsonBytes);
	           String message = new String(jsonBytes, "UTF-8");
	           //JSONObject demoJson = new JSONObject(message);
	           is.close();
	           System.out.println("ok http ---------" + url);
	       } catch (Exception e) {
	    	   System.out.println("error http ---------" + e.getMessage());
	    	   return "failed";
	       } 
		return "success";
}

}


