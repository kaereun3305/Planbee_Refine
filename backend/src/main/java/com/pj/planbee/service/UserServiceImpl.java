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
	        // 최종 중복 검사 (Real_User 테이블에서 확인)
	        if (isUserIdExists(user.getUserId())) {
	            return -1; // 중복된 ID
	        }
	        if (isEmailExists(user.getUserEmail())) {
	            return -2; // 중복된 Email
	        }

	        // Temp_User 테이블에서 저장된 데이터 가져오기
	        TempUserDTO tempUser = tus.getTempUserByEmail(user.getUserEmail());

	        // 저장된 인증 코드 비교 (인증 코드 불일치 시 가입 불가)
	        if (tempUser == null || !tempUser.getTempUserCode().equals(user.getTempUserCode())) {
	            return -3; // 인증 코드 불일치
	        }

	        // 저장된 Temp_User 정보와 입력한 정보 비교 (모든 값이 일치해야 가입 가능)
	        if (!tempUser.getTempUserId().equals(user.getUserId()) ||
	            !tempUser.getTempUserPw().equals(user.getUserPw()) ||
	            !tempUser.getTempUserName().equals(user.getUserName()) ||
	            !tempUser.getTempUserPhone().equals(user.getUserPhone())) {
	            return -4; // Temp_User 정보 불일치
	        }

	        // 모든 정보가 일치하면 Real_User 테이블에 정보 저장
	        result = mapper.insertUser(user);

	        // 회원가입 성공 시 TempUser 삭제
	        if (result > 0) {
	            tus.deleteTempUser(user.getUserEmail());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("회원가입 실패");
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
