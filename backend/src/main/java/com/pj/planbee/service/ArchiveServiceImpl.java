package com.pj.planbee.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.config.CacheConfig;
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
            userId, startDate, endDate);

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
    
    // 특정 날짜의 데이터 가져오기
    @Override
    public List<ArchiveDTO> getArchivesByDate(String userId, String date) {
        return mapper.findArchivesByDate(userId, date);
    }
    
    
    @Override
    public List<ArchiveDTO> searchArchivesByDate(String userId, String date) {
        // 검색 날짜 기준으로 3일 전 ~ 2일 후 데이터
        return mapper.findArchives(userId, date);
    }
    
    @Override
    public List<ArchiveDTO> searchByDetail(String userId, String keyword) {
        return mapper.searchByDetail(userId, keyword);
    }
    
    @Override
    public List<ArchiveDTO> getArchivesByRange(String userId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate requestedDate = LocalDate.parse(date, formatter);

        // 요청된 날짜 기준 데이터 조회
        List<ArchiveDTO> totalData = new ArrayList<>();
        for (int i = 5; i >= 0; i--) { 
            String targetDate = requestedDate.minusDays(i).format(formatter);

            // 개별 날짜 캐시 조회
            List<ArchiveDTO> cachedData = CacheConfig.getCache(userId, targetDate);
            if (cachedData != null) {
                System.out.println("캐시 데이터 있음 : " + targetDate + " 데이터 반환");
                totalData.addAll(cachedData);
            } else {
                //DB에서 캐싱
                List<ArchiveDTO> archives = mapper.findArchivesByDate(userId, targetDate);
                System.out.println("캐시 데이터 없음 : " + targetDate + " 데이터 추가");
                CacheConfig.putCache(userId, targetDate, archives);
                totalData.addAll(archives);
            }
        }
        CacheConfig.printCacheStatus();
        return totalData;
    }
}