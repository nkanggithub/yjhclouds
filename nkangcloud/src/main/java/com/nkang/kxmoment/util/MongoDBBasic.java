package com.nkang.kxmoment.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.nkang.kxmoment.baseobject.ClientInformation;
import com.nkang.kxmoment.baseobject.ExtendedOpportunity;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.MongoClientCollection;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.Teamer;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.baseobject.WeChatUser;

public class MongoDBBasic { 
	private static Logger log = Logger.getLogger(MongoDBBasic.class);
	private static DB mongoDB = null;
	private static String collectionMasterDataName = "masterdata";
	private static String access_key = "Access_Key";
	private static String wechat_user = "Wechat_User";
	private static String client_pool = "ClientPool";
	private static String wechat_comments = "Wechat_Comments";

	private static DB getMongoDB(){
		if(mongoDB != null){
			return mongoDB;
		}
		MongoClientCollection mongoClientCollection = new MongoClientCollection();
		ResourceBundle resourceBundle=ResourceBundle.getBundle("database_info");
		String databaseName=resourceBundle.getString("databaseName");
		String hostm = resourceBundle.getString("hostm");
		String portm = resourceBundle.getString("portm");
		String usrname=resourceBundle.getString("usrname");
		String passwrd=resourceBundle.getString("passwrd");
        String serverName = hostm + ":" + portm;

        MongoClient mongoClient = new MongoClient(
        		new ServerAddress(serverName),
        		Arrays.asList(MongoCredential.createMongoCRCredential(usrname, databaseName,passwrd.toCharArray())),
        		new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
        mongoClientCollection.setMongoClient(mongoClient);

        mongoDB = mongoClient.getDB(databaseName);
        mongoDB.addUser(usrname, passwrd.toCharArray());

        return mongoDB;
	}
	
	public static String getValidAccessKey(){
		String AccessKey = QueryAccessKey();
		if(AccessKey == null){
			AccessKey = RestUtils.getAccessKey();
		}	
		return AccessKey;
	}
	
	public static void updateAccessKey(String key, String expiresIn){
		try{
			mongoDB = getMongoDB();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			DBObject update = new BasicDBObject();
			update.put("ExpiresIn", Integer.valueOf(expiresIn));
			update.put("LastUpdated", DateUtil.timestamp2Str(cursqlTS));
			update.put("AKey", key);
			update.put("ID", "1");
			mongoDB.getCollection(access_key).update(new BasicDBObject().append("ID", "1"), update, true, false);
		}
		catch(Exception e){
			log.info("updateAccessKey--" + e.getMessage());
		}
	}
	
	public static String QueryAccessKey(){
		String validKey = null;
		mongoDB = getMongoDB();
		java.sql.Timestamp sqlTS = null;;
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
	    try{
			DBObject query = new BasicDBObject();
			query.put("ID", "1");
			validKey =mongoDB.getCollection(access_key).findOne(query).get("AKey").toString();
			String timehere = mongoDB.getCollection(access_key).findOne(query).get("LastUpdated").toString();
			sqlTS = DateUtil.str2Timestamp(timehere);
	    	int diff = (int) ((cursqlTS.getTime() - sqlTS.getTime())/1000);
	    	if((7200 - diff) > 0){
	    		//log.info(diff + " is less than 7200. so use original valid Key as : "+ validKey);
	    	}
	    	else{
	    		log.info(diff + " is close to 7200. and is to re-generate the key");
	    		validKey = null;
	    	}
	    	
	    }
		catch(Exception e){
			log.info("QueryAccessKey--" + e.getMessage());
		}
	    return validKey;
	}
	
	@SuppressWarnings("null")
	public static WeChatUser queryWeChatUser(String OpenID){
		mongoDB = getMongoDB();
		WeChatUser ret = null;
	    try{
	    	DBObject query = new BasicDBObject();
	    	query.put("OpenID", OpenID);
	    	DBObject queryresult = mongoDB.getCollection(wechat_user).findOne(query);
	    	if(queryresult != null){
		    	ret.setLat(queryresult.get("CurLAT").toString());
		    	ret.setLng(queryresult.get("CurLNG").toString());
		    	ret.setOpenid(OpenID);
	    	}
	    }
		catch(Exception e){
			log.info("queryWeChatUser--" + e.getMessage());
		}
		return ret;
	}
	public static boolean queryWeChatUserTelephone(String telephone){
		mongoDB = getMongoDB();
	    try{
	    	DBObject query = new BasicDBObject();
	    	query.put("telephone", telephone);
	    	DBObject queryresult = mongoDB.getCollection(wechat_user).findOne(query);
	    	if(queryresult != null){
		    	return true;
	    	}
	    }
		catch(Exception e){
			log.info("queryTelephone--" + e.getMessage());
		}
		return false;
	}
	
	public static boolean queryWeChatUserEmail(String email){
		mongoDB = getMongoDB();
	    try{
	    	DBObject query = new BasicDBObject();
	    	query.put("email", email);
	    	DBObject queryresult = mongoDB.getCollection(wechat_user).findOne(query);
	    	if(queryresult != null){
	    		return true;
	    	}
	    }
		catch(Exception e){
			log.info("queryEmail--" + e.getMessage());
		}
		return false;
	}
	public static boolean createUser(WeChatUser wcu){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		Boolean ret = false;
	    try{
	    	DBObject insert = new BasicDBObject();
	    	insert.put("OpenID", wcu.getOpenid());
	    	insert.put("HeadUrl", wcu.getHeadimgurl());
	    	insert.put("NickName", wcu.getNickname());
	    	insert.put("Created", DateUtil.timestamp2Str(cursqlTS));
	    	insert.put("FormatAddress", "");
	    	insert.put("CurLAT", "");
	    	insert.put("CurLNG", "");
	    	insert.put("LastUpdatedDate", DateUtil.timestamp2Str(cursqlTS));
			mongoDB.getCollection(wechat_user).insert(insert);
			ret = true;
	    }
		catch(Exception e){
			log.info("createUser--" + e.getMessage());
		}
		return ret;
	}
	
	public static boolean registerUser(Teamer teamer){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		Boolean ret = false;
		String OpenID =  teamer.getOpenid();
	    try{
	    	DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", OpenID));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		DBObject dbo = new BasicDBObject();
            		dbo.put("Teamer.openid", teamer.getOpenid()); 
            		dbo.put("Teamer.groupid", teamer.getGroupid()); 
            		dbo.put("Teamer.realName", teamer.getRealName()); 
        			dbo.put("Teamer.email", teamer.getEmail());
        			dbo.put("Teamer.phone", teamer.getPhone());
        			dbo.put("Teamer.role", teamer.getRole());
        			dbo.put("Teamer.selfIntro", teamer.getSelfIntro());
        			dbo.put("Teamer.suppovisor", teamer.getSuppovisor()); 
        			Object teamer2 = o.get("Teamer");
        			if(teamer2 == null){
            			dbo.put("Teamer.registerDate", teamer.getRegisterDate());
        			}
        			BasicDBObject doc = new BasicDBObject();  
        			doc.put("$set", dbo);  
        			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", OpenID),doc);
            		ret = true;
            	}
            }
	    }
		catch(Exception e){
			log.info("registerUser--" + e.getMessage());
		}
		return ret;
	}
	
	public static boolean removeUser(String OpenID){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		Boolean ret = false;
	    try{
			DBObject removeQuery = new BasicDBObject();
			removeQuery.put("OpenID", OpenID);
			mongoDB.getCollection(wechat_user).remove(removeQuery);
			ret = true;
	    }
		catch(Exception e){
			log.info("removeUser--" + e.getMessage());
		}
		return ret;
	}
	
	public static boolean removeOrgSiteInstance(String source,String target, String cmd){
		mongoDB = getMongoDB();
		Boolean ret = false;
		try{
			if(StringUtils.isEqual(cmd, "rm-")){
				DBObject removeQuery = new BasicDBObject();
				removeQuery.put("state", source);
				mongoDB.getCollection(collectionMasterDataName).remove(removeQuery);
				ret = true;
			}
			else if(StringUtils.isEqual(cmd, "mf-")){
				for(int i = 0; i < 1000 ; i++){
					DBObject findQuery = new BasicDBObject();
					findQuery.put("state", source);
					DBObject updateQuery = new BasicDBObject();
					updateQuery.put("state", target);
					mongoDB.getCollection(collectionMasterDataName).findAndModify(findQuery, updateQuery);
				}
				ret = true;
			}
	    }
		catch(Exception e){
			log.info("removeOrgSiteInstance--" + e.getMessage());
		}
		return ret;
	}
	
	
	public static boolean updateUser(String OpenID, String Lat, String Lng, WeChatUser wcu){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		Boolean ret = false;
	    try{
	    	List<DBObject> arrayHistdbo = new ArrayList<DBObject>();
	    	DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", OpenID));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		BasicDBList hist = (BasicDBList) o.get("VisitHistory");
            		if(hist != null){
                		Object[] visitHistory = hist.toArray();
                		for(Object dbobj : visitHistory){
                			if(dbobj instanceof DBObject){
                				arrayHistdbo.add((DBObject)dbobj);
                			}
                		}
            		}
            	}

            	String faddr = RestUtils.getUserCurLocWithLatLng(Lat, Lng);
                BasicDBObject doc = new BasicDBObject();  
    	    	DBObject update = new BasicDBObject();
//    	    	update.put("OpenID", wcu.getOpenid());
//    	    	update.put("HeadUrl", wcu.getHeadimgurl());
//    	    	update.put("NickName", wcu.getNickname());
//    	    	update.put("Created", DateUtil.timestamp2Str(cursqlTS));
    	    	update.put("FormatAddress", faddr);
    	    	update.put("CurLAT", Lat);
    	    	update.put("CurLNG", Lng);
    	    	update.put("LastUpdatedDate", DateUtil.timestamp2Str(cursqlTS));
    	    	DBObject innerInsert = new BasicDBObject();
    	    	innerInsert.put("lat", Lat);
    	    	innerInsert.put("lng", Lng);
    	    	innerInsert.put("visitDate", DateUtil.timestamp2Str(cursqlTS));
    	    	innerInsert.put("FAddr", faddr);
    	    	arrayHistdbo.add(innerInsert);
    	    	update.put("VisitHistory", arrayHistdbo);
    	    	doc.put("$set", update);  
    			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", OpenID), doc);
            }
            ret = true;
	    }
		catch(Exception e){
			log.info("updateUser--" + e.getMessage());
		}
		return ret;
	}
	
	public static String mongoDBInsert(OrgOtherPartySiteInstance opsi){
		String ret = "error";
		mongoDB = getMongoDB();
			try {
				if(mongoDB == null){
					mongoDB = getMongoDB();
				}
				DBObject dbo = new BasicDBObject();
				
				dbo.put("amid2", opsi.getAmid2());
				dbo.put("branchIndicator", opsi.getBranchIndicator());
				dbo.put("charScriptCode", opsi.getCharScriptCode());
				dbo.put("cityRegion", opsi.getCityRegion());
				dbo.put("countOfEmployee", opsi.getCountOfEmployee());
				dbo.put("countryCode", opsi.getCountryCode());
				dbo.put("countryName", opsi.getCountryName());
				dbo.put("countryRegionCode", opsi.getCountryRegionCode());
				dbo.put("countryRegionName", opsi.getCountryRegionName());
				dbo.put("deletionIndicator", opsi.getDeletionIndicator());
				dbo.put("domesticDuns", opsi.getDomesticDuns());
				dbo.put("duns", opsi.getDuns());
				dbo.put("focusAccountIndicator", opsi.getFocusAccountIndicator());
				dbo.put("globalAccountIndicator", opsi.getGlobalAccountIndicator());
				dbo.put("globalDuns", opsi.getGlobalDuns());
				dbo.put("globalDunsName", opsi.getGlobalDunsName());
				dbo.put("headDuns", opsi.getHeadDuns());
				dbo.put("headDunsName", opsi.getHeadDunsName());
				dbo.put("hyperscaleAccountIndicator", opsi.getHyperscaleAccountIndicator());
				dbo.put("includePartnerOrgIndicator", opsi.getIncludePartnerOrgIndicator());
				dbo.put("indicatorBags", opsi.getIndicatorBags());
				dbo.put("industrySegmentNames", opsi.getIndustrySegmentNames());
				dbo.put("industryVerticalNames", opsi.getIndustryVerticalNames());
				dbo.put("isCompetitor", opsi.getIsCompetitor());
				dbo.put("isGlobalAccount", opsi.getIsGlobalAccount());
				dbo.put("isOutOfBusiness", opsi.getIsOutOfBusiness());
				dbo.put("languageCode", opsi.getLanguageCode());
				dbo.put("latinCity", opsi.getLatinCity());
				dbo.put("latinStreet1LongName", opsi.getLatinStreet1LongName());
				dbo.put("mailingSiteDuns", opsi.getMailingSiteDuns());
				dbo.put("namedAccountIndicator", opsi.getNamedAccountIndicator());
				dbo.put("nonlatinCity", opsi.getNonlatinCity());
				dbo.put("nonlatinStreet1LongName", opsi.getNonlatinStreet1LongName());
				dbo.put("onlyPresaleCustomer", opsi.getOnlyPresaleCustomer());
				dbo.put("organizationExtendedName", opsi.getOrganizationExtendedName());
				dbo.put("organizationId", opsi.getOrganizationId());
				dbo.put("organizationLegalName", opsi.getOrganizationLegalName());
				dbo.put("organizationNonLatinExtendedName", opsi.getOrganizationNonLatinExtendedName());
				dbo.put("organizationNonLatinLegalName", opsi.getOrganizationNonLatinLegalName());
				dbo.put("organizationNonLatinReportingName", opsi.getOrganizationNonLatinReportingName());
				dbo.put("organizationReportingName", opsi.getOrganizationReportingName());
				dbo.put("orgCountryCode", opsi.getOrgCountryCode());
				dbo.put("orgCountryName", opsi.getOrgCountryName());
				dbo.put("parentCountryCode", opsi.getParentCountryCode());
				dbo.put("parentOrganizationId", opsi.getParentOrganizationId());
				dbo.put("parentOrganizationName", opsi.getParentOrganizationName());
				dbo.put("postalCode", opsi.getPostalCode());
				dbo.put("postalCode2", opsi.getPostalCode2());
				dbo.put("presalesId", opsi.getPresalesId());
				dbo.put("rad", opsi.getRad());
				dbo.put("radBags", opsi.getRadBags());
				dbo.put("rplStatusCode", opsi.getRplStatusCode());
				//opsi.setReturnPartnerFlag(radBagsreturnPartnerFlag);
				dbo.put("rplStatusTime", opsi.getRplStatusTime());
				dbo.put("salesCoverageSegments", opsi.getSalesCoverageSegments());
				dbo.put("siteDuns", opsi.getSiteDuns());
				dbo.put("siteId", opsi.getSiteId());
				dbo.put("siteInstanceId", opsi.getSiteInstanceId());
				dbo.put("siteName", opsi.getSiteName());
				dbo.put("slsCrgSegmtNameBags", opsi.getSlsCrgSegmtNameBags());
				dbo.put("state", opsi.getState());
				dbo.put("streetAddress1", opsi.getStreetAddress1());
				dbo.put("streetAddress2", opsi.getStreetAddress2());
				dbo.put("streetAddress3", opsi.getStreetAddress3());
				dbo.put("targetSegmentNames", opsi.getTargetSegmentNames());
				dbo.put("targetSubSegmentNames", opsi.getTargetSubSegmentNames());
				dbo.put("taxIds", opsi.getTaxIds());
				dbo.put("tgtSegmtNameBags", opsi.getTgtSegmtNameBags());
				dbo.put("topAccountIndicator", opsi.getTopAccountIndicator());
				dbo.put("topParentOrganizationId", opsi.getTopParentOrganizationId());
				dbo.put("topParentOrganizationName", opsi.getTopParentOrganizationName());
				dbo.put("worldRegion", opsi.getWorldRegion());
				dbo.put("worldRegionPath", opsi.getWorldRegionPath());
				dbo.put("lat", opsi.getLat());
				dbo.put("lng", opsi.getLng());
				dbo.put("qualityGrade", opsi.getQualityGrade());
				mongoDB.getCollection(collectionMasterDataName).insert(dbo);
				ret = "ok";
			} 
			catch(Exception e){
				log.info("mongoDBInsert--" + e.getMessage());
			}
		return ret;
	}

	
	@SuppressWarnings("unchecked")
	public static List<DBObject> getDistinctSubjectArea(String fieldname) {
		List<DBObject> result = null;
		try{
			mongoDB = getMongoDB();
	        if (null != mongoDB.getCollection(collectionMasterDataName)) {
	        	result = mongoDB.getCollection(collectionMasterDataName).distinct(fieldname);
	        }
		}
		catch(Exception e){
			log.info("getDistinctSubjectArea--" + e.getMessage());
		}
        return result;
	}



	public static GeoLocation getDBUserGeoInfo(String OpenID){
		mongoDB = getMongoDB();
		GeoLocation loc = new GeoLocation();
	    try{
	    	DBObject query = new BasicDBObject();
	    	query.put("OpenID", OpenID);
	    	DBObject result = mongoDB.getCollection(wechat_user).findOne(query);
    		loc.setLAT(result.get("CurLAT").toString());
    		loc.setLNG(result.get("CurLNG").toString());
    		loc.setFAddr(result.get("FormatAddress").toString());
	    }
		catch(Exception e){
			log.info("getDBUserGeoInfo--" + e.getMessage());
		}
		return loc;
	}
	
	public static boolean InsertCommentsFromVisitor(String OpenID, String comments){
		mongoDB = getMongoDB();
		boolean ret = false;
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime());
	    try{
	    	DBObject insert = new BasicDBObject();
	    	insert.put("OpenID", OpenID);
	    	insert.put("CreatedDate", DateUtil.timestamp2Str(cursqlTS));
	    	insert.put("Comments", comments);
	    	DBCollection result = mongoDB.getCollection(wechat_comments);
	    	result.insert(insert);
	    	ret = true;
	    }
		catch(Exception e){
			log.info("InsertCommentsFromVisitor--" + e.getMessage());
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getFilterSegmentArea(String state){
		mongoDB = getMongoDB();
		List<String> listOfSegmentArea = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;
	    try{
	    	//results =  mongoDB.getCollection(collectionMasterDataName).distinct("industrySegmentNames");
	    	BasicDBObject query = new BasicDBObject();
/*			if(state != "" && state != null && state.toLowerCase() != "null" ){
				Pattern pattern3 = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE);
				query.put("state", pattern3);
			}*/
			query.put("state", state);
	    	results =  mongoDB.getCollection(collectionMasterDataName).distinct("industrySegmentNames", query);
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			String tmp =  (String) results.get(i);
	    			tmp =  tmp.trim();
	    			tmp =  tmp.substring(1,tmp.length()-1);
	    			String [] d = tmp.split(",");
	    			for(int j = 0; j < d.length; j ++){
	    				if(!listOfSegmentArea.contains(d[j].trim())){
	    					listOfSegmentArea.add(d[j].trim());
	    				}
		        	}
	    		}
	    	}
	    }
		catch(Exception e){
			log.info("getFilterSegmentArea--" + e.getMessage());
		}
	    return listOfSegmentArea;
	}
	
	public static List<String> getFilterRegionFromMongo(String state){
		mongoDB = getMongoDB();
		List<String> listOfRegion = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;
	    try{
	    	DBObject dbquery = new BasicDBObject();  
			if(state != "" && state != null && state != "null" ){
				Pattern pattern = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE); 
				dbquery.put("state", pattern);
			}
					
/*	    	DBObject query1 = new BasicDBObject("state", "重庆市");  
	    	DBObject query2 = new BasicDBObject("state", "重庆");     
	    	BasicDBList or = new BasicDBList();
	    	or.add(query1);
	    	or.add(query2);
	    	DBObject query = new BasicDBObject("$or", or);*/

	    	results = mongoDB.getCollection(collectionMasterDataName).distinct("cityRegion", dbquery);
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			listOfRegion.add((String) results.get(i));
	    		}
	    	}
	    }
		catch(Exception e){
			log.info("getFilterRegionFromMongo--" + e.getMessage());
		}
	    return listOfRegion;
	}

	public static List<String> getFilterNonLatinCitiesFromMongo(String state){
		mongoDB = getMongoDB();
		List<String> listOfNonLatinCities = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;
	    try{
	    	DBObject dbquery = new BasicDBObject();  
			if(state != "" && state != null && state != "null" ){
				Pattern pattern = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE); 
				dbquery.put("state", pattern);
			}
			
/*	    	DBObject query1 = new BasicDBObject("state", state);  
	    	DBObject query2 = new BasicDBObject("state", "重庆");     
	    	BasicDBList or = new BasicDBList();
	    	or.add(query1);
	    	or.add(query2);
	    	DBObject query = new BasicDBObject("$or", or);*/

	        if (StringUtils.isLatinString(state)) {
	        	results = mongoDB.getCollection(collectionMasterDataName).distinct("latinCity", dbquery);
	        }else{
	        	results = mongoDB.getCollection(collectionMasterDataName).distinct("nonlatinCity", dbquery);
	        }
	        
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			String tmpStr = (String) results.get(i);
	        	    if(tmpStr.contains(state)){
	        		   tmpStr = tmpStr.replaceAll("\\s+","");
	        		   tmpStr = tmpStr.replaceAll(state, "");
	        	    }
	        	    if(tmpStr != null && !tmpStr.isEmpty() && tmpStr!="."){
	        	    	if(!listOfNonLatinCities.contains(tmpStr.toUpperCase())){
	        	    		listOfNonLatinCities.add(tmpStr.toUpperCase());
	        	    	}
	        	    }
	    		}
	    	}
	    }
		catch(Exception e){
			log.info("getFilterNonLatinCitiesFromMongo--" + e.getMessage());
		}
	    return listOfNonLatinCities;
	}
	
	public static List<String> getFilterStateFromMongo(){
		mongoDB = getMongoDB();
		List<String> listOfstates = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;

	    try{
	    	results = mongoDB.getCollection(collectionMasterDataName).distinct("state");
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			listOfstates.add((String) results.get(i));
	    		}
	    	}
	    }
		catch(Exception e){
			log.info("getFilterStateFromMongo--" + e.getMessage());
		}
	    return listOfstates;
	}
	
	public static String getFilterCountOnCriteriaFromMongo(String industrySegmentNames, String nonlatinCity, String state, String cityRegion){
		mongoDB = getMongoDB();
		String ret = "0";
		DBObject query  = new BasicDBObject();
		if(industrySegmentNames != "" && industrySegmentNames != null && industrySegmentNames != "null" ){
			Pattern pattern = Pattern.compile("^.*" + industrySegmentNames + ".*$", Pattern.CASE_INSENSITIVE); 
			query.put("industrySegmentNames", pattern);
			//query.put("industrySegmentNames", industrySegmentNames);
		}
		if(nonlatinCity != "" && nonlatinCity != null && nonlatinCity.toLowerCase() != "null" ){
			Pattern pattern2 = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("nonlatinCity", pattern2);
		}
		if(state != "" && state != null && state.toLowerCase() != "null" ){
			Pattern pattern3 = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("state", pattern3);
		}
		if(cityRegion != "" && cityRegion != null && cityRegion.toLowerCase() != "null" ){
			Pattern pattern4 = Pattern.compile("^.*" + cityRegion + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("cityRegion", pattern4);
		}
	    try{
	    	//Pattern pattern = Pattern.compile("^.*name8.*$", Pattern.CASE_INSENSITIVE);
	    	ret = String.valueOf( mongoDB.getCollection(collectionMasterDataName).count(query));
	    }
		catch(Exception e){
			log.info("getFilterCountOnCriteriaFromMongo--" + e.getMessage());
		}
	    return ret;
	}
	
	/*
	 * author chang-zheng
	 */
	
	public static Map<String,String> CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(List<String> listOfSegmentArea, String nonlatinCity, String state, String cityRegion){
		mongoDB = getMongoDB();
		Map<String,String> radarmap = new  HashMap<String,String>();
		
		for(String area : listOfSegmentArea){
			String ret = "0";
			DBObject query  = new BasicDBObject();
			if(!StringUtils.isEmpty(area)){
				Pattern pattern = Pattern.compile("^.*" + area + ".*$", Pattern.CASE_INSENSITIVE); 
				query.put("industrySegmentNames", pattern);
				//query.put("industrySegmentNames", area);
				
				if(nonlatinCity != "" && nonlatinCity != null && nonlatinCity.toLowerCase() != "null" ){
					Pattern pattern2 = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
					query.put("nonlatinCity", pattern2);
				}
				if(state != "" && state != null && state.toLowerCase() != "null" ){
					Pattern pattern3 = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE);
					query.put("state", pattern3);
				}
				if(cityRegion != "" && cityRegion != null && cityRegion.toLowerCase() != "null" ){
					Pattern pattern4 = Pattern.compile("^.*" + cityRegion + ".*$", Pattern.CASE_INSENSITIVE);
					query.put("cityRegion", pattern4);
				}
				 try{
				    	//Pattern pattern = Pattern.compile("^.*name8.*$", Pattern.CASE_INSENSITIVE);
				    	ret = String.valueOf( mongoDB.getCollection(collectionMasterDataName).count(query));
				    }
					catch(Exception e){
						log.info("getFilterCountOnCriteriaFromMongo--" + e.getMessage());
					}
				
			}
			 radarmap.put(area, ret);
		}
		
	    return radarmap;
	}
	
	
	@SuppressWarnings("unused")
	public static String getFilterTotalOPSIFromMongo(String stateProvince, String nonlatinCity, String cityRegion){
		mongoDB = getMongoDB();
		String ret = "0";
	    try{
	    	DBObject query = new BasicDBObject();
	    	if(stateProvince != "" && stateProvince!= null && stateProvince.toLowerCase()!= "null"){
	    		Pattern patternst = Pattern.compile("^.*" + stateProvince + ".*$", Pattern.CASE_INSENSITIVE);
				query.put("state", patternst);
			}
			if(nonlatinCity != "" && nonlatinCity!= null && nonlatinCity.toLowerCase()!= "null"){
				Pattern patternstnc = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
				query.put("nonlatinCity", patternstnc);
			}
			if(cityRegion != "" && cityRegion!= null && cityRegion.toLowerCase()!= "null"){
				Pattern patterncr = Pattern.compile("^.*" + cityRegion + ".*$", Pattern.CASE_INSENSITIVE);
				query.put("cityRegion", patterncr);
			}
			
			if(query != null){
				ret = String.valueOf(mongoDB.getCollection(collectionMasterDataName).find(query).count()) ;
			}
			else{
				ret = String.valueOf(mongoDB.getCollection(collectionMasterDataName).find().count()) ;
			}
	    	
	    }
		catch(Exception e){
			log.info("getFilterTotalOPSIFromMongo--" + e.getMessage());
		}
	    return ret;
	}
	
	public static MdmDataQualityView getDataQualityReport(String stateProvince, String nonlatinCity, String cityRegion){
		MdmDataQualityView mqv = new MdmDataQualityView();
		mongoDB = getMongoDB();
		try{
			// competitor
			int cnt_competitor = 0;
			BasicDBObject query_competitor = new BasicDBObject();
			query_competitor.put("isCompetitor", "true");
			if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
				query_competitor.put("state", stateProvince);
			}
			if(nonlatinCity != "" && nonlatinCity!= null && nonlatinCity.toUpperCase()!= "NULL"){
				query_competitor.put("nonlatinCity", nonlatinCity);
			}
			if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
				query_competitor.put("cityRegion", cityRegion);
			}
			cnt_competitor = mongoDB.getCollection(collectionMasterDataName).find(query_competitor).count();
			// partner
			int cnt_partner = 0;
			BasicDBObject query_partner = new BasicDBObject();
			query_partner.put("includePartnerOrgIndicator", "true");
			if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
				query_partner.put("state", stateProvince);
			}
			if(nonlatinCity != "" && nonlatinCity!= null && nonlatinCity.toUpperCase()!= "NULL"){
				query_partner.put("nonlatinCity", nonlatinCity);
			}
			if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
				query_partner.put("cityRegion", cityRegion);
			}
			cnt_partner = mongoDB.getCollection(collectionMasterDataName).find(query_partner).count();
			// customer
			int cnt_customer = 0;
			BasicDBObject query_customer = new BasicDBObject();
			query_customer.put("onlyPresaleCustomer", "true");
			if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
				query_customer.put("state", stateProvince);
			}
			if(nonlatinCity != "" && nonlatinCity!= null && nonlatinCity.toUpperCase()!= "NULL"){
				query_customer.put("nonlatinCity", nonlatinCity);
			}
			if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
				query_customer.put("cityRegion", cityRegion);
			}
			cnt_customer = mongoDB.getCollection(collectionMasterDataName).find(query_customer).count();
			
			// potential leads
			int cnt_lead = 0;
			BasicDBObject query_leads = new BasicDBObject();
			query_leads.put("onlyPresaleCustomer", "false");
			query_leads.put("includePartnerOrgIndicator", "false");
			query_leads.put("isCompetitor", "false");
			if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
				query_leads.put("state", stateProvince);
			}
			if(nonlatinCity != "" && nonlatinCity!= null && nonlatinCity.toUpperCase()!= "NULL"){
				query_leads.put("nonlatinCity", nonlatinCity);
			}
			if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
				query_leads.put("cityRegion", cityRegion);
			}
			cnt_lead = mongoDB.getCollection(collectionMasterDataName).find(query_leads).count();
			
			mqv.setNumberOfCompetitor(cnt_competitor);
			mqv.setNumberOfPartner(cnt_partner);
			mqv.setNumberOfCustomer(cnt_customer);
			mqv.setPercents("0.68");
			mqv.setNumberOfLeads(cnt_lead);
			mqv.setNumberOfOppt(cnt_lead);
			mqv.setNumberOfEmptyCityArea(1000);
			mqv.setNumberOfThreeGrade(2000);
			mqv.setNumberOfNonGeo(2000);

		}
		catch(Exception e){
			log.info("getDataQualityReport--" + e.getMessage());
		}
		
		return mqv;
	}
	
	/*
	 * author  chang-zheng
	 * purpose to get userState list .eg 上海市，重庆市
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getAllStates(String countryCode){
		mongoDB = getMongoDB();
		BasicDBObject query_State = new BasicDBObject();
		query_State.put("countryCode", countryCode);
		List<String> lst = mongoDB.getCollection(collectionMasterDataName).distinct("state", query_State);
		List<String> lstRet = new ArrayList<String>();
		for(String i:lst ){
			if(!StringUtils.isEmpty(i)){
				lstRet.add(i);
			}
		}
		return lstRet;
	}
	
	/*
	 * author  chang-zheng
	 */
	public static  Map<String, MdmDataQualityView> getDataQualityReport(String stateProvince, List<String> ListnonlatinCity, String cityRegion){
		Map<String, MdmDataQualityView> map = new HashMap<String, MdmDataQualityView>();
		mongoDB = getMongoDB();
		for(String str : ListnonlatinCity){
			MdmDataQualityView tmpmqv = new MdmDataQualityView();
			tmpmqv.setPercents("0.68");
			tmpmqv.setNumberOfEmptyCityArea(1000);
			tmpmqv.setNumberOfThreeGrade(2000);
			tmpmqv.setNumberOfNonGeo(2000);
			
			try{
				// competitor
				int cnt_competitor = 0;
				BasicDBObject query_competitor = new BasicDBObject();
				query_competitor.put("isCompetitor", "true");
				if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
					query_competitor.put("state", stateProvince);
				}
				if(str != "" && str!= null && str.toUpperCase()!= "NULL"){
					if(StringUtils.isLatinString(str)){
						query_competitor.put("latinCity", str);
					}
					else{
						query_competitor.put("nonlatinCity", str);
					}
				}
				if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
					Pattern patternst = Pattern.compile("^.*" + cityRegion + ".*$", Pattern.CASE_INSENSITIVE);
					query_competitor.put("cityRegion", patternst);
				}
				cnt_competitor = mongoDB.getCollection(collectionMasterDataName).find(query_competitor).count();
				// partner
				int cnt_partner = 0;
				BasicDBObject query_partner = new BasicDBObject();
				query_partner.put("includePartnerOrgIndicator", "true");
				if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
					query_partner.put("state", stateProvince);
				}
				if(str != "" && str!= null && str.toUpperCase()!= "NULL"){
					if(StringUtils.isLatinString(str)){
						query_partner.put("latinCity", str);
					}
					else{
						query_partner.put("nonlatinCity", str);
					}
				}
				if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
					query_partner.put("cityRegion", cityRegion);
				}
				cnt_partner = mongoDB.getCollection(collectionMasterDataName).find(query_partner).count();
				// customer
				int cnt_customer = 0;
				BasicDBObject query_customer = new BasicDBObject();
				query_customer.put("onlyPresaleCustomer", "true");
				if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
					query_customer.put("state", stateProvince);
				}
				if(str != "" && str!= null && str.toUpperCase()!= "NULL"){
					if(StringUtils.isLatinString(str)){
						query_customer.put("latinCity", str);
					}
					else{
						query_customer.put("nonlatinCity", str);
					}
				}
				if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
					query_customer.put("cityRegion", cityRegion);
				}
				cnt_customer = mongoDB.getCollection(collectionMasterDataName).find(query_customer).count();
				
				// potential leads
				int cnt_lead = 0;
				BasicDBObject query_leads = new BasicDBObject();
				query_leads.put("onlyPresaleCustomer", "false");
				query_leads.put("includePartnerOrgIndicator", "false");
				query_leads.put("isCompetitor", "false");
				if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
					query_leads.put("state", stateProvince);
				}
				if(str != "" && str!= null && str.toUpperCase()!= "NULL"){
					if(StringUtils.isLatinString(str)){
						query_leads.put("latinCity", str);
					}
					else{
						query_leads.put("nonlatinCity", str);
					}
				}
				if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
					query_leads.put("cityRegion", cityRegion);
				}
				cnt_lead = mongoDB.getCollection(collectionMasterDataName).find(query_leads).count();
				
				tmpmqv.setNumberOfCompetitor(cnt_competitor);
				tmpmqv.setNumberOfPartner(cnt_partner);
				tmpmqv.setNumberOfCustomer(cnt_customer);
				tmpmqv.setNumberOfLeads(cnt_lead);
				tmpmqv.setNumberOfOppt(cnt_lead);
				
				map.put(str, tmpmqv);
			}
			catch(Exception e){
				log.info("getDataQualityReport--" + e.getMessage());
			}
			
			
		}
		return map;
	}
	
	public static List<String> getFilterOnIndustryByAggregateFromMongo(){
		List<String> ret = new ArrayList<String>();
		mongoDB = getMongoDB();
		ret.add("--connected---");
		try{
			DBObject fields = new BasicDBObject("industrySegmentNames", 1);
			fields.put("industrySegmentNames", 1);
			fields.put("_id", 0);
			DBObject project = new BasicDBObject("$project", fields);
			ret.add("--projecting completed---");
			DBObject groupFields = new BasicDBObject("_id", "$industrySegmentNames");
			groupFields.put("count", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupFields);
			ret.add("--group completed---");
			AggregationOutput aop = mongoDB.getCollection(collectionMasterDataName).aggregate(project, group);
			ret.add("--aggregate completed---");
			Iterable<DBObject> results = aop.results();
			int i = 0;
			while(results.iterator().hasNext()){
				i = i + 1;
			}
			ret.add("---count---" + i);
			ret.add("---desc---" + results.toString());
		}
		catch(Exception e){
			log.info("getFilterOnIndustryByAggregateFromMongo--" + e.getMessage());
		}
		return ret;
	}
	
	public static String setLocationtoMongoDB(String state){
		String ret = "error";
		//mongoDB = getMongoDB();
		
		
		return ret;
	}
	
	public static String CallLoadClientIntoMongoDB(String ClientID, String ClientIdentifier,String ClientDesc, String WebService){
		String ret = "error while loading client data";
		mongoDB = getMongoDB();
	    try{
	    	DBObject insert = new BasicDBObject();
	    	insert.put("ClientID", ClientID);
	    	insert.put("ClientIdentifier",ClientIdentifier);
	    	insert.put("ClientDesc", ClientDesc);
	    	insert.put("WebService", WebService.split(","));
			mongoDB.getCollection(client_pool).insert(insert);
			ret = "Loading Completed";
	    }
		catch(Exception e){
			log.info("CallLoadClientIntoMongoDB--" + e.getMessage());
		}
		return ret;
	}
	
	public static List<ClientInformation> CallGetClientFromMongoDB(){
		List<ClientInformation> ret = new ArrayList<ClientInformation>();
		ClientInformation ci;
		
		mongoDB = getMongoDB();
	    try{
	    	DBCursor queryresults = mongoDB.getCollection(client_pool).find();
            if (null != queryresults) {
            	while(queryresults.hasNext()){
            		ci = new ClientInformation();
            		List<String> consumedWebServices = new ArrayList<String> ();
            		DBObject o = queryresults.next();
            		ci.setClientDescription(o.get("ClientDesc").toString());
            		ci.setClientID(o.get("ClientID").toString());
            		ci.setClientIdentifier(o.get("ClientIdentifier").toString());
            		BasicDBList hist = (BasicDBList) o.get("WebService");
            		if(hist != null){
                		Object[] visitHistory = hist.toArray();
                		for(Object dbobj : visitHistory){
                			if(dbobj instanceof String){
                				consumedWebServices.add((String)dbobj);
                			}
                		}
            		}
            		ci.setConsumedWebService(consumedWebServices);
            		if(ci != null){
            			ret.add(ci);
            		}
            	}
            }
	    }
		catch(Exception e){
			log.info("CallGetClientFromMongoDB--" + e.getMessage());
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static List<OrgOtherPartySiteInstance> getOPSIWithOutLatLngFromMongoDB() {
		mongoDB = getMongoDB();
		List<OrgOtherPartySiteInstance> opsiList= new ArrayList<OrgOtherPartySiteInstance>();
		OrgOtherPartySiteInstance orgOtherPartySiteInstance = null;
	    try{
	    	DBObject dbquery = new BasicDBObject(); 
	    	dbquery.put("lat", null);
	    	dbquery.put("lng", null);
	    	DBCursor queryresults = mongoDB.getCollection(collectionMasterDataName).find(dbquery).limit(100);
            if (null != queryresults) {
            	while(queryresults.hasNext()){
            		orgOtherPartySiteInstance = new OrgOtherPartySiteInstance();
            		DBObject o = queryresults.next();
            		if( o.get("siteInstanceId") != null){
            			String opsi =  o.get("siteInstanceId").toString();
            			orgOtherPartySiteInstance.setSiteInstanceId(opsi);
            		}
            		if( o.get("organizationNonLatinExtendedName") != null){
            			String organizationNonLatinExtendedName =  o.get("organizationNonLatinExtendedName").toString();
            			orgOtherPartySiteInstance.setOrganizationNonLatinExtendedName(organizationNonLatinExtendedName);
            		}
            		if( o.get("organizationExtendedName") != null){
            			String organizationExtendedName =  o.get("organizationExtendedName").toString();
            			orgOtherPartySiteInstance.setOrganizationExtendedName(organizationExtendedName);
            		}
            		if(orgOtherPartySiteInstance != null){
            			opsiList.add(orgOtherPartySiteInstance);
            		}
/*            		if(!StringUtils.isEmpty(opsi)){
            			log.info("----3--");
            			DBObject update = new BasicDBObject();
            	    	update.put("lat", "111");
            	    	update.put("lng", "222");
            			WriteResult wr = mongoDB.getCollection(collectionMasterDataName).update(new BasicDBObject().append("siteInstanceId", opsi), update);
            			log.info("----4--");
            			ret = ret + 1;
            		}*/
            	}
            }
	    }
		catch(Exception e){
			log.info("getOPSIWithOutLatLngFromMongoDB--" + e.getMessage());
		}
	    return opsiList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ExtendedOpportunity> getNearByOpptFromMongoDB(String StateProvince, String OpptCityName, String CityArea, String bizType, String lat, String lng){
		List<ExtendedOpportunity> Oppts =  new ArrayList<ExtendedOpportunity>();
		ExtendedOpportunity opptExt = null;
		mongoDB = getMongoDB();
	    try{
	    	DBObject dbquery = new BasicDBObject(); 
	    	if(!StringUtils.isEmpty(StateProvince)){
	    		dbquery.put("state", StateProvince);
	    	}
	    	if(!StringUtils.isEmpty(CityArea)){
	    		dbquery.put("nonlatinCity", CityArea);
	    	}
	    	if(bizType == "customer"){
	    		dbquery.put("onlyPresaleCustomer", "true");
	    	}
	    	else if(bizType == "partner"){
	    		dbquery.put("includePartnerOrgIndicator", "true");
	    	}
	    	else if(bizType == "competitor"){
	    		dbquery.put("isCompetitor", "true");
	    	}
	    	dbquery.put("lat", new BasicDBObject("$ne", null));
	    	dbquery.put("lng", new BasicDBObject("$ne", null));
	    	DBCursor queryresults = mongoDB.getCollection(collectionMasterDataName).find(dbquery);
            if (null != queryresults) {
            	while(queryresults.hasNext()){
            		DBObject o = queryresults.next();
    	    		opptExt =  new ExtendedOpportunity();
    	    		opptExt.setOpptLAT(o.get("lat").toString());
    	    		opptExt.setOpptLNG(o.get("lng").toString());
    	    		opptExt.setOPSIID(o.get("siteInstanceId").toString());
    	    		opptExt.setOpptID(o.get("organizationId").toString());
    	    		opptExt.setOpptName(o.get("siteName").toString());
    	    		opptExt.setOpptCityName(o.get("state").toString());
    	    		opptExt.setCityArea(o.get("nonlatinCity").toString());
    	    		opptExt.setOpptAddress(o.get("streetAddress1").toString());
    	    		opptExt.setOpptEstdYr("");
    	    		opptExt.setOpptGblHc(o.get("countOfEmployee").toString());
    	    		opptExt.setOpptYrRev("");
    	    		opptExt.setSegmentArea(o.get("industrySegmentNames").toString());
    	    		opptExt.setDistance(RestUtils.GetDistance(Double.parseDouble(opptExt.getOpptLNG()), Double.parseDouble(opptExt.getOpptLAT()), Double.parseDouble(lng), Double.parseDouble(lat))); 
    	    		if(opptExt.getDistance() < 4000){ // only return  distance less than 45 KM
    	    			Oppts.add(opptExt);
    	    		}
    	    		Collections.sort(Oppts);
            	}
            }

	    }
		catch(Exception e){
			log.info("getNearByOpptFromMongoDB--" + e.getMessage());
		}
		return Oppts;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WeChatMDLUser> getWeChatUserFromMongoDB(String OpenID) {
		mongoDB = getMongoDB();
		List<WeChatMDLUser> ret = new ArrayList<WeChatMDLUser>();
		WeChatMDLUser weChatMDLUser = null;
		DBCursor queryresults;
		try{
			if(!StringUtils.isEmpty(OpenID)){
				DBObject query = new BasicDBObject();
				query.put("OpenID", OpenID);
				queryresults = mongoDB.getCollection(wechat_user).find(query).limit(1);
			}
			else{
				BasicDBObject sort=new BasicDBObject();
				sort.put("Teamer.role", -1);
				sort.put("Teamer.registerDate", 1);
				sort.put("Created", 1);
				queryresults = mongoDB.getCollection(wechat_user).find().limit(500).sort(sort);
			}
            if (null != queryresults) {
            	while(queryresults.hasNext()){
            		weChatMDLUser = new WeChatMDLUser();
            		DBObject o = queryresults.next();
            		if(o.get("OpenID") != null){
            			weChatMDLUser.setOpenid(o.get("OpenID").toString());
                		if(o.get("CurLAT").toString() != null){
                			weChatMDLUser.setLat(o.get("CurLAT").toString());
                		}
                		if(o.get("CurLNG") != null){
                			weChatMDLUser.setLng(o.get("CurLNG").toString());
                		}
                		if(o.get("HeadUrl") != null){
                			weChatMDLUser.setHeadimgurl(o.get("HeadUrl").toString());
                		}
                		if(o.get("NickName") != null){
                			weChatMDLUser.setNickname(o.get("NickName").toString());
                		}
                		Object teamer = o.get("Teamer");
            			DBObject teamobj = new BasicDBObject();
            			teamobj = (DBObject)teamer;
            			if(teamobj != null){
            				if(teamobj.get("selfIntro") != null){
            					weChatMDLUser.setSelfIntro(teamobj.get("selfIntro").toString());
            				}
            				if(teamobj.get("realName") != null){
            					weChatMDLUser.setNickname(teamobj.get("realName").toString());
            				}
            				if(teamobj.get("role") != null){
            					weChatMDLUser.setRole(teamobj.get("role").toString());
            				}
            				if(teamobj.get("registerDate") != null){
            					weChatMDLUser.setRegisterDate(teamobj.get("registerDate").toString());
            					SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                				String dstr=teamobj.get("registerDate").toString();
                				java.util.Date date=sdf.parse(dstr);
                				long  s1=date.getTime();//将时间转为毫秒
                				long s2=System.currentTimeMillis();//得到当前的毫秒
                				int  day=(int) ((s2-s1)/1000/60/60/24)+1;
                				weChatMDLUser.setWorkDay(day);
            				}
            			}
                		if(!StringUtils.isEmpty(OpenID)){
                			if(teamobj != null){
                				if(teamobj.get("email") != null){
                					weChatMDLUser.setEmail(teamobj.get("email").toString());
                				}
                				if(teamobj.get("phone") != null){
                					weChatMDLUser.setPhone(teamobj.get("phone").toString());
                				}
                				if(teamobj.get("realName") != null){
                					weChatMDLUser.setRealName(teamobj.get("realName").toString());
                				}
                				if(teamobj.get("groupid") != null){
                					weChatMDLUser.setGroupid(teamobj.get("groupid").toString());
                				}
                    		}
                		}
            		}
            		if(weChatMDLUser != null){
            			ret.add(weChatMDLUser);
            		}
            	}
            }
	    }
		catch(Exception e){
			log.info("getWeChatUserFromMongoDB--" + e.getMessage());
		}
	    return ret;
	}
	
	// Bit Add Start
	public static DBObject getOpptByOpsiFromMongoDB(String opsi) {
		DBObject queryresults = null;
		mongoDB = getMongoDB();
		try {
		    DBObject dbquery = new BasicDBObject();
		    if (!StringUtils.isEmpty(opsi)) {
			dbquery.put("siteInstanceId", opsi);
		    }

		    DBCursor dbCursor = mongoDB.getCollection(collectionMasterDataName)
			    .find(dbquery);
		    if (null == dbCursor) {
			queryresults = null;
		    }else {
			queryresults = dbCursor.next();
		    }

		} 		
		catch(Exception e){
			log.info("getOpptByOpsiFromMongoDB--" + e.getMessage());
		}
		return queryresults;
	}
	    
	    /***
	     * search mongodb with condition lng&lat=null. only return one record.
	     * @param inputString 
	     * @return
	     */
	public static String updateOpptLatLngIntoMongoDB(String state){
		mongoDB = getMongoDB();
		String queryOrg = "";
		try {
		    DBObject dbquery = new BasicDBObject();
		    dbquery.put("state", state);
		    dbquery.put("lat", null);
		    dbquery.put("lng", null);
		    log.info("-1--" + dbquery.toString());
/*		    dbquery.put("lat", new BasicDBObject("$eq", null));
		    dbquery.put("lng", new BasicDBObject("$eq", null));*/
		    DBObject queryresult = mongoDB.getCollection(collectionMasterDataName).findOne(dbquery);
		    log.info("-2--" + queryresult.toString());
		    if (queryresult != null) {
		    	log.info("-2.1--");
		    	String OPSIID = queryresult.get("siteInstanceId").toString();
		    	log.info("-2.2--" + OPSIID);
		    	String organizationNonLatinExtendedName = queryresult.get("organizationNonLatinExtendedName").toString();
		    	String organizationExtendedName = queryresult.get("organizationExtendedName").toString();
		    	log.info("-3--" + OPSIID+organizationNonLatinExtendedName+organizationExtendedName);
		    	if(!StringUtils.isEmpty(organizationNonLatinExtendedName)){
		    		queryOrg = organizationNonLatinExtendedName;
		    	}
		    	else{
		    		queryOrg = organizationExtendedName;
		    	}
		    	log.info("-4--" + queryOrg);
		    	GoogleLocationUtils gApi = new GoogleLocationUtils();
				GeoLocation geo =  new GeoLocation();
				geo = gApi.geocodeByAddressNoSSL(queryOrg);
		    	DBObject update = new BasicDBObject();
		    	update.put("lat",geo.getLAT());
		    	update.put("lng",geo.getLNG());		    	
		    	WriteResult wr = mongoDB.getCollection(collectionMasterDataName).update(new BasicDBObject().append("siteInstanceId", OPSIID), update);
		    	queryOrg = queryOrg +"[" + geo.getLAT() + "," +geo.getLNG()+"]";
		    }
		} catch (Exception e) {
			log.info("updateOpptLatLngIntoMongoDB--" + e.getMessage());
			queryOrg = e.getMessage().toString();
		} 
		return queryOrg;
	}
	    /***
	     * search oppts from mongodb with lng=null&lat=null limit(limitSize)
	     * @param limitSize integer of return row number.
	     * @return oppts's json DBCursor
	     */
	    public static DBCursor getOpptListFromMongoDB(int limitSize) {
		DBCursor queryresults = null;
		mongoDB = getMongoDB();
		try {
		    DBObject dbquery = new BasicDBObject();
		    dbquery.put("lat", new BasicDBObject("$eq", null));
		    dbquery.put("lng", new BasicDBObject("$eq", null));

		    log.info("[getOpptFromMongoDB] query mongodb filter :"+dbquery.toString());
		    if (limitSize <= 0 || limitSize >302) {
			limitSize = 302;
		    } 
		    queryresults = mongoDB.getCollection(collectionMasterDataName).find(dbquery).limit(limitSize);
		    if (queryresults.size() == 0) {
			queryresults = null;
		    }
		} catch (Exception e) {
		    if (mongoDB.getMongo() != null) {
			mongoDB.getMongo().close();
		    }
		} finally {
		    if (mongoDB.getMongo() != null) {
			mongoDB.getMongo().close();
		    }
		}
		return queryresults;
	    }


	    public static String getJSListOfCustomersFromMongoDB(String fromUserName, String bizType){
			GeoLocation geol = MongoDBBasic.getDBUserGeoInfo(fromUserName);
			String lat = geol.getLAT();
			String lng = geol.getLNG();
			List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
			List<String> cityInfo = new ArrayList<String>();
			cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
			NearByOpptsExt = MongoDBBasic.getNearByOpptFromMongoDB(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), bizType, lat, lng);
			int opptCnt = NearByOpptsExt.size();
			String Ret = "";
			int it = 30;
			if(opptCnt <= 30){
				it = opptCnt;
			}
			for(int i = 0; i < it ; i ++){
				//Ret =  Ret + "<li>" + NearByOpptsExt.get(i).getDistance() + " KM " + NearByOpptsExt.get(i).getOpptName() + "<br />" + NearByOpptsExt.get(i).getSegmentArea() + "</li>";
				Ret =  Ret + "<li id=\" customer" + i + " \"><span style='float:left;'>" + NearByOpptsExt.get(i).getOpptName() + " </span> <span style='float:right;'>" + NearByOpptsExt.get(i).getDistance() + " KM </span></li>";
			}
			return Ret;
	    }
	    
	    public static String getJSMoreFiveOfCustomersFromMongoDB(String fromUserName, String bizType, int lastLi){
			GeoLocation geol = MongoDBBasic.getDBUserGeoInfo(fromUserName);
			String lat = geol.getLAT();
			String lng = geol.getLNG();
			List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
			List<String> cityInfo = new ArrayList<String>();
			cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
			NearByOpptsExt = MongoDBBasic.getNearByOpptFromMongoDB(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), bizType, lat, lng);
			int opptCnt = NearByOpptsExt.size();
			String Ret = "";
			
			for (int i=0; i<5; i++) {
				Ret = Ret + "li = document.createElement('li'); ";
				Ret = Ret + "li.setAttribute('id','customer" + i + "');";
				Ret = Ret + "li.innerHTML = '<span style=\" float:left;\">" + NearByOpptsExt.get(i).getOpptName() + "</span><span style=\" float:right; \">" +NearByOpptsExt.get(i).getDistance() + "</span>'; ";
				Ret = Ret + "el.appendChild(li, el.childNodes[0]); ";
			}
			return Ret;
	    }
	    
	    /*
		 * author  chang-zheng
		 */
		public static  List<MdmDataQualityView> getDataQualityReportOSfCountry(String Country){
			List<MdmDataQualityView> listDdm = new ArrayList< MdmDataQualityView>();
			mongoDB = getMongoDB();
			MdmDataQualityView tmpmqv = new MdmDataQualityView();
			int cnt_competitor = 0;
			int cnt_partner = 0;
			int cnt_customer = 0;
			int cnt_lead = 0;
			try{
				
				BasicDBObject query_competitor = new BasicDBObject();
				query_competitor.put("isCompetitor", "true");
				BasicDBObject query_partner = new BasicDBObject();
				query_partner.put("includePartnerOrgIndicator", "true");
				BasicDBObject query_customer = new BasicDBObject();
				query_customer.put("onlyPresaleCustomer", "true");
				BasicDBObject query_leads = new BasicDBObject();
				query_leads.put("onlyPresaleCustomer", "false");
				query_leads.put("includePartnerOrgIndicator", "false");
				query_leads.put("isCompetitor", "false");
				
				if(!StringUtils.isEmpty(Country)){
					query_competitor.put("countryCode", Country);
					query_partner.put("countryCode", Country);
					query_customer.put("countryCode", Country);
					query_leads.put("countryCode", Country);
					
					// competitor
					
					cnt_competitor = mongoDB.getCollection(collectionMasterDataName).find(query_competitor).count();
					
					// partner
					
					cnt_partner = mongoDB.getCollection(collectionMasterDataName).find(query_partner).count();
					
					// customer
					
					cnt_customer = mongoDB.getCollection(collectionMasterDataName).find(query_customer).count();
					
					// potential leads
					
					cnt_lead = mongoDB.getCollection(collectionMasterDataName).find(query_leads).count();
				}
				
				tmpmqv.setNumberOfCompetitor(cnt_competitor);
				tmpmqv.setNumberOfPartner(cnt_partner);
				tmpmqv.setNumberOfCustomer(cnt_customer);
				tmpmqv.setNumberOfLeads(cnt_lead);
				tmpmqv.setNumberOfOppt(cnt_lead);
				
				listDdm.add(tmpmqv);
			}
			catch(Exception e){
				log.info("getDataQualityReport--" + e.getMessage());
			}
			
		
			return listDdm;
		}
		/*
		 * chang-zheng
		 *  get opsi
		 */
		
		public static  List<OrgOtherPartySiteInstance> getDataQualityReportOSfCity(String state, String City, String cityRegion){
			List<OrgOtherPartySiteInstance> listDdm = new ArrayList<OrgOtherPartySiteInstance>();
			
			mongoDB = getMongoDB();
			try{
				
				BasicDBObject query = new BasicDBObject();
				if(!StringUtils.isEmpty(state)){
					Pattern patternst = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE);
					query.put("state", patternst);
					//query.put("state", state);
				}
				if (!StringUtils.isEmpty(City) && City.toUpperCase()!="NULL") {
					
					if(StringUtils.isLatinString(City)){
						String tempstr="";
						 String arr[]=City.trim().toLowerCase().split("\\s+");
						 if(City.length()>0)
						 for (int i = 0; i < arr.length; i++) {
						 arr[i]=Character.toUpperCase(arr[i].charAt(0))+arr[i].substring(1);
						 tempstr = tempstr + arr[i]+" ";
						 }
						 City = tempstr.trim();
						 
						Pattern patternst = Pattern.compile("^.*" + City + ".*$", Pattern.CASE_INSENSITIVE);
						query.put("latinCity", patternst);
						//query.put("latinCity", City);
					}
					else{
						Pattern patternst = Pattern.compile("^.*" + City + ".*$", Pattern.CASE_INSENSITIVE);
						query.put("nonlatinCity", patternst);
						//query.put("nonlatinCity", City);
						
					}
				}
				
				if(!StringUtils.isEmpty(cityRegion)){
					Pattern patternst = Pattern.compile("^.*" + cityRegion + ".*$", Pattern.CASE_INSENSITIVE);
					query.put("cityRegion", patternst);
					//query.put("cityRegion", cityRegion);
				}
				
				DBCursor dbOpsi = mongoDB.getCollection(collectionMasterDataName).find(query);
				while(dbOpsi.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = dbOpsi.next();
					opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName").toString());
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName").toString());
					opsi.setIsCompetitor(objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator").toString());
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1").toString());
					listDdm.add(opsi);
				}
			}
			catch(Exception e){
				log.info("getDataQualityReport--" + e.getMessage());
			}
			
			return listDdm;
		}
		/*
		 * chang-zheng
		 * 	get opsi	 
		 * */
		public static List<List<OrgOtherPartySiteInstance>> getDataQualityReportbynonatinCity(String stateProvince, String nonlatinCity, String cityRegion){
			List<List<OrgOtherPartySiteInstance>> lilist = new ArrayList<List<OrgOtherPartySiteInstance>>();
			List<OrgOtherPartySiteInstance> listcompetitor = new ArrayList<OrgOtherPartySiteInstance>();
			List<OrgOtherPartySiteInstance> listpartner = new ArrayList<OrgOtherPartySiteInstance>();
			List<OrgOtherPartySiteInstance> listcustomer = new ArrayList<OrgOtherPartySiteInstance>();
			mongoDB = getMongoDB();

			try{
				// competitor
				BasicDBObject query_competitor = new BasicDBObject();
				query_competitor.put("isCompetitor", "true");
				// partner
				BasicDBObject query_partner = new BasicDBObject();
				query_partner.put("includePartnerOrgIndicator", "true");
				// customer
				BasicDBObject query_customer = new BasicDBObject();
				query_customer.put("onlyPresaleCustomer", "true");
				if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
					query_competitor.put("state", stateProvince);
					query_partner.put("state", stateProvince);
					query_customer.put("state", stateProvince);
					
				}
				if (!StringUtils.isEmpty(nonlatinCity) && nonlatinCity.toUpperCase()!="NULL") {
					if(StringUtils.isLatinString(nonlatinCity)){
						/*String tempstr="";
						 String arr[]=nonlatinCity.trim().toLowerCase().split("\\s+");
						 if(nonlatinCity.length()>0)
						 for (int i = 0; i < arr.length; i++) {
						 arr[i]=Character.toUpperCase(arr[i].charAt(0))+arr[i].substring(1);
						 tempstr = tempstr + arr[i]+" ";
						 }
						 nonlatinCity = tempstr.trim();*/
						nonlatinCity=nonlatinCity.substring(0,4)+" "+nonlatinCity.substring(4);
						
						Pattern patternst = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
						query_competitor.put("latinCity", patternst);
						query_partner.put("latinCity", patternst);
						query_customer.put("latinCity", patternst);
					}
					else{
						Pattern patternst = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
						query_competitor.put("nonlatinCity", patternst);
						query_partner.put("nonlatinCity", patternst);
						query_customer.put("nonlatinCity", patternst);
						
					}
				}
				if(cityRegion != "" && cityRegion!= null && cityRegion.toUpperCase()!= "NULL"){
					query_competitor.put("cityRegion", cityRegion);
					query_partner.put("cityRegion", cityRegion);
					query_customer.put("cityRegion", cityRegion);
				}

				DBCursor competitor = mongoDB.getCollection(collectionMasterDataName).find(query_competitor);
				DBCursor partner = mongoDB.getCollection(collectionMasterDataName).find(query_partner);
				DBCursor customer  = mongoDB.getCollection(collectionMasterDataName).find(query_customer);
				
				while(competitor.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = competitor.next();
					if(StringUtils.isLatinString(stateProvince)){
						if(objOpsi.get("organizationExtendedName")==null){
							opsi.setOrganizationNonLatinExtendedName("");
						}else{
							opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationExtendedName").toString());
						}
						
					}else{
						if(objOpsi.get("organizationNonLatinExtendedName")==null){
							opsi.setOrganizationNonLatinExtendedName("");
						}else{
							opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName").toString());
						}
					}
					if(objOpsi.get("industrySegmentNames")==null){
						opsi.setIndustrySegmentNames("");
					}else{
						opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames").toString());
					}
					//opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName").toString());
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName") == null ? "" : objOpsi.get("organizationExtendedName").toString() );
					
					//opsi.setIsCompetitor(objOpsi.get("isCompetitor").toString());
					opsi.setIsCompetitor(objOpsi.get("isCompetitor") == null ? "" : objOpsi.get("isCompetitor").toString());
					//opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator") == null ? "" : objOpsi.get("includePartnerOrgIndicator").toString() );
					//opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer")== null ? "" : objOpsi.get("onlyPresaleCustomer").toString());
					//opsi.setStreetAddress1(objOpsi.get("streetAddress1").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1")== null ? "" : objOpsi.get("streetAddress1").toString());
					listcompetitor.add(opsi);
				}
				while(partner.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = partner.next();
					if(StringUtils.isLatinString(stateProvince)){
						opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationExtendedName")== null ? "" : objOpsi.get("organizationExtendedName").toString());
					}else{
						opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName") == null ? "" : objOpsi.get("organizationNonLatinExtendedName").toString());
					}
					
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName") == null ? "" : objOpsi.get("organizationExtendedName").toString() );
					opsi.setIsCompetitor(objOpsi.get("isCompetitor") == null ? "" : objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator") == null ? "" : objOpsi.get("includePartnerOrgIndicator").toString() );
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer")== null ? "" : objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1")== null ? "" : objOpsi.get("streetAddress1").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames")== null ? "" : objOpsi.get("industrySegmentNames").toString());
					/*opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName").toString());
					opsi.setIsCompetitor(objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator").toString());
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1").toString());*/
					listpartner.add(opsi);
				}
				while(customer.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = customer.next();
					if(StringUtils.isLatinString(stateProvince)){
						opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationExtendedName")== null ? "" : objOpsi.get("organizationExtendedName").toString());
					}else{
						opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName") == null ? "" : objOpsi.get("organizationNonLatinExtendedName").toString());
					}
					
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName") == null ? "" : objOpsi.get("organizationExtendedName").toString() );
					opsi.setIsCompetitor(objOpsi.get("isCompetitor") == null ? "" : objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator") == null ? "" : objOpsi.get("includePartnerOrgIndicator").toString() );
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer")== null ? "" : objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1")== null ? "" : objOpsi.get("streetAddress1").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames")== null ? "" : objOpsi.get("industrySegmentNames").toString());
					listcustomer.add(opsi);
				}
				lilist.add(listcustomer);
				lilist.add(listpartner);
				lilist.add(listcompetitor);
			}
			catch(Exception e){
				log.info("getDataQualityReport--" + e.getMessage());
			}
			
			return lilist;
		}
		
		/*
		 * chang-zheng
		 * 	get opsi	test-------- 
		 * */
		public static List<List<OrgOtherPartySiteInstance>> getDataQualityReportbynonatinCityToTest(String stateProvince, String nonlatinCity){
			List<List<OrgOtherPartySiteInstance>> lilist = new ArrayList<List<OrgOtherPartySiteInstance>>();
			List<OrgOtherPartySiteInstance> listcompetitor = new ArrayList<OrgOtherPartySiteInstance>();
			List<OrgOtherPartySiteInstance> listpartner = new ArrayList<OrgOtherPartySiteInstance>();
			List<OrgOtherPartySiteInstance> listcustomer = new ArrayList<OrgOtherPartySiteInstance>();
			mongoDB = getMongoDB();
			
			try{
				// competitor
				BasicDBObject query_competitor = new BasicDBObject();
				query_competitor.put("isCompetitor", "true");
				// partner
				BasicDBObject query_partner = new BasicDBObject();
				query_partner.put("includePartnerOrgIndicator", "true");
				// customer
				BasicDBObject query_customer = new BasicDBObject();
				query_customer.put("onlyPresaleCustomer", "true");
				if(stateProvince != "" && stateProvince!= null && stateProvince.toUpperCase()!= "NULL"){
					query_competitor.put("state", stateProvince);
					query_partner.put("state", stateProvince);
					query_customer.put("state", stateProvince);
					
				}
				if (!StringUtils.isEmpty(nonlatinCity) && nonlatinCity.toUpperCase()!="NULL") {
					if(StringUtils.isLatinString(nonlatinCity)){
						String tempstr="";
						 String arr[]=nonlatinCity.trim().toLowerCase().split("\\s+");
						 if(nonlatinCity.length()>0)
						 for (int i = 0; i < arr.length; i++) {
						 arr[i]=Character.toUpperCase(arr[i].charAt(0))+arr[i].substring(1);
						 tempstr = tempstr + arr[i]+" ";
						 }
						 nonlatinCity = tempstr.trim();
						//Pattern patternst = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
						query_competitor.put("latinCity", nonlatinCity);
						query_partner.put("latinCity", nonlatinCity);
						query_customer.put("latinCity", nonlatinCity);
					}
					else{
						Pattern patternst = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
						query_competitor.put("nonlatinCity", patternst);
						query_partner.put("nonlatinCity", patternst);
						query_customer.put("nonlatinCity", patternst);
						
					}
				}
				DBCursor competitor = mongoDB.getCollection(collectionMasterDataName).find(query_competitor);
				DBCursor partner = mongoDB.getCollection(collectionMasterDataName).find(query_partner);
				DBCursor customer  = mongoDB.getCollection(collectionMasterDataName).find(query_customer);
				
				while(competitor.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = competitor.next();
					opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName").toString());
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName").toString());
					opsi.setIsCompetitor(objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator").toString());
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1").toString());
					listcompetitor.add(opsi);
				}
				while(partner.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = partner.next();
					opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName").toString());
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName").toString());
					opsi.setIsCompetitor(objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator").toString());
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1").toString());
					listpartner.add(opsi);
				}
				while(customer.hasNext()){
					OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
					DBObject objOpsi = customer.next();
					opsi.setOrganizationNonLatinExtendedName(objOpsi.get("organizationNonLatinExtendedName").toString());
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName").toString());
					opsi.setIsCompetitor(objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator").toString());
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1").toString());
					listcustomer.add(opsi);
				}
				lilist.add(listcustomer);
				lilist.add(listpartner);
				lilist.add(listcompetitor);
			}
			catch(Exception e){
				log.info("getDataQualityReport--" + e.getMessage());
			}
			
			return lilist;
		}
		/*
		 * chang-zheng
		 * get NonLatinCity
		 */
		public static List<String> getAllDistrict(String state){
			mongoDB = getMongoDB();
			List<String> listOfRegion = new ArrayList<String>();
			@SuppressWarnings("rawtypes")
			List results;
		    try{
		    	DBObject dbquery = new BasicDBObject();  
				if(state != "" && state != null && state != "null" ){
					Pattern pattern = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE); 
					dbquery.put("state", pattern);
				}
						
		    	results = mongoDB.getCollection(collectionMasterDataName).distinct("cityRegion", dbquery);
		    	for(int i = 10; i < results.size(); i ++){
		    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
		    			listOfRegion.add((String) results.get(i));
		    		}
		    	}
		    }
			catch(Exception e){
				log.info("getFilterRegionFromMongo--" + e.getMessage());
			}
		    return listOfRegion;
		}

	    // END
}