package com.nkang.kxmoment.controller;

import java.util.List;

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
			@RequestParam(value="locationList", required=false) List<Location> locationList,
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
			quotation.setLocationList(locationList);
			quotation.setAvaliableInventory(avaliableInventory);
			quotation.setOnDelivery(onDelivery);
			quotation.setSoldOutOfPay(soldOutOfPay);
			quotation.setOriginalProducer(originalProducer);
			
			String ret="";
			ret=MongoDBBasic.saveOnlineQuotation(quotation);
		
		return ret;
		
	}
}
