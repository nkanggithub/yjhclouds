package com.nkang.kxmoment.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.descriptor.tld.TldRuleSet.Variable;
import org.bson.types.ObjectId;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.nkang.kxmoment.baseobject.ArticleMessage;
import com.nkang.kxmoment.baseobject.QuoteVisit;
import com.nkang.kxmoment.baseobject.ShortNews;
import com.nkang.kxmoment.baseobject.BillOfSell;
import com.nkang.kxmoment.baseobject.ClientInformation;
import com.nkang.kxmoment.baseobject.ClientMeta;
import com.nkang.kxmoment.baseobject.CongratulateHistory;
import com.nkang.kxmoment.baseobject.ExtendedOpportunity;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.Location;
import com.nkang.kxmoment.baseobject.Market;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.MongoClientCollection;
import com.nkang.kxmoment.baseobject.Notification;
import com.nkang.kxmoment.baseobject.OnDelivery;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
import com.nkang.kxmoment.baseobject.OrderNopay;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.QuotationList;
import com.nkang.kxmoment.baseobject.Role;
import com.nkang.kxmoment.baseobject.Teamer;
import com.nkang.kxmoment.baseobject.Visited;
import com.nkang.kxmoment.baseobject.Visitedreturn;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.service.PlasticItemService;

public class MongoDBBasic { 
	private static Logger log = Logger.getLogger(MongoDBBasic.class);
	private static DB mongoDB = null;
	private static String collectionMasterDataName = "masterdata";
	private static String access_key = "Access_Key";
	private static String wechat_user = "Wechat_User";
	private static String client_pool = "ClientPool";
	private static String wechat_comments = "Wechat_Comments";
	private static String ClientMeta = "Client_Meta";
	private static String Article_Message = "Article_Message";
	private static String collectionBill = "SaleBill";
	private static String collectionQuotation = "Quotation";
	private static String short_news = "ShortNews";
	private static String collectionInventory = "Inventory";
	private static String collectionOnDelivery = "OnDelivery";
	private static String collectionOrderNopay = "OrderNopay";
	private static String collectionQuotationList = "QuotationList";
	private static String collectionVisited = "Visited";
	public static DB getMongoDB(){
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
        		// createMongoCRCredential / createScramSha1Credential 
        		Arrays.asList(MongoCredential.createMongoCRCredential(usrname, databaseName,passwrd.toCharArray())),
        		new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
        mongoClientCollection.setMongoClient(mongoClient);

        mongoDB = mongoClient.getDB(databaseName);
//        mongoDB.addUser(usrname, passwrd.toCharArray());

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
	public static boolean checkUserPoint(String OpenID){
		mongoDB = getMongoDB();
		boolean ret=false;
		String date="";
	    try{
	    	DBObject result = mongoDB.getCollection(wechat_user).findOne(new BasicDBObject().append("OpenID", OpenID));
    		Object likeobj = result.get("Point");
			DBObject like= new BasicDBObject();
			like= (DBObject)likeobj;
			if(like != null){
				if(like.get("date")!=null){
					date=like.get("date").toString();
	    		}
			}
    		Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        String dateNowStr = sdf.format(d);  
	        if(dateNowStr.equals(date)){
	        	ret=false;
	        }else{
	        	ret=true;
	        }
	    }
		catch(Exception e){
			log.info("registerUser--" + e.getMessage());
		}
		return ret;
	}
	public static int updateUserPoint(String OpenID,int point){
		mongoDB = getMongoDB();
		int pointSum=point;
	    try{
	    	DBObject result = mongoDB.getCollection(wechat_user).findOne(new BasicDBObject().append("OpenID", OpenID));
    		Object likeobj = result.get("Point");
			DBObject like= new BasicDBObject();
			like= (DBObject)likeobj;
			if(like != null){
				if(like.get("num")!=null){
	    			pointSum=Integer.parseInt(like.get("num").toString())+point;
	    		}
			}
    		if(point!=0){
	    		DBObject dbo = new BasicDBObject();
	    		dbo.put("Point.num", pointSum); 
	    		Date d = new Date();  
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		        String dateNowStr = sdf.format(d);  
	
	    		dbo.put("Point.date", dateNowStr); 
	    		
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", dbo);  
				WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", OpenID),doc);
	    	}
    	}
		catch(Exception e){
			log.info("registerUser--" + e.getMessage());
		}
		return pointSum;
	}
	public static boolean createShortNews(String content){
		mongoDB = getMongoDB(); 
		Boolean ret = false;
	    try{
			Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d); 
	        
	    	DBObject insert = new BasicDBObject();
	    	insert.put("date",dateNowStr);
	    	insert.put("content", content);
	    	mongoDB.getCollection(short_news).insert(insert);
			ret = true;
	    }
		catch(Exception e){
			log.info("createRoleOfAreaMap--" + e.getMessage());
		}
		return ret;
	}
	public static ArrayList<ShortNews> queryShortNews(){
		mongoDB = getMongoDB();
		ArrayList<ShortNews> result = new ArrayList<ShortNews>();
		BasicDBObject sort=new BasicDBObject();
		sort.put("date", -1);
		DBCursor dbcur = mongoDB.getCollection(short_news).find().sort(sort);
		StringBuilder  tempStr ;  
		if (null != dbcur) {
        	while(dbcur.hasNext()){
        		DBObject o = dbcur.next();
        		ShortNews temp=new ShortNews();
        		if(o.get("date")!=null){
        			tempStr = new StringBuilder (o.get("date").toString());  
        			tempStr.insert(tempStr.length()-9, "<br/>");
        			temp.setDate(tempStr.toString());
        		}
        		if(o.get("content")!=null){
        			temp.setContent(o.get("content").toString());
        		}
        		temp.setMongoID(o.get("_id").toString());
        		result.add(temp);
        	}
        }
		return result;
	}
	public static ArrayList<ShortNews> queryShortNews(int startNumber,int pageSize){
		mongoDB = getMongoDB();
		ArrayList<ShortNews> result = new ArrayList<ShortNews>();
		BasicDBObject sort=new BasicDBObject();
		sort.put("date", -1);
		DBCursor dbcur = mongoDB.getCollection(short_news).find().sort(sort).skip(startNumber).limit(pageSize);
		StringBuilder  tempStr ;  
        if (null != dbcur) {
        	while(dbcur.hasNext()){
        		DBObject o = dbcur.next();
        		ShortNews temp=new ShortNews();
        		if(o.get("date")!=null){
        			tempStr = new StringBuilder (o.get("date").toString());  
        			tempStr.insert(tempStr.length()-9, "<br/>");
        			temp.setDate(tempStr.toString());
        		}
        		if(o.get("content")!=null){
        			temp.setContent(o.get("content").toString());
        		}
        		temp.setMongoID(o.get("_id").toString());
        		result.add(temp);
        	}
        }
		return result;
	}
	
	public static boolean deleteShortNews(String id){
		Boolean ret = false;
	    try{
			DBObject removeQuery = new BasicDBObject();
			removeQuery.put("_id", new ObjectId(id));
			mongoDB.getCollection(short_news).remove(removeQuery);
			ret = true;
	    }
		catch(Exception e){
			log.info("remove--" + e.getMessage());
		}
		return ret;
		
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
	public static ArrayList<Map> QueryVisitPage(){
		mongoDB = getMongoDB();
		ArrayList list=new ArrayList();
	    try{
			DBObject query = new BasicDBObject();
			query.put("Active", "Y");
			DBObject queryresults = mongoDB.getCollection(ClientMeta).findOne(query);
			BasicDBList visitPage = (BasicDBList) queryresults.get("visitPage");
    		if(visitPage != null){
        		Object[] tagObjects = visitPage.toArray();
        		for(Object dbobj : tagObjects){
        			if(dbobj instanceof DBObject){
        				HashMap<String, String> temp=new HashMap<String, String>();
        				temp.put("realName", ((DBObject)dbobj).get("realName").toString());
        				temp.put("descName", ((DBObject)dbobj).get("descName").toString());
        				temp.put("attention", ((DBObject)dbobj).get("attention").toString());
        				list.add(temp);
        			}
        		}
    		}
	    }
		catch(Exception e){
			log.info("QueryVisitPage--" + e.getMessage());
		}
	    return list;
	}
	public static ArrayList<Map> QueryVisitPageAttention(){
		mongoDB = getMongoDB();
		ArrayList list=new ArrayList();
	    try{
			DBObject query = new BasicDBObject();
			query.put("Active", "Y");
			DBObject queryresults = mongoDB.getCollection(ClientMeta).findOne(query);
			BasicDBList visitPage = (BasicDBList) queryresults.get("visitPage");
    		if(visitPage != null){
        		Object[] tagObjects = visitPage.toArray();
        		for(Object dbobj : tagObjects){
        			if(dbobj instanceof DBObject){
        				HashMap<String, String> temp=new HashMap<String, String>();
        				temp.put("realName", ((DBObject)dbobj).get("realName").toString());
        				temp.put("descName", ((DBObject)dbobj).get("descName").toString());
        				if("1".equals(((DBObject)dbobj).get("attention").toString()))
        				{
        					list.add(temp);
        				}
        			}
        		}
    		}
	    }
		catch(Exception e){
			log.info("QueryVisitPage--" + e.getMessage());
		}
	    return list;
	}
	
	public static boolean updateVisitPage(String realName,String flag){
		mongoDB = getMongoDB();
		ArrayList list=new ArrayList();
		DBObject query = new BasicDBObject();
		query.put("Active", "Y");
		DBObject queryresults = mongoDB.getCollection(ClientMeta).findOne(query);
		BasicDBList visitPage = (BasicDBList) queryresults.get("visitPage");
		if(visitPage != null){
    		Object[] tagObjects = visitPage.toArray();
    		for(Object dbobj : tagObjects){
    			if(dbobj instanceof DBObject){
    				HashMap<String, String> temp=new HashMap<String, String>();
    				temp.put("realName", ((DBObject)dbobj).get("realName").toString());
    				temp.put("descName", ((DBObject)dbobj).get("descName").toString());
    				temp.put("attention", ((DBObject)dbobj).get("attention").toString());
    				if(realName.equals(((DBObject)dbobj).get("realName").toString()))
    				{
    					if("add".equals(flag)){
    						temp.put("attention", "1");
    					}else{
    						temp.put("attention", "0");
    					}
    				}
    				list.add(temp);
    			}
    		}
		}
		
		
		
		BasicDBObject doc = new BasicDBObject();
		DBObject update = new BasicDBObject();
		update.put("visitPage", list);
		doc.put("$set", update); 
		WriteResult wr = mongoDB.getCollection(ClientMeta).update(new BasicDBObject().append("Active","Y"), doc);  
		return true;
	}
	public static boolean addSkimNum(){
		boolean result=false;
		mongoDB = getMongoDB();
		
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(d); 

        java.util.Random random=new java.util.Random();// 定义随机类
        int randomNum=random.nextInt(3);// 返回[0,10)集合中的整数，注意不包括10
    	DBObject query = new BasicDBObject();
		query.put("Active", "Y");
		DBObject queryresults = mongoDB.getCollection(ClientMeta).findOne(query);
		BasicDBList skim = (BasicDBList) queryresults.get("SkimNum");
		ArrayList list1=new ArrayList();
		if(skim != null){
    		Object[] tagObjects = skim.toArray();
    		for(Object dbobj : tagObjects){
    			if(dbobj instanceof DBObject){
    				HashMap<String, Object> temp=new HashMap<String, Object>();
    				temp.put("date", ((DBObject)dbobj).get("date").toString());
    				if(dateNowStr.equals(((DBObject)dbobj).get("date").toString())){
    					temp.put("num", Integer.parseInt( ((DBObject)dbobj).get("num").toString() ) +1);
    					result=true;
    				}else{
    					temp.put("num", Integer.parseInt( ((DBObject)dbobj).get("num").toString() ) );
    				}
    				list1.add(temp);
    			}
    		}
		}
		if(!result){
			HashMap<String, Object> temp=new HashMap<String, Object>();
			temp.put("date", dateNowStr);
			temp.put("num", 1);
			list1.add(temp);
		}
		BasicDBObject doc = new BasicDBObject();
		BasicDBObject update = new BasicDBObject();
    	update.append("SkimNum",list1);
		doc.put("$set", update);
		WriteResult wr = mongoDB.getCollection(ClientMeta).update(new BasicDBObject().append("Active", "Y"), doc);  
		result=true;
		return result;
}
	public static ClientMeta QueryClientMeta(){
		ClientMeta cm = new ClientMeta();
		mongoDB = getMongoDB();
	    try{
			DBObject query = new BasicDBObject();
			query.put("Active", "Y");
			DBObject queryresults = mongoDB.getCollection(ClientMeta).findOne(query);
			String clientCopyRight = queryresults.get("ClientCopyRight")==null?"":queryresults.get("ClientCopyRight").toString();
			String clientLogo = queryresults.get("ClientLogo")==null?"":queryresults.get("ClientLogo").toString();
			String clientName = queryresults.get("ClientName")==null?"":queryresults.get("ClientName").toString();
			String clientSubName = queryresults.get("ClientSubName")==null?"":queryresults.get("ClientSubName").toString();
			String clientThemeColor = queryresults.get("ClientThemeColor")==null?"":queryresults.get("ClientThemeColor").toString();
			String clientStockCode =queryresults.get("ClientCode")==null?"": queryresults.get("ClientCode").toString();
			String clientActive = queryresults.get("Active")==null?"":queryresults.get("Active").toString();
			BasicDBList skim = (BasicDBList) queryresults.get("SkimNum");
			if(skim != null){
    			ArrayList list1=new ArrayList();
        		Object[] sObjects = skim.toArray();
        		for(Object dbobj : sObjects){
        			if(dbobj instanceof DBObject){
        				HashMap<String, Object> temp=new HashMap<String, Object>();
        				temp.put("date", ((DBObject)dbobj).get("date").toString());
        				temp.put("num",  Integer.parseInt(((DBObject)dbobj).get("num").toString()) );
        				list1.add(temp);
        			}
        		}
        		cm.setSkimNum(list1);
    		}
			BasicDBList slide = (BasicDBList) queryresults.get("Slide");
    		if(slide != null){
    			ArrayList list=new ArrayList();
        		Object[] tagObjects = slide.toArray();
        		for(Object dbobj : tagObjects){
        			if(dbobj instanceof DBObject){
        				list.add( ((DBObject)dbobj).get("src").toString());
        			}
        		}
        		cm.setSlide(list);
    		}
    		
			cm.setClientCopyRight(clientCopyRight);
			cm.setClientLogo(clientLogo);
			cm.setClientName(clientName);
			cm.setClientSubName(clientSubName);
			cm.setClientActive(clientActive);
			cm.setClientStockCode(clientStockCode);
			cm.setClientThemeColor(clientThemeColor);
	    }
		catch(Exception e){
			log.info("QueryClientMeta--" + e.getMessage());
		}
	    return cm;
	}
	public static String ActivaeClientMeta(String clientCode){
		String cm = "";
		mongoDB = getMongoDB();
	    try{
			DBObject query = new BasicDBObject();
			query.put("ClientCode", clientCode);
			String clientName =mongoDB.getCollection(ClientMeta).findOne(query).get("ClientName").toString();
			if(!StringUtils.isEmpty(clientName)){
				BasicDBObject doc = new BasicDBObject();
				DBObject update = new BasicDBObject();
				update.put("Active", "Y");
				doc.put("$set", update); 
				WriteResult wr = mongoDB.getCollection(ClientMeta).update(new BasicDBObject().append("ClientCode", clientCode), doc);  
				
				
				//disable other clients
				DBObject q = new BasicDBObject();
				q.put("ClientCode", new BasicDBObject("$ne", clientCode));
				q.put("Active","Y");
				DBCursor dbcur = mongoDB.getCollection(ClientMeta).find(q);
	            if (null != dbcur) {
	            	while(dbcur.hasNext()){
	            		DBObject o = dbcur.next();
	    				DBObject qry = new BasicDBObject();
	    				qry.put("ClientCode", new BasicDBObject("$ne", clientCode));
	    				qry.put("Active","Y");
	    				BasicDBObject doc1 = new BasicDBObject();
	    				DBObject update1 = new BasicDBObject();
	    				update1.put("Active", "N");
	    				doc1.put("$set", update1); 
	    				WriteResult wr1 = mongoDB.getCollection(ClientMeta).update(qry, doc1); 
	            	}
	            }
	            cm = clientName;
			}
	    }
		catch(Exception e){
			log.info("ActivaeClientMeta--" + e.getMessage());
			cm =  e.getMessage();
		}
	    return cm;
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
	public static boolean checkUserRole(String OpenID,String role){
		mongoDB = getMongoDB();
	    try{
	    	BasicDBList condList = new BasicDBList();
	    	if("Internal".equals(role)){
	    		DBObject query1 = new BasicDBObject();
		    	query1.put("Role.isInternalSeniorMgt", true);
		    	condList.add(query1);
		    	
		    	DBObject query2 = new BasicDBObject();
		    	query2.put("Role.isInternalImtMgt", true);
		    	condList.add(query2);
		    	
		    	DBObject query3 = new BasicDBObject();
		    	query3.put("Role.isITOperations", true);
		    	condList.add(query3);
		    	
		    	DBObject query4 = new BasicDBObject();
		    	query4.put("Role.isInternalQuoter", true);
		    	condList.add(query4);
		    	
	    	}else if("InternalApprover".equals(role)){
	    		DBObject query1 = new BasicDBObject();
		    	query1.put("Role.isInternalSeniorMgt", true);
		    	condList.add(query1);
		    	
	    	}
	    	else if("External".equals(role)){
	    		DBObject query1 = new BasicDBObject();
		    	query1.put("Role.isExternalUpStream", true);
		    	condList.add(query1);
		    	
		    	DBObject query2 = new BasicDBObject();
		    	query2.put("Role.isExternalCustomer", true);
		    	condList.add(query2);
		    	
		    	DBObject query3 = new BasicDBObject();
		    	query3.put("Role.isExternalPartner", true);
		    	condList.add(query3);
		    	
		    	DBObject query4 = new BasicDBObject();
		    	query4.put("Role.isExternalCompetitor", true);
		    	condList.add(query4);
	    	}else{
	    		return false;
	    	}
	    	BasicDBObject searchCond = new BasicDBObject();
	    	searchCond.put("$or", condList);
	    	searchCond.put("OpenID", OpenID);
	    	
	    	DBObject queryresult = mongoDB.getCollection(wechat_user).findOne(searchCond);
	    	if(queryresult != null){
	    		return true;
	    	}
	    }
		catch(Exception e){
			log.info("queryEmail--" + e.getMessage());
		}
		return false;
	}
	public static boolean checkUserAuth(String OpenID,String RoleName){
		mongoDB = getMongoDB();
	    try{
	    	DBObject query = new BasicDBObject();
	    	query.put("OpenID", OpenID);
	    	query.put("Role."+RoleName, true);
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
	public static boolean delNullUser(){
		Boolean ret = false;
		mongoDB = getMongoDB();
		DBCollection dbCol = mongoDB.getCollection(wechat_user);  
        BasicDBObject doc = new BasicDBObject();  
        doc.put("OpenID", null);  
        dbCol.remove(doc);  
        ret = true;
		return ret;
	}
	public static boolean syncWechatUserToMongo(WeChatUser wcu){
		mongoDB = getMongoDB();
		boolean result=false;
		WeChatUser ret = null;
	    try{
	    	DBObject query = new BasicDBObject();
	    	query.put("OpenID", wcu.getOpenid());
	    	DBObject queryresult = mongoDB.getCollection(wechat_user).findOne(query);
	    	if(queryresult != null){
	    		result=updateUser(wcu);
	    	}else{
	    		result=createUser(wcu);
	    	}
	    	result=true;
	    }
		catch(Exception e){

		}
		return result;
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
	    	insert.put("IsActive", "false");
	    	insert.put("Role",new Role());
	    	
	    	DBObject RoleObj = new BasicDBObject();
	    	Role role=new Role();
	    	RoleObj.put("isExternalUpStream", role.isExternalUpStream());
	    	RoleObj.put("isExternalCustomer", role.isExternalCustomer());
	    	RoleObj.put("isExternalPartner", role.isExternalPartner());
	    	RoleObj.put("isExternalCompetitor", role.isExternalCompetitor());
	    	RoleObj.put("isInternalSeniorMgt", role.isInternalSeniorMgt());
	    	RoleObj.put("isInternalImtMgt", role.isInternalImtMgt());
	    	RoleObj.put("isInternalBizEmp", role.isInternalBizEmp());
	    	RoleObj.put("isInternalNonBizEmp", role.isInternalNonBizEmp());
	    	RoleObj.put("isInternalQuoter", role.isInternalQuoter());
	    	RoleObj.put("isITOperations", role.isITOperations());

	    	insert.put("Role", RoleObj);
	    	
	    	
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
            		dbo.put("Teamer.selfIntro", teamer.getSelfIntro()); 
            		//dbo.put("Teamer.groupid", teamer.getGroupid()); 
            		dbo.put("Teamer.realName", teamer.getRealName()); 
        			dbo.put("Teamer.email", teamer.getEmail());
        			dbo.put("Teamer.phone", teamer.getPhone());
        			//dbo.put("Teamer.role", teamer.getRole());
        			//dbo.put("Teamer.selfIntro", teamer.getSelfIntro());
        			//dbo.put("Teamer.suppovisor", teamer.getSuppovisor()); 
        			//dbo.put("Teamer.tag", teamer.getTag()); 
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
	
	public static boolean modifyOrgSiteInstance(String field, String source,String target, String cmd){
		mongoDB = getMongoDB();
		Boolean ret = false;
		try{
			if(StringUtils.isEqual(cmd, "remove")){
				DBObject removeQuery = new BasicDBObject();
				removeQuery.put(field, source);
				mongoDB.getCollection(collectionMasterDataName).remove(removeQuery);
				ret = true;
			}
			else if(StringUtils.isEqual(cmd, "modify")){
				for(int i = 0; i < 100 ; i++){
					DBObject findQuery = new BasicDBObject();
					findQuery.put(field, source);
					DBObject updateQuery = new BasicDBObject();
					updateQuery.put(field, target);
					BasicDBObject doc = new BasicDBObject();  
					doc.put("$set", updateQuery);
					mongoDB.getCollection(collectionMasterDataName).update(findQuery, doc);
				}
				ret = true;
			}
	    }
		catch(Exception e){
			log.info("modifyOrgSiteInstance--" + e.getMessage());
			ret = false;
		}
		return ret;
	}
	
	public static boolean updateClientMeta(ClientMeta cm){
		mongoDB = getMongoDB();
		Boolean ret = false;
	    try{
                BasicDBObject doc = new BasicDBObject();  
    	    	DBObject update = new BasicDBObject();
    	    	update.put("ClientCopyRight", cm.getClientCopyRight());
    	    	update.put("ClientLogo", cm.getClientLogo());
    	    	update.put("ClientName",cm.getClientName());
    	    	update.put("ClientSubName", cm.getClientSubName());
    	    	update.put("ClientThemeColor", cm.getClientThemeColor());
    	    	update.put("ClientName", cm.getClientName());
    	    	update.put("Slide", cm.getSlide());
    	    	doc.put("$set", update);  
    	    	WriteResult wr = mongoDB.getCollection(ClientMeta).update(new BasicDBObject().append("ClientCode", cm.getClientStockCode()), doc);
    			ret = true;
	    }
		catch(Exception e){
			log.info("updateUser--" + e.getMessage());
		}
		return ret;
	}
	public static boolean updateUser(WeChatUser wcu){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		Boolean ret = false;
	    try{
            BasicDBObject doc = new BasicDBObject();  
	    	DBObject update = new BasicDBObject();
	    	update.put("HeadUrl", wcu.getHeadimgurl());
	    	update.put("NickName", wcu.getNickname());
    	    update.put("Created", DateUtil.timestamp2Str(cursqlTS));
	    	doc.put("$set", update);  
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", wcu.getOpenid()), doc);
            ret = true;
	    }
		catch(Exception e){
			log.info("updateUser--" + e.getMessage());
		}
		return ret;
	}
	public static WeChatMDLUser queryUserKM(String openid){
		mongoDB = getMongoDB();
		WeChatMDLUser user=new WeChatMDLUser();
		ArrayList<String> kmLists = new ArrayList<String>();
		ArrayList<String> kmApproveLists = new ArrayList<String>();
	    try{
	    	DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", openid));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		if(o.get("kmLists")!=null){
            			BasicDBList hist = (BasicDBList) o.get("kmLists");
                		Object[] kmObjects = hist.toArray();
                		for(Object dbobj : kmObjects){
                			if(dbobj instanceof String){
                				kmLists.add((String) dbobj);
                			}
                		}
                		user.setKmLists(kmLists);
            		}
            		if(o.get("kmApproveLists")!=null){
            			BasicDBList histApprove = (BasicDBList) o.get("kmApproveLists");
            			Object[] kmApproveObjects = histApprove.toArray();
            			for(Object dbobj : kmApproveObjects){
                			if(dbobj instanceof String){
                				kmApproveLists.add((String) dbobj);
                			}
                		}
                		user.setKmApproveLists(kmApproveLists);
            		}
            		Object teamer = o.get("Teamer");
        			DBObject teamobj = new BasicDBObject();
        			teamobj = (DBObject)teamer;
        			if(teamobj != null){
        				if(teamobj.get("selfIntro") != null){
        					user.setSelfIntro(teamobj.get("selfIntro").toString());
        				}
        				if(teamobj.get("realName") != null){
        					user.setRealName(teamobj.get("realName").toString());
        				}
        				if(teamobj.get("phone") != null){
        					user.setPhone(teamobj.get("phone").toString());
        				}
        			}
            	}
            }
	    }
		catch(Exception e){
			log.info("queryUserKM--" + e.getMessage());
		}
		return user;
	}
	public static int findUserApproveNum(){
		mongoDB = getMongoDB();
		List<WeChatMDLUser> ret = new ArrayList<WeChatMDLUser>();
		WeChatMDLUser weChatMDLUser = null;
		DBCursor queryresults;
		BasicDBObject query = new BasicDBObject();
		query.append("kmApproveLists.0", new BasicDBObject().append("$exists",true));
		queryresults = mongoDB.getCollection(wechat_user).find(query);
		return queryresults.size();
	}
	public static boolean saveUserApproveKM(String openid,String kmItem,String flag){
		kmItem=kmItem.trim();
		mongoDB = getMongoDB();
		Boolean ret = false;
	    try{
	    	HashSet<String> kmSets = new HashSet<String>();
	    	DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", openid));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		if(o.get("kmApproveLists")!=null){
            			BasicDBList hist = (BasicDBList) o.get("kmApproveLists");
                		Object[] kmObjects = hist.toArray();
                		for(Object dbobj : kmObjects){
                			if(dbobj instanceof String){
                				if("del".equals(flag)){
                					if(!kmItem.equals((String) dbobj)){
                						kmSets.add((String) dbobj);
                					}
                				}else{
                					kmSets.add((String) dbobj);
                				}
                			}
                		}
            		}
            	}
            }
            BasicDBObject doc = new BasicDBObject();  
	    	DBObject update = new BasicDBObject();
	    	if("add".equals(flag)){
	    		kmSets.add(kmItem);
	    	}
    	    update.put("kmApproveLists",kmSets);
	    	doc.put("$set", update);  
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID",openid), doc);
            ret = true;
            
	    }
		catch(Exception e){
			log.info("saveUserKM--" + e.getMessage());
		}
		return ret;
	}
	public static boolean saveUserKM(String openid,String kmItem,String flag){
		kmItem=kmItem.trim();
		mongoDB = getMongoDB();
		Boolean ret = false;
		Boolean isSendMess=false;
	    try{
	    	HashSet<String> kmSets = new HashSet<String>();
	    	HashSet<String> kmApproveSets = new HashSet<String>();
	    	DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", openid));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		if(o.get("kmLists")!=null){
            			BasicDBList hist = (BasicDBList) o.get("kmLists");
                		Object[] kmObjects = hist.toArray();
                		for(Object dbobj : kmObjects){
                			if(dbobj instanceof String){
                				if("del".equals(flag)){
                					if(!kmItem.equals((String) dbobj)){
                						kmSets.add((String) dbobj);
                					}
                				}else{
                					kmSets.add((String) dbobj);
                				}
                			}
                		}
            		}
            		if(o.get("kmApproveLists")!=null){
            			BasicDBList histApprove = (BasicDBList) o.get("kmApproveLists");
                		Object[] kmApproveObjects = histApprove.toArray();
                		for(Object dbobj : kmApproveObjects){
                			if(dbobj instanceof String){
            					if(!kmItem.equals((String) dbobj)){
            						kmApproveSets.add((String) dbobj);
            					}else{
            						if("add".equals(flag)){
            							isSendMess=true;
            						}
            					}
                			}
                		}
            		}
            	}
            }
            BasicDBObject doc = new BasicDBObject();  
	    	DBObject update = new BasicDBObject();
	    	if("add".equals(flag)){
	    		kmSets.add(kmItem);
	    	}
    	    update.put("kmLists",kmSets);
    	    update.put("kmApproveLists",kmApproveSets);
	    	doc.put("$set", update);  
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID",openid), doc);
            ret = true;
            if(isSendMess){
            	WeChatMDLUser toWeChatMDLUser=new WeChatMDLUser();
            	toWeChatMDLUser.openid=openid;
            	
            	HashMap<String, String> res=getWeChatUserFromOpenID(openid);
            	
            	
            	String title=res.get("NickName")+"：您所申请的询价牌号【"+kmItem+"】已通过审核";
            	RestUtils.sendQuotationToUser(toWeChatMDLUser,"点击查看详细报价信息","https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DnUBS&oid=00D90000000pkXM",title,"http://"+Constants.baehost+"/mdm/quoteDetailExternal.jsp?UID="); 
            	
            }
	    }
		catch(Exception e){
			log.info("saveUserKM--" + e.getMessage());
		}
		return ret;
	}
	public static boolean updateUser(String OpenID){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		Boolean ret = false;
	    try{
            BasicDBObject doc = new BasicDBObject();  
	    	DBObject update = new BasicDBObject();
	    	update.put("LastUpdatedDate", DateUtil.timestamp2Str(cursqlTS));
	    	doc.put("$set", update);  
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", OpenID), doc);
            ret = true;
            addSkimNum();
	    }
		catch(Exception e){
			log.info("updateUser--" + e.getMessage());
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
            addSkimNum();
	    }
		catch(Exception e){
			log.info("updateUser--" + e.getMessage());
		}
		return ret;
	}
	
	
	public static boolean updateUserWithSignature(String openid, String svg){
		mongoDB = getMongoDB();
		boolean ret = false;
		try{
			BasicDBObject doc = new BasicDBObject();
			DBObject update = new BasicDBObject();
			update.put("Signature", svg);
			doc.put("$set", update); 
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", openid), doc);     
			ret = true;
		}
		catch(Exception e){
			log.info("updateUserWithSignature--" + e.getMessage());
		}
		return ret;
	}
	
	public static boolean updateUserWithManageStatus(WeChatMDLUser user){
		mongoDB = getMongoDB();
		boolean ret = false;
		try{
			BasicDBObject doc = new BasicDBObject();
			DBObject update = new BasicDBObject();
			update.put("IsActive", user.getIsActive());
			update.put("IsAuthenticated", user.getIsAuthenticated());
			update.put("IsRegistered", user.getIsRegistered());
			update.put("Teamer.registerDate", user.getRegisterDate());
			update.put("Teamer.realName", user.getRealName());
			update.put("Teamer.companyName", user.getCompanyName());
			update.put("Teamer.email", user.getEmail());
			update.put("Teamer.phone", user.getPhone());
			update.put("Teamer.selfIntro", user.getSelfIntro());
			update.put("Teamer.role", user.getRole());
			
			DBObject RoleObj = new BasicDBObject();
	    	Role role=user.getRoleObj();
	    	RoleObj.put("isExternalUpStream", role.isExternalUpStream());
	    	RoleObj.put("isExternalCustomer", role.isExternalCustomer());
	    	RoleObj.put("isExternalPartner", role.isExternalPartner());
	    	RoleObj.put("isExternalCompetitor", role.isExternalCompetitor());
	    	RoleObj.put("isInternalSeniorMgt", role.isInternalSeniorMgt());
	    	RoleObj.put("isInternalImtMgt", role.isInternalImtMgt());
	    	RoleObj.put("isInternalBizEmp", role.isInternalBizEmp());
	    	RoleObj.put("isInternalNonBizEmp", role.isInternalNonBizEmp());
	    	RoleObj.put("isInternalQuoter", role.isInternalQuoter());
	    	RoleObj.put("isITOperations", role.isITOperations());

	    	update.put("Role", RoleObj);
			doc.put("$set", update); 
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", user.getOpenid()), doc);     
			ret = true;
		}
		catch(Exception e){
			log.info("updateUserWithManageStatus--" + e.getMessage());
		}
		return ret;
	}
	
	public static boolean updateUserWithFaceUrl(String openid, String picurl){
		mongoDB = getMongoDB();
		boolean ret = false;
		try{
			BasicDBObject doc = new BasicDBObject();
			DBObject update = new BasicDBObject();
			update.put("FaceUrl", picurl);
			doc.put("$set", update); 
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", openid), doc);     
			ret = true;
		}
		catch(Exception e){
			log.info("updateUserWithFaceUrl--" + e.getMessage());
		}
		return ret;
	}

	public static boolean updateUserWithLike(String openid,String likeToName,String ToOpenId){
		mongoDB = getMongoDB();
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		boolean ret = false;
		try{
			BasicDBObject doc = new BasicDBObject();
			DBObject innerInsert = new BasicDBObject();
	    	innerInsert.put("Like.lastLikeTo", likeToName);
	    	innerInsert.put("Like.lastLikeDate", DateUtil.timestamp2Str(cursqlTS));
			doc.put("$set", innerInsert); 
			WriteResult wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", openid), doc); 
			
			doc = new BasicDBObject();
			BasicDBObject update = new BasicDBObject();
	    	update.append("Like.number",1);
			doc.put("$inc", update);
			wr = mongoDB.getCollection(wechat_user).update(new BasicDBObject().append("OpenID", ToOpenId), doc); 
			ret = true;
		}
		catch(Exception e){
			log.info("updateUserWithSignature--" + e.getMessage());
		}
		return ret;
	}
	public static String getUserWithSignature(String openid){
		mongoDB = getMongoDB();
		String ret = "";
		try{
			ret = mongoDB.getCollection(wechat_user).findOne(new BasicDBObject().append("OpenID", openid)).get("Signature").toString();
		}
		catch(Exception e){
			log.info("getUserWithSignature--" + e.getMessage());
			ret = e.getMessage();
		}
		return ret;
	}
	public static String getUserWithFaceUrl(String openid){
		mongoDB = getMongoDB();
		String ret = "";
		try{
			ret = mongoDB.getCollection(wechat_user).findOne(new BasicDBObject().append("OpenID", openid)).get("FaceUrl").toString();
		}
		catch(Exception e){
			log.info("getUserWithSignature--" + e.getMessage());
			e.getMessage();
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
					
/*	    	DBObject query1 = new BasicDBObject("state", "é‡�åº†å¸‚");  
	    	DBObject query2 = new BasicDBObject("state", "é‡�åº†");     
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
	    	DBObject query2 = new BasicDBObject("state", "é‡�åº†");     
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
	 * purpose to get userState list .eg ä¸Šæµ·å¸‚ï¼Œé‡�åº†å¸‚
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
	public static ArrayList<ClientMeta> QueryClientMetaList(){
		ArrayList<ClientMeta> result=new ArrayList<ClientMeta>();
		mongoDB = getMongoDB();
		DBCursor queryresults;
	    try{
	    	BasicDBObject sort=new BasicDBObject();
			sort.put("Active", -1);
			queryresults = mongoDB.getCollection(ClientMeta).find().limit(500).sort(sort);
			if (null != queryresults) {
            	while(queryresults.hasNext()){
            		ClientMeta cm = new ClientMeta();
            		DBObject o = queryresults.next();
            		String clientCopyRight = o.get("ClientCopyRight")==null?"":o.get("ClientCopyRight").toString();
        			String clientLogo = o.get("ClientLogo")==null?"":o.get("ClientLogo").toString();
        			String clientName = o.get("ClientName")==null?"":o.get("ClientName").toString();
        			String clientSubName = o.get("ClientSubName")==null?"":o.get("ClientSubName").toString();
        			String clientThemeColor = o.get("ClientThemeColor")==null?"":o.get("ClientThemeColor").toString();
        			String clientStockCode = o.get("ClientCode")==null?"":o.get("ClientCode").toString();
        			String clientActive =o.get("Active")==null?"": o.get("Active").toString();
        			BasicDBList slide = (BasicDBList) o.get("Slide");
            		if(slide != null){
            			ArrayList<String> list=new ArrayList<String>();
                		Object[] tagObjects = slide.toArray();
                		for(Object dbobj : tagObjects){
                			if(dbobj instanceof DBObject){
                				list.add(((DBObject)dbobj).get("src").toString());
                			}
                		}
                		cm.setSlide(list);
            		}
        			cm.setClientCopyRight(clientCopyRight);
        			cm.setClientLogo(clientLogo);
        			cm.setClientName(clientName);
        			cm.setClientSubName(clientSubName);
        			cm.setClientActive(clientActive);
        			cm.setClientStockCode(clientStockCode);
        			cm.setClientThemeColor(clientThemeColor);
        			result.add(cm);
            	}
            }
	    }
		catch(Exception e){
			log.info("QueryClientMeta--" + e.getMessage());
		}
	    return result;
	}
	public static HashMap<String, String> getWeChatUserFromOpenID(String OpenID){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		query.put("OpenID", OpenID);
		HashMap<String, String> res=null;
		DBObject queryresults = mongoDB.getCollection(wechat_user).findOne(query);
		if (null != queryresults) {
    		DBObject o = queryresults;
    		res=new HashMap<String, String>();
			if(o.get("HeadUrl") != null){
    			res.put("HeadUrl", o.get("HeadUrl").toString());
    		}
			if(o.get("NickName") != null){
    			res.put("NickName", o.get("NickName").toString());
    		}
			Object teamer = o.get("Teamer");
			DBObject teamobj = new BasicDBObject();
			teamobj = (DBObject)teamer;
			if(teamobj != null){
				if(teamobj.get("realName") != null){
					res.put("NickName", teamobj.get("realName").toString());
				}
			}
			if(o.get("IsAuthenticated") != null){
    			res.put("IsAuthenticated", o.get("IsAuthenticated").toString());
    		}
			String selfIntro=""; 
			if(teamobj.get("selfIntro") != null){
				selfIntro=teamobj.get("selfIntro").toString();
			}
			String[] aStrings=new Market().getMarket(selfIntro);
			res.put("market0", aStrings[0]);
			res.put("market1", aStrings[1]);
			res.put("market2", aStrings[2]);
		}
		return res;
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
				sort.put("IsActive", -1);
				sort.put("IsRegistered", -1);
				sort.put("LastUpdatedDate", 1);
				/*sort.put("Teamer.registerDate", 1);
				sort.put("IsActive", -1);
				sort.put("Created", 1);*/
				queryresults = mongoDB.getCollection(wechat_user).find().limit(500).sort(sort);
			}
            if (null != queryresults) {
            	while(queryresults.hasNext()){
            		weChatMDLUser = new WeChatMDLUser();
            		DBObject o = queryresults.next();
            		
            		ArrayList<String> kmApproveLists=new ArrayList<String>();
            		if(o.get("kmApproveLists")!=null){
            			BasicDBList histApprove = (BasicDBList) o.get("kmApproveLists");
            			Object[] kmApproveObjects = histApprove.toArray();
            			for(Object dbobj : kmApproveObjects){
                			if(dbobj instanceof String){
                				kmApproveLists.add((String) dbobj);
                			}
                		}
            			weChatMDLUser.setKmApproveLists(kmApproveLists);
            		}
            		if(o.get("OpenID") != null){
            			weChatMDLUser.setOpenid(o.get("OpenID").toString());
                		if(o.get("CurLAT") != null){
                			weChatMDLUser.setLat(o.get("CurLAT").toString());
                		}
                		if(o.get("CurLNG") != null){
                			weChatMDLUser.setLng(o.get("CurLNG").toString());
                		}
                		if(o.get("LastUpdatedDate") != null){
                			weChatMDLUser.setLastUpdatedDate(o.get("LastUpdatedDate").toString());
                		}
                		if(o.get("HeadUrl") != null){
                			weChatMDLUser.setHeadimgurl(o.get("HeadUrl").toString());
                		}
                		if(o.get("NickName") != null){
                			weChatMDLUser.setNickname(o.get("NickName").toString());
                		}
                		Object CongratulateHistory = o.get("CongratulateHistory");
                		BasicDBList CongratulateHistoryObj = (BasicDBList)CongratulateHistory;
                		if(CongratulateHistoryObj != null){
                			ArrayList conList=new ArrayList();
                    		Object[] ConObjects = CongratulateHistoryObj.toArray();
                    		weChatMDLUser.setCongratulateNum(ConObjects.length);
                		}
                		ArrayList<String> kmLists = new ArrayList<String>();
                		if(o.get("kmLists")!=null){
                			BasicDBList hist = (BasicDBList) o.get("kmLists");
                    		Object[] kmObjects = hist.toArray();
                    		for(Object dbobj : kmObjects){
                    			if(dbobj instanceof String){
                    				kmLists.add((String) dbobj);
                    			}
                    		}
                    		weChatMDLUser.setKmLists(kmLists);
                		}
                		Object role = o.get("Role");
            			DBObject roleobj = new BasicDBObject();
            			roleobj = (DBObject)role;
            			Role RoleObj=new Role();
            			if(roleobj != null){
            				if(roleobj.get("isExternalUpStream") != null){
            					RoleObj.setExternalUpStream(Boolean.parseBoolean(roleobj.get("isExternalUpStream").toString()));
            				}
            				if(roleobj.get("isExternalCustomer") != null){
            					RoleObj.setExternalCustomer(Boolean.parseBoolean(roleobj.get("isExternalCustomer").toString()));
            				}
            				if(roleobj.get("isExternalPartner") != null){
            					RoleObj.setExternalPartner(Boolean.parseBoolean(roleobj.get("isExternalPartner").toString()));
            				}
            				if(roleobj.get("isExternalCompetitor") != null){
            					RoleObj.setExternalCompetitor(Boolean.parseBoolean(roleobj.get("isExternalCompetitor").toString()));
            				}
            				if(roleobj.get("isInternalSeniorMgt") != null){
            					RoleObj.setInternalSeniorMgt(Boolean.parseBoolean(roleobj.get("isInternalSeniorMgt").toString()));
            				}
            				if(roleobj.get("isInternalImtMgt") != null){
            					RoleObj.setInternalImtMgt(Boolean.parseBoolean(roleobj.get("isInternalImtMgt").toString()));
            				}
            				if(roleobj.get("isInternalBizEmp") != null){
            					RoleObj.setInternalBizEmp(Boolean.parseBoolean(roleobj.get("isInternalBizEmp").toString()));
            				}
            				if(roleobj.get("isInternalNonBizEmp") != null){
            					RoleObj.setInternalNonBizEmp(Boolean.parseBoolean(roleobj.get("isInternalNonBizEmp").toString()));
            				}
            				if(roleobj.get("isInternalQuoter") != null){
            					RoleObj.setInternalQuoter(Boolean.parseBoolean(roleobj.get("isInternalQuoter").toString()));
            				}
            				if(roleobj.get("isITOperations") != null){
            					RoleObj.setITOperations(Boolean.parseBoolean(roleobj.get("isITOperations").toString()));
            				}
            				
            			}
                		
            			weChatMDLUser.setRoleObj(RoleObj);
            			
            			
                		Object teamer = o.get("Teamer");
            			DBObject teamobj = new BasicDBObject();
            			teamobj = (DBObject)teamer;
            			if(teamobj != null){
            				if(teamobj.get("selfIntro") != null){
            					weChatMDLUser.setSelfIntro(teamobj.get("selfIntro").toString());
            				}
            				if(teamobj.get("phone") != null){
            					weChatMDLUser.setPhone(teamobj.get("phone").toString());
            				}
            				if(teamobj.get("realName") != null){
            					weChatMDLUser.setRealName(teamobj.get("realName").toString());
            				}
            				if(teamobj.get("role") != null){
            					weChatMDLUser.setRole(teamobj.get("role").toString());
            				}
            				if(teamobj.get("companyName") != null){
                    			weChatMDLUser.setCompanyName(teamobj.get("companyName").toString());
                    		}
            				if(teamobj.get("registerDate") != null){
            					weChatMDLUser.setRegisterDate(teamobj.get("registerDate").toString());
            					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                				String dstr=teamobj.get("registerDate").toString();
                				dstr=dstr.replaceAll("/", "-");
                				java.util.Date date=sdf.parse(dstr);
                				long  s1=date.getTime();//å°†æ—¶é—´è½¬ä¸ºæ¯«ç§’
                				long s2=System.currentTimeMillis();//å¾—åˆ°å½“å‰�çš„æ¯«ç§’
                				int  day=(int) ((s2-s1)/1000/60/60/24)+1;
                				weChatMDLUser.setWorkDay(day);
            				}
            				if(teamobj.get("tag") != null){
            					BasicDBList hist = (BasicDBList) teamobj.get("tag");
                    			ArrayList list=new ArrayList();
                        		Object[] tagObjects = hist.toArray();
                        		for(Object dbobj : tagObjects){
                        			if(dbobj instanceof DBObject){
                        				HashMap<String, String> temp=new HashMap<String, String>();
                        				temp.put(((DBObject)dbobj).get("key").toString(), ((DBObject)dbobj).get("value").toString());
                        				list.add(temp);
                        			}
                        		}
                        		weChatMDLUser.setTag(list);
                    		}
            			}
            			Object likeobj = o.get("Like");
            			DBObject like= new BasicDBObject();
            			like= (DBObject)likeobj;
            			HashMap<String, String> likeMap=new HashMap<String, String>();
            			likeMap.put("number","");
            			likeMap.put("lastLikeTo","");
            			likeMap.put("lastLikeDate","");
            			if(like != null){
            				if(like.get("number") != null){
            					likeMap.put("number", like.get("number").toString());
            				}
            				if(like.get("lastLikeTo") != null)
            				{
            					likeMap.put("lastLikeTo", like.get("lastLikeTo").toString());
            				}
            				if(like.get("lastLikeDate") != null)
            				{
            					likeMap.put("lastLikeDate", like.get("lastLikeDate").toString());
            				}
            			}
            			weChatMDLUser.setLike(likeMap);
            			if(o.get("IsActive") != null){
                			weChatMDLUser.setIsActive(o.get("IsActive").toString());
                		}
            			if(o.get("IsAuthenticated") != null){
                			weChatMDLUser.setIsAuthenticated(o.get("IsAuthenticated").toString());
                		}
            			if(o.get("IsRegistered") != null){
                			weChatMDLUser.setIsRegistered(o.get("IsRegistered").toString());
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
					opsi.setOrganizationExtendedName(objOpsi.get("organizationExtendedName") == null ? "" : objOpsi.get("organizationExtendedName").toString() );
					opsi.setIsCompetitor(objOpsi.get("isCompetitor") == null ? "" : objOpsi.get("isCompetitor").toString());
					opsi.setIncludePartnerOrgIndicator(objOpsi.get("includePartnerOrgIndicator") == null ? "" : objOpsi.get("includePartnerOrgIndicator").toString() );
					opsi.setOnlyPresaleCustomer(objOpsi.get("onlyPresaleCustomer")== null ? "" : objOpsi.get("onlyPresaleCustomer").toString());
					opsi.setStreetAddress1(objOpsi.get("streetAddress1")== null ? "" : objOpsi.get("streetAddress1").toString());
					opsi.setIndustrySegmentNames(objOpsi.get("industrySegmentNames")== null ? "" : objOpsi.get("industrySegmentNames").toString());
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
						//nonlatinCity=nonlatinCity.substring(0,4)+" "+nonlatinCity.substring(4);
						
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

		/*
		 * chang-zheng
		 *  getCountryName
		 */
		public static List<OrgOtherPartySiteInstance> getAllCountryName(){
			mongoDB = getMongoDB();
			List<OrgOtherPartySiteInstance> listOfRegion = new ArrayList<OrgOtherPartySiteInstance>();
			List results;
		    try{
		    	DBObject dbquery = new BasicDBObject();  
		    	results = mongoDB.getCollection(collectionMasterDataName).distinct("countryCode", dbquery);

		    	for(int i=0; i<results.size();i++){
		    		Locale obj = new Locale("", results.get(i).toString());
		    		OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
		    		opsi.setCountryCode(obj.getCountry());
		    		opsi.setCountryName(obj.getDisplayCountry());
		    		listOfRegion.add(opsi);
		    	}
		    }
			catch(Exception e){
				log.info("getFilterRegionFromMongo--" + e.getMessage());
			}
		    return listOfRegion;
		}
		
		/*
		 * chang-zheng
		 * to update user CongratulateHistory
		 */
		public static boolean updateUserCongratulateHistory(String OpenID,CongratulateHistory conhis){
			mongoDB = getMongoDB();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			Boolean ret = false;
		    try{
		    	List<DBObject> arrayHistdbo = new ArrayList<DBObject>();
		    	DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", OpenID));
	            if (null != dbcur) {
	            	while(dbcur.hasNext()){
	            		DBObject o = dbcur.next();
	            		BasicDBList hist = (BasicDBList) o.get("CongratulateHistory");
	            		if(hist != null){
	                		Object[] CongratulateHistory = hist.toArray();
	                		for(Object dbobj : CongratulateHistory){
	                			if(dbobj instanceof DBObject){
	                				arrayHistdbo.add((DBObject)dbobj);
	                			}
	                		}
	            		}
	            	}

	                BasicDBObject doc = new BasicDBObject();  
	    	    	DBObject update = new BasicDBObject();
	    	    	DBObject innerInsert = new BasicDBObject();
	    	    	innerInsert.put("num", conhis.getNum());
	    	    	innerInsert.put("from", conhis.getFrom());
	    	    	innerInsert.put("to", conhis.getTo());
	    	    	innerInsert.put("comments", conhis.getComments());
	    	    	innerInsert.put("type", conhis.getType());
	    	    	innerInsert.put("point", conhis.getPoint());
	    	    	innerInsert.put("giftImg", conhis.getGiftImg());
	    	    	innerInsert.put("userImg", conhis.getUserImg());
	    	    	innerInsert.put("congratulateDate", DateUtil.timestamp2Str(cursqlTS));
	    	    	arrayHistdbo.add(innerInsert);
	    	    	update.put("CongratulateHistory", arrayHistdbo);
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

		/*
		 * chang-zheng
		 */
		public static List<String> getRegisterUserByOpenID(String openID){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("OpenID", openID);
			@SuppressWarnings("unchecked")
			List<String> dbuser = mongoDB.getCollection(wechat_user).distinct("Teamer.realName",query);
				return dbuser;
		}
		/*
		 * Panda
		 */
		public static List<CongratulateHistory> getRecognitionInfoByOpenID(String OpenID,String num) {
			mongoDB = getMongoDB();
			List<CongratulateHistory> chList = new ArrayList<CongratulateHistory>();
			CongratulateHistory ch = null;
			DBCursor queryresults;
			try{
					DBObject query = new BasicDBObject();
					query.put("OpenID", OpenID);
					queryresults = mongoDB.getCollection(wechat_user).find(query).limit(1);
				if (null != queryresults) {
	            	while(queryresults.hasNext()){
					DBObject o = queryresults.next();
	                Object CongratulateHistory = o.get("CongratulateHistory");
	                BasicDBList CongratulateHistoryObj = (BasicDBList)CongratulateHistory;
	                if(CongratulateHistoryObj != null){
	                    Object[] ConObjects = CongratulateHistoryObj.toArray();
						for(Object co : ConObjects){
	                        			if(co instanceof DBObject){
	                        				if(!StringUtils.isEmpty(num)){
	                        					if(null!=((DBObject)co).get("num"))
	                        					{
	                        						if(num.equals(((DBObject)co).get("num").toString()))
	                        						{
	                    							ch=new CongratulateHistory();
	    											ch.setComments(((DBObject)co).get("comments").toString());
	    											ch.setCongratulateDate(((DBObject)co).get("congratulateDate").toString().substring(0,11));
	    											ch.setFrom(((DBObject)co).get("from").toString());
	    											ch.setTo(((DBObject)co).get("to").toString());
	    											ch.setPoint(((DBObject)co).get("point").toString());
	    											ch.setType(((DBObject)co).get("type").toString());
	    											ch.setGiftImg(((DBObject)co).get("giftImg").toString());
	    											ch.setUserImg(((DBObject)co).get("userImg").toString());
	    											chList.add(ch);
	                    						}
	                        					}
	                    					}
	                        				else{
	                        					ch=new CongratulateHistory();
    											ch.setComments(((DBObject)co).get("comments").toString());
    											ch.setCongratulateDate(((DBObject)co).get("congratulateDate").toString().substring(0,11));
    											ch.setFrom(((DBObject)co).get("from").toString());
    											ch.setTo(((DBObject)co).get("to").toString());
    											ch.setPoint(((DBObject)co).get("point").toString());
    											ch.setType(((DBObject)co).get("type").toString());
    											ch.setGiftImg(((DBObject)co).get("giftImg").toString());
    											ch.setUserImg(((DBObject)co).get("userImg").toString());
    											chList.add(ch);
	                        				}
	                        				
	                        			}
	                        		}
	                		}
	            	}
				}
			}catch(Exception e){
				log.info("getWeChatUserFromMongoDB--" + e.getMessage());
			}
			return chList;
		}
		
		/*
		 * Panda
		 */
		public static int getRecognitionMaxNumByOpenID(String OpenID) {
			int num=0;
			mongoDB = getMongoDB();
			DBCursor queryresults;
			try{
					DBObject query = new BasicDBObject();
					query.put("OpenID", OpenID);
					queryresults = mongoDB.getCollection(wechat_user).find(query).limit(1);
				if (null != queryresults) {
	            	while(queryresults.hasNext()){
					DBObject o = queryresults.next();
	                Object CongratulateHistory = o.get("CongratulateHistory");
	                BasicDBList CongratulateHistoryObj = (BasicDBList)CongratulateHistory;
	                if(CongratulateHistoryObj != null){
	                	
	                    Object[] ConObjects = CongratulateHistoryObj.toArray();
						for(Object co : ConObjects){
	                        			if(co instanceof DBObject){
	                        				if(null!=((DBObject)co).get("num"))
	                        				{
	                        					num=Integer.parseInt(String.valueOf( ((DBObject)co).get("num")).trim());
	                        					}
	                        			}
	                        		}
	                		}
	            	}
				}
			}catch(Exception e){
				log.info("getWeChatUserFromMongoDB--" + e.getMessage());
			}
			return num;
		}
		public static int getNotificationMaxNumByOpenID(String OpenID) {
			int num=0;
			mongoDB = getMongoDB();
			DBCursor queryresults;
			try{
					DBObject query = new BasicDBObject();
					query.put("OpenID", OpenID);
					queryresults = mongoDB.getCollection(wechat_user).find(query).limit(1);
				if (null != queryresults) {
	            	while(queryresults.hasNext()){
					DBObject o = queryresults.next();
	                Object TechnologyCar = o.get("TechnologyCar");
	                BasicDBList TechnologyCarObj = (BasicDBList)TechnologyCar;
	                if(TechnologyCarObj != null){
	                	
	                    Object[] ConObjects = TechnologyCarObj.toArray();
						for(Object co : ConObjects){
	                        			if(co instanceof DBObject){
	                        				if(null!=((DBObject)co).get("num"))
	                        				{
	                        					num=Integer.parseInt(String.valueOf( ((DBObject)co).get("num")).trim());
	                        					}
	                        			}
	                        		}
	                		}
	            	}
				}
			}catch(Exception e){
				log.info("getWeChatUserFromMongoDB--" + e.getMessage());
			}
			return num;
		}
		public static String getRegisterUserByrealName(String realName){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("Teamer.realName", realName);
			@SuppressWarnings("unchecked")
			List<String> dbuser = mongoDB.getCollection(wechat_user).distinct("OpenID",query);
			if(dbuser!=null){
				return dbuser.get(0);
			}
			return "null";
		}
		
		/*
		 * chang-zheng
		 */
		public static List<String> getAllRegisterUsers(){
			mongoDB = getMongoDB();
			@SuppressWarnings("unchecked")
			List<String> lst = mongoDB.getCollection(wechat_user).distinct("Teamer.realName");
			return lst;
		}
		

		/*
		 * chang-zheng
		 */
		public static String getfaceURL(String openID){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("OpenID", openID);
			@SuppressWarnings("unchecked")
			List<String> dbuser = mongoDB.getCollection(wechat_user).distinct("FaceUrl",query);
				return dbuser==null?"":dbuser.get(0);
		}
		

		/*
		 * chang-zheng
		 */
		public static List<String> getAllOpenIDByIsActivewithIsRegistered(){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("IsActive", "true");
			query.put("IsRegistered", "true");
			@SuppressWarnings("unchecked")
			List<String> dbuser = mongoDB.getCollection(wechat_user).distinct("OpenID",query);
				
			return dbuser;
		}
		public static List<String> getAllOpenIDByIsRegistered(){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("IsRegistered", "true");
			@SuppressWarnings("unchecked")
			List<String> dbuser = mongoDB.getCollection(wechat_user).distinct("OpenID",query);
				
			return dbuser;
		}
		public static List<WeChatMDLUser> getAllUserByIsRegistered(){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("IsRegistered", "true");
			DBCursor queryresults = mongoDB.getCollection(wechat_user).find(query);
			List<WeChatMDLUser> ret = new ArrayList<WeChatMDLUser>();
			if (null != queryresults) {
            	while(queryresults.hasNext()){
            		WeChatMDLUser weChatMDLUser = new WeChatMDLUser();
            		DBObject o = queryresults.next();
            		if(o.get("OpenID") != null){
            			weChatMDLUser.setOpenid(o.get("OpenID").toString());
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
            			}
            			ret.add(weChatMDLUser);
            		}
            	}
			}
			return ret;
		}
		public static List<WeChatMDLUser> getAllUserByIsInternalSeniorMgt(){
			mongoDB = getMongoDB();
			DBObject query = new BasicDBObject();
			query.put("Role.isInternalSeniorMgt", true);
			DBCursor queryresults = mongoDB.getCollection(wechat_user).find(query);
			List<WeChatMDLUser> ret = new ArrayList<WeChatMDLUser>();
			if (null != queryresults) {
            	while(queryresults.hasNext()){
            		WeChatMDLUser weChatMDLUser = new WeChatMDLUser();
            		DBObject o = queryresults.next();
            		if(o.get("OpenID") != null){
            			weChatMDLUser.setOpenid(o.get("OpenID").toString());
            			if(o.get("NickName") != null){
                			weChatMDLUser.setNickname(o.get("NickName").toString());
                		}
            			if(o.get("HeadUrl") != null){
                			weChatMDLUser.setHeadimgurl(o.get("HeadUrl").toString());
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
            			}
            			ret.add(weChatMDLUser);
            		}
            	}
			}
			return ret;
		}
		/*
		 * chang-zheng to update user CongratulateHistory
		 */
		public static boolean updateNotification(String OpenID, Notification note) {
			mongoDB = getMongoDB();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(
					new java.util.Date().getTime());
			Boolean ret = false;
			try {
				List<DBObject> arrayTcar = new ArrayList<DBObject>();
				DBCursor dbcur = mongoDB.getCollection(wechat_user).find(new BasicDBObject().append("OpenID", OpenID));
				if (null != dbcur) {
					while (dbcur.hasNext()) {
						DBObject o = dbcur.next();
						BasicDBList hist = (BasicDBList) o.get("TechnologyCar");
						if (hist != null) {
							Object[] TechnologyCar = hist.toArray();
							for (Object dbobj : TechnologyCar) {
								if (dbobj instanceof DBObject) {
									arrayTcar.add((DBObject) dbobj);
								}
							}
						}
					}

					BasicDBObject doc = new BasicDBObject();
					DBObject update = new BasicDBObject();
					DBObject innerInsert = new BasicDBObject();
					innerInsert.put("num", note.getNum());
					innerInsert.put("content", note.getContent());
					innerInsert.put("time", note.getTime());
					innerInsert.put("type", note.getType());
					innerInsert.put("title", note.getTitle());
					
					arrayTcar.add(innerInsert);
					update.put("TechnologyCar", arrayTcar);
					doc.put("$set", update);
					WriteResult wr = mongoDB.getCollection(wechat_user).update(
							new BasicDBObject().append("OpenID", OpenID), doc);
				}
				ret = true;
			} catch (Exception e) {
				log.info("updateUser--" + e.getMessage());
			}
			return ret;
		}
		
		/*
		 * chang-zheng to update user getNotification
		 */
	public static List<Notification> getNotification(String OpenID, String num) {
		mongoDB = getMongoDB();
		List<Notification> nfList = new ArrayList<Notification>();
		DBObject query = new BasicDBObject();
		query.put("OpenID", OpenID);
		DBCursor queryresults = mongoDB.getCollection(wechat_user).find(query).limit(1);
		if (null != queryresults) {
			while (queryresults.hasNext()) {
				DBObject o = queryresults.next();
				Object TechnologyCar = o.get("TechnologyCar");
				BasicDBList TechnologyCarObj = (BasicDBList) TechnologyCar;
				if (TechnologyCarObj != null) {
					Notification nt = new Notification();
					Object[] ConObjects = TechnologyCarObj.toArray();
					for (Object co : ConObjects) {
						if (co instanceof DBObject) {
							if (!StringUtils.isEmpty(num)) {
								if (null != ((DBObject) co).get("num")) {
									if (num.equals(((DBObject) co).get("num").toString())) {
										nt.setContent(((DBObject) co).get("content").toString());
										nt.setNum(num);
										nt.setTime(((DBObject) co).get("time").toString());
										nt.setTitle(((DBObject) co).get("title").toString());
										nt.setType(((DBObject) co).get("type").toString());
										nfList.add(nt);
									}

								}
							} else {
								nt.setContent(((DBObject) co).get("content").toString());
								nt.setNum(((DBObject) co).get("num").toString());
								nt.setTime(((DBObject) co).get("time").toString());
								nt.setTitle(((DBObject) co).get("title").toString());
								nt.setType(((DBObject) co).get("type").toString());
								nfList.add(nt);
							}
						}
					}
				}

			}
		}
		return nfList;
	}
	
	/*
	 * chang-zheng
	 * FOR billOfSell
	 */
	/*public static void saveBillOfSell(List<BillOfSell> billOfSellList){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		
		for(BillOfSell bs : billOfSellList){
			query.put("orderNumber", bs.getOrderNumber());
			query.put("ordersForChildTableID", bs.getOrdersForChildTableID());
			DBCursor queryresult = mongoDB.getCollection(collectionBill).find(query).limit(1);
		
			
			if(queryresult!=null){
				
				DBObject updateQuery = new BasicDBObject();
				updateQuery.put("businessType",bs.getBusinessType());
				updateQuery.put("sellType",bs.getSellType());
				updateQuery.put("orderNumber",bs.getOrderNumber());
				updateQuery.put("orderTime",bs.getOrderTime());
				updateQuery.put("customerName",bs.getCustomerName());
				updateQuery.put("currency",bs.getCurrency());
				updateQuery.put("parities",bs.getParities());
				updateQuery.put("salesDepartments",bs.getSalesDepartments());
				updateQuery.put("salesman",bs.getSalesman());
				updateQuery.put("inventoryCoding",bs.getInventoryCoding());
				updateQuery.put("inventoryCode",bs.getInventoryCode());
				updateQuery.put("inventoryName",bs.getInventoryName());
				updateQuery.put("specificationsModels",bs.getSpecificationsModels());
				updateQuery.put("measurement",bs.getMeasurement());
				updateQuery.put("count",bs.getCount());
				
				updateQuery.put("unitPrice",bs.getUnitPrice());
				updateQuery.put("priceExcludingTax",bs.getPriceExcludingTax());
				updateQuery.put("noTaxAmount",bs.getNoTaxAmount());
				updateQuery.put("tax",bs.getTax());
				updateQuery.put("totalPriceWithTax",bs.getTotalPriceWithTax());
				updateQuery.put("taxRateString",bs.getTaxRateString());
				updateQuery.put("deductible",bs.getDeductible());
				updateQuery.put("deductible2",bs.getDeductible2());
				updateQuery.put("advanceShipmentDate",bs.getAdvanceShipmentDate());
				updateQuery.put("ordersForChildTableID",bs.getOrdersForChildTableID());
				updateQuery.put("unfilledOrderCount",bs.getUnfilledOrderCount());
				updateQuery.put("noInvoiceCount",bs.getNoInvoiceCount());
				updateQuery.put("reservedNum",bs.getReservedNum());
				updateQuery.put("notDeliverNum",bs.getNotDeliverNum());
				updateQuery.put("notDeliverAmount",bs.getNotDeliverAmount());
				updateQuery.put("noInvoiceCounts",bs.getNoInvoiceCounts());
				updateQuery.put("noInvoiceAmount",bs.getNoInvoiceAmount());
				updateQuery.put("amountPurchased",bs.getAmountPurchased());
				updateQuery.put("noamountPurchased",bs.getNoamountPurchased());
				updateQuery.put("noProduction",bs.getNoProduction());
				updateQuery.put("noOutsourcing",bs.getNoOutsourcing());
				updateQuery.put("noImportVolume",bs.getNoImportVolume());
				
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", updateQuery);
				mongoDB.getCollection(collectionBill).update(updateQuery, doc);
			}else{
				BasicDBObject doc = BasicDBObject.parse(bs.toString());  
				mongoDB.getCollection(collectionBill).save(doc);
			}
			
		}
		
	}
	*/
	/*
	 * chang-zheng
	 * FOR billOfSell
	 */
	public static String saveBillOfSell(BillOfSell bs){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		String ret="";
		if(bs!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			query.put("orderNumber", bs.getOrderNumber());
			query.put("ordersForChildTableID", bs.getOrdersForChildTableID());
			DBObject queryresult = mongoDB.getCollection(collectionBill).findOne(query);

			
			DBObject updateQuery = new BasicDBObject();
			updateQuery.put("businessType",bs.getBusinessType());
			updateQuery.put("sellType",bs.getSellType());
			updateQuery.put("orderNumber",bs.getOrderNumber());
			updateQuery.put("orderTime",bs.getOrderTime());
			updateQuery.put("customerName",bs.getCustomerName());
			updateQuery.put("currency",bs.getCurrency());
			updateQuery.put("exchange",bs.getExchange());
			updateQuery.put("salesDepartments",bs.getSalesDepartments());
			updateQuery.put("salesman",bs.getSalesman());
			updateQuery.put("inventoryCoding",bs.getInventoryCoding());
			updateQuery.put("inventoryCode",bs.getInventoryCode());
			updateQuery.put("inventoryName",bs.getInventoryName());
			updateQuery.put("specificationsModels",bs.getSpecificationsModels());
			updateQuery.put("unit",bs.getUnit());
			updateQuery.put("amount",bs.getAmount());
			
			updateQuery.put("unitPriceIncludTax",bs.getUnitPriceIncludTax());
			updateQuery.put("priceExcludingTax",bs.getPriceExcludingTax());
			updateQuery.put("noTaxAmount",bs.getNoTaxAmount());
			updateQuery.put("tax",bs.getTax());
			updateQuery.put("totalPriceWithTax",bs.getTotalPriceWithTax());
			updateQuery.put("taxRateString",bs.getTaxRateString());
			updateQuery.put("deductible",bs.getDeductible());
			updateQuery.put("deductible2",bs.getDeductible2());
			updateQuery.put("advanceShipmentDate",bs.getAdvanceShipmentDate());
			updateQuery.put("ordersForChildTableID",bs.getOrdersForChildTableID());
			updateQuery.put("unfilledOrderCount",bs.getUnfilledOrderCount());
			updateQuery.put("noInvoiceCount",bs.getNoInvoiceCount());
			updateQuery.put("reservedNum",bs.getReservedNum());
			updateQuery.put("notDeliverNum",bs.getNotDeliverNum());
			updateQuery.put("notDeliverAmount",bs.getNotDeliverAmount());
			updateQuery.put("noInvoiceCounts",bs.getNoInvoiceCounts());
			updateQuery.put("noInvoiceAmount",bs.getNoInvoiceAmount());
			updateQuery.put("amountPurchased",bs.getAmountPurchased());
			updateQuery.put("noamountPurchased",bs.getNoamountPurchased());
			updateQuery.put("noProduction",bs.getNoProduction());
			updateQuery.put("noOutsourcing",bs.getNoOutsourcing());
			updateQuery.put("noImportVolume",bs.getNoImportVolume());
			if(queryresult!=null && bs.getOrderNumber()!=null && bs.getOrdersForChildTableID()!=null){
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", updateQuery);
				mongoDB.getCollection(collectionBill).update(updateQuery, doc);
				ret="update ok";
			}else if(!bs.getOrderNumber().equals("") && !bs.getOrdersForChildTableID().equals("")){
				WriteResult writeResult;
				writeResult=mongoDB.getCollection(collectionBill).insert(updateQuery);
				ret="insert ok  -->" + writeResult;
			}
			
		}
		return ret;
		
	}
	
	/*
	 * chang-zheng
	 * FOR OnlineQuotation
	 */
	public static String saveOnlineQuotation(OnlineQuotation onlineQuotation){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		String ret="Quotation fail";
		if(onlineQuotation!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			query.put("item", onlineQuotation.getItem());
			DBObject queryresult = mongoDB.getCollection(collectionQuotation).findOne(query);

			DBObject insertQuery = new BasicDBObject();
			
			WriteResult writeResult;
			if(queryresult==null){
				insertQuery.put("item",onlineQuotation.getItem());
				insertQuery.put("category",onlineQuotation.getCategory());
				insertQuery.put("categoryGrade",onlineQuotation.getCategoryGrade());
				insertQuery.put("quotationPrice",onlineQuotation.getQuotationPrice());
				insertQuery.put("avaliableInventory",onlineQuotation.getAvaliableInventory());
				insertQuery.put("comments",onlineQuotation.getComments());
				insertQuery.put("onDelivery",onlineQuotation.getOnDelivery());
				insertQuery.put("locationAmounts",onlineQuotation.getLocationAmounts());
				insertQuery.put("soldOutOfPay",onlineQuotation.getSoldOutOfPay());
				insertQuery.put("originalProducer",onlineQuotation.getOriginalProducer());
				insertQuery.put("lastUpdate",onlineQuotation.getLastUpdate());
				insertQuery.put("approveStatus",onlineQuotation.getApproveStatus());
				
				writeResult=mongoDB.getCollection(collectionQuotation).insert(insertQuery);
				ret="insert Quotation ok  -->" + writeResult;
			}else{
				if(onlineQuotation.getCategory()==null && queryresult.get("category")!=null){
					insertQuery.put("category",queryresult.get("category").toString());
				}else {
					insertQuery.put("category",onlineQuotation.getCategory());
				}
				
				if(onlineQuotation.getCategoryGrade()==null && queryresult.get("categoryGrade")!=null){
					insertQuery.put("categoryGrade",queryresult.get("categoryGrade").toString());
				}else {
					insertQuery.put("categoryGrade",onlineQuotation.getCategoryGrade());
				}
				
				if(onlineQuotation.getQuotationPrice()==null && queryresult.get("quotationPrice")!=null){
					insertQuery.put("quotationPrice",queryresult.get("quotationPrice").toString());
				}else {
					insertQuery.put("quotationPrice",onlineQuotation.getQuotationPrice());
				}
				

				if(onlineQuotation.getAvaliableInventory()==null && queryresult.get("avaliableInventory")!=null){
					insertQuery.put("avaliableInventory",queryresult.get("avaliableInventory").toString());
				}else {
					insertQuery.put("avaliableInventory",onlineQuotation.getAvaliableInventory());
				}

				if(onlineQuotation.getComments()==null && queryresult.get("comments")!=null){
					insertQuery.put("comments",queryresult.get("comments").toString());
				}else {
					insertQuery.put("comments",onlineQuotation.getComments());
				}
				
				if(onlineQuotation.getOnDelivery()==null && queryresult.get("onDelivery")!=null){
					insertQuery.put("onDelivery",queryresult.get("onDelivery").toString());
				}else {
					insertQuery.put("onDelivery",onlineQuotation.getOnDelivery());
				}

				if(onlineQuotation.getLocationAmounts()==null && queryresult.get("locationAmounts")!=null){
					insertQuery.put("locationAmounts",queryresult.get("locationAmounts").toString());
				}else {
					insertQuery.put("locationAmounts",onlineQuotation.getLocationAmounts());
				}

				if(onlineQuotation.getSoldOutOfPay()==null && queryresult.get("soldOutOfPay")!=null){
					insertQuery.put("soldOutOfPay",queryresult.get("soldOutOfPay").toString());
				}else {
					insertQuery.put("soldOutOfPay",onlineQuotation.getSoldOutOfPay());
				}
				
				if(onlineQuotation.getOriginalProducer()==null && queryresult.get("originalProducer")!=null){
					insertQuery.put("originalProducer",queryresult.get("originalProducer").toString());
				}else {
					insertQuery.put("originalProducer",onlineQuotation.getOriginalProducer());
				}
				

				if(onlineQuotation.getLastUpdate()==null && queryresult.get("lastUpdate")!=null){
					insertQuery.put("lastUpdate",queryresult.get("lastUpdate").toString());
				}else {
					insertQuery.put("lastUpdate",onlineQuotation.getLastUpdate());
				}
				insertQuery.put("approveStatus",onlineQuotation.getApproveStatus());
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", insertQuery);
				writeResult=mongoDB.getCollection(collectionQuotation).update(new BasicDBObject().append("item", onlineQuotation.getItem()), doc);
				ret="update Quotation ok  -->" + writeResult;
			}
		}
		return ret;
	}
	
	/*
	 * chang-zheng
	 * remove OnlineQuotation
	 */
	public static String delQuotationByItem(String item){
		String ret = "fail";
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		query.put("item", item);
		try{
			WriteResult wt = mongoDB.getCollection(collectionQuotation).remove(query);  
	        
	        if(wt!=null){
	        	ret = "removed";
	        }
		}catch(Exception e){
			log.info("delQuotationByItem--" + e.getMessage());
		}
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR OnlineQuotation
	 */
	/*public static String UpdateOnlineQuotation(OnlineQuotation onlineQuotation){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		String ret="Quotation fail";
		if(onlineQuotation!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			query.put("item", onlineQuotation.getItem());
			DBObject queryresult = mongoDB.getCollection(collectionQuotation).findOne(query);
			if(queryresult!=null){
				DBObject updateQuery = new BasicDBObject();
				updateQuery.put("category",onlineQuotation.getCategory());
				updateQuery.put("categoryGrade",onlineQuotation.getCategoryGrade());
				updateQuery.put("item",onlineQuotation.getItem());
				updateQuery.put("quotationPrice",onlineQuotation.getQuotationPrice());
				updateQuery.put("locationList",onlineQuotation.getLocationList());
				updateQuery.put("avaliableInventory",onlineQuotation.getAvaliableInventory());
				updateQuery.put("onDelivery",onlineQuotation.getOnDelivery());
				updateQuery.put("soldOutOfPay",onlineQuotation.getSoldOutOfPay());
				updateQuery.put("originalProducer",onlineQuotation.getOriginalProducer());
				WriteResult writeResult;
				writeResult=mongoDB.getCollection(collectionQuotation).insert(updateQuery);
			}
		}
		return ret;
	}
	*/
	
	/*
	 * chang-zheng
	 * FOR OnlineQuotation
	 */
	/*public static String saveLocation(String item, Location location){
		mongoDB = getMongoDB();
		//DBObject query = new BasicDBObject();
		String ret="saveLocation fail";
		if(location!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}

			List<DBObject> locationList = new ArrayList<DBObject>();
			DBCursor onlineQuotation = mongoDB.getCollection(collectionQuotation).find(new BasicDBObject().append("item", item));
			if (null != onlineQuotation) {
				BasicDBObject doc = new BasicDBObject();
				DBObject update = new BasicDBObject();
//				DBObject innerInsert = new BasicDBObject();
//				innerInsert.put("chengDu",location.getChengDu() );
//				innerInsert.put("chongQing", location.getChongQing());
				
				while (onlineQuotation.hasNext()) {
					DBObject o = onlineQuotation.next();
					BasicDBList hist = (BasicDBList) o.get("locationList");
					if (hist != null) {
						Object[] locations = hist.toArray();
						for (Object dbobj : locations) {
							if (dbobj instanceof DBObject) {
								if(location.getChengDu()!=null){
									((DBObject) dbobj).put("chengDu",location.getChengDu() );
								}
								if(location.getChongQing()!=null){
									((DBObject) dbobj).put("chongQing", location.getChongQing() );
								}
								
								locationList.add((DBObject) dbobj);
							}
						}
					}
					else{
						DBObject innerInsert = new BasicDBObject();
						innerInsert.put("chengDu",location.getChengDu() );
						innerInsert.put("chongQing", location.getChongQing());
						
						locationList.add(innerInsert);
					}
				}

				
				
				
				update.put("locationList", locationList);
				
				doc.put("$set", update);
				WriteResult wr = mongoDB.getCollection(collectionQuotation).update(new BasicDBObject().append("item", item), doc);
				if(wr!=null){
					ret = "saveLocation success";
				}
			}
		}
			
		return ret;
	}
	*/
	/*
	 * chang-zheng
	 * getAllQuotations
	 */
	
	public static List<OnlineQuotation> getAllQuotations() {
		DBCursor cor ;
		cor = mongoDB.getCollection(collectionQuotation).find();
		List<OnlineQuotation> quotationList = new ArrayList<OnlineQuotation>();
		if(cor!=null){
			while (cor.hasNext()) {
				DBObject objQuotation = cor.next();
				OnlineQuotation qt = new OnlineQuotation();
				qt.setCategory(objQuotation.get("category") == null ? "" : objQuotation.get("category").toString());
				qt.setCategoryGrade(objQuotation.get("categoryGrade") == null ? "" : objQuotation.get("categoryGrade").toString());
				qt.setItem(objQuotation.get("item") == null ? "" : objQuotation.get("item").toString());
				qt.setQuotationPrice(objQuotation.get("quotationPrice") == null ? "" : objQuotation.get("quotationPrice").toString());
				qt.setComments(objQuotation.get("comments") == null ? "" : objQuotation.get("comments").toString());
				qt.setLocationAmounts(objQuotation.get("locationAmounts") == null ? "" : objQuotation.get("locationAmounts").toString());
				qt.setAvaliableInventory(objQuotation.get("avaliableInventory") == null ? "" : objQuotation.get("avaliableInventory").toString());
				qt.setOnDelivery(objQuotation.get("onDelivery") == null ? "" : objQuotation.get("onDelivery").toString());
				qt.setSoldOutOfPay(objQuotation.get("soldOutOfPay") == null ? "" : objQuotation.get("soldOutOfPay").toString());
				qt.setOriginalProducer(objQuotation.get("originalProducer") == null ? "" : objQuotation.get("originalProducer").toString());
				qt.setLastUpdate(objQuotation.get("lastUpdate") == null ? "" : objQuotation.get("lastUpdate").toString());
				qt.setApproveStatus(objQuotation.get("approveStatus") == null ? "" : objQuotation.get("approveStatus").toString());
				quotationList.add(qt);
			}
			 
		}
		
		return quotationList;
	}
	
	
	/*
	 * chang-zheng
	 * getOneQuotation
	 */
	
	public static List<OnlineQuotation> getOneQuotation(String item) {
		DBObject dbQuotation ;
		DBObject dbquery = new BasicDBObject();  
		dbquery.put("item", item);
		dbQuotation = mongoDB.getCollection(collectionQuotation).findOne(dbquery);
		List<OnlineQuotation> quotationList = new ArrayList<OnlineQuotation>();
		
			OnlineQuotation qt = new OnlineQuotation();
			qt.setCategory(dbQuotation.get("category") == null ? "" : dbQuotation.get("category").toString());
			qt.setCategoryGrade(dbQuotation.get("categoryGrade") == null ? "" : dbQuotation.get("categoryGrade").toString());
			qt.setItem(dbQuotation.get("item") == null ? "" : dbQuotation.get("item").toString());
			qt.setQuotationPrice(dbQuotation.get("quotationPrice") == null ? "" : dbQuotation.get("quotationPrice").toString());
			qt.setComments(dbQuotation.get("comments") == null ? "" : dbQuotation.get("comments").toString());
			qt.setLocationAmounts(dbQuotation.get("locationAmounts") == null ? "" : dbQuotation.get("locationAmounts").toString());
			qt.setAvaliableInventory(dbQuotation.get("avaliableInventory") == null ? "" : dbQuotation.get("avaliableInventory").toString());
			qt.setOnDelivery(dbQuotation.get("onDelivery") == null ? "" : dbQuotation.get("onDelivery").toString());
			qt.setSoldOutOfPay(dbQuotation.get("soldOutOfPay") == null ? "" : dbQuotation.get("soldOutOfPay").toString());
			qt.setOriginalProducer(dbQuotation.get("originalProducer") == null ? "" : dbQuotation.get("originalProducer").toString());
			qt.setLastUpdate(dbQuotation.get("lastUpdate") == null ? "" : dbQuotation.get("lastUpdate").toString());
			quotationList.add(qt);
		 
		return quotationList;
	}
	
	/*
	 * chang-zheng
	 * getQuotations by query
	 */
	
	public static List<OnlineQuotation> getQuotationsByQuery(String category,String item) {
		DBObject dbquery = new BasicDBObject();
		if(category!=null){
			dbquery.put("category", category);
		}
		if(item!=null){
			dbquery.put("item", item);
		}
		DBCursor cor  = mongoDB.getCollection(collectionQuotation).find();
		List<OnlineQuotation> quotationList = new ArrayList<OnlineQuotation>();
		if(cor!=null){
			while (cor.hasNext()) {
				DBObject objQuotation = cor.next();
				OnlineQuotation qt = new OnlineQuotation();
				qt.setCategory(objQuotation.get("category") == null ? "" : objQuotation.get("category").toString());
				qt.setCategoryGrade(objQuotation.get("categoryGrade") == null ? "" : objQuotation.get("categoryGrade").toString());
				qt.setItem(objQuotation.get("item") == null ? "" : objQuotation.get("item").toString());
				qt.setQuotationPrice(objQuotation.get("quotationPrice") == null ? "" : objQuotation.get("quotationPrice").toString());
				qt.setComments(objQuotation.get("comments") == null ? "" : objQuotation.get("comments").toString());
				qt.setLocationAmounts(objQuotation.get("locationAmounts") == null ? "" : objQuotation.get("locationAmounts").toString());
				qt.setAvaliableInventory(objQuotation.get("avaliableInventory") == null ? "" : objQuotation.get("avaliableInventory").toString());
				qt.setOnDelivery(objQuotation.get("onDelivery") == null ? "" : objQuotation.get("onDelivery").toString());
				qt.setSoldOutOfPay(objQuotation.get("soldOutOfPay") == null ? "" : objQuotation.get("soldOutOfPay").toString());
				qt.setOriginalProducer(objQuotation.get("originalProducer") == null ? "" : objQuotation.get("originalProducer").toString());
				qt.setLastUpdate(objQuotation.get("lastUpdate") == null ? "" : objQuotation.get("lastUpdate").toString());
				quotationList.add(qt);
			}
		}
		
		return quotationList;
			
	}
	
	/*
	 * chang-zheng
	 * to save Inventory
	 */
	public static String saveInventory(Inventory inventory){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		String ret="Quotation fail";
		if(inventory!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			query.put("repositoryName", inventory.getRepositoryName());
			query.put("plasticItem", inventory.getPlasticItem());
			DBObject queryresult = mongoDB.getCollection(collectionInventory).findOne(query);

			DBObject insertQuery = new BasicDBObject();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			WriteResult writeResult;
			if(queryresult==null){
				insertQuery.put("repositoryName",inventory.getRepositoryName());
				insertQuery.put("plasticItem",inventory.getPlasticItem());
				insertQuery.put("unit",inventory.getUnit());
				insertQuery.put("inventoryAmount",inventory.getInventoryAmount());
				insertQuery.put("waitDeliverAmount",inventory.getWaitDeliverAmount());
				insertQuery.put("reserveDeliverAmount",inventory.getReserveDeliverAmount());
				insertQuery.put("availableAmount",inventory.getAvailableAmount());
				insertQuery.put("lastUpdate",cursqlTS.toString());
				
				writeResult=mongoDB.getCollection(collectionInventory).insert(insertQuery);
				if(writeResult==null){
					writeResult=mongoDB.getCollection(collectionInventory).insert(insertQuery);
				}
				ret="insert Inventory ok  -->" + writeResult;
			}else{
				if(inventory.getRepositoryName()==null){
					insertQuery.put("repositoryName",queryresult.get("repositoryName"));
				}else {
					insertQuery.put("repositoryName",inventory.getRepositoryName());
				}
				
				if(inventory.getUnit()==null){
					insertQuery.put("unit",queryresult.get("categoryGrade"));
				}else {
					insertQuery.put("unit",inventory.getUnit());
				}
				
				if(inventory.getInventoryAmount()==null){
					insertQuery.put("inventoryAmount",queryresult.get("inventoryAmount"));
				}else {
					insertQuery.put("inventoryAmount",inventory.getInventoryAmount());
				}
				

				if(inventory.getWaitDeliverAmount()==null){
					insertQuery.put("waitDeliverAmount",queryresult.get("waitDeliverAmount"));
				}else {
					insertQuery.put("waitDeliverAmount",inventory.getWaitDeliverAmount());
				}

				if(inventory.getReserveDeliverAmount()==null ){
					insertQuery.put("reserveDeliverAmount",queryresult.get("reserveDeliverAmount"));
				}else {
					insertQuery.put("reserveDeliverAmount",inventory.getReserveDeliverAmount());
				}
				
				if(inventory.getAvailableAmount()==null ){
					insertQuery.put("availableAmount",queryresult.get("availableAmount"));
				}else {
					insertQuery.put("availableAmount",inventory.getAvailableAmount());
				}

				
				insertQuery.put("lastUpdate",cursqlTS.toString());
			
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", insertQuery);
				writeResult=mongoDB.getCollection(collectionInventory).update(new BasicDBObject().append("plasticItem", inventory.getPlasticItem()), doc);
				ret="update Inventory ok  -->" + writeResult;
				// åˆ¤æ–­å¹¶æ�’å…¥
				PlasticItemService.judgeAndInsert(inventory);
			}
		}
		return ret;
	}
	
	/*
	 * chang-zheng
	 * to save OnDelivery
	 */
	public static String saveOnDelivery(OnDelivery onDelivery){
		mongoDB = getMongoDB();
		String ret="Quotation fail";
		if(onDelivery!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}

			DBObject insertQuery = new BasicDBObject();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			WriteResult writeResult;
				insertQuery.put("billID", onDelivery.getBillID());
				insertQuery.put("date", onDelivery.getDate());
				insertQuery.put("provider", onDelivery.getProvider());
				insertQuery.put("plasticItem",onDelivery.getPlasticItem());
				insertQuery.put("amount",onDelivery.getAmount());
				insertQuery.put("originalPrice",onDelivery.getOriginalPrice());
				insertQuery.put("taxRate",onDelivery.getTaxRate());
				insertQuery.put("billType",onDelivery.getBillType());
				insertQuery.put("notInInRepository",onDelivery.getNotInInRepository());
				
				insertQuery.put("lastUpdate",cursqlTS.toString());
				//mongoDB.getCollection(collectionOnDelivery).remove(query);
				writeResult=mongoDB.getCollection(collectionOnDelivery).insert(insertQuery);
				ret="insert onDelivery ok  -->" + writeResult;
				// åˆ¤æ–­å¹¶æ�’å…¥
				PlasticItemService.judgeAndInsert(onDelivery);
		}
		return ret;
	}
	
	/*
	 * chang-zheng
	 * to save OrderNopay
	 */
	public static String saveOrderNopay(OrderNopay orderNopay){

		mongoDB = getMongoDB();
	
		String ret="Quotation fail";
		if(orderNopay!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}

			DBObject insertQuery = new BasicDBObject();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			WriteResult writeResult;
				insertQuery.put("customerName", orderNopay.getCustomerName());
				insertQuery.put("salesman", orderNopay.getSalesman());
				insertQuery.put("billID", orderNopay.getBillID());
				insertQuery.put("billDate",orderNopay.getBillDate());
				insertQuery.put("plasticItem",orderNopay.getPlasticItem());
				insertQuery.put("unfilledOrderAmount",orderNopay.getUnfilledOrderAmount());
				insertQuery.put("filledOrderAmount",orderNopay.getFilledOrderAmount());
				insertQuery.put("noInvoiceAmount",orderNopay.getNoInvoiceAmount());
				
				insertQuery.put("lastUpdate",cursqlTS.toString());
				//mongoDB.getCollection(collectionorderNopay).remove(query);
				writeResult=mongoDB.getCollection(collectionOrderNopay).insert(insertQuery);
				ret="insert orderNopay ok  -->" + writeResult;
				// åˆ¤æ–­å¹¶æ�’å…¥
				PlasticItemService.judgeAndInsert(orderNopay);
		}
		return ret;
	
	}
	
	/*
	 * chang-zheng
	 * to save QuotationList
	 */
	public static String insertQuotationList(QuotationList quotation){

		mongoDB = getMongoDB();
	
		String ret="Quotation fail";
		if(quotation!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			DBObject queryresult = null;
			/*if(!StringUtils.isEmpty(mongoID) ){
				DBObject Query = new BasicDBObject();
				Query.put("_id", mongoID);
				queryresult = mongoDB.getCollection(collectionQuotationList).findOne(Query);
			}*/
			WriteResult writeResult;
			DBObject insertQuery = new BasicDBObject();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			/*if(queryresult!=null && queryresult.get("status")!="Y"){
				insertQuery.put("plasticItem", queryresult.get("plasticItem"));
				insertQuery.put("status", quotation.getStatus());
				insertQuery.put("approveBy",quotation.getApproveBy());
				insertQuery.put("editBy",quotation.getEditBy());
				insertQuery.put("dateTime",queryresult.get("dateTime"));
				insertQuery.put("suggestPrice",quotation.getSuggestPrice());
				insertQuery.put("lastUpdate",cursqlTS.toString());
				
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", insertQuery);
				writeResult=mongoDB.getCollection(collectionQuotationList).update(new BasicDBObject().append("_id", mongoID), doc);
				
				ret="update QuotationList ok  -->" + writeResult;
			}else{*/
				insertQuery.put("plasticItem", quotation.getPlasticItem());
				insertQuery.put("status", quotation.getStatus());
				insertQuery.put("approveBy", quotation.getApproveBy());
				insertQuery.put("editBy",quotation.getEditBy());
				insertQuery.put("dateTime",quotation.getDateTime());
				insertQuery.put("suggestPrice",quotation.getSuggestPrice());
				insertQuery.put("lastUpdate",cursqlTS.toString());
				insertQuery.put("type",quotation.getType());
				writeResult=mongoDB.getCollection(collectionQuotationList).insert(insertQuery);
				ret="insert quotation ok  -->" + writeResult;
				
				Float price = null;
				if(quotation.getSuggestPrice() != null){
					price = Float.parseFloat(quotation.getSuggestPrice()+"");
				}
				// æ›´æ–°ä»·æ ¼
				PlasticItemService.updatePriceInfo(quotation.getPlasticItem(), price, quotation.getType());
			
				
		}
		return ret;
	
	}
	/*public static String insertQuotationList(QuotationList quotation){
		mongoDB = getMongoDB();
		String ret="Quotation fail";
		if(quotation!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			DBObject queryresult = null;
			WriteResult writeResult;
			DBObject insertQuery = new BasicDBObject();
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
				insertQuery.put("plasticItem", quotation.getPlasticItem());
				insertQuery.put("status", quotation.getStatus());
				insertQuery.put("approveBy", quotation.getApproveBy());
				insertQuery.put("editBy",quotation.getEditBy());
				insertQuery.put("dateTime",quotation.getDateTime());
				insertQuery.put("suggestPrice",quotation.getSuggestPrice());
				insertQuery.put("lastUpdate",cursqlTS.toString());
				writeResult=mongoDB.getCollection(collectionOrderNopay).insert(insertQuery);
				ret="insert quotation ok  -->" + writeResult;
		}
		return ret;
	
	}*/
	/*
	 * CHANG -ZHENG
	 *
	 *delete
	 **/
	public static WriteResult DeleteDB(String dbName){
	DBObject query = new BasicDBObject();
	WriteResult wr = mongoDB.getCollection(dbName).remove(query);
	return wr;
	}
	
	
	
	public static String saveArticleMessage(ArticleMessage articleMessage){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		String ret="ArticleMessage fail";
		if(articleMessage!=null){
			if(mongoDB == null){
				mongoDB = getMongoDB();
			}
			query.put("num", articleMessage.getNum());
			DBObject queryresult = mongoDB.getCollection(Article_Message).findOne(query);

			DBObject insertQuery = new BasicDBObject();
			
			WriteResult writeResult;
			if(queryresult==null){

				System.out.println("add new Article--------------");
				insertQuery.put("num",articleMessage.getNum());
				insertQuery.put("title",articleMessage.getTitle());
				insertQuery.put("type",articleMessage.getType());
				insertQuery.put("content",articleMessage.getContent());
				insertQuery.put("time",articleMessage.getTime());
				insertQuery.put("picture",articleMessage.getPicture());
				insertQuery.put("webUrl",articleMessage.getWebUrl());
				insertQuery.put("visitedNum",articleMessage.getVisitedNum());
				if(!"".equals(articleMessage.getAuthor())){
				insertQuery.put("author",articleMessage.getAuthor());}
				writeResult=mongoDB.getCollection(Article_Message).insert(insertQuery);
				ret="insert articleMessage ok  -->" + writeResult;
			}else{
				System.out.println("update old Article--------------");
				if(articleMessage.getNum()==null && queryresult.get("num")!=null){
					insertQuery.put("num",queryresult.get("num").toString());
				}else {
					insertQuery.put("num",articleMessage.getNum());
				}
				
				if(articleMessage.getTitle()==null && queryresult.get("title")!=null){
					insertQuery.put("title",queryresult.get("title").toString());
				}else {
					insertQuery.put("title",articleMessage.getTitle());
				}
				
				if(articleMessage.getType()==null && queryresult.get("type")!=null){
					insertQuery.put("type",queryresult.get("type").toString());
				}else {
					insertQuery.put("type",articleMessage.getType());
				}
				

				if(articleMessage.getContent()==null && queryresult.get("content")!=null){
					insertQuery.put("content",queryresult.get("content").toString());
				}else {
					insertQuery.put("content",articleMessage.getContent());
				}

				if(articleMessage.getTime()==null && queryresult.get("time")!=null){
					insertQuery.put("time",queryresult.get("time").toString());
				}else {
					insertQuery.put("time",articleMessage.getTime());
				}
				
				if(articleMessage.getPicture()==null && queryresult.get("picture")!=null){
					insertQuery.put("picture",queryresult.get("picture").toString());
				}else {
					insertQuery.put("picture",articleMessage.getPicture());
				}

				if(articleMessage.getWebUrl()==null && queryresult.get("webUrl")!=null){
					insertQuery.put("webUrl",queryresult.get("webUrl").toString());
				}else {
					insertQuery.put("webUrl",articleMessage.getWebUrl());
				}

				if(articleMessage.getAuthor()==null && queryresult.get("author")!=null){
					insertQuery.put("author",queryresult.get("author").toString());
				}else {
					insertQuery.put("author",articleMessage.getAuthor());
				}
				if(articleMessage.getVisitedNum()==null && queryresult.get("visitedNum")!=null){
					insertQuery.put("visitedNum",queryresult.get("visitedNum").toString());
				}else {
					insertQuery.put("visitedNum",articleMessage.getVisitedNum());
				}
				
				BasicDBObject doc = new BasicDBObject();  
				doc.put("$set", insertQuery);
				writeResult=mongoDB.getCollection(Article_Message).update(new BasicDBObject().append("num", articleMessage.getNum()), doc);
				ret="update Article_Message ok  -->" + writeResult;
			}
		}
		return ret;
	}
	
	public static int getArticleMessageMaxNum() {
		DBCursor cor  = mongoDB.getCollection(Article_Message).find();
		String maxNum="";
		if(cor!=null){
			while (cor.hasNext()) {
				DBObject objam = cor.next();
				maxNum=objam.get("num") == null ? "" : objam.get("num").toString();
				System.out.println("maxNum----------------"+maxNum);
			}
		}
		if(!"".equals(maxNum)){
		return Integer.parseInt(maxNum);
		}else{
			return 0;}
	}
	public static List<String> getArticleMessageTitleByType(String type){
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		query.put("type", type);
		@SuppressWarnings("unchecked")
		List<String> amList = mongoDB.getCollection(Article_Message).distinct("title",query);
			return amList;
	}
	public static List<ArticleMessage> getArticleMessageByType(String type) {
		mongoDB = getMongoDB();
		List<ArticleMessage> amList = new ArrayList<ArticleMessage>();
		ArticleMessage am = null;
		DBCursor queryresults;
		try{
				DBObject query = new BasicDBObject();
				BasicDBObject sort=new BasicDBObject();
				sort.put("visitedNum", -1);
				query.put("type", type);
				queryresults = mongoDB.getCollection(Article_Message).find(query).sort(sort);
			if (null != queryresults) {
            	while(queryresults.hasNext()){
				DBObject o = queryresults.next();
				am=new ArticleMessage();
				am.setNum(o.get("num") == null ? "" : o.get("num").toString());
				am.setContent(o.get("content") == null ? "" : o.get("content").toString());
				am.setTime(o.get("time") == null ? "" : o.get("time").toString());
				am.setTitle(o.get("title") == null ? "" : o.get("title").toString());
				am.setVisitedNum(o.get("visitedNum") == null ? "" : o.get("visitedNum").toString());
				amList.add(am);
            	}
			}
		}catch(Exception e){
			log.info("getArcticleMessageByNum--" + e.getMessage());
		}
		return amList;
	}
	public static List<ArticleMessage> getArticleMessageByNum(String num) {
		mongoDB = getMongoDB();
		List<ArticleMessage> amList = new ArrayList<ArticleMessage>();
		ArticleMessage am = null;
		DBCursor queryresults;
		try {
			if ("".equals(num)) {
				BasicDBObject sort = new BasicDBObject();
				sort.put("_id", -1);
				queryresults = mongoDB.getCollection(Article_Message).find()
						.sort(sort);
			} else {
				DBObject query = new BasicDBObject();
				query.put("num", num);
				queryresults = mongoDB.getCollection(Article_Message)
						.find(query).limit(1);
			}
			if (null != queryresults) {
				while (queryresults.hasNext()) {
					DBObject o = queryresults.next();
					am = new ArticleMessage();
					am.setNum(o.get("num") == null ? "" : o.get("num")
							.toString());
					am.setContent(o.get("content") == null ? "" : o.get(
							"content").toString());
					am.setTime(o.get("time") == null ? "" : o.get("time")
							.toString());
					am.setTitle(o.get("title") == null ? "" : o.get("title")
							.toString());
					am.setVisitedNum(o.get("visitedNum") == null ? "" : o.get(
							"visitedNum").toString());
					am.setPicture(o.get("picture") == null ? "" : o.get(
							"picture").toString());
					amList.add(am);
					System.out.println("am.getPicture()---------"
							+ am.getPicture());
				}
			}
		} catch (Exception e) {
			log.info("getArcticleMessageByNum--" + e.getMessage());
		}
		return amList;
	}
	public static ArrayList<ArticleMessage> queryArticleMessage(
			int startNumber, int pageSize) {
		mongoDB = getMongoDB();
		ArrayList<ArticleMessage> result = new ArrayList<ArticleMessage>();
		BasicDBObject sort = new BasicDBObject();
		sort.put("_id", -1);
		DBCursor dbcur = mongoDB.getCollection(Article_Message).find()
				.sort(sort).skip(startNumber).limit(pageSize);
		if (null != dbcur) {
			while (dbcur.hasNext()) {
				DBObject o = dbcur.next();
				ArticleMessage temp = new ArticleMessage();
				if (o.get("time") != null) {
					temp.setTime(o.get("time").toString());
				}
				if (o.get("content") != null) {
					temp.setContent(o.get("content").toString());
				}
				if (o.get("num") != null) {
					temp.setNum(o.get("num").toString());
				}
				if (o.get("title") != null) {
					temp.setTitle(o.get("title").toString());
				}
				if (o.get("picture") != null) {
					temp.setTitle(o.get("picture").toString());
				}
				result.add(temp);
			}
		}
		return result;
	}

	public static boolean updateVisitedNumber(String num){
		mongoDB = getMongoDB();
		Boolean ret = false;
	    try{
	    	DBCursor dbcur = mongoDB.getCollection(Article_Message).find(new BasicDBObject().append("num", num));
	    	String visitedNum="";
			if(dbcur!=null){
				while (dbcur.hasNext()) {
					DBObject objam = dbcur.next();
					visitedNum=objam.get("visitedNum") == null ? "" : objam.get("visitedNum").toString();
				}
			}
            BasicDBObject doc = new BasicDBObject();
			DBObject update = new BasicDBObject();
			update.put("visitedNum", Integer.parseInt(visitedNum)+1);
			doc.put("$set", update); 
			WriteResult wr = mongoDB.getCollection(Article_Message).update(new BasicDBObject().append("num", num), doc);     
			ret = true;
            
	    }
		catch(Exception e){
			log.info("saveUserKM--" + e.getMessage());
		}
		return ret;
	}
/*	public static boolean updateVisitedHistory(String num,String openId){
		mongoDB = getMongoDB();
		Boolean ret = false;
	    try{
	    	HashSet<String> kmSets = new HashSet<String>();
	    	DBCursor dbcur = mongoDB.getCollection(Article_Message).find(new BasicDBObject().append("num", num));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		if(o.get("Visited")!=null){
            			BasicDBList hist = (BasicDBList) o.get("Visited");
                		Object[] vObjects = hist.toArray();
                		for(Object dbobj : vObjects){
                			if(dbobj instanceof String){
                				
                					kmSets.add((String) dbobj);
                				
                			}
                		}
            		}
            	}
            }
            BasicDBObject doc = new BasicDBObject();  
	    	DBObject update = new BasicDBObject();
	    		kmSets.add(openId);
    	    update.put("Visited",kmSets);
	    	doc.put("$set", update);  
			WriteResult wr = mongoDB.getCollection(Article_Message).update(new BasicDBObject().append("num",num), doc);
            ret = true;
            
	    }
		catch(Exception e){
			log.info("saveUserKM--" + e.getMessage());
		}
		return ret;
	}*/
/*	public static List<String> queryVisitedHistoryByNum(String num){
		mongoDB = getMongoDB();
		List<String> vLists = new ArrayList<String>();
	    try{
	    	DBCursor dbcur = mongoDB.getCollection(Article_Message).find(new BasicDBObject().append("num", num));
            if (null != dbcur) {
            	while(dbcur.hasNext()){
            		DBObject o = dbcur.next();
            		if(o.get("Visited")!=null){
            			BasicDBList hist = (BasicDBList) o.get("Visited");
                		Object[] kmObjects = hist.toArray();
                		for(Object dbobj : kmObjects){
                			if(dbobj instanceof String){
                				vLists.add((String) dbobj);
                			}
                		}
            		}
            	}
            }
	    }
		catch(Exception e){
			log.info("queryUserKM--" + e.getMessage());
		}
		return vLists;
	}*/
	
	/*
	 * CHANG -ZHENG
	 *
	 * for Visited
	 **/
	public static String updateVisited(String openid,String date,String pageName,String imgUrl,String nickName){
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		String ret="ok";
		try{
			DBObject query = new BasicDBObject();
			query.put("openid", openid);
			query.put("date", date);
			query.put("pageName", pageName);
			query.put("imgUrl", imgUrl);
			query.put("nickName", nickName);
			DBObject visited = mongoDB.getCollection(collectionVisited).findOne(query);
			if(visited!=null){
				//String num = visited.get("visitedNum")+"";
				BasicDBObject doc = new BasicDBObject();
				BasicDBObject update = new BasicDBObject();
				//update.put("visitedNum", Integer.parseInt(num)+1);
				update.append("visitedNum",1);
				doc.put("$inc", update);
				//doc.put("$set", update);  
				mongoDB.getCollection(collectionVisited).update(query, doc);
			}else{
				query.put("visitedNum", 1);
				mongoDB.getCollection(collectionVisited).insert(query);
			}
			
		}catch (Exception e){
			ret=e.getMessage();
		}
		
		return ret;
	}
	
	public static String updateShared(String openid, String date,
			String pageName, String imgUrl, String nickName) {
		if (mongoDB == null) {
			mongoDB = getMongoDB();
		}
		String ret = "ok";
		try {
			DBObject query = new BasicDBObject();
			query.put("openid", openid);
			query.put("date", date);
			query.put("pageName", pageName);
			query.put("imgUrl", imgUrl);
			query.put("nickName", nickName);
			DBObject visited = mongoDB.getCollection(collectionVisited)
					.findOne(query);
			if (visited != null) {
				BasicDBObject doc = new BasicDBObject();
				BasicDBObject update = new BasicDBObject();
				update.append("sharedNum", 1);
				doc.put("$inc", update);
				mongoDB.getCollection(collectionVisited).update(query, doc);
			} else {
				query.put("sharedNum", 1);
				mongoDB.getCollection(collectionVisited).insert(query);
			}

		} catch (Exception e) {
			ret = e.getMessage();
		}

		return ret;
	}
	/*
	 * CHANG -ZHENG
	 *
	 * for Visited
	 **/
	public static List<Visited> getVisited(String openid,String date){
		List<Visited> vitlist = new ArrayList<Visited>();
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		
		DBObject query = new BasicDBObject();
		query.put("openid", openid);
		query.put("date", date);
		DBCursor visiteds = mongoDB.getCollection(collectionVisited).find(query);
		
		while(visiteds.hasNext()){
			Visited vit = new Visited();
			DBObject obj = visiteds.next();
			vit.setDate(date);
			vit.setOpenid(openid);
			vit.setVisitedNum(Integer.parseInt(obj.get("visitedNum")+""));
			vit.setPageName(obj.get("pageName")+"");
			vit.setImgUrl(obj.get("imgUrl")+"");
			vit.setNickName(obj.get("nickName")+"");
			vitlist.add(vit);
		}
		return vitlist;
		}
	/*
	 * CHANG -ZHENG
	 *
	 * for Visiteds
	 **/
	public static Map<String,String> getVisitedbTotalNumByDate(List<String> date){
		HashMap<String,String> map = new HashMap<String,String>();
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		for(String str : date){
			int totalNum=0;
			DBObject query = new BasicDBObject();
			query.put("date", str);
			DBCursor visiteds = mongoDB.getCollection(collectionVisited).find(query);
			while(visiteds.hasNext()) {
			       DBObject obj = visiteds.next();
			       if(obj.get("date")!=null && obj.get("visitedNum")!=null){
			    	   int vitnum = Integer.parseInt(obj.get("visitedNum")+"");
			    	   totalNum = totalNum + vitnum;
			       }
			    }
			map.put(str, totalNum+"");
		}
		return map;
	}
	
	public static Map<String,List<Visited>> getVisitedByDate(List<String> date){
		HashMap<String,List<Visited>> map = new HashMap<String,List<Visited>>();
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		List<Visited> vtlist = new ArrayList<Visited>();
		for(String str : date){ 
			DBObject query = new BasicDBObject();
			query.put("date", str);
			DBCursor visiteds = mongoDB.getCollection(collectionVisited).find(query);
			
			while(visiteds.hasNext()) {
			       DBObject obj = visiteds.next();
			       Visited vit = new Visited();
			       vit.setDate(str);
					vit.setOpenid(obj.get("openid")+"");
					vit.setVisitedNum(Integer.parseInt(obj.get("visitedNum")+""));
					vit.setPageName(obj.get("pageName")+"");
					vit.setImgUrl(obj.get("imgUrl")+"");
					vit.setNickName(obj.get("nickName")+"");
					vtlist.add(vit);
					
			    }
			map.put(str, vtlist);
		}
		return map;
	}

	public static List<Integer> getTotalVisitedNumByPage(List<String> dates,String page)
	{
		/*for(int a=0;a<dates.size();a++){
			System.out.println("dates list-------:"+dates.get(a));
		}*/
		int num=0;
		List<Integer> numList=new ArrayList<Integer>();
		List<Visited> data=new ArrayList<Visited>();
		for(int i=0;i<dates.size();i++){
			data=MongoDBBasic.getVisitedDetail(dates.get(i),page);
		//	System.out.println("data.size():"+data.size());
			for(int j=0;j<data.size();j++){
				
			//	System.out.println("j index-----"+j+"num:---"+data.get(j).getVisitedNum()+"/page:---"+data.get(j).getPageName());
				num+=data.get(j).getVisitedNum();
			}
		//	System.out.println("num  ====:"+num);
			numList.add(num);
			num=0;
		}
		return numList;
	}
	/*
	 * CHANG -ZHENG
	 *
	 * for Visiteds get dates
	 **/
	public static List<String> getVisitedAllDate(){
		//HashMap<String,String> map = new HashMap<String,String>();
//		List<String> dates = new ArrayList<String>();
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		DBObject query = new BasicDBObject();
		//DBCursor visiteds ;
		@SuppressWarnings("unchecked")
		List<String> visiteds = mongoDB.getCollection(collectionVisited).distinct("date",query);
		List<String> finalVisiteds =new ArrayList<String>();
		if(visiteds.size()>7){
			for(int i=0;i<7;i++){
				finalVisiteds.add(visiteds.get(i));
			}
		}else{
			SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date=new Date();
			String currentDate = format.format(date);
			finalVisiteds.add(currentDate);
			for(int i=-6;i<0;i++){
				finalVisiteds.add(beforNumDay(date,i));
			}
		}
//		while(visiteds.hasNext()) {
//		       DBObject obj = visiteds.next();
//		       if(obj.get("date")!=null){
//		    	   String date = (String)obj.get("date");
//		    	   dates.add(date);
//		       }
//		    }
		return finalVisiteds;
	}
	public static List<String> getLastestDate(int day){
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date=new Date();
		List<String> finalVisiteds =new ArrayList<String>();
		String currentDate = format.format(date);
		finalVisiteds.add(currentDate);
		for(int i=-1;i>day-1;i--){
			finalVisiteds.add(beforNumDay(date,i));
		}
		return finalVisiteds;
	}
	  public static String beforNumDay(Date date, int day) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.DAY_OF_YEAR, day);
	        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    }
	
	public static List<Visited> getVisitedDetail(String date,String pageName){
		List<Visited> vitlist = new ArrayList<Visited>();
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		DBObject query = new BasicDBObject();
		query.put("date", date);
		query.put("pageName", pageName);
		DBCursor visiteds = mongoDB.getCollection(collectionVisited).find(query);
		if (null != visiteds) {
		while(visiteds.hasNext()) {
			Visited vit = new Visited();
		       DBObject obj = visiteds.next();
		       vit.setDate(date);
			   vit.setOpenid(obj.get("openid")+"");
			   if(obj.get("visitedNum")!=null){
			   vit.setVisitedNum(Integer.parseInt(obj.get("visitedNum")+""));}
			   else
			   {
				   vit.setVisitedNum(0);
			   }
			   if(obj.get("sharedNum")!=null){
					vit.setSharedNum(Integer.parseInt(obj.get("sharedNum") + ""));
				}
				else {
					vit.setSharedNum(0);
				}
			   vit.setPageName(obj.get("pageName")+"");
				vit.setImgUrl(obj.get("imgUrl")+"");
				vit.setNickName(obj.get("nickName")+"");
			   vitlist.add(vit);
		    }}else{
		    	Visited vit = new Visited();
			       vit.setDate(date);
				   vit.setOpenid("");
				   vit.setVisitedNum(0);
				   vit.setPageName(pageName);
					vit.setImgUrl("");
					vit.setNickName("");
				   vitlist.add(vit);
		    }
		return vitlist;
	}
	
	public static List<Inventory> getInventoryDetailByItem(String item){
		List<Inventory> Inventorys = new ArrayList<Inventory>();
		if(mongoDB==null){
			mongoDB = getMongoDB();
		}
		DBObject query = new BasicDBObject();
		query.put("plasticItem", item);
		DBCursor inventorys = mongoDB.getCollection(collectionInventory).find(query);
		while(inventorys.hasNext()) {
			Inventory it = new Inventory();
			   DBObject obj = inventorys.next();
			   if(!StringUtils.isEmpty(obj.get("plasticItem")+"")){
				   it.setPlasticItem(obj.get("plasticItem")+""); 
			   }
			   if(!StringUtils.isEmpty(obj.get("repositoryName")+"")){
				   it.setRepositoryName(obj.get("repositoryName")+"");
			   }
		       if(obj.get("waitDeliverAmount")!=null&&obj.get("waitDeliverAmount")!=""){
		    	   it.setWaitDeliverAmount(Double.parseDouble(obj.get("waitDeliverAmount")+""));
		       }
		       if(obj.get("availableAmount")!=null&&obj.get("availableAmount")!=""){
		    	   it.setAvailableAmount(Double.parseDouble(obj.get("availableAmount")+""));
		       }
		      
		       if(obj.get("inventoryAmount")!=null&&obj.get("inventoryAmount")!=""){
		    	   it.setInventoryAmount(Double.parseDouble(obj.get("inventoryAmount")+""));
		       }
		      
		       it.setLastUpdate(obj.get("lastUpdate")+"");
		     
		       if(obj.get("reserveDeliverAmount")!=null&&obj.get("reserveDeliverAmount")!=""){
		    	   it.setReserveDeliverAmount(Double.parseDouble(obj.get("reserveDeliverAmount")+""));
		       }
		     
		       if(obj.get("unit")!=null&&obj.get("unit")!=""){
		    	   it.setUnit(obj.get("unit")+"");
		       }
		      
		       Inventorys.add(it);
		}
		return Inventorys;
	}
	public static List<String> getAllOpenID(){
		mongoDB = getMongoDB();
		@SuppressWarnings("unchecked")
		List<String> dbuser = mongoDB.getCollection(wechat_user).distinct("OpenID");
		return dbuser;
	}
	
	public static List<QuoteVisit> getVisitedDetailByWeek(String date) {
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		query.put("date", date);
		query.put("pageName", "quoteDetailExternal");
		List<QuoteVisit> quoteVisit = new ArrayList<QuoteVisit>();
		DBCursor queryresults = mongoDB.getCollection(collectionVisited).find(
				query);
		System.out.println("implement mongo query....");
		if (null != queryresults) {
			while(queryresults.hasNext()){
			DBObject o = queryresults.next();
			QuoteVisit qv;
			if (o.get("nickName") != null) {
				qv = new QuoteVisit();
				qv.setName(o.get("nickName").toString());
				System.out.println("realName==="+o.get("nickName").toString());
				qv.setTotalVisited(o.get("visitedNum").toString());
				System.out.println("realvisited==="+o.get("visitedNum").toString());
				quoteVisit.add(qv);
			}
		}
		}
		List<QuoteVisit> quoteVisitCount = new ArrayList<QuoteVisit>();
		combVisitedDetail(quoteVisit,quoteVisitCount);
		return quoteVisitCount;
	}
	public static void combVisitedDetail(List<QuoteVisit> before,List<QuoteVisit> after){
        for (QuoteVisit qv : before) {  
            boolean state = false;  
            for (QuoteVisit qvs : after) {  
                if(qvs.getName().equals(qv.getName())){  
                    int count = Integer.parseInt(qvs.getTotalVisited());  
                    count += Integer.parseInt(qv.getTotalVisited());
                    qvs.setTotalVisited(count+"");
                    state = true;  
                }  
            }  
            if(!state){  
            	after.add(qv);  
            }  
        }
	}
	public static List<QuoteVisit> getVisitedDetailByMonth(String month) {
		mongoDB = getMongoDB();
		DBObject query = new BasicDBObject();
		Pattern pattern = Pattern.compile("^.*" + month + ".*$",
				Pattern.CASE_INSENSITIVE);
		query.put("date", pattern);
		query.put("pageName", "quoteDetailExternal");
		List<QuoteVisit> quoteVisit = new ArrayList<QuoteVisit>();
		DBCursor queryresults = mongoDB.getCollection(collectionVisited).find(
				query);
		if (null != queryresults) {
			while(queryresults.hasNext()){
			DBObject o = queryresults.next();
			QuoteVisit qv;
			if (o.get("nickName") != null) {
				qv = new QuoteVisit();
				qv.setName(o.get("nickName").toString());
				System.out.println("realName==="+o.get("nickName").toString());
				qv.setTotalVisited(o.get("visitedNum").toString());
				System.out.println("realvisited==="+o.get("visitedNum").toString());
				quoteVisit.add(qv);
			}
		}
		}
		List<QuoteVisit> quoteVisitCount = new ArrayList<QuoteVisit>();
		combVisitedDetail(quoteVisit,quoteVisitCount);
		return quoteVisitCount;
	}
}
					
