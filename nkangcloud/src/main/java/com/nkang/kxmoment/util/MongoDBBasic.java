package com.nkang.kxmoment.util;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

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
import com.mysql.jdbc.Statement;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.MongoClientCollection;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.WeChatUser;

public class MongoDBBasic { 
	private static Logger log = Logger.getLogger(MongoDBBasic.class);
	private static DB mongoDB;
	private static String collectionMasterDataName = "masterdata";
	private static String access_key = "Access_Key";
	private static String wechat_user = "Wechat_User";
	private static String wechat_comments = "Wechat_Comments";

	private static DB getMongoDB(){
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
			if(mongoDB.getMongo() != null){
				mongoDB.getMongo().close();
			}
		}
		finally{
			if(mongoDB.getMongo() != null){
				mongoDB.getMongo().close();
			}
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
	    		log.info(diff + " is less than 7200. so use original valid Key as : "+ validKey);
	    	}
	    	else{
	    		log.info(diff + " is close to 7200. and is to re-generate the key");
	    		validKey = null;
	    	}
	    	
	    }catch(Exception e){
	    	e.printStackTrace();
			if(mongoDB.getMongo() != null){
				mongoDB.getMongo().close();
			}
	    }
	    finally{
	    	mongoDB.getMongo().close();
			if(mongoDB.getMongo() != null){
				mongoDB.getMongo().close();
			}
	    }
	    
	    return validKey;
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
			log.info("-----Wechat user created----" + wcu.getOpenid() + ":" + wcu.getNickname());
			ret = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    	ret = false;
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
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
    	    	DBObject update = new BasicDBObject();
    	    	update.put("OpenID", wcu.getOpenid());
    	    	update.put("HeadUrl", wcu.getHeadimgurl());
    	    	update.put("NickName", wcu.getNickname());
    	    	update.put("Created", DateUtil.timestamp2Str(cursqlTS));
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
    			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", OpenID), update);
    			log.info("----end updating user----4 \n" + wr);
            }
            ret = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    	log.info("-----error occurs---"+ e.getMessage());
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    	ret = false;
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
		return ret;
	}
	
	public String mongoDBInsert(OrgOtherPartySiteInstance opsi){
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
			} catch (Exception e) {
				return "error";
			}
		return "ok";
	}

	public static String getTotalRecordCount() {
		if(mongoDB == null){
			mongoDB = getMongoDB();
		}
		String result = String.valueOf(mongoDB.getCollection(collectionMasterDataName).getCount());
        return result;
	}
	
	public boolean isDocumentExsit(String collectionName, DBObject query) {
        boolean result = false;
        DBCursor dbCursor = null;
        DBCollection collection = mongoDB.getCollection(collectionName);
        if (null != collection) {
            dbCursor = collection.find(query);
            if (null != dbCursor && dbCursor.hasNext()) {
                result = true;
            }
        }
        return result;
	}
	
	public static int getSelectedDocumentWithQuery(DBObject query) {
		if(mongoDB == null){
			mongoDB = getMongoDB();
		}
        int cnt = 0;
        if (null != mongoDB.getCollection(collectionMasterDataName)) {
        	cnt = (int) mongoDB.getCollection(collectionMasterDataName).getCount(query);
        }
        return cnt;
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
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}

	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
        return result;
	}
	
	public DBObject selectDocument(String collectionName, DBObject query) {
		DBObject result = null;
		DBCursor dbCursor = null;
		DBCollection collection = mongoDB.getCollection(collectionName);
		if (null != collection) {
			dbCursor = collection.find(query);
			if (null != dbCursor && dbCursor.hasNext()) {
				result = dbCursor.next();
			}
		}
		return result;
	}
	
	public boolean deleteDocument(String collectionName, DBObject query) {
		boolean result = false;
		WriteResult writeResult = null;
		DBCollection collection = mongoDB.getCollection(collectionName);
		if (null != collection) {
			writeResult = collection.remove(query);
			if (null != writeResult) {
				if (writeResult.getN() > 0) {
					result = true;
				}
			}
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
	    }catch(Exception e){
	    	e.printStackTrace();
	    	log.info("error---getDBUserGeoInfo: " + e.getMessage());
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
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
	    }catch(Exception e){
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    	ret = false;
	    	e.printStackTrace();
	    	log.info("error---getDBUserGeoInfo: " + e.getMessage());
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getFilterSegmentArea(){
		mongoDB = getMongoDB();
		List<String> listOfSegmentArea = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;
	    try{
	    	results =  mongoDB.getCollection(collectionMasterDataName).distinct("industrySegmentNames");
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			listOfSegmentArea.add((String) results.get(i));
	    		}
	    	}
	    }catch(Exception e){
	    	listOfSegmentArea.add("--1--" + e.getMessage().toString());
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    return listOfSegmentArea;
	}
	
	public static List<String> getFilterRegionFromMongo(String state){
		mongoDB = getMongoDB();
		List<String> listOfRegion = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;
	    try{
	    	DBObject query1 = new BasicDBObject("state", "重庆市");  
	    	DBObject query2 = new BasicDBObject("state", "重庆");     
	    	BasicDBList or = new BasicDBList();
	    	or.add(query1);
	    	or.add(query2);
	    	DBObject query = new BasicDBObject("$or", or);
	    	results = mongoDB.getCollection(collectionMasterDataName).distinct("cityRegion", query);
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			listOfRegion.add((String) results.get(i));
	    		}
	    	}
	    }catch(Exception e){
	    	listOfRegion.add("--1--" + e.getMessage().toString());
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    return listOfRegion;
	}

	public static List<String> getFilterNonLatinCitiesFromMongo(String state){
		mongoDB = getMongoDB();
		List<String> listOfNonLatinCities = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List results;
	    try{
	    	DBObject query1 = new BasicDBObject("state", "重庆市");  
	    	DBObject query2 = new BasicDBObject("state", "重庆");     
	    	BasicDBList or = new BasicDBList();
	    	or.add(query1);
	    	or.add(query2);
	    	DBObject query = new BasicDBObject("$or", or);

	    	results = mongoDB.getCollection(collectionMasterDataName).distinct("nonlatinCity", query);
	    	for(int i = 0; i < results.size(); i ++){
	    		if(results.get(i) != "null" && results.get(i) != "NULL" && results.get(i) != null){
	    			listOfNonLatinCities.add((String) results.get(i));
	    		}
	    	}
	    }catch(Exception e){
	    	listOfNonLatinCities.add("--1--" + e.getMessage().toString());
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
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
	    }catch(Exception e){
	    	listOfstates.add("--1--" + e.getMessage().toString());
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    return listOfstates;
	}
	
	public static String getFilterCountOnCriteriaFromMongo(String industrySegmentNames, String nonlatinCity, String state, String cityRegion){
		mongoDB = getMongoDB();
		String ret = "error";
		DBObject query  = new BasicDBObject();
		if(industrySegmentNames != "" && industrySegmentNames != null && industrySegmentNames != "null" ){
			Pattern pattern = Pattern.compile("^.*" + industrySegmentNames + ".*$", Pattern.CASE_INSENSITIVE); 
			query.put("industrySegmentNames", pattern);
		}
		if(nonlatinCity != "" && nonlatinCity != null && nonlatinCity != "null" ){
			Pattern pattern2 = Pattern.compile("^.*" + nonlatinCity + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("nonlatinCity", pattern2);
		}
		if(state != "" && state != null && state != "null" ){
			Pattern pattern3 = Pattern.compile("^.*" + state + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("state", pattern3);
		}
		if(cityRegion != "" && cityRegion != null && cityRegion != "null" ){
			Pattern pattern4 = Pattern.compile("^.*" + cityRegion + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("cityRegion", pattern4);
		}
	    try{
	    	//Pattern pattern = Pattern.compile("^.*name8.*$", Pattern.CASE_INSENSITIVE);
	    	ret = String.valueOf( mongoDB.getCollection(collectionMasterDataName).count(query));
	    }catch(Exception e){
	    	ret = "Exception: " + e.getMessage().toString();
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    return ret;
	}
	
	
	public static String getFilterTotalOPSIFromMongo(){
		mongoDB = getMongoDB();
		String ret = "0";
	    try{
	    	ret = String.valueOf(mongoDB.getCollection(collectionMasterDataName).find().count()) ;
	    }catch(Exception e){
	    	ret =  ret + "--1--" + e.getMessage().toString();
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    finally{
	    	if(mongoDB.getMongo() != null){
	    		mongoDB.getMongo().close();
	    	}
	    }
	    return ret;
	}
	
	
	
}