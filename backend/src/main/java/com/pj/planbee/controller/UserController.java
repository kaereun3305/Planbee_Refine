package com.pj.planbee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.service.UserService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")  //  User API 기본 경로 설정
public class UserController {

    @Autowired
    private UserService userService;

    //  회원 가입
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public int registerUser(@RequestBody UserDTO user) {
        int result = userService.regiseterUser(user);

        if (result == -1) {
            //"회원가입 실패: user_id 중복됨";
            return result;
        } else if (result == -2) {
            //"회원가입 실패: email 중복됨";
        	return result;
        } else if (result == -3) {
            //"회원가입 실패: 인증 코드 불일치";
        	return result;
        } else if (result == -4) {
            //"회원가입 실패: 인증 정보 불일치";
        	return result;
        } else if (result > 0) {
            //"회원가입 성공!";
        	return result;
        } else {
            //"회원가입 실패!";
        	return 0;
        }
    }


    //  특정 userId가 존재하는지 확인
    @GetMapping(value = "/check-id/{userId}", produces = "application/json; charset=utf-8")
    public boolean checkUserId(@PathVariable String userId) {
        return userService.isUserIdExists(userId);
    }

    //  특정 email이 존재하는지 확인
    @GetMapping(value = "/check-email/{email}", produces = "application/json; charset=utf-8")
    public boolean checkEmail(@PathVariable String email) {
        return userService.isEmailExists(email);
    }
}
