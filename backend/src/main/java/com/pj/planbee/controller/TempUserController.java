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
@CrossOrigin
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
    @PostMapping(value = "/sendcode", produces = "application/json; charset=utf-8")
    public String sendVerificationCode(@RequestBody TempUserDTO dto) {
        try {
            tempUserService.insertTempUser(dto);
            return "인증 코드가 이메일로 발송되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "인증 코드 발송에 실패했습니다.";
        }
    }

    // TempUser 삭제
    @DeleteMapping(value = "/delete/{tempUserEmail}", produces = "application/json; charset=utf-8")
    public String deleteTempUser(@PathVariable String tempUserEmail) {
        int result = tempUserService.deleteTempUser(tempUserEmail);
        if (result > 0) {
            return "임시 사용자 데이터가 삭제되었습니다.";
        } else {
            return "삭제 실패: 해당 이메일이 존재하지 않습니다.";
        }
    }
}