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
        
        LocalDate today = LocalDate.now(); // ����
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String endDate = today.minusDays(1).format(formatter);   // ���� 
        String startDate = today.minusDays(6).format(formatter); // 6�� �� 
        
        // ���� ~ 6�� �� 
        List<ArchiveDTO> archives = mapper.findRecentArchives(
            userId, startDate, endDate);

        // ���� ���ٸ�, ���� �ֽ��� �����͸� ������ 6�ϰ� ��������
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
    
    // Ư�� ��¥�� ������ ��������
    @Override
    public List<ArchiveDTO> getArchivesByDate(String userId, String date) {
        return mapper.findArchivesByDate(userId, date);
    }
    
    
    @Override
    public List<ArchiveDTO> searchArchivesByDate(String userId, String date) {
        // �˻� ��¥ �������� 3�� �� ~ 2�� �� ������
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

        // ��û�� ��¥ ���� ������ ��ȸ
        List<ArchiveDTO> totalData = new ArrayList<>();
        for (int i = 5; i >= 0; i--) { 
            String targetDate = requestedDate.minusDays(i).format(formatter);

            // ���� ��¥ ĳ�� ��ȸ
            List<ArchiveDTO> cachedData = CacheConfig.getCache(userId, targetDate);
            if (cachedData != null) {
                System.out.println("ĳ�� ������ ���� : " + targetDate + " ������ ��ȯ");
                totalData.addAll(cachedData);
            } else {
                //DB���� ĳ��
                List<ArchiveDTO> archives = mapper.findArchivesByDate(userId, targetDate);
                System.out.println("ĳ�� ������ ���� : " + targetDate + " ������ �߰�");
                CacheConfig.putCache(userId, targetDate, archives);
                totalData.addAll(archives);
            }
        }
        CacheConfig.printCacheStatus();
        return totalData;
    }
    
    @Override
    public String findLatestDate(String userId) {
        return mapper.findLatestDate(userId);
    }
}
