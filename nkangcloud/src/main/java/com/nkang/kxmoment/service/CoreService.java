package com.nkang.kxmoment.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.DBObject;
import com.nkang.kxmoment.baseobject.CongratulateHistory;
import com.nkang.kxmoment.baseobject.ExtendedOpportunity;
import com.nkang.kxmoment.baseobject.FaceObj;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.response.Article;
import com.nkang.kxmoment.response.NewsMessage;
import com.nkang.kxmoment.response.TextMessage;
import com.nkang.kxmoment.util.CommenJsonUtil;
import com.nkang.kxmoment.util.CronJob;
import com.nkang.kxmoment.util.FaceRecognition;
import com.nkang.kxmoment.util.MessageUtil;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;

public class CoreService
{
	private static Logger log = Logger.getLogger(CoreService.class);
	private static Timer timer= new Timer();
	public static String processRequest(HttpServletRequest request)
	{
		
		String respXml = null;
		String respContent = "unknown request type.";
		String AccessKey = MongoDBBasic.getValidAccessKey();
		try {
			Element requestObject 	= 	MessageUtil.parseXml(request);
			String fromUserName 	= 	requestObject.element("FromUserName").getText();
			String toUserName 		= 	requestObject.element("ToUserName").getText();
			String msgType 			= 	requestObject.element("MsgType").getText();
			
			
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

			List<Article> articleList = new ArrayList<Article>();

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String textContent = requestObject.element("Content").getText().trim();
				if ("cm".equals(textContent)) {
					respContent = RestUtils.createMenu(AccessKey);
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}
				else if ("SJOB".equals(textContent)) {
					Calendar date = Calendar.getInstance();
				    //date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				    date.set(Calendar.HOUR, 0);
				    date.set(Calendar.MINUTE, 5);
				    date.set(Calendar.SECOND, 0);
				    date.set(Calendar.MILLISECOND, 0);
				    // Schedule to run every Sunday in midnight
				    //timer.schedule(new CronJob(),date.getTime(),1000 * 60 * 60 * 24 * 7);
				    timer.schedule(new CronJob(), 0 , 5 * 60 * 1000);
				    log.info("-----Job Started and running----" + timer.toString());
				    respContent = "started " + timer.toString();
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}
				else if ("TJOB".equals(textContent)) {
					timer.purge();
					timer.cancel();
					log.info("-----Job Stoped and Cancel----" + timer.toString());
					respContent = "stoped" + timer.toString();
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}else if ("MSJOB".equals(textContent)) {
					Calendar date = Calendar.getInstance();
					    //date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					    date.set(Calendar.HOUR, 0);
					    date.set(Calendar.MINUTE, 5);
					    date.set(Calendar.SECOND, 0);
					    date.set(Calendar.MILLISECOND, 0);
					    // Schedule to run every Sunday in midnight
					    //timer.schedule(new CronJob(),date.getTime(),1000 * 60 * 60 * 24 * 7);
//					    timer.schedule(new CronJobForMangoDB(), 0 , 5 * 60 * 1000);
//					    log.info("-----CronJobForMangoDB Started and running----" + timer.toString());
//					    respContent = "started " + timer.toString();
//						textMessage.setContent(respContent);
//						respXml = MessageUtil.textMessageToXml(textMessage);
					}
					else if ("MTJOB".equals(textContent)) {
						timer.purge();
						timer.cancel();
						log.info("-----CronJobForMangoDB Stoped and Cancel----" + timer.toString());
						respContent = "stoped" + timer.toString();
						textMessage.setContent(respContent);
						respXml = MessageUtil.textMessageToXml(textMessage);
					}
				else {
					List<String> allUser = MongoDBBasic.getAllOpenIDByIsActivewithIsRegistered();
	                for(int i=0;i<allUser.size();i++){
	                     if(fromUserName.equals(allUser.get(i))){
	                         allUser.remove(i);
	                     }
	                }
	                    
	                 for(int i=0;i<allUser.size();i++){
	                    RestUtils.sendTextMessageToUserOnlyByCustomInterface(textContent,allUser.get(i),fromUserName);
	                }
	                TextMessage textMessage1 = new TextMessage();
	                textMessage1.setContent("message sent");
	                respXml = MessageUtil.textMessageToXml(textMessage1);
/*	                textMessage1.setFromUserName("oqPI_xLq1YEJOczHi4DS2-1U0zqc"); // from Ning
	                textMessage1.setCreateTime(new Date().getTime());
	                textMessage1.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	                textMessage1.setToUserName("oqPI_xACjXB7pVPGi5KH9Nzqonj4"); // to qing
                    try{
                    	respXml = MessageUtil.textMessageToXml(textMessage1);
                    }
                    catch(Exception e){
                    	textMessage1.setContent(e.toString());
                    	respXml = MessageUtil.textMessageToXml(textMessage1);
                    }*/
                    
				}
			}
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				String mediaId = requestObject.element("MediaId").getText();
				String picurl = requestObject.element("PicUrl").getText();
				MongoDBBasic.updateUserWithFaceUrl(fromUserName,picurl);
				if(StringUtils.isEmpty(picurl)){
					picurl = "http://shenan.duapp.com/MetroStyleFiles/menu-face.png";
				}

				List<String> allUser = MongoDBBasic.getAllOpenIDByIsActivewithIsRegistered();
		    	for(int i=0;i<allUser.size();i++){
		    		if(fromUserName.equals(allUser.get(i))){
		    			allUser.remove(i);
		    		}
		    	}
		    	log.info("mediaId-----------------:"+mediaId);
		        
		    	for(int i=0;i<allUser.size();i++){
		    		RestUtils.sendImgMessageToUserOnlyByCustomInterface(allUser.get(i),mediaId);
		    	}
				
				textMessage.setContent("image sent");
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "LINK";
				textMessage.setContent(respContent);
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "VOICE";
				List<DBObject> results = MongoDBBasic.getDistinctSubjectArea("industrySegmentNames");
				respContent = respContent + results.size() + "\n";

				textMessage.setContent(respContent);
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "VIDEO";
				textMessage.setContent(respContent);
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestObject.element("Event").getText();
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, fromUserName);
					//DBUtils.createUser(wcu);
					Boolean ret =  false;
					if(wcu.getOpenid() != "" || wcu.getOpenid() != null){
						ret = MongoDBBasic.createUser(wcu);
					}
					articleList.clear();
					Article article = new Article();
					article.setTitle("Master Data Quality Governace");
					article.setDescription("Master Data Quality Governace Reporting");
					article.setPicUrl("http://www.micropole.com/library/img/SCHEMA-1.png");
					article.setUrl("http://shenan.duapp.com/mdm/DQNavigate.jsp?UID=" + fromUserName);
					articleList.add(article);
					Article article2 = new Article();
					article2.setTitle("User Profile");
					article2.setDescription("Master Data Quality Governace Reporting");
					article2.setPicUrl("http://www.ecozine.com/sites/default/files/imagecache/category_blog/imagefield_default_images/icn-profile_0.png");
					article2.setUrl("http://shenan.duapp.com/mdm/profile.jsp?UID=" + fromUserName);
					articleList.add(article2);
					Article article4 = new Article();
					article4.setTitle("Data Visualization");
					article4.setDescription("Master Data Visualization");
					article4.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009urNv&oid=00D90000000pkXM");
					article4.setUrl("http://shenan.duapp.com/DQMenu?UID=" + fromUserName);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respXml = MessageUtil.newsMessageToXml(newsMessage);
					if(!ret){
						respContent = "User Initialization Failed：\n";
						textMessage.setContent(respContent);
						respXml = MessageUtil.textMessageToXml(textMessage);
					}
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// Inactive the User - To-Be-Done
					MongoDBBasic.removeUser(fromUserName);

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestObject.element("EventKey").getText();
					if(eventKey.equals("MDLAKE")){ // Data Lake
						articleList.clear();
						Article article = new Article();
						article.setTitle("Master Data Quality Governace");
						article.setDescription("Master Data Quality Governace Reporting");
						article.setPicUrl("http://www.micropole.com/library/img/SCHEMA-1.png");
						article.setUrl("http://shenan.duapp.com/mdm/DQNavigate.jsp?UID=" + fromUserName);
						articleList.add(article);
						Article article4 = new Article();
						article4.setTitle("Data Visualization");
						article4.setDescription("Master Data Visualization");
						article4.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009urNv&oid=00D90000000pkXM");
						article4.setUrl("http://shenan.duapp.com/DQMenu?UID=" + fromUserName);
						articleList.add(article4);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("MYAPPS")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("Master Data Quality Governace");
						article.setDescription("Master Data Quality Governace Reporting");
						article.setPicUrl("http://www.micropole.com/library/img/SCHEMA-1.png");
						article.setUrl("http://shenan.duapp.com/mdm/DQNavigate.jsp?UID=" + fromUserName);
						articleList.add(article);
						Article article2 = new Article();
						article2.setTitle("了解我自己");
						article2.setDescription("My Personal Applications");
						article2.setPicUrl("http://www.ecozine.com/sites/default/files/imagecache/category_blog/imagefield_default_images/icn-profile_0.png");
						article2.setUrl("http://shenan.duapp.com/mdm/profile.jsp?UID=" + fromUserName);
						articleList.add(article2);
						String hardcodeUID = "oqPI_xLq1YEJOczHi4DS2-1U0zqc";
						if(hardcodeUID.equalsIgnoreCase(fromUserName)){
							Article article3 = new Article();
							article3.setTitle("产品管理");
							article3.setDescription("Administration");
							article3.setPicUrl("http://www.ecozine.com/sites/default/files/imagecache/category_blog/imagefield_default_images/icn-profile_0.png");
							article3.setUrl("http://shenan.duapp.com/admin/index.jsp");
							articleList.add(article3);
						}
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("MYRECOG")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("My Lasted Recognitions");
						article.setDescription("My Lasted Recognition");
						article.setPicUrl("http://shenan.duapp.com/MetroStyleFiles/RecognitionImage.jpg");
						article.setUrl("http://shenan.duapp.com/mdm/DQNavigate.jsp?UID=" + fromUserName);
						articleList.add(article);
						
						// add article here
						List<CongratulateHistory> myrecoghistList = MongoDBBasic.getRecognitionInfoByOpenID(fromUserName, "");
						int myRecog = myrecoghistList.size();
						if(myRecog > 6){
							myRecog = 6;
						}
						Article myarticle;
						CongratulateHistory congratulateHistory;
						String icoURLPartnerFirst = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkaRc&oid=00D90000000pkXM";
						String icoURLBaisForAction = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkaS6&oid=00D90000000pkXM";
						String icoURLInnovator = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkaSa&oid=00D90000000pkXM";
						String icoURL = "http://shenan.duapp.com/MetroStyleFiles/RecognitionImage.jpg";
						for(int i = myRecog; i >= 1; i--){
							myarticle = new Article();
							congratulateHistory = new CongratulateHistory();
							congratulateHistory = myrecoghistList.get(i-1);
							String type = congratulateHistory.getType();
							String comments  = congratulateHistory.getComments();
							if(StringUtils.isEmpty(comments)){
								comments = "You must done something amazaing";
							}
							else if(comments.length() >= 30){
								comments =  comments.trim();
								comments = comments.substring(0, 30) + "...";
							}
							myarticle.setTitle(congratulateHistory.getComments()  +"\n" + congratulateHistory.getCongratulateDate() + " | " + congratulateHistory.getFrom() + "\n" + type);
							myarticle.setDescription("My Recognition");
							if(type.equalsIgnoreCase("Bais For Action")){
								icoURL = icoURLBaisForAction;
							}
							else if(type.equalsIgnoreCase("Innovators at Heart")){
									icoURL = icoURLInnovator;
							}
							else{ 
								icoURL = icoURLPartnerFirst;
							}
							myarticle.setPicUrl(icoURL);
							myarticle.setUrl("http://shenan.duapp.com/mdm/RecognitionCenter.jsp?uid=" + fromUserName + "&num="+i);
							articleList.add(myarticle);
						}						

						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("MYFACE")){
						String picurl = MongoDBBasic.getUserWithFaceUrl(fromUserName);
						if(StringUtils.isEmpty(picurl)){
							picurl = "http://shenan.duapp.com/MetroStyleFiles/menu-face.png";
						}
						articleList.clear();
						Article article = new Article();
						article.setTitle("看我颜值如何爆表-发个照片到这个公众号");
						article.setDescription("看我颜值如何爆表");
						article.setPicUrl(picurl);
						article.setUrl("http://shenan.duapp.com/mdm/DQNavigate.jsp?UID=" + fromUserName);
						articleList.add(article);
						
						Article article2 = new Article();
						article2.setTitle("测我颜值");
						article2.setDescription("测我颜值");
						article2.setPicUrl("http://shenan.duapp.com/MetroStyleFiles/menu-face.png");
						article2.setUrl("http://shenan.duapp.com/mdm/face.jsp?UID=" + fromUserName);
						articleList.add(article2);
						
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if (eventKey.equals("nbcust")) {// Customer
						String CurType = "customer";
						GeoLocation geol = MongoDBBasic.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();
						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = MongoDBBasic.getNearByOpptFromMongoDB(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), CurType, lat, lng);
						Article article = new Article();
						article.setTitle(NearByOpptsExt.size() + " Customers NearBy HPE ");
						article.setDescription(NearByOpptsExt.size() + " Customers " +  "Found Near By You \n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://shenan.duapp.com/mdm/MoreCustomer.jsp?UID=" + fromUserName);
						articleList.add(article);
						int opptCount = 7;
						if(NearByOpptsExt.size() < opptCount ){
							opptCount = NearByOpptsExt.size();
						}
						for(int i = 0; i < opptCount ;  i++){
							Article articlevar = new Article();
							articlevar.setTitle(NearByOpptsExt.get(i).getOpptName() + "\n" + NearByOpptsExt.get(i).getSegmentArea() + "\n" + NearByOpptsExt.get(i).getDistance() + " KM");
							articlevar.setDescription("NearBy Opportunity");
							articlevar.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2gP&oid=00D90000000pkXM");
							articlevar.setUrl("http://shenan.duapp.com/mdm/MoreCustomer.jsp?UID=" + fromUserName);
							articleList.add(articlevar);
						}
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
						
					} else if (eventKey.equals("nbcompe")) {// Competitor
						String CurType = "competitor";
						GeoLocation geol = MongoDBBasic.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();

						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = MongoDBBasic.getNearByOpptFromMongoDB(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), CurType, lat, lng);

						Article article = new Article();
						article.setTitle(NearByOpptsExt.size() + " Competitors NearBy HPE");
						article.setDescription(NearByOpptsExt.size() +  " Competitor " +  "Found Near By You \n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://shenan.duapp.com/index.jsp");
						articleList.add(article);
						int opptCount = 7;
						if(NearByOpptsExt.size() < opptCount ){
							opptCount = NearByOpptsExt.size();
						}
						for(int i = 0; i < opptCount ;  i++){
							Article articlevar = new Article();
							articlevar.setTitle(NearByOpptsExt.get(i).getOpptName() + "\n" + NearByOpptsExt.get(i).getSegmentArea() + "\n" + NearByOpptsExt.get(i).getDistance() + " KM");
							articlevar.setDescription("NearBy Opportunity");
							articlevar.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2gP&oid=00D90000000pkXM");
							articlevar.setUrl("http://shenan.duapp.com/index.jsp");
							articleList.add(articlevar);
						}
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					} else if (eventKey.equals("nbpartner")) {// Partner
						String CurType = "partner";
						GeoLocation geol = MongoDBBasic.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();

						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = MongoDBBasic.getNearByOpptFromMongoDB(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), CurType, lat, lng);

						Article article = new Article();
						article.setTitle(NearByOpptsExt.size() + " Partners NearBy HPE");
						article.setDescription(NearByOpptsExt.size() + " Partner " +  "Found Near By You \n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://shenan.duapp.com/index.jsp");
						articleList.add(article);
						int opptCount = 7;
						if(NearByOpptsExt.size() < opptCount ){
							opptCount = NearByOpptsExt.size();
						}
						for(int i = 0; i < opptCount ;  i++){
							Article articlevar = new Article();
							articlevar.setTitle(NearByOpptsExt.get(i).getOpptName() + "\n" + NearByOpptsExt.get(i).getSegmentArea() + "\n" + NearByOpptsExt.get(i).getDistance() + " KM");
							articlevar.setDescription("NearBy Opportunity");
							articlevar.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2gP&oid=00D90000000pkXM");
							articlevar.setUrl("http://shenan.duapp.com/index.jsp");
							articleList.add(articlevar);
						}
						
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if (eventKey.equals("nboppt")) {// Partner
						String CurType = "";
						GeoLocation geol = MongoDBBasic.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();
						
						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = MongoDBBasic.getNearByOpptFromMongoDB(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), "", lat, lng);

						Article article = new Article();
						article.setTitle(NearByOpptsExt.size() + " Opportunity NearBy HPE ");
						article.setDescription(NearByOpptsExt.size() + " Opportunity " +  "Found Near By You \n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://shenan.duapp.com/index.jsp");
						articleList.add(article);
						int opptCount = 7;
						if(NearByOpptsExt.size() < opptCount ){
							opptCount = NearByOpptsExt.size();
						}
						for(int i = 0; i < opptCount ;  i++){
							Article articlevar = new Article();
							articlevar.setTitle(NearByOpptsExt.get(i).getOpptName() + "\n" + NearByOpptsExt.get(i).getSegmentArea() + "\n" + NearByOpptsExt.get(i).getDistance() + " KM");
							articlevar.setDescription("NearBy Opportunity");
							articlevar.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2gP&oid=00D90000000pkXM");
							articlevar.setUrl("http://shenan.duapp.com/index.jsp");
							articleList.add(articlevar);
						}
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN_TEXT)) {
					String eventKey = requestObject.element("EventKey").getText();
					if (eventKey.equals("C1")) {
						respContent = requestObject.element("ScanCodeInfo").element("ScanResult").getText();
						textMessage.setContent(respContent);
						respXml = MessageUtil.textMessageToXml(textMessage);
					}
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN_URL)) {
					respContent = "scan url and redirect.";
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					respContent = "scan qrcode.";
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					respContent = "upload location detail.";
					textMessage.setContent(respContent);
					WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, fromUserName);
					MongoDBBasic.updateUser(fromUserName, requestObject.element("Latitude").getText(), requestObject.element("Longitude").getText(),wcu);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					respContent = "page redirect.";
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
	
}
