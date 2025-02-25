package com.pj.planbee.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public int logout(HttpSession session) {
        session.invalidate();
        return 1; // 로그아웃 성공
    }

    // 로그인 상태 확인(로그인 연결시 삭제 예정)
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession session) {
        return (session.getAttribute("sessionId") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
    }
    

    @GetMapping(value = "/archives", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getArchives(HttpSession session, Model model) {
    	
    	// 세션에서 userId 가져오기
        String userId = (String) session.getAttribute("sessionId");

        // 데이터 가져오기
        List<ArchiveDTO> archives = as.getArchivesWithDetails(userId);

        return archives;
    }
    
    // 날짜 검색
    @GetMapping(value = "/search/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(@PathVariable("date") String date, HttpSession session) {
        // 세션 가져오기
        String userId = (String) session.getAttribute("sessionId"); 

        // 검색
        List<ArchiveDTO> archives = as.searchArchivesByDate(userId, date);

        return archives;
    }
}
