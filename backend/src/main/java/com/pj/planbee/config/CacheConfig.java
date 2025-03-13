package com.pj.planbee.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.pj.planbee.dto.ArchiveDTO;

@Component
public class CacheConfig {

    public static final int CACHE_SIZE = 100;

    // 동시성 해결
    public static final Map<String, List<ArchiveDTO>> archiveCache = new ConcurrentHashMap<>();

    // 멀티스레드 환경에서 동기화 보장
    private static final ReentrantLock cacheLock = new ReentrantLock();

    // 날짜별로 캐시 저장 (멀티스레드 환경 안전)
    public static void putCache(String userId, String date, List<ArchiveDTO> value) {
        String cacheKey = userId + "_" + date;

        cacheLock.lock(); // 락 획득
        try {
            if (archiveCache.size() >= CACHE_SIZE) {
            	 // 가장 오래된 항목 삭제 (LRU 방식)
                String eldestKey = archiveCache.keySet().iterator().next();
                archiveCache.remove(eldestKey);
                System.out.println("ĳ�� ����: " + eldestKey);
            }
            archiveCache.put(cacheKey, value);
            System.out.println("ĳ�� �߰�: " + cacheKey);
        } finally {
            cacheLock.unlock(); // 락 해제
        }
    }

    // 날짜별로 캐시 조회 (읽기 작업은 동기화 필요 없음)
    public static List<ArchiveDTO> getCache(String userId, String date) {
        return archiveCache.get(userId + "_" + date);
    }

 // 캐시 상태 출력 (반복 중 캐시가 수정되지 않도록 동기화 추가)
    public static void printCacheStatus() {
        cacheLock.lock();
        try {
            System.out.println("���� ĳ�õ� ������ ���:");
            archiveCache.forEach((key, value) -> 
                System.out.println(" - " + key + " �� ������ : " + value.size() + " ��")
            );
        } finally {
            cacheLock.unlock(); // 락 해제
        }
    }
}
