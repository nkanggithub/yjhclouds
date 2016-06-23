package com.nkang.kxmoment.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.bson.BSONObject;

import com.ctc.wstx.util.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.service.CoreService;

public class MongoDBBasic { 
	private static Logger log = Logger.getLogger(MongoDBBasic.class);
	public static String mongoDBInsert(OrgOtherPartySiteInstance opsi){
			try {
				String databaseName = "VGWUeoTkcVUrpONUGzUO";
				String host = "mongo.duapp.com";
				String port = "8908";
				String username = "473c34a48268d02986e607cb5d7d564c";
				String password = "807640004b73f4ec49d3d3af79493ade";
				String serverName = host + ":" + port;
				MongoClient mongoClient = new MongoClient(new ServerAddress(serverName), Arrays.asList(MongoCredential.createMongoCRCredential(username, databaseName,password.toCharArray())),new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
				DB mongoDB = mongoClient.getDB(databaseName);
				mongoDB.addUser(username, password.toCharArray());
				DBCollection mongoCollection = mongoDB.getCollection("masterdata");
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

				mongoCollection.insert(dbo);
				mongoClient.close();
			} catch (Exception e) {
				return "error";
			}
		return "ok";
	}

	public static void getmongoDB()
	{
        try {
            String databaseName = "VGWUeoTkcVUrpONUGzUO"; 
            String host = "mongo.duapp.com";
            String port = "8908";
            String username = "473c34a48268d02986e607cb5d7d564c";//用户名(api key);
            String password = "807640004b73f4ec49d3d3af79493ade";//密码(secret key)
            String serverName = host + ":" + port;

            MongoClient mongoClient = new MongoClient(
            		new ServerAddress(serverName),
            		Arrays.asList(MongoCredential.createMongoCRCredential(username, databaseName,password.toCharArray())),
            		new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
            
            DB mongoDB = mongoClient.getDB(databaseName);
            mongoDB.addUser(username, password.toCharArray());

		    DBCollection mongoCollection = mongoDB.getCollection("masterdata");

			ResourceBundle resourceBundle=ResourceBundle.getBundle("MDMExtract_0_0-50000");
			String recordinfo = "";
			for(int i = 1; i <= 50000; i ++){
				recordinfo=resourceBundle.getString(String.valueOf(i));
				recordinfo = StringUtils.changeCharset(recordinfo, StringUtils.UTF_8);
				String[] info = recordinfo.split("\\|");
			    DBObject basic = new BasicDBObject(); 
			    if(!StringUtils.isEmpty(info[0])){
			    	basic.put("OtherPartySiteInstanceId"			, info[0]);
			    }
			    if(!StringUtils.isEmpty(info[1])){
			    	basic.put("OrganizationID"						, info[1]);  
			    }
				    if(!StringUtils.isEmpty(info[2])){
			    	basic.put("OrganizationNonLatinName"			, info[2]);
			    }
			    if(!StringUtils.isEmpty(info[3])){
			    	basic.put("OrganizationNonLatinAddress"			, info[3]); 
			    }
			    if(!StringUtils.isEmpty(info[4])){
			    	basic.put("Lat"									, info[4]);
			    }
			    if(!StringUtils.isEmpty(info[5])){
			    	basic.put("Lng"									, info[5]);
			    }
			    if(!StringUtils.isEmpty(info[6])){
			    	basic.put("OrganizationLatinName"				, info[6]); 
			    }
			    if(!StringUtils.isEmpty(info[7])){
			    	basic.put("NonlatinCity"						, info[7]); 
			    }
			    if(!StringUtils.isEmpty(info[8])){
			    	basic.put("Found Year"							, info[8]);
			    }
			    if(!StringUtils.isEmpty(info[9])){
			    	basic.put("CountOfEmployee"						, info[9]); 
			    }
			    if(!StringUtils.isEmpty(info[10])){
			    	basic.put("State"								, info[10]); 
			    }
			    if(!StringUtils.isEmpty(info[11])){
			    	basic.put("BRID"								, info[11]);
			    }
			    if(!StringUtils.isEmpty(info[12])){
			    	basic.put("CityRegion"							, info[12]); 
			    }
			    if(!StringUtils.isEmpty(info[13])){
			    	basic.put("Anunal Revene"						, info[13]);
			    }
			    if(!StringUtils.isEmpty(info[14])){
			    	basic.put("BRIID"								, info[14]); 
			    }
			    if(!StringUtils.isEmpty(info[15])){
			    	basic.put("SalesCoverageSegments"				, info[15]); 
			    }
			    if(!StringUtils.isEmpty(info[16])){
			    	basic.put("IsCompetitor"						, info[16]); 
			    }
			    if(!StringUtils.isEmpty(info[17])){
			    	basic.put("OnlyPresaleCustomer"					, info[17]);
			    }
			    if(!StringUtils.isEmpty(info[18])){
			    	basic.put("isReturnPartnerFlag"					, info[18]);
			    }
			    basic.put("QualityGrade"							, ""); 
			    
			    
			    mongoCollection.insert(basic);
				recordinfo = "";
			}
		    mongoClient.close();
		    
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}

 
}