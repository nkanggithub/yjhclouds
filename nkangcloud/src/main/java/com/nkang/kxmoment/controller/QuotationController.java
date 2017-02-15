package com.nkang.kxmoment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.Location;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
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
		quotation.setCategory(request.getParameter("category").trim());
		quotation.setCategoryGrade(request.getParameter("categoryGrade").trim());
		quotation.setComments(request.getParameter("comments"));
		quotation.setItem(request.getParameter("item"));
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		quotation.setLastUpdate(cursqlTS.toString());
		quotation.setLocationAmounts(request.getParameter("locationAmounts"));
		quotation.setOnDelivery(request.getParameter("onDelivery"));
		quotation.setOriginalProducer(request.getParameter("originalProducer"));
		quotation.setQuotationPrice(request.getParameter("quotationPrice"));
		quotation.setSoldOutOfPay(request.getParameter("soldOutOfPay"));
		
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
	
}
