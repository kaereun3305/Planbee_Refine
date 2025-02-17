package com.pj.planbee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired UserMapper mapper;
	
	@Autowired UserService us;
	
	@Autowired TempUserService tus;
	
	@Transactional
	@Override
	public int regiseterUser(UserDTO user) {
	    int result = 0;

	    try {
	        if (isUserIdExists(user.getUserId())) {
	            return -1; // 이미 가입된 ID
	        }
	        if (isEmailExists(user.getUserEmail())) {
	            return -2; // 이미 가입된 이메일
	        }

	        // 인증 상태 확인
	        int verifyStatus = tus.getVerifyStatus(user.getUserEmail());
	        if (verifyStatus != 1) {
	            return -4; // 이메일 인증이 완료되지 않음
	        }

	        // 인증 코드 확인
	        String storedCode = tus.getTempUserCode(user.getUserEmail());
	        if (storedCode == null || !storedCode.equals(user.getTempUserCode())) {
	            return -3; // 인증 코드 불일치
	        }

	        // RealUser 테이블에 저장
	        result = mapper.insertUser(user);

	        // 회원가입 성공 시 TempUser 삭제
	        if (result > 0) {
	            tus.deleteTempUser(user.getUserEmail());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}




	
    @Override
    public boolean isUserIdExists(String userId) {
        return mapper.countUserId(userId) > 0;
    }

    @Override
    public boolean isEmailExists(String userEmail) {
        return mapper.countUserEmail(userEmail) > 0;
    }
	
}
