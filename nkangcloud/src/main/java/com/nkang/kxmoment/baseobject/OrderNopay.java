package com.nkang.kxmoment.baseobject;

public class OrderNopay {
	//客户简称
	private String customerName;
	//业务员
	private String salesman;
	//订单号
	private String billID;
	//订单日期
	private String billDate;
	//存货编码
	private String plasticItem;
	//未发货数量
	private Double unfilledOrderAmount;
	//出库数量
	private Double filledOrderAmount;
	//未开票数量
	private Double noInvoiceAmount;
	private String lastUpdate;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getBillID() {
		return billID;
	}
	public void setBillID(String billID) {
		this.billID = billID;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getPlasticItem() {
		return plasticItem;
	}
	public void setPlasticItem(String plasticItem) {
		this.plasticItem = plasticItem;
	}

	public Double getUnfilledOrderAmount() {
		return unfilledOrderAmount;
	}
	public void setUnfilledOrderAmount(Double unfilledOrderAmount) {
		this.unfilledOrderAmount = unfilledOrderAmount;
	}
	public Double getFilledOrderAmount() {
		return filledOrderAmount;
	}
	public void setFilledOrderAmount(Double filledOrderAmount) {
		this.filledOrderAmount = filledOrderAmount;
	}
	public Double getNoInvoiceAmount() {
		return noInvoiceAmount;
	}
	public void setNoInvoiceAmount(Double noInvoiceAmount) {
		this.noInvoiceAmount = noInvoiceAmount;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String info(){
		return customerName + "\n"
				+billID + "\n"
				+noInvoiceAmount + "\n"
				+salesman + "\n";
		
	}
}
