package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.ArchiveDTO;

public interface ArchiveService {

	//기본
	List<ArchiveDTO> getArchivesWithDetails(String userId);

	//날짜 검색
	List<ArchiveDTO> searchArchivesByDate(String userId, String date);
	
	//내용 검색
	List<ArchiveDTO> searchByDetail(String userId, String keyword);

	//특정 날짜 불러오기
	List<ArchiveDTO> getArchivesByDate(String userId, String date);

	//특정 날짜 범위별로 불러오기
	List<ArchiveDTO> getArchivesByRange(String userId, String date);

	
}
