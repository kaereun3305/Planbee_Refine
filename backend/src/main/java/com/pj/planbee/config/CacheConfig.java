package com.pj.planbee.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pj.planbee.dto.ArchiveDTO;

public class CacheConfig {

    // LRU 캐시 (최대 30개 저장, 초과되면 가장 오래된 데이터부터 삭제)
    public static final int CACHE_SIZE = 30;
    
    public static final Map<String, List<ArchiveDTO>> archiveCache =
    	    new LinkedHashMap<String, List<ArchiveDTO>>(CACHE_SIZE, 0.75f, true) {
    	        @Override
    	        protected boolean removeEldestEntry(Map.Entry<String, List<ArchiveDTO>> eldest) {
    	            return size() > CACHE_SIZE;  // 캐시 사이즈 초과 시 가장 오래된 데이터 삭제
    	        }
    	    };

}
