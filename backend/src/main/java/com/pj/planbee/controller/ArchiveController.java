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
@RequestMapping("/archive") // ���� �ٲ�
public class ArchiveController {

	@Autowired ArchiveService as;
	
	//���� ���� �޼ҵ�(�α��� ����� ���� ����)
	@PostMapping(value = "/makeSession", produces = "application/json; charset=utf-8")
	public String session(HttpSession se) {
		se.setAttribute("sessionId", "admin");
		return (String) se.getAttribute("sessionId");
	}
	
	// �α׾ƿ�(�α��� ����� ���� ����)
    @PostMapping(value = "/logout", produces = "application/json; charset=utf-8")
    public int logout(HttpSession se) {
        se.invalidate();
        return 1; // �α׾ƿ� ����
    }

    // �α��� ���� Ȯ��(�α��� ����� ���� ����)
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession se) {
        return (se.getAttribute("sessionId") != null) ? 1 : 0; // 1: �α��ε� ����, 0: �α��ε��� ����
    }
    
    //���Ⱑ �׽�Ʈ ����
    // ó�� /archive �������� �����ϸ� ���� ��¥�� �����̷�Ʈ
    @GetMapping(value = "/", produces = "application/json; charset=utf-8")
    public void redirectToYesterday(HttpSession se, HttpServletRequest req, HttpServletResponse res) throws IOException {
    	 // 세션에서 userId 가져오기
        String userId = (String) se.getAttribute("sessionId");

        // 날짜 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        
        // 어제 날짜 가져오기
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String yesterdayDate = yesterday.format(formatter);
        
        // 어제 데이터가 있는지 확인
        List<ArchiveDTO> yesterdayArchives = as.getArchivesByDate(userId, yesterdayDate);

        if (!yesterdayArchives.isEmpty()) {
            // 어제 데이터가 있으면 기존처럼 어제 날짜로 리다이렉트
            res.sendRedirect(req.getContextPath() + "/archive/" + yesterdayDate);
        } else {
            // 어제 데이터가 없으면 가장 최신 날짜 조회
            String latestDate = as.findLatestDate(userId);
            if (latestDate != null) {
                // 최신 데이터를 기준으로 6일간의 데이터 조회 후 해당 날짜로 리다이렉트
                res.sendRedirect(req.getContextPath() + "/archive/" + latestDate);
            } else {
                // 최신 데이터도 없는 경우 기본적으로 어제 날짜로 리다이렉트
                res.sendRedirect(req.getContextPath() + "/archive/" + yesterdayDate);
            }
        }
    }

    // ���� ��¥ �Ǵ� �˻��� ��¥�� ������ ��������
    @GetMapping(value = "/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getArchivesByDate(@PathVariable String date, HttpSession se) {
        // ���ǿ��� userId ��������
        String userId = (String) se.getAttribute("sessionId");

        // ���� ��¥ �Ǵ� �˻��� ��¥�� ������ ��������
        List<ArchiveDTO> archives = as.getArchivesByRange(userId, date);

        return archives;
    }
    //���Ⱑ �׽�Ʈ ������
    
    // ��¥ �˻�
    @GetMapping(value = "/searchDate/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(@PathVariable("date") String date, HttpSession se) {
        // ���� ��������
        String userId = (String) se.getAttribute("sessionId"); 

        // �˻�
        List<ArchiveDTO> archives = as.searchArchivesByDate(userId, date);

        return archives;
    }
     
    // ���� �˻�
    @GetMapping(value = "/searchKeyword/{keyword}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchByDetail(@PathVariable String keyword, HttpSession se) {
        // ���ǿ��� userId ��������
        String userId = (String) se.getAttribute("sessionId");
        return as.searchByDetail(userId, keyword);
    }
}
