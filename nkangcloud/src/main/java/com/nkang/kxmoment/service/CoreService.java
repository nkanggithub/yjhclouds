package com.nkang.kxmoment.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.nkang.kxmoment.baseobject.ExtendedOpportunity;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.Opportunity;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.response.Article;
import com.nkang.kxmoment.response.NewsMessage;
import com.nkang.kxmoment.response.TextMessage;
import com.nkang.kxmoment.util.CronJob;
import com.nkang.kxmoment.util.DBUtils;
import com.nkang.kxmoment.util.MessageUtil;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.MySqlUtil;
import com.nkang.kxmoment.util.RestUtils;
import com.nkang.kxmoment.util.StopWatch;

public class CoreService
{

	private static Logger log = Logger.getLogger(CoreService.class);
	private static Timer timer= new Timer();
	private static String CurType = "";
	public static String processRequest(HttpServletRequest request)
	{
		String respXml = null;
		String respContent = "unknown request type.";
		String AccessKey = DBUtils.getValidAccessKey();
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
				if ("nkang".equals(textContent)) {
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
				}
				else if ("CLR".equals(textContent)) {
					respContent = "MDM DataBase Cleaning:\n";
					respContent = respContent + DBUtils.CleanDatabase();
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}
				else if ("LDSA".equals(textContent)) {
					respContent = "MDM Segament Load :\n";
					respContent = respContent + DBUtils.LoadSegmentArea() + " of " + DBUtils.getAllSegmentArea().size();
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}

				else {
					respContent = "OK：" + textContent + "\n";
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}
			}
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "IMAGE" + "\n";
				DBObject query = new BasicDBObject();
				query.put("industrySegmentNames", "[Oil and Gas]");
				List<DBObject> results = MongoDBBasic.getDistinctSubjectArea("industrySegmentNames");
				

				List<DBObject> resultsqueryNonLatinCity = MongoDBBasic.getDistinctSubjectArea("nonlatinCity");
				
				
				List<DBObject> resultscityRegion = MongoDBBasic.getDistinctSubjectArea("cityRegion");
				
				respContent = respContent + "Total Records: " + MongoDBBasic.getTotalRecordCount() +"\n Total Oil Gas: "+ MongoDBBasic.getSelectedDocumentWithQuery(query) + "\n Total Subject Area: " + results.size()+"\n Total City: "+ resultsqueryNonLatinCity.size()+" \n Total City Region: " +resultscityRegion.size();
				textMessage.setContent(respContent);
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
/*				for(DBObject dbo : results){
					respContent =  respContent + dbo.get("industrySegmentNames") + "\n";
				}*/
				
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
/*					respContent = "感谢您的关注 - SUBSCRIBE";
					textMessage.setContent(respContent);
					WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, fromUserName);
					DBUtils.createUser(wcu);
					respXml = MessageUtil.textMessageToXml(textMessage);
					*/
					
					WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, fromUserName);
					DBUtils.createUser(wcu);
					MongoDBBasic.createUser(wcu);
					
					articleList.clear();
					Article article = new Article();
					article.setTitle("Master Data Quality Governace");
					article.setDescription("Master Data Quality Governace Reporting");
					article.setPicUrl("http://www.micropole.com/library/img/SCHEMA-1.png");
					article.setUrl("http://shenan.duapp.com/mdm/DQDashBoard.jsp?UID=" + fromUserName);
					articleList.add(article);
					Article article2 = new Article();
					article2.setTitle("User Profile");
					article2.setDescription("Master Data Quality Governace Reporting");
					article2.setPicUrl("http://www.ecozine.com/sites/default/files/imagecache/category_blog/imagefield_default_images/icn-profile_0.png");
					article2.setUrl("http://shenan.duapp.com/mdm/profile.jsp?UID=" + fromUserName);
					articleList.add(article2);
					Article article3 = new Article();
					article3.setTitle("Data Dashboard");
					article3.setDescription("Master Data Dashboard");
					article3.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009urNv&oid=00D90000000pkXM");
					article3.setUrl("http://shenan.duapp.com/mdm/DQDashBoard.jsp?UID=" + fromUserName);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respXml = MessageUtil.newsMessageToXml(newsMessage);
					
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestObject.element("EventKey").getText();
					if(eventKey.equals("MDLAKE")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("Master Data Quality Governace");
						article.setDescription("Master Data Quality Governace Reporting");
						article.setPicUrl("http://www.micropole.com/library/img/SCHEMA-1.png");
						article.setUrl("http://shenan.duapp.com/mdm/DQDashBoard.jsp?UID=" + fromUserName);
						articleList.add(article);
						Article article2 = new Article();
						article2.setTitle("User Profile");
						article2.setDescription("Master Data Quality Governace Reporting");
						article2.setPicUrl("http://www.ecozine.com/sites/default/files/imagecache/category_blog/imagefield_default_images/icn-profile_0.png");
						article2.setUrl("http://shenan.duapp.com/mdm/profile.jsp?UID=" + fromUserName);
						articleList.add(article2);
						Article article3 = new Article();
						article3.setTitle("Data Dashboard");
						article3.setDescription("Master Data Dashboard");
						article3.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009urNv&oid=00D90000000pkXM");
						article3.setUrl("http://shenan.duapp.com/mdm/DQDashBoard.jsp?UID=" + fromUserName);
						articleList.add(article3);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if (eventKey.equals("nbcust")) {// Customer
						CurType = "customer";
						log.info("I am here custmer");
						GeoLocation geol = DBUtils.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();
						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = DBUtils.getNearByOppt(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), CurType, lat, lng);
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
						log.info("customer Send");
						
					} else if (eventKey.equals("nbcompe")) {// Competitor
						CurType = "competitor";
						log.info("I am here competitor");
						GeoLocation geol = DBUtils.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();

						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = DBUtils.getNearByOppt(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), CurType, lat, lng);

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
						log.info("competitor Send");
					} else if (eventKey.equals("nbpartner")) {// Partner
						CurType = "partner";
						log.info("I am here partner");
						GeoLocation geol = DBUtils.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();

						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = DBUtils.getNearByOppt(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), CurType, lat, lng);

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
						log.info("partner Send");
					}
					else if (eventKey.equals("nboppt")) {// Partner
						CurType = "";
						log.info("I am here Opportunity");
						GeoLocation geol = DBUtils.getDBUserGeoInfo(fromUserName);
						String lat = geol.getLAT();
						String lng = geol.getLNG();
						String addr = geol.getFAddr();
						
						List<ExtendedOpportunity> NearByOpptsExt =  new ArrayList<ExtendedOpportunity>();
						List<String> cityInfo = new ArrayList<String>();
						cityInfo = RestUtils.getUserCityInfoWithLatLng(lat,lng);
						NearByOpptsExt = DBUtils.getNearByOppt(cityInfo.get(0), cityInfo.get(1), cityInfo.get(2), "", lat, lng);

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
					
					else if (eventKey.equals("B3")) {// 2048
						Article article = new Article();
						article.setTitle("2048ÓÎÏ·");
						article.setDescription("ÓÎÏ·¹æÔòºÜ¼òµ¥£¬Ã¿´Î¿ÉÒÔÑ¡ÔñÉÏÏÂ×óÓÒÆäÖÐÒ»¸ö·½ÏòÈ¥»¬¶¯£¬Ã¿»¬¶¯Ò»´Î£¬ËùÓÐµÄÊý×Ö·½¿é¶¼»áÍù»¬¶¯µÄ·½Ïò¿¿Â£Íâ£¬ÏµÍ³Ò²»áÔÚ¿Õ°×µÄµØ·½ÂÒÊý³öÏÖÒ»¸öÊý×Ö·½¿é£¬ÏàÍ¬Êý×ÖµÄ·½¿éÔÚ¿¿Â£¡¢Ïà×²Ê±»áÏà¼Ó¡£ÏµÍ³¸øÓèµÄÊý×Ö·½¿é²»ÊÇ2¾ÍÊÇ4£¬Íæ¼ÒÒªÏë°ì·¨ÔÚÕâÐ¡Ð¡µÄ16¸ñ·¶Î§ÖÐ´Õ³ö¡°2048¡±Õâ¸öÊý×Ö·½¿é");
						article.setPicUrl("http://kaixinmoment.duapp.com/2048/1395908994962.png");
						article.setUrl("http://kaixinmoment.duapp.com/2048");
						articleList.add(article);
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
					DBUtils.updateUser(fromUserName, requestObject.element("Latitude").getText(), requestObject.element("Longitude").getText());
					MongoDBBasic.updateUser(fromUserName, requestObject.element("Latitude").getText(), requestObject.element("Longitude").getText(),wcu);
					//respXml = MessageUtil.textMessageToXml(textMessage);
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
