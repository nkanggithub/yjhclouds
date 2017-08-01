package com.nkang.kxmoment.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.Location;
import com.nkang.kxmoment.baseobject.Market;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
import com.nkang.kxmoment.baseobject.PlasticItem;
import com.nkang.kxmoment.baseobject.QuotationList;
import com.nkang.kxmoment.baseobject.Rate;
import com.nkang.kxmoment.baseobject.Visited;
import com.nkang.kxmoment.baseobject.Visitedreturn;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.service.PlasticItemService;
import com.nkang.kxmoment.util.Constants;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;

@Controller
public class QuotationController {

	@RequestMapping("/updateQuotation")
	@ResponseBody
	public String updateQuotation(@RequestParam(value="category", required=false) String category,
			@RequestParam(value="categoryGrade", required=false) String categoryGrade,
			@RequestParam(value="item", required=false) String item,
			@RequestParam(value="quotationPrice", required=false) String quotationPrice,
			@RequestParam(value="comments", required=false) String comments,
			@RequestParam(value="locationAmounts", required=false) String locationAmounts,
			@RequestParam(value="avaliableInventory", required=false) String avaliableInventory,
			@RequestParam(value="onDelivery", required=false) String onDelivery,
			@RequestParam(value="soldOutOfPay", required=false) String soldOutOfPay,
			@RequestParam(value="originalProducer", required=false) String originalProducer
			){
			OnlineQuotation quotation = new OnlineQuotation();
			quotation.setCategory(category);
			quotation.setCategoryGrade(categoryGrade);
			quotation.setItem(item);
			quotation.setComments(comments);
			quotation.setLocationAmounts(locationAmounts);
			quotation.setAvaliableInventory(avaliableInventory);
			quotation.setOnDelivery(onDelivery);
			quotation.setSoldOutOfPay(soldOutOfPay);
			quotation.setOriginalProducer(originalProducer);
			java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
			quotation.setLastUpdate(cursqlTS.toString());
			String ret="";
			ret=MongoDBBasic.saveOnlineQuotation(quotation);
		
		return ret;
		
	}
	
	
	@RequestMapping("/updateQuotationByItem")
	@ResponseBody
	public String updateQuotation(HttpServletRequest request, HttpServletResponse response){
		OnlineQuotation quotation = new OnlineQuotation();
		quotation.setAvaliableInventory(request.getParameter("avaliableInventory"));
		if(!"/".equals(request.getParameter("category"))){
		quotation.setCategory(request.getParameter("category"));}
		else{quotation.setCategory("");}
		if(!"/".equals(request.getParameter("categoryGrade"))){
		quotation.setCategoryGrade(request.getParameter("categoryGrade"));}
		else{quotation.setCategoryGrade("");}
		quotation.setComments(request.getParameter("comments"));
		quotation.setItem(request.getParameter("item"));
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		quotation.setLastUpdate(cursqlTS.toString());
		quotation.setLocationAmounts(request.getParameter("locationAmounts"));
		quotation.setOnDelivery(request.getParameter("onDelivery"));
		quotation.setOriginalProducer(request.getParameter("originalProducer"));
		quotation.setQuotationPrice(request.getParameter("quotationPrice"));
		quotation.setSoldOutOfPay(request.getParameter("soldOutOfPay"));
		if(request.getParameter("isUpdatePrice").equals("1")){
		quotation.setApproveStatus("0");}
		
		String ret="";
		ret=MongoDBBasic.saveOnlineQuotation(quotation);
		return ret;
		
	}
	

	@RequestMapping("/removeQuotationByItem")
	@ResponseBody
	public String removeQuotationByItem(@RequestParam(value="item", required=true) String item){
		String ret="";
		ret=MongoDBBasic.delQuotationByItem(item);
		return ret;
	}
			
	@RequestMapping("/getAllQuotations")
	public @ResponseBody List<OnlineQuotation> getAllQuotations(){
		
		return MongoDBBasic.getAllQuotations();
		
	}
	
	@RequestMapping("/getOneQuotation")
	public @ResponseBody List<OnlineQuotation> getOneQuotation(@RequestParam(value="item", required=true) String item){
		List<OnlineQuotation> Quotation =new ArrayList<OnlineQuotation>();
		Quotation=MongoDBBasic.getOneQuotation(item);
		return Quotation;
	}
	
	@RequestMapping("/getQuotationsByQuery")
	public @ResponseBody List<OnlineQuotation> getQuotationsByQuery(@RequestParam(value="category", required=false) String category,
			@RequestParam(value="item", required=false) String item){
		
		return MongoDBBasic.getQuotationsByQuery(category,item);
		
	}
	
	@RequestMapping("/updateStatus")
	public @ResponseBody String updateStatus(@RequestParam(value="item", required=true) String item,
			@RequestParam(value="approveStatus", required=false) String approveStatus){
		OnlineQuotation onlineQuotation = new OnlineQuotation();
		onlineQuotation.setApproveStatus(approveStatus);
		onlineQuotation.setItem(item);
		return MongoDBBasic.saveOnlineQuotation(onlineQuotation);
		
	}
	

	@RequestMapping("/saveQuotationList")
	public @ResponseBody String saveQuotationList(@RequestParam(value="plasticItem", required=true) String plasticItem,
			@RequestParam(value="status", required=false) String status,
			@RequestParam(value="approveBy", required=false) String approveBy,
			@RequestParam(value="editBy", required=false) String editBy,
			@RequestParam(value="dateTime", required=false) String dateTime,
			@RequestParam(value="suggestPrice", required=false) Double suggestPrice,
			@RequestParam(value="type", required=false) int type
			){
		QuotationList quotation = new QuotationList();
		quotation.setApproveBy(approveBy);
		quotation.setDateTime(dateTime);
		quotation.setEditBy(editBy);
		quotation.setPlasticItem(plasticItem);
		quotation.setSuggestPrice(suggestPrice);
		quotation.setStatus(status);
		quotation.setType(type);
		
		String ret = MongoDBBasic.insertQuotationList(quotation);
		
		return ret;
	}
	
	
	@RequestMapping("/pushPlasticItem")
	public @ResponseBody List<PlasticItem> getPushPlasticItem(@RequestParam(value="openid", required=true) String openid){
		 List<PlasticItem>  plasticItemlist = new ArrayList<PlasticItem>();
		List<String> itemsList = new ArrayList<String>();
		WeChatMDLUser user=MongoDBBasic.queryUserKM(openid); 
		itemsList=user.getKmLists();
		for(String str : itemsList){
			plasticItemlist.add(PlasticItemService.getDetailByNo(str));
		}
		return plasticItemlist;
		
	}
	

	@RequestMapping("/insertVisited")
	public @ResponseBody String insertVisited(@RequestParam(value="openid") String openid,@RequestParam(value="pageName") String pageName,@RequestParam(value="imgUrl") String imgUrl,@RequestParam(value="nickName") String nickName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		String str = MongoDBBasic.updateVisited(openid,date,pageName,imgUrl,nickName); 
		return str;
	}
	
	@RequestMapping("/getVisitedByOpenid")
	public @ResponseBody List<Visited> getVisitedByOpenid(@RequestParam(value="openid") String openid,@RequestParam(value="date")String date ){
		
		return MongoDBBasic.getVisited(openid,date); 
	}
	@RequestMapping("/getVisitedDetail")
	public @ResponseBody List<Visited> getVisitedByDate(@RequestParam(value="dateIndex")String dateIndex,@RequestParam(value="pageName")String pageName){
		String pn="";
		ArrayList<Map> visitedPageList=MongoDBBasic.QueryVisitPage();
		for(int i=0;i<visitedPageList.size();i++){
			if(visitedPageList.get(i).get("descName").toString().equals(pageName)){
				pn=visitedPageList.get(i).get("realName").toString().trim();
			}
		}
		int index=Integer.parseInt(dateIndex);
		String date=MongoDBBasic.getLastestDate(-6).get(index);
		return MongoDBBasic.getVisitedDetail(date,pn); 
	}
	
	@RequestMapping("/getVisitedAllDate")
	public @ResponseBody List<String> getVisitedAllDate(){
		
		return MongoDBBasic.getVisitedAllDate(); 
	}
	
	@RequestMapping("/getVisitedbTotalNumByDate")
	public @ResponseBody  Map<String,String> getVisitedbTotalNumByDate(){
		List<String> dates = MongoDBBasic.getVisitedAllDate();
		
		return MongoDBBasic.getVisitedbTotalNumByDate(dates); 
	}
	
	@RequestMapping("/getInventoryDetailByItem")
	public @ResponseBody List<Inventory> getInventoryDetailByItem(@RequestParam(value="item")String item){
		
		return MongoDBBasic.getInventoryDetailByItem(item); 
	}

	@RequestMapping("/getRate")
	public @ResponseBody Double getRate(@RequestParam(value="name")String name){
		Double rate = 0.0;
		try {
			List<Rate> lt = RestUtils.callGetRate();
			Map<String, String> map = new HashMap<String, String>();
			for(Rate rt : lt){
				map.put(rt.getName(), rt.getRate());
			}
			rate = Double.valueOf(map.get(name));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rate;
	}
	@RequestMapping("/getNoVisitedDetail")
	public @ResponseBody List<WeChatMDLUser> getNoVisitedDetail(@RequestParam(value="dateIndex")String dateIndex,@RequestParam(value="pageName")String pageName){
		System.out.println("start to implement getNoVisitedDetail method");
		List<WeChatMDLUser> allUser = MongoDBBasic.getAllUserByIsInternalSeniorMgt();
		if(allUser.isEmpty()){
			System.out.println("no data in allUser Obj");
		}else
		{
			System.out.println("fisrt obj:-----"+allUser.get(1).getNickname());
		}
		int index=Integer.parseInt(dateIndex);
		String date=MongoDBBasic.getLastestDate(-6).get(index);
		List<Visited> visited= MongoDBBasic.getVisitedDetail(date,pageName); 
		for(int i=0;i<visited.size();i++){
			
			for(int j=0;j<allUser.size();j++){
			//	System.out.println("visited.get(j).nickName:----"+visited.get(j).getOpenid()+"--"+visited.get(j).getNickName());
				if(allUser.get(j).getOpenid().equals(visited.get(i).getOpenid())){
					System.out.println("allUser.get(i).nickName:----"+allUser.get(j).getOpenid()+"--"+allUser.get(j).getNickname());
					allUser.remove(j);
					}
				}
			}
		return allUser;
		}
/*	@RequestMapping("/getVisitedbTotalNumPage")
	public @ResponseBody Map<String,Visitedreturn> getVisitedbTotalNumPage(){
		Map<String,Visitedreturn> mapret = new HashMap<String,Visitedreturn>();
		List<String> dates = MongoDBBasic.getVisitedAllDate();
		//Map<String,List<Visited>> maps = MongoDBBasic.getVisitedByDate(dates); 
		int page1Num=0;
		int page2Num=0;
		for(String date : dates){
			Visitedreturn vrtn = new Visitedreturn();
			List<Visited> visiteds = MongoDBBasic.getVisitedDetail(date);
			for(Visited vis : visiteds){
				if(vis.getPageName().equals("page1")){
					page1Num = page1Num+vis.getVisitedNum();
				}else if(vis.getPageName().equals("page2")){
					page2Num = page2Num+vis.getVisitedNum();
				}
			}
			vrtn.setPage1Num(page1Num);
			vrtn.setPage2Num(page2Num);
			mapret.put(date, vrtn);
			page1Num=0;
			page2Num=0;
			visiteds.clear();
		}
		return mapret;
	}*/
	@RequestMapping("/sendQuotationMessage")
	public @ResponseBody String sendQuotationMessage(@RequestParam(value="openid", required=true) String openid,@RequestParam(value="title", required=false) String title,@RequestParam(value="img", required=true) String img,@RequestParam(value="imgType", required=true) String imgType){
		// List<PlasticItem>  plasticItemlist = new ArrayList<PlasticItem>();
		 if("".equals(title)||title==null){
				title="永佳和塑胶报价更新啦~";
			}
			if("1".equals(imgType)){
				img="http://wonderfulcq.bj.bcebos.com/"+img;
			}
		    List<WeChatMDLUser> allUser = MongoDBBasic.getAllUserByIsRegistered();
			List<String> itemsList = new ArrayList<String>();
			String url="";
             for(int i=0;i<allUser.size();i++){
            		WeChatMDLUser user=MongoDBBasic.queryUserKM(allUser.get(i).getOpenid());
            		itemsList=user.getKmLists();
     			String[] aStrings=new Market().getMarket(allUser.get(i).getSelfIntro());
     			String content="永佳和【"+aStrings[1]+"-"+aStrings[0]+"-"+aStrings[2]+"】邀您查看您所关注的牌号\n";
     			System.out.println("starting to get the itemsList....");
     			if(itemsList!=null&&itemsList.size()!=0){
     			for(String str : itemsList){
     				System.out.println("str-----------------------"+str);
     				if(PlasticItemService.getDetailByNo(str)!=null){
     			//	plasticItemlist.add();
     				content+="["+PlasticItemService.getDetailByNo(str).getItemNo()+"-￥"+PlasticItemService.getDetailByNo(str).getPrice()+"]\n";
     				}
     			}
     			}
     			
     			url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APP_ID+"&redirect_uri=http%3A%2F%2F"+Constants.baehost+"%2Fmdm%2FquoteDetailExternal.jsp?UID="+allUser.get(i).getOpenid()+"&response_type=code&scope=snsapi_userinfo&state="+allUser.get(i).getOpenid()+"#wechat_redirect&UID=";
            	 RestUtils.sendQuotationToUser(allUser.get(i),content,img,"【"+allUser.get(i).getNickname()+"】"+title,url);
            	 content="";
            }
             
		
		return allUser.size()+"";
		
	}
	
	@RequestMapping("/sendReminderForQuotation")
	public @ResponseBody String sendReminderForQuotation()
	{
		String url="http://"+Constants.baehost+"/mdm/quoteDetail.jsp?UID=";
		int itemNeedApprove = PlasticItemService.findListWithOutApprove();
		String title=itemNeedApprove+"个牌号需要您审批";
		String content="";
		List<WeChatMDLUser> allUser = MongoDBBasic.getAllUserByIsInternalSeniorMgt();
		 for(int i=0;i<allUser.size();i++){
			 RestUtils.sendQuotationToUser(allUser.get(i),content," https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Do9zj&oid=00D90000000pkXM","【"+allUser.get(i).getNickname()+"】"+title,url);
		 }
		 return "ok";
	}
}
