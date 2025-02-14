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
        int result = tempUserService.insertTempUser(dto);
  

        if (result == -1) {
           //이미 가입된 ID
            return result;
        } else if (result == -2) {
           //이미 가입된 이메일
            return result;
        } else if (result > 0) {
            //이메일 인증 코드 발송
            return result;
        } else {
            //?? 무슨 오류인지 모름
            return result;
        }
    }

}