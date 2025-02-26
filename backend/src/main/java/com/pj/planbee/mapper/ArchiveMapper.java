package com.pj.planbee.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.pj.planbee.dto.ArchiveDTO;

@Mapper
public interface ArchiveMapper {
    
    // 어제 ~ 6일 전
    List<ArchiveDTO> findRecentArchives(
            @Param("userId") String userId, 
            @Param("startDate") String startDate, 
            @Param("endDate") String endDate);

    // 가장 최신 데이터를 포함한 6일간의 데이터
    List<ArchiveDTO> findLatestArchives(
            @Param("userId") String userId, 
            @Param("sixDaysAgo") String sixDaysAgo, 
            @Param("latestDate") String latestDate);

    // 최신 날짜
    String findLatestDate(@Param("userId") String userId);
    
    // 날짜 검색
    List<ArchiveDTO> findArchives(
            @Param("userId") String userId, 
            @Param("searchDate") String searchDate);
    
    // 내용 검색
    List<ArchiveDTO> searchByDetail(
    		@Param("userId") String userId, 
            @Param("keyword") String keyword);
    
 // 특정 날짜의 데이터 가져오기
    List<ArchiveDTO> findArchivesByDate(
            @Param("userId") String userId, 
            @Param("date") String date);
}
