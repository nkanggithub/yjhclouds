package com.nkang.kxmoment.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.nkang.kxmoment.service.PlasticItemService;
import com.nkang.kxmoment.util.MongoDBBasic;

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
	public @ResponseBody String insertVisited(@RequestParam(value="openid") String openid){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		MongoDBBasic.updateVisited(openid,date); 
		return openid;
	}
	
	@RequestMapping("/getVisited")
	public @ResponseBody Visited getVisited(@RequestParam(value="openid") String openid){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		
		return MongoDBBasic.getVisited(openid,date); 
	}
}
