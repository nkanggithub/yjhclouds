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
import com.nkang.kxmoment.service.CoreService;
public class MongoDBBasic { 
	private static Logger log = Logger.getLogger(MongoDBBasic.class);
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
		    log.info("----------------get started");
		    List<DBObject> list = new ArrayList<DBObject>();
			ResourceBundle resourceBundle=ResourceBundle.getBundle("MDMExtract_0_0-50000");
			String recordinfo = "";
			for(int i = 1; i <= 50000; i ++){
				recordinfo=resourceBundle.getString(String.valueOf(i));
				String[] info = recordinfo.split("\\|");
				log.info("-----in size loop end 1 ----" + i + " with " + info[0]);
			    DBObject basic = new BasicDBObject();  
			    basic.put("OtherPartySiteInstanceId"			, info[0]);    
			    basic.put("OrganizationID"						, info[1]);    
			    basic.put("OrganizationNonLatinName"			, info[2]); 
			    basic.put("OrganizationNonLatinAddress"			, info[3]); 
			    basic.put("Lat"									, info[4]); 
			    basic.put("Lng"									, info[5]); 
			    basic.put("OrganizationLatinName"				, info[6]); 
			    basic.put("NonlatinCity"						, info[7]); 
			    basic.put("Found Year"							, info[8]); 
			    basic.put("CountOfEmployee"						, info[9]); 
			    basic.put("State"								, info[10]); 
			    basic.put("BRID"								, info[11]); 
			    basic.put("CityRegion"							, info[12]); 
			    basic.put("Anunal Revene"						, info[13]); 
			    basic.put("BRIID"								, info[14]); 
			    basic.put("SalesCoverageSegments"				, info[15]); 
			    basic.put("IsCompetitor"						, info[16]); 
			    basic.put("OnlyPresaleCustomer"					, info[17]); 
			    basic.put("isReturnPartnerFlag"					, info[18]); 
			    basic.put("QualityGrade"						, info[19]); 
			    list.add(basic);
			    mongoCollection.insert(basic);
/*			    if(i==5000){
			    	log.info("-----in size loop end 1.1 ----" + i + " with " + info[0]);
	                mongoCollection.insert(list);
	                list.clear();  
			    }*/
/*			    if(i!=0 && i % 2000 == 0){
	                mongoCollection.insert(list);
	                list.clear();  
			    }*/
				recordinfo = "";
				log.info("-----in size loop end 2----" + i + " with " + info[0]);
			}
			
			//mongoCollection.insert(list);
			
		    mongoClient.close();
		    
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}

 
}