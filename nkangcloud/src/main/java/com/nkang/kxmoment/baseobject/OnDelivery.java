package com.nkang.kxmoment.baseobject;

public class OnDelivery {
	//单据号
	private String billID;
	
	private String date;
	//供应商
	private String provider;
	//存货名称
	private String plasticItem;
	private Double amount;
	//原币单价
	private Double originalPrice;
	private Double taxRate;
	//单据类型
	private String billType;
	//未入库数量
	private Double notInInRepository;
	private String lastUpdate;
	public String getBillID() {
		return billID;
	}
	public void setBillID(String billID) {
		this.billID = billID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getPlasticItem() {
		return plasticItem;
	}
	public void setPlasticItem(String plasticItem) {
		this.plasticItem = plasticItem;
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public Double getNotInInRepository() {
		return notInInRepository;
	}
	public void setNotInInRepository(Double notInInRepository) {
		this.notInInRepository = notInInRepository;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String info(){
		return billID + "\n" +notInInRepository;
		
	}
}
