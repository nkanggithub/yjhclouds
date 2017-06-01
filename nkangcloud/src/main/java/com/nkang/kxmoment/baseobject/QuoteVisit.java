package com.nkang.kxmoment.baseobject;

public class QuoteVisit {
	public String name;
	public String totalVisited;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public QuoteVisit(String name, String totalVisited) {
		super();
		this.name = name;
		this.totalVisited = totalVisited;
	}

	public String getTotalVisited() {
		return totalVisited;
	}
	public QuoteVisit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setTotalVisited(String totalVisited) {
		this.totalVisited = totalVisited;
	}
}
