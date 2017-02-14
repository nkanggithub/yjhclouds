package com.nkang.kxmoment.baseobject;

import java.util.List;

public class OnlineQuotation {
	private String category;
	private String categoryGrade;
	private String item;
	private String quotationPrice;
	private String comments;
	private String locationAmounts;
	private String avaliableInventory;
	private String onDelivery;
	private String soldOutOfPay;
	private String originalProducer;
	
	
	public String getLocationAmounts() {
		return locationAmounts;
	}
	public void setLocationAmounts(String locationAmounts) {
		this.locationAmounts = locationAmounts;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryGrade() {
		return categoryGrade;
	}
	public void setCategoryGrade(String categoryGrade) {
		this.categoryGrade = categoryGrade;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getQuotationPrice() {
		return quotationPrice;
	}
	public void setQuotationPrice(String quotationPrice) {
		this.quotationPrice = quotationPrice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAvaliableInventory() {
		return avaliableInventory;
	}
	public void setAvaliableInventory(String avaliableInventory) {
		this.avaliableInventory = avaliableInventory;
	}
	public String getOnDelivery() {
		return onDelivery;
	}
	public void setOnDelivery(String onDelivery) {
		this.onDelivery = onDelivery;
	}
	public String getSoldOutOfPay() {
		return soldOutOfPay;
	}
	public void setSoldOutOfPay(String soldOutOfPay) {
		this.soldOutOfPay = soldOutOfPay;
	}
	
	
	public String getOriginalProducer() {
		return originalProducer;
	}
	public void setOriginalProducer(String originalProducer) {
		this.originalProducer = originalProducer;
	}
	public String info() {
		String infString = this.category + "\n"+
				this.categoryGrade + "\n"+
				this.item + "\n"+
				this.quotationPrice + "\n"+
				this.comments + "\n"+
				this.avaliableInventory + "\n"+
				this.onDelivery + "\n"+
				this.soldOutOfPay + "\n"+
				this.originalProducer;
		
		return infString ;
		
	}

}
