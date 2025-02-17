package com.pj.planbee.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.LoginDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.service.LoginService;

@RestController
@CrossOrigin(origins = "*") // CSR을 위한 CORS 허용
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired LoginService service;
	
	@PostMapping(value="/login", produces = "application/json; charset=utf-8")
	public int login(@RequestBody UserDTO userDto, HttpSession session) {
        String userId = userDto.getUserId();
        String userPw = userDto.getUserPw();

        LoginDTO user = service.login(userId, userPw);

        if (user != null) {
            session.setAttribute("user", user); // 세션 생성
            return 1; // 로그인 성공
        } else {
            return -1; // 로그인 실패 (아이디 또는 비밀번호 불일치)
        }
    }

    // 로그아웃
    @PostMapping(value = "/logout", produces = "application/json; charset=utf-8")
    public int logout(HttpSession session) {
        session.invalidate();
        return 1; // 로그아웃 성공
    }
    

    // 로그인 상태 확인
    @GetMapping(value = "/session", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession session) {
        return (session.getAttribute("user") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
    }
}
