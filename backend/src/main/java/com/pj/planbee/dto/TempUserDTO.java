package com.pj.planbee.dto;

import java.sql.Timestamp;

public class TempUserDTO {
	
	String tempUserId, tempUserPw, tempUserName, tempUserEmail, tempUserPhone, tempUserCode="";
	Integer verifyStatus=0;
	Timestamp createdAt; 

	public String getTempUserId() {
		return tempUserId;
	}

	public void setTempUserId(String tempUserId) {
		this.tempUserId = tempUserId;
	}

	public String getTempUserPw() {
		return tempUserPw;
	}

	public void setTempUserPw(String tempUserPw) {
		this.tempUserPw = tempUserPw;
	}

	public String getTempUserName() {
		return tempUserName;
	}

	public void setTempUserName(String tempUserName) {
		this.tempUserName = tempUserName;
	}

	public String getTempUserEmail() {
		return tempUserEmail;
	}

	public void setTempUserEmail(String tempUserEmail) {
		this.tempUserEmail = tempUserEmail;
	}

	public String getTempUserPhone() {
		return tempUserPhone;
	}

	public void setTempUserPhone(String tempUserPhone) {
		this.tempUserPhone = tempUserPhone;
	}

	public String getTempUserCode() {
		return tempUserCode;
	}

	public void setTempUserCode(String tempUserCode) {
		this.tempUserCode = tempUserCode;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	
}
