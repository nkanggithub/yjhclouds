package com.nkang.kxmoment.baseobject;

public class FaceObj {

	String smile ;
	String gender ;
	String moustache ;
	String beard;
	String age ;
	String glasses ;
	public String getSmile() {
		return smile;
	}
	public void setSmile(String smile) {
		this.smile = smile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMoustache() {
		return moustache;
	}
	public void setMoustache(String moustache) {
		this.moustache = moustache;
	}
	public String getBeard() {
		return beard;
	}
	public void setBeard(String beard) {
		this.beard = beard;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGlasses() {
		return glasses;
	}
	public void setGlasses(String glasses) {
		this.glasses = glasses;
	}
	
	public String Info(){
		return "smile : "+this.smile +"\n"+"age :"+this.age +"\n"+"glasses :"+this.glasses +"\n"+"gender :"+this.gender +"\n"+"moustache :"+this.moustache +"\n"+"beard :"+this.beard;
	}
}
