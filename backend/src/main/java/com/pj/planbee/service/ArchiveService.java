package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.ArchiveDTO;

public interface ArchiveService {

	List<ArchiveDTO> getArchivesWithDetails(String userId);

	List<ArchiveDTO> searchArchivesByDate(String userId, String date);

}
