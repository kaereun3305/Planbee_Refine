package com.pj.planbee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.LoginDTO;
import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.service.LoginService;
import com.pj.planbee.service.TempUserService;
import com.pj.planbee.service.UserService;

@RestController
@CrossOrigin(origins = "*") // CSR을 위한 CORS 허용
@RequestMapping("/auth")
public class AuthController {

    @Autowired TempUserService tempUserService;
    
    @Autowired UserService userService;

    @Autowired LoginService loginService;

    // 이메일 인증 코드 발송
    @PostMapping(value = "/email/send", produces = "application/json; charset=utf-8")
    public int sendVerificationCode(@RequestBody TempUserDTO dto) {
        int result = tempUserService.insertOrUpdateTempUser(dto);

        if (result == -1) return result; // 이미 가입된 ID
        else if (result == -2) return result; // 이미 가입된 이메일
        else if (result > 0) return result; // 이메일 인증 코드 발송 성공
        else return 0; // 알 수 없는 오류
    }

    // 이메일 인증 코드 확인
    @PostMapping(value = "/email/verify", produces = "application/json; charset=utf-8")
    public int verifyUserCode(@RequestBody TempUserDTO dto) {
        String storedCode = tempUserService.getTempUserCode(dto.getTempUserEmail());

        if (storedCode != null && storedCode.equals(dto.getTempUserCode())) {
            return tempUserService.updateVerifyStatus(dto.getTempUserEmail()); // 인증 성공
        } else {
            return -1; // 인증 코드 불일치
        }
    }

    // 회원가입
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public int registerUser(@RequestBody UserDTO user) {
        int result = userService.regiseterUser(user);

        if (result == -1) return result; // "회원가입 실패: user_id 중복됨"
        else if (result == -2) return result; // "회원가입 실패: email 중복됨"
        else if (result == -3) return result; // "회원가입 실패: 인증 코드 불일치"
        else if (result == -4) return result; // "회원가입 실패: 인증 완료되지 않음"
        else if (result > 0) return result; // "회원가입 성공!"
        else return 0; // "회원가입 실패!"
    }

    // (테스트) 특정 userId가 존재하는지 확인
    @GetMapping(value = "/check-id/{userId}", produces = "application/json; charset=utf-8")
    public boolean checkUserId(@PathVariable String userId) {
        return userService.isUserIdExists(userId);
    }

    // (테스트) 특정 email이 존재하는지 확인
    @GetMapping(value = "/check-email/{email}", produces = "application/json; charset=utf-8")
    public boolean checkEmail(@PathVariable String email) {
        return userService.isEmailExists(email);
    }

 // 로그인
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public int login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        String userId = loginDTO.getUserId();
        String userPw = loginDTO.getUserPw();

        if (userId == null || userPw == null) {
            return -2; // 입력값이 null이면 로그인 실패 (-2)
        }

        // 파라미터를 Map으로 전달
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("userPw", userPw);

        LoginDTO user = loginService.login(paramMap);

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
