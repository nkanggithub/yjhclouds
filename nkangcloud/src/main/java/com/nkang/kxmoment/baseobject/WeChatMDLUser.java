package com.nkang.kxmoment.baseobject;

import java.util.ArrayList;
import java.util.HashMap;

public class WeChatMDLUser extends WeChatUser {

	public String realName;
	public String companyName;
	public String registerDate;
	public int workDay;
	public int CongratulateNum;
	public String role;
	public Role roleObj;
	public String selfIntro;
	public String email;
	public String phone;
	public String point;
	public String IsActive;
	public String IsAuthenticated;
	public String lastUpdatedDate;
	public String IsRegistered;
	public ArrayList tag;
	public ArrayList kmLists;
	public HashMap like;
	
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public ArrayList getKmLists() {
		return kmLists;
	}
	public void setKmLists(ArrayList kmLists) {
		this.kmLists = kmLists;
	}
	public Role getRoleObj() {
		return roleObj;
	}
	public void setRoleObj(Role roleObj) {
		this.roleObj = roleObj;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	public String getIsAuthenticated() {
		return IsAuthenticated;
	}
	public void setIsAuthenticated(String isAuthenticated) {
		IsAuthenticated = isAuthenticated;
	}
	public String getIsRegistered() {
		return IsRegistered;
	}
	public void setIsRegistered(String isRegistered) {
		IsRegistered = isRegistered;
	}
	public HashMap getLike() {
		return like;
	}
	public void setLike(HashMap like) {
		this.like = like;
	}
	public int getCongratulateNum() {
		return CongratulateNum;
	}
	public void setCongratulateNum(int congratulateNum) {
		CongratulateNum = congratulateNum;
	}
	public ArrayList getTag() {
		return tag;
	}
	public void setTag(ArrayList list) {
		this.tag = list;
	}
	public String getRealName() {
		return realName;
	}
	public int getWorkDay() {
		return workDay;
	}
	public void setWorkDay(int workDay) {
		this.workDay = workDay;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSelfIntro() {
		return selfIntro;
	}
	public void setSelfIntro(String selfIntro) {
		this.selfIntro = selfIntro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	
	
}
