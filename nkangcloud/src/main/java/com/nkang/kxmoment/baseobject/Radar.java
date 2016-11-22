package com.nkang.kxmoment.baseobject;

public class Radar {

	public String axis;
	public double value;
	public String getAxis() {
		return axis;
	}
	public void setAxis(String axis) {
		this.axis = axis;
	}
	public Radar(String axis, double value) {
		super();
		this.axis = axis;
		this.value = value;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
