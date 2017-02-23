package com.nkang.kxmoment.baseobject;

import java.util.List;

/**
 * 塑料库存
 * @author cqdxk
 *
 */
public class PlasticItem {
	private String id;
	// 牌号
	private String itemNo;
	// 物性
	private String property;
	// 批次
	private String batch;
	// 价格
	private float price;
	// 变化价格
	private float diffPrice;
	// 关注者ID
	private List<Object> followers;
	// 库存ID
	private List<Object> inventorys;
	// 库存最大可用值
	private double inventorysAvailableAmountSum;
	// 在途ID
	private List<Object> onDeliverys;
	// 在途最大可用值
	private double onDeliveryNotInInRepositorySum;
	// 已售未出货ID
	private List<Object> orderNopays;
	// 已售未出货最大可用值
	private double orderNopaynoInvoiceAmountSum;
	
	// 报价信息ID
	private List<Object> quotations;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiffPrice() {
		return diffPrice;
	}
	public void setDiffPrice(float diffPrice) {
		this.diffPrice = diffPrice;
	}
	public List<Object> getFollowers() {
		return followers;
	}
	public void setFollowers(List<Object> followers) {
		this.followers = followers;
	}
	public List<Object> getInventorys() {
		return inventorys;
	}
	public void setInventorys(List<Object> inventorys) {
		this.inventorys = inventorys;
	}
	public double getInventorysAvailableAmountSum() {
		return inventorysAvailableAmountSum;
	}
	public void setInventorysAvailableAmountSum(double inventorysAvailableAmountSum) {
		this.inventorysAvailableAmountSum = inventorysAvailableAmountSum;
	}
	public List<Object> getOnDeliverys() {
		return onDeliverys;
	}
	public void setOnDeliverys(List<Object> onDeliverys) {
		this.onDeliverys = onDeliverys;
	}
	public double getOnDeliveryNotInInRepositorySum() {
		return onDeliveryNotInInRepositorySum;
	}
	public void setOnDeliveryNotInInRepositorySum(
			double onDeliveryNotInInRepositorySum) {
		this.onDeliveryNotInInRepositorySum = onDeliveryNotInInRepositorySum;
	}
	public List<Object> getOrderNopays() {
		return orderNopays;
	}
	public void setOrderNopays(List<Object> orderNopays) {
		this.orderNopays = orderNopays;
	}
	public List<Object> getQuotations() {
		return quotations;
	}
	public void setQuotations(List<Object> quotations) {
		this.quotations = quotations;
	}
	public double getOrderNopaynoInvoiceAmountSum() {
		return orderNopaynoInvoiceAmountSum;
	}
	public void setOrderNopaynoInvoiceAmountSum(double orderNopaynoInvoiceAmountSum) {
		this.orderNopaynoInvoiceAmountSum = orderNopaynoInvoiceAmountSum;
	}
}
