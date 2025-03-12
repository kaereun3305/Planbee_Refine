package com.pj.planbee.dto;

public class ArchDetailDTO {

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

	public int getTdDetailId() {
		return tdDetailId;
	}

	public void setTdDetailId(int tdDetailId) {
		this.tdDetailId = tdDetailId;
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

}
