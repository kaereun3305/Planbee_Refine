package com.pj.planbee.dto;

public class TDdetailDTO { 
	//dto에서 첫글자가 대문자면 getter,setter 지정시 오류가 나서
	//첫글자 무조건 소문자로 아래와 같이 데이터타입 변경함
	int tdDetailId, tdId;
	String tdDetail, tdDetailTime;
	boolean tdDetailState;

	public int getTdDetailId() {
		return tdDetailId;
	}
	public void setTdDetailId(int tdDetailId) {
		this.tdDetailId = tdDetailId;
	}
	public int getTdId() {
		return tdId;
	}
	public void setTdId(int tdId) {
		this.tdId = tdId;
	}
	
	public String getTdDetail() {
		return tdDetail;
	}
	public void setTdDetail(String tdDetail) {
		this.tdDetail = tdDetail;
	}
	public String getTdDetailTime() {
		return tdDetailTime;
	}
	public void setTdDetailTime(String tdDetailTime) {
		this.tdDetailTime = tdDetailTime;
	}
	public boolean isTdDetailState() {
		return tdDetailState;
	}
	public void setTdDetailState(boolean tdDetailState) {
		this.tdDetailState = tdDetailState;
	}
	
}
	
	
	
	
	