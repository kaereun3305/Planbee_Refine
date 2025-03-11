package com.pj.planbee.dto;

public class ArchDetailDTO {
	//dto에서 첫글자가 대문자면 getter,setter 지정시 오류가 나서
		//첫글자 무조건 소문자로 아래와 같이 데이터타입 변경함
		int archDetailId, archiveId, tdDetailId;
		
		String archDetail, archDetailTime;
		boolean archDetailState;
		public int getArchDetailId() {
			return archDetailId;
		}
		public void setArchDetailId(int archDetailId) {
			this.archDetailId = archDetailId;
		}
		public int getArchiveId() {
			return archiveId;
		}
		public void setArchiveId(int archiveId) {
			this.archiveId = archiveId;
		}
		public String getArchDetail() {
			return archDetail;
		}
		public void setArchDetail(String archDetail) {
			this.archDetail = archDetail;
		}
		public String getArchDetailTime() {
			return archDetailTime;
		}
		public void setArchDetailTime(String archDetailTime) {
			this.archDetailTime = archDetailTime;
		}
		public boolean isArchDetailState() {
			return archDetailState;
		}
		public void setArchDetailState(boolean archDetailState) {
			this.archDetailState = archDetailState;
		}
		public int getTdDetailId() {
			return tdDetailId;
		}
		public void setTdDetailId(int tdDetailId) {
			this.tdDetailId = tdDetailId;
		}
		
		
		
}
