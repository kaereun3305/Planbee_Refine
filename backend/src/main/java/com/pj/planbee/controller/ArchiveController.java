package com.pj.planbee.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        return (se.getAttribute("sessionId") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
    }
    
    //여기가 테스트 시작
    // 처음 /archive 페이지에 접속하면 어제 날짜로 리다이렉트
    @GetMapping(value = "/", produces = "application/json; charset=utf-8")
    public void redirectToYesterday(HttpServletResponse res) throws IOException {
        // 어제 날짜 구하기
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String yesterdayDate = yesterday.format(formatter);
        
        // 어제 날짜로 리다이렉트
        res.sendRedirect("/archive/" + yesterdayDate);
    }

    // 어제 날짜 또는 검색한 날짜의 데이터 가져오기
    @GetMapping(value = "/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getArchivesByDate(@PathVariable String date, HttpSession se) {
        // 세션에서 userId 가져오기
        String userId = (String) se.getAttribute("sessionId");

        // 어제 날짜 또는 검색한 날짜의 데이터 가져오기
        List<ArchiveDTO> archives = as.getArchivesWithDetails(userId);

        return archives;
    }
    //여기가 테스트 마지막
    
    // 날짜 검색
    @GetMapping(value = "/searchDate/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(@PathVariable("date") String date, HttpSession se) {
        // 세션 가져오기
        String userId = (String) se.getAttribute("sessionId"); 

        // 검색
        List<ArchiveDTO> archives = as.searchArchivesByDate(userId, date);

        return archives;
    }
     
    // 내용 검색
    @GetMapping(value = "/searchKeyword/{keyword}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchByDetail(@PathVariable String keyword, HttpSession se) {
        // 세션에서 userId 가져오기
        String userId = (String) se.getAttribute("sessionId");
        return as.searchByDetail(userId, keyword);
    }
}
