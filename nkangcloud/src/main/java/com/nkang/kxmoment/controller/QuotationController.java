package com.nkang.kxmoment.controller;

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

import com.nkang.kxmoment.baseobject.Location;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
import com.nkang.kxmoment.baseobject.PlasticItem;
import com.nkang.kxmoment.baseobject.QuotationList;
import com.nkang.kxmoment.baseobject.Visited;
import com.nkang.kxmoment.baseobject.Visitedreturn;
import com.nkang.kxmoment.service.PlasticItemService;
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
		itemsList=MongoDBBasic.queryUserKM(openid); 
		for(String str : itemsList){
			plasticItemlist.add(PlasticItemService.getDetailByNo(str));
		}
		return plasticItemlist;
		
	}
	

	@RequestMapping("/insertVisited")
	public @ResponseBody String insertVisited(@RequestParam(value="openid") String openid,@RequestParam(value="pageName") String pageName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		String str = MongoDBBasic.updateVisited(openid,date,pageName); 
		return str;
	}
	
	@RequestMapping("/getVisitedByOpenid")
	public @ResponseBody List<Visited> getVisitedByOpenid(@RequestParam(value="openid") String openid,@RequestParam(value="date")String date ){
		
		return MongoDBBasic.getVisited(openid,date); 
	}
	@RequestMapping("/getVisitedDetail")
	public @ResponseBody List<Visited> getVisitedByDate(@RequestParam(value="date")String date){
		
		return MongoDBBasic.getVisitedDetail(date); 
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
	
	@RequestMapping("/getVisitedbTotalNumPage")
	public @ResponseBody Map<String,Visitedreturn> getVisitedbTotalNumPage(){
		Map<String,Visitedreturn> mapret = new HashMap<String,Visitedreturn>();
		 Map<String,List<Visited>> maps = new HashMap<String, List<Visited>>();
		List<String> dates = MongoDBBasic.getVisitedAllDate();
		maps=MongoDBBasic.getVisitedByDate(dates); 
		
		for(String date : dates){
			int page1Num=0;
			int page2Num=0;
			Visitedreturn vrtn = new Visitedreturn();
			List<Visited> visiteds = maps.get(date);
			for(Visited vis : visiteds){
				if(vis.getPageName().equals("page1")){
					page1Num=page1Num+vis.getVisitedNum();
				}else{
					page2Num = page2Num+vis.getVisitedNum();
				}
			}
			vrtn.setPage1Num(page1Num);
			vrtn.setPage2Num(page2Num);
			mapret.put(date, vrtn);
		}
		return mapret;
	}
	@RequestMapping("/sendQuotationMessage")
	public @ResponseBody String sendQuotationMessage(@RequestParam(value="openid", required=true) String openid,@RequestParam(value="title", required=false) String title,@RequestParam(value="img", required=true) String img){
		// List<PlasticItem>  plasticItemlist = new ArrayList<PlasticItem>();
			if("".equals(title)||title==null){
				title="报价更新啦";
			}
			List<String> allUser = MongoDBBasic.getAllOpenIDByIsRegistered();
			List<String> itemsList = new ArrayList<String>();
             for(int i=0;i<allUser.size();i++){
     			itemsList=MongoDBBasic.queryUserKM(allUser.get(i)); 
     			String content="您所关注的牌号快览\n";
     			for(String str : itemsList){
     				System.out.println("str-----------------------"+str);
     				if(PlasticItemService.getDetailByNo(str)!=null){
     			//	plasticItemlist.add();
     				content+="["+PlasticItemService.getDetailByNo(str).getItemNo()+"-￥"+PlasticItemService.getDetailByNo(str).getPrice()+"]\n";
     				}
     			}
     			
            	 RestUtils.sendQuotationToUser(allUser.get(i),content,img,title);
            	 content="";
            }
		
		return allUser.size()+"";
		
	}
}
