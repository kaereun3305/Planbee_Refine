package com.pj.planbee.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.ArchiveDTO;
import com.pj.planbee.service.ArchiveService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders= "*", allowCredentials = "true")
@RequestMapping("/archive") // 순서 바꿈
public class ArchiveController {

	@Autowired ArchiveService as;
	
    // 페이징 기반으로 아카이브 데이터 조회
    @GetMapping(produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getPagedArchives(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit,
            HttpSession session) {

        String userId = (String) session.getAttribute("sessionId");

        int offset = page * limit;
        return as.getPagedArchives(userId, offset, limit);
    }
    
    
    // 검색 API (searchType에 따라 동작)
    @GetMapping(value = "/search", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(
            @RequestParam(name = "searchType", required = false) String searchType,
            @RequestParam(name = "query", required = false) String query,
            HttpSession session) {

        String userId = (String) session.getAttribute("sessionId");
      
        if ("date".equalsIgnoreCase(searchType)) {
            return as.searchArchivesByDate(userId, query);
        } else if ("content".equalsIgnoreCase(searchType)) {
            return as.searchByDetail(userId, query);
        } else {
            throw new IllegalArgumentException("유효하지 않은 searchType 입니다. 'date' 또는 'content'를 사용하세요.");
        }
    }
}
