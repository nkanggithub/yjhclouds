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
import com.nkang.kxmoment.util.Constants;

public class CoreService
{
	private static Logger log = Logger.getLogger(CoreService.class);
	private static Timer timer= new Timer();
	public static String processRequest(HttpServletRequest request)
	{
		String xiamenlogo = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM3R&oid=00D90000000pkXM";
		String chongqinglogo = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM3b&oid=00D90000000pkXM";
		String wuhanlogo = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM3l&oid=00D90000000pkXM";
		String hongkonglogo = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM3v&oid=00D90000000pkXM";
		String subcompanylogo = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM45&oid=00D90000000pkXM";
		String contactUS = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM7n&oid=00D90000000pkXM";
		String companyCulture = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM82&oid=00D90000000pkXM";
		String navPic = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Dmtxx&oid=00D90000000pkXM";
		String inventoryPic = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmtyW&oid=00D90000000pkXM";
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
				}else if ("syncUser".equals(textContent)) {
					boolean res;
					int sucNum=0;
					String akey=RestUtils.getAccessKey();
					List<String> IdLists=RestUtils.getWeChatUserListID(akey);
					MongoDBBasic.delNullUser();
					for (int i = 0; i < IdLists.size(); i++) {
						WeChatUser user=RestUtils.getWeChatUserInfo(akey,IdLists.get(i).replaceAll("\"",""));
						res=MongoDBBasic.syncWechatUserToMongo(user);
						if(res==true){
							sucNum++;
						}
					}
					respContent = sucNum+"条数据同步成功!";
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}else if ("K8003".equals(textContent)||"8003".equals(textContent)) {
					respContent = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmjQs&oid=00D90000000pkXM";
					textMessage.setContent(respContent);
					respXml = MessageUtil.textMessageToXml(textMessage);
				}else if ("SJOB".equals(textContent)) {
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

	                textMessage.setContent(allUser.size() + " recevied");
	                respXml = MessageUtil.textMessageToXml(textMessage);
				}
			}
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				String mediaId = requestObject.element("MediaId").getText();
				String picurl = requestObject.element("PicUrl").getText();
				MongoDBBasic.updateUserWithFaceUrl(fromUserName,picurl);
				if(StringUtils.isEmpty(picurl)){
					picurl = "http://"+Constants.baehost+"/MetroStyleFiles/menu-face.png";
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
					article.setTitle("永佳和塑胶有限公司简介");
					article.setDescription("永佳和塑胶有限公司简介");
					article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJST&oid=00D90000000pkXM");
					article.setUrl("http://mp.weixin.qq.com/s/KtlqEakeUhJuAun5sbm6og");
					articleList.add(article);

					Article articleculture = new Article();
					articleculture.setTitle("企业文化");
					articleculture.setDescription("企业文化");
					articleculture.setPicUrl(companyCulture);
					articleculture.setUrl("http://mp.weixin.qq.com/s/YaBzsBHerq7I_UIZO29Tmw");
					articleList.add(articleculture);
					
					Article article4 = new Article();
					article4.setTitle("董事长致词");
					article4.setDescription("董事长致词");
					article4.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJSx&oid=00D90000000pkXM");
					article4.setUrl("http://mp.weixin.qq.com/s/2Ia4fdPe3dmTnKynbX1DkA");
					articleList.add(article4);
					
					Article articleContactUs = new Article();
					articleContactUs.setTitle("联系我们");
					articleContactUs.setDescription("联系我们");
					articleContactUs.setPicUrl(contactUS);
					articleContactUs.setUrl("http://mp.weixin.qq.com/s/qErL5R9ZqIuf_BFJv-NA7g");
					articleList.add(articleContactUs);
					
					
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respXml = MessageUtil.newsMessageToXml(newsMessage);
					if(!ret){
						respContent = "User Initialization Failed \n";
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
						article.setTitle("永佳和大数据");
						article.setDescription("永佳和大数据");
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmMS2&oid=00D90000000pkXM"); //big data
						article.setUrl("http://"+Constants.baehost+"/mdm/DQNavigate.jsp?UID=" + fromUserName);
						articleList.add(article);
						Article article4 = new Article();
						article4.setTitle("永佳和主数据");
						article4.setDescription("永佳和主数据");
						article4.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DlU1k&oid=00D90000000pkXM");
						article4.setUrl("http://"+Constants.baehost+"/DQMenu?UID=" + fromUserName);
						articleList.add(article4);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}else if(eventKey.equals("bizBA")){ // Data Lake
						articleList.clear();
						Article article = new Article();
						article.setTitle("永佳和实时报价");
						article.setDescription("永佳和实时报价");
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmMS2&oid=00D90000000pkXM"); //big data
						article.setUrl("http://"+Constants.baehost+"/mdm/quoteDetail.jsp?UID=" + fromUserName);
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("MYAPPS")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("胖和欢迎您随时砸单");
						article.setDescription("我的应用");
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DlU0c&oid=00D90000000pkXM");
						article.setUrl("http://"+Constants.baehost+"/mdm/welcome.jsp?UID=" + fromUserName);
						articleList.add(article);
						Article article2 = new Article();
						article2.setTitle("胖和微应用");
						article2.setDescription("My Personal Applications");
						article2.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJUj&oid=00D90000000pkXM");
						article2.setUrl("http://"+Constants.baehost+"/mdm/profile.jsp?UID=" + fromUserName);
						articleList.add(article2);
						
						
						Article article4 = new Article();
						article4.setTitle("U8登录");
						article4.setDescription("My Personal Applications");
						article4.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJXi&oid=00D90000000pkXM");
						article4.setUrl("https://udh.yonyouup.com/login");
						articleList.add(article4);
						
						Article article5 = new Article();
						article5.setTitle("U订货登录");
						article5.setDescription("My Personal Applications");
						article5.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJX9&oid=00D90000000pkXM");
						article5.setUrl("https://udh.yonyouup.com/login");
						articleList.add(article5);
						
						Article article6 = new Article();
						article6.setTitle("ERP登录");
						article6.setDescription("My Personal Applications");
						article6.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJa3&oid=00D90000000pkXM");
						article6.setUrl("https://udh.yonyouup.com/login");
						articleList.add(article6);
						
						Article article7 = new Article();
						article7.setTitle("CRM登录");
						article7.setDescription("My Personal Applications");
						article7.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJZe&oid=00D90000000pkXM");
						article7.setUrl("https://udh.yonyouup.com/login");
						articleList.add(article7);
						
						String hardcodeUID = "oij7nt5GgpKftiaoMSKD68MTLXpc";
						String hardcodeUID2 = "oij7ntwDnybi-9PLvGjuRR_EcJYg";
						if(MongoDBBasic.checkUserAuth(fromUserName, "isITOperations")||hardcodeUID.equalsIgnoreCase(fromUserName)||hardcodeUID2.equalsIgnoreCase(fromUserName)){
							Article article3 = new Article();
							article3.setTitle("胖和微管理");
							article3.setDescription("Administration");
							article3.setPicUrl("http://"+Constants.baehost+"/mdm/images/yjhadmin.jpg");
							article3.setUrl("http://"+Constants.baehost+"/admin/index.jsp?UID=" + fromUserName);
							articleList.add(article3);
						}
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("MYRECOG")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("我的胖和业绩");
						article.setDescription("我的胖和业绩");
						article.setPicUrl("http://"+Constants.baehost+"/MetroStyleFiles/RecognitionImage.jpg");
						article.setUrl("http://"+Constants.baehost+"/mdm/DQNavigate.jsp?UID=" + fromUserName);
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
						String icoURL = "http://"+Constants.baehost+"/MetroStyleFiles/RecognitionImage.jpg";
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
							myarticle.setUrl("http://"+Constants.baehost+"/mdm/RecognitionCenter.jsp?uid=" + fromUserName + "&num="+i);
							articleList.add(myarticle);
						}						

						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("MYFACE")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("永佳和塑胶有限公司简介");
						article.setDescription("永佳和塑胶有限公司简介");
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJST&oid=00D90000000pkXM");
						article.setUrl("http://mp.weixin.qq.com/s/KtlqEakeUhJuAun5sbm6og");
						articleList.add(article);

						Article articleculture = new Article();
						articleculture.setTitle("企业文化");
						articleculture.setDescription("企业文化");
						articleculture.setPicUrl(companyCulture);
						articleculture.setUrl("http://mp.weixin.qq.com/s/YaBzsBHerq7I_UIZO29Tmw");
						articleList.add(articleculture);
						
						Article article4 = new Article();
						article4.setTitle("董事长致词");
						article4.setDescription("董事长致词");
						article4.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmJSx&oid=00D90000000pkXM");
						article4.setUrl("http://mp.weixin.qq.com/s/2Ia4fdPe3dmTnKynbX1DkA");
						articleList.add(article4);
						
						Article articleContactUs = new Article();
						articleContactUs.setTitle("联系我们");
						articleContactUs.setDescription("联系我们");
						articleContactUs.setPicUrl(contactUS);
						articleContactUs.setUrl("http://mp.weixin.qq.com/s/qErL5R9ZqIuf_BFJv-NA7g");
						articleList.add(articleContactUs);
						
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if(eventKey.equals("subcompany")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("永佳和塑胶有限公司下属公司");
						article.setDescription("永佳和塑胶有限公司下属公司");
						article.setPicUrl(subcompanylogo);
						article.setUrl("http://mp.weixin.qq.com/s/byKfEHFk6keGvq17ggZeOA");
						articleList.add(article);

						Article articlesub1 = new Article();
						articlesub1.setTitle("厦门永佳和塑料有限公司");
						articlesub1.setDescription("厦门永佳和塑料有限公司");
						articlesub1.setPicUrl(xiamenlogo);
						articlesub1.setUrl("http://mp.weixin.qq.com/s/YXlFOaGXo7QqZy4gyu0tDg");
						articleList.add(articlesub1);
						
						Article articlesub2 = new Article();
						articlesub2.setTitle("重庆永佳和塑料有限公司");
						articlesub2.setDescription("重庆永佳和塑料有限公司");
						articlesub2.setPicUrl(chongqinglogo);
						articlesub2.setUrl("http://mp.weixin.qq.com/s/RpBKLRYhKUJ_2QFpaK3RkQ");
						articleList.add(articlesub2);
						
						Article articlesub3 = new Article();
						articlesub3.setTitle("武汉永佳和新材料有限公司");
						articlesub3.setDescription("武汉永佳和新材料有限公司");
						articlesub3.setPicUrl(wuhanlogo);
						articlesub3.setUrl("http://mp.weixin.qq.com/s/0qrFDbZAnOU-wNvnIYIL2A");
						articleList.add(articlesub3);
						
						Article articlesub4 = new Article();
						articlesub4.setTitle("香港永佳和");
						articlesub4.setDescription("香港永佳和");
						articlesub4.setPicUrl(hongkonglogo);
						articlesub4.setUrl("http://mp.weixin.qq.com/s/JBj5mqttK_NVPGj6R8zdvQ");
						articleList.add(articlesub4);
						
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
					}
					else if (eventKey.equals("storagenav")){
						articleList.clear();
						Article article = new Article();
						article.setTitle("永佳和塑胶有限公司仓库导航");
						article.setDescription("永佳和塑胶有限公司仓库为您导航");
						article.setPicUrl(inventoryPic);
						article.setUrl("http://map.baidu.com/mobile");
						articleList.add(article);
						
						Article articlenav1 = new Article();
						articlenav1.setTitle("公运库\n九龙坡区铁路村198号\n工作时间8:30AM-17:30PM\n电话68526840");
						articlenav1.setDescription("公运库-电话68526840");
						articlenav1.setPicUrl(navPic);
						articlenav1.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E4%B9%9D%E9%BE%99%E5%9D%A1%E5%8C%BA%E9%93%81%E8%B7%AF%E6%9D%91198%E5%8F%B7&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&src=0/vt=map");
						articleList.add(articlenav1);
						
						Article articlenav2 = new Article();
						articlenav2.setTitle("囤鑫中石化库\n港城工业园区C区\n工作时间8:30AM-17:20PM\n囤鑫中石化库-电话88301978");
						articlenav2.setDescription("囤鑫中石化库-电话88301978");
						articlenav2.setPicUrl(navPic);
						articlenav2.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E6%B8%AF%E5%9F%8E%E5%B7%A5%E4%B8%9A%E5%9B%AD%E5%8C%BAc%E5%8C%BA&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&wd2=%E9%87%8D%E5%BA%86%E5%B8%82%E6%B1%9F%E5%8C%97%E5%8C%BA&sug_forward=c1f324f93a6385b832f71d3b&src=1/vt=map");
						articleList.add(articlenav2);
						
						Article articlenav3 = new Article();
						articlenav3.setTitle("直发库");
						articlenav3.setDescription("直发库");
						articlenav3.setPicUrl(navPic);
						articlenav3.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E5%85%AC%E8%BF%90%E6%B8%9D%E8%A5%BF%E7%89%A9%E6%B5%81%E4%B8%AD%E5%BF%83&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&wd2=%E9%87%8D%E5%BA%86%E5%B8%82%E6%B2%99%E5%9D%AA%E5%9D%9D%E5%8C%BA&sug_forward=9f97b1c5c690087bb64f5467&src=1/vt=map");
						articleList.add(articlenav3);
						
						Article articlenav4 = new Article();
						articlenav4.setTitle("天地金中心库\n重庆沙坪坝区土主镇西部物流园区中石油仓储中心\n工作时间8:30AM-18:30PM\n电话65329881");
						articlenav4.setDescription("天地金中心库-电话65329881");
						articlenav4.setPicUrl(navPic);
						articlenav4.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E8%A5%BF%E9%83%A8%E7%89%A9%E6%B5%81%E5%9B%AD&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&wd2=%E9%87%8D%E5%BA%86%E5%B8%82%E9%87%8D%E5%BA%86%E5%B8%82&sug_forward=9434cf40ee8a5a6d83a00c0f&src=1/vt=map");
						articleList.add(articlenav4);
						
						Article articlenav5 = new Article();
						articlenav5.setTitle("铁风库");
						articlenav5.setDescription("铁风库");
						articlenav5.setPicUrl(navPic);
						articlenav5.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E5%85%AC%E8%BF%90%E6%B8%9D%E8%A5%BF%E7%89%A9%E6%B5%81%E4%B8%AD%E5%BF%83&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&wd2=%E9%87%8D%E5%BA%86%E5%B8%82%E6%B2%99%E5%9D%AA%E5%9D%9D%E5%8C%BA&sug_forward=9f97b1c5c690087bb64f5467&src=1/vt=map");
						articleList.add(articlenav5);
						
						Article articlenav6 = new Article();
						articlenav6.setTitle("成都宏达库");
						articlenav6.setDescription("成都宏达库");
						articlenav6.setPicUrl(navPic);
						articlenav6.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E5%85%AC%E8%BF%90%E6%B8%9D%E8%A5%BF%E7%89%A9%E6%B5%81%E4%B8%AD%E5%BF%83&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&wd2=%E9%87%8D%E5%BA%86%E5%B8%82%E6%B2%99%E5%9D%AA%E5%9D%9D%E5%8C%BA&sug_forward=9f97b1c5c690087bb64f5467&src=1/vt=map");
						articleList.add(articlenav6);
						
						Article articlenav7 = new Article();
						articlenav7.setTitle("成都力庆库");
						articlenav7.setDescription("成都力庆库");
						articlenav7.setPicUrl(navPic);
						articlenav7.setUrl("http://map.baidu.com/mobile/webapp/index/index#search/search/qt=s&wd=%E5%85%AC%E8%BF%90%E6%B8%9D%E8%A5%BF%E7%89%A9%E6%B5%81%E4%B8%AD%E5%BF%83&c=132&searchFlag=bigBox&version=5&exptype=dep&src_from=webapp_all_bigbox&wd2=%E9%87%8D%E5%BA%86%E5%B8%82%E6%B2%99%E5%9D%AA%E5%9D%9D%E5%8C%BA&sug_forward=9f97b1c5c690087bb64f5467&src=1/vt=map");
						articleList.add(articlenav7);
						
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
						article.setTitle(NearByOpptsExt.size() + " 个永佳和客户在您附近 ");
						article.setDescription(NearByOpptsExt.size() + "个永佳和客户在您附近\n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://"+Constants.baehost+"/mdm/MoreCustomer.jsp?UID=" + fromUserName);
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
							articlevar.setUrl("http://"+Constants.baehost+"/mdm/MoreCustomer.jsp?UID=" + fromUserName);
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
						article.setTitle(NearByOpptsExt.size() + " 个永佳和竞争者在您附近");
						article.setDescription(NearByOpptsExt.size() +"个永佳和竞争者在您附近\n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://"+Constants.baehost+"/index.jsp");
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
							articlevar.setUrl("http://"+Constants.baehost+"/index.jsp");
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
						article.setTitle(NearByOpptsExt.size() + " 个永佳和合作伙伴在您附近");
						article.setDescription(NearByOpptsExt.size() + "个永佳和合作伙伴在您附近 \n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://"+Constants.baehost+"/index.jsp");
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
							articlevar.setUrl("http://"+Constants.baehost+"/index.jsp");
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
						article.setTitle(NearByOpptsExt.size() + " 个永佳和机遇在您附近 ");
						article.setDescription(NearByOpptsExt.size() + "个永佳和机遇在您附近 \n" + addr);
						article.setPicUrl("https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=01590000009v2eJ&oid=00D90000000pkXM");
						article.setUrl("http://"+Constants.baehost+"/index.jsp");
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
							articlevar.setUrl("http://"+Constants.baehost+"/index.jsp");
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