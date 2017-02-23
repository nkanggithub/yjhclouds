package com.nkang.kxmoment.baseobject;

public class Inventory {
	private String repositoryName;
	private String plasticItem;
	private String unit;
	private Double inventoryAmount;
	private Double waitDeliverAmount;
	private Double reserveDeliverAmount;
	private Double availableAmount;
	private String lastUpdate;
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getRepositoryName() {
		return repositoryName;
	}
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
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
	
	public Double getInventoryAmount() {
		return inventoryAmount;
	}
	public void setInventoryAmount(Double inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}
	public Double getWaitDeliverAmount() {
		return waitDeliverAmount;
	}
	public void setWaitDeliverAmount(Double waitDeliverAmount) {
		this.waitDeliverAmount = waitDeliverAmount;
	}
	public Double getReserveDeliverAmount() {
		return reserveDeliverAmount;
	}
	public void setReserveDeliverAmount(Double reserveDeliverAmount) {
		this.reserveDeliverAmount = reserveDeliverAmount;
	}
	public Double getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String info(){
		return repositoryName + "\n"
				+plasticItem + "\n"
				+unit + "\n"
				+inventoryAmount + "\n"
				+waitDeliverAmount + "\n"
				+reserveDeliverAmount + "\n"
				+availableAmount + "\n";
		
	}
}
