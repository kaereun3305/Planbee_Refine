package com.pj.planbee.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
	
	//세션 생성 메소드(로그인 연결시 삭제 예정)
	@PostMapping(value = "/makeSession", produces = "application/json; charset=utf-8")
	public String session(HttpSession se) {
		se.setAttribute("sessionId", "admin");
		return (String) se.getAttribute("sessionId");
	}
	
	// 로그아웃(로그인 연결시 삭제 예정)
    @PostMapping(value = "/logout", produces = "application/json; charset=utf-8")
    public int logout(HttpSession se) {
        se.invalidate();
        return 1; // 로그아웃 성공
    }

    // 로그인 상태 확인(로그인 연결시 삭제 예정)
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession se) {
        return (se.getAttribute("sessionId") != null) ? 1 : 0;  // 1: 로그인된 상태, 0: 로그인되지 않음
    }
    
    // 페이징 기반으로 아카이브 데이터 조회
    @GetMapping(produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getPagedArchives(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit,
            HttpSession session) {

        String userId = (String) session.getAttribute("sessionId");
        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다."); // 로그인 체크 추가
        }

        int offset = page * limit;
        return as.getPagedArchives(userId, offset, limit);
    }
    
    
    // 날짜 검색
    @GetMapping(value = "/searchDate/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(@PathVariable("date") String date, HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return as.searchArchivesByDate(userId, date);
    }
     
    // 내용 검색
    @GetMapping(value = "/searchKeyword/{keyword}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchByDetail(@PathVariable String keyword, HttpSession se) {
    	  String userId = (String) se.getAttribute("sessionId");
          if (userId == null) {
              throw new RuntimeException("로그인이 필요합니다.");
          }

          return as.searchByDetail(userId, keyword);
      }
}
