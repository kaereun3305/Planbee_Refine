package com.pj.planbee.dto;

public class TodoListDTO {
	
	int tdId;
	String tdDate, tdMemo;
	double tdProgress;
	String userId; //유저아이디는 나중에 만들어지는 것과 맞춰서 만들어야함
	
	public static class SubTodoListDTO{ //추가: getMemo 메소드를 위해 하위 DTO를 생성함
		private int tdId;
		private String tdMemo;
		
		public SubTodoListDTO(int tdId, String tdMemo) {
			this.tdId = tdId;
			this.tdMemo = tdMemo;
		}
		public int getTdId() {
			return tdId;
		}
		public String getTdMemo() {
			return tdMemo;
		}
	}
	
	public SubTodoListDTO toSubTodoListDTO() {
        return new SubTodoListDTO(this.tdId, this.tdMemo);
    }
	
	public int getTdId() {
		return tdId;
	}
	public void setTdId(int tdId) {
		this.tdId = tdId;
	}
	public String getTdDate() {
		return tdDate;
	}
	public void setTdDate(String tdDate) {
		this.tdDate = tdDate;
	}
	public String getTdMemo() {
		return tdMemo;
	}
	public void setTdMemo(String tdMemo) {
		this.tdMemo = tdMemo;
	}
	public double getTdProgress() {
		return tdProgress;
	}
	public void setTdProgress(double tdProgress) {
		this.tdProgress = tdProgress;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	


}
