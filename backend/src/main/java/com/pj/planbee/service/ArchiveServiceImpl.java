package com.pj.planbee.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.ArchiveDTO;
import com.pj.planbee.mapper.ArchiveMapper;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired ArchiveMapper mapper;

    @Override
    public List<ArchiveDTO> getArchivesWithDetails(String userId) {
        
        LocalDate today = LocalDate.now(); // 오늘
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String endDate = today.minusDays(1).format(formatter);   // 어제 
        String startDate = today.minusDays(6).format(formatter); // 6일 전 
        
        // 어제 ~ 6일 전 
        List<ArchiveDTO> archives = mapper.findRecentArchives(
            userId, startDate.toString(), endDate.toString());

        // 위가 없다면, 가장 최신의 데이터를 포함한 6일간 가져오기
        if (archives.isEmpty()) {
            String latestDate = mapper.findLatestDate(userId);
            if (latestDate != null) {
            	 LocalDate latest = LocalDate.parse(latestDate, formatter);
                 String sixDaysAgo = latest.minusDays(5).format(formatter);
                 archives = mapper.findLatestArchives(userId, sixDaysAgo, latestDate);
            }
        }

        return archives;
    }
    
    
    @Override
    public List<ArchiveDTO> searchArchivesByDate(String userId, String date) {
        // 검색 날짜 기준으로 3일 전 ~ 2일 후 데이터
        return mapper.findArchives(userId, date);
    }
}
