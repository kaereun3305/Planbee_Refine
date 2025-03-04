package com.pj.planbee.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pj.planbee.dto.ArchiveDTO;

public class CacheConfig {

    // LRU 캐시 (최대 30개 저장, 초과되면 가장 오래된 데이터부터 삭제)
    public static final int CACHE_SIZE = 30;
    
    // 다중 스레드 안전 처리
    public static final Map<String, List<ArchiveDTO>> archiveCache =
        new ConcurrentHashMap<>();
    
    //캐시 추가 및 LRU 처리
    public static synchronized void putCache(String key, List<ArchiveDTO> value) {
        if (archiveCache.size() >= CACHE_SIZE) {
            // 가장 오래된 항목 삭제
            String eldestKey = archiveCache.keySet().iterator().next();
            archiveCache.remove(eldestKey);
            System.out.println("캐시 제거: " + eldestKey);
        }
        archiveCache.put(key, value);
        System.out.println("캐시 추가: " + key);
    }

    //캐시 상태 출력 (동기화 추가)
    public static void printCacheStatus() {
        System.out.println("현재 캐시된 데이터 목록:");
        archiveCache.forEach((key, value) -> 
            System.out.println(" - " + key + " 의 데이터 : " + value.size() + " 개")
        );
    }
}
