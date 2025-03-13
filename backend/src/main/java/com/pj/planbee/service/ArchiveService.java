package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.ArchiveDTO;

public interface ArchiveService {

	//�⺻
	List<ArchiveDTO> getArchivesWithDetails(String userId);

	//��¥ �˻�
	List<ArchiveDTO> searchArchivesByDate(String userId, String date);
	
	//���� �˻�
	List<ArchiveDTO> searchByDetail(String userId, String keyword);

	//Ư�� ��¥ �ҷ�����
	List<ArchiveDTO> getArchivesByDate(String userId, String date);

	//Ư�� ��¥ �������� �ҷ�����
	List<ArchiveDTO> getArchivesByRange(String userId, String date);

	String findLatestDate(String userId);

	
}
