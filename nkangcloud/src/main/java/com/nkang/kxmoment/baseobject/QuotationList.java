package com.nkang.kxmoment.baseobject;

public class QuotationList {
	private String plasticItem;
	private String status;
	private String approveBy;
	private String editBy;
	private String dateTime;
	private Double suggestPrice;
	private int type;// 0:默认值， 1：edit， 2：approve
	public String getPlasticItem() {
		return plasticItem;
	}
	public void setPlasticItem(String plasticItem) {
		this.plasticItem = plasticItem;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApproveBy() {
		return approveBy;
	}
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Double getSuggestPrice() {
		return suggestPrice;
	}
	public void setSuggestPrice(Double suggestPrice) {
		this.suggestPrice = suggestPrice;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

}
