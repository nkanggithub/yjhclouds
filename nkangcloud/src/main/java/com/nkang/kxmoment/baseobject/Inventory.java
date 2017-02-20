package com.nkang.kxmoment.baseobject;

public class Inventory {
	private String repository;
	private String plasticItem;
	private String unit;
	private String inventoryAmount;
	private String waitDeliverAmount;
	private String reserveDeliverAmount;
	private String availableAmount;
	private String lastUpdate;
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public String getPlasticItem() {
		return plasticItem;
	}
	public void setPlasticItem(String plasticItem) {
		this.plasticItem = plasticItem;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getInventoryAmount() {
		return inventoryAmount;
	}
	public void setInventoryAmount(String inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}
	public String getWaitDeliverAmount() {
		return waitDeliverAmount;
	}
	public void setWaitDeliverAmount(String waitDeliverAmount) {
		this.waitDeliverAmount = waitDeliverAmount;
	}
	public String getReserveDeliverAmount() {
		return reserveDeliverAmount;
	}
	public void setReserveDeliverAmount(String reserveDeliverAmount) {
		this.reserveDeliverAmount = reserveDeliverAmount;
	}
	public String getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}
	
}
