package com.pj.planbee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.service.TempUserService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/tempuser")
public class TempUserController {

    @Autowired
    TempUserService tempUserService;

    // 테스트 전체 TempUser 조회
    @GetMapping(value = "/all", produces = "application/json; charset=utf-8")
    public List<TempUserDTO> getAllTempUsers() {
        return tempUserService.getTempUser();
    }

    // 테스트용 특정 TempUser 조회
    @GetMapping(value = "/{tempUserId}", produces = "application/json; charset=utf-8")
    public List<TempUserDTO> getTempUserData(@PathVariable String tempUserId) {
        return tempUserService.getTempUserData(tempUserId);
    }

    // 이메일 인증 코드 발송 + TempUser 저장
    @PostMapping(value = "/sendCode", produces = "application/json; charset=utf-8")
    public int sendVerificationCode(@RequestBody TempUserDTO dto) {
        int result = tempUserService.insertOrUpdateTempUser(dto);

        if (result == -1) {
            return result; // 이미 가입된 ID
        } else if (result == -2) {
            return result; // 이미 가입된 이메일
        } else if (result > 0) {
            return result; // 이메일 인증 코드 발송 성공
        } else {
            return 0; // 알 수 없는 오류
        }
    }

    @PostMapping(value = "/verifyCode", produces = "application/json; charset=utf-8")
    public int verifyUserCode(@RequestBody TempUserDTO dto) {
    	int result = 0;
        String storedCode = tempUserService.getTempUserCode(dto.getTempUserEmail());

        if (storedCode != null && storedCode.equals(dto.getTempUserCode())) {
            // 인증 성공 → 1로 업데이트
        	result = tempUserService.updateVerifyStatus(dto.getTempUserEmail());
            return result;
        }else {
        return -1; // 인증 코드 불일치
        }
    }
}