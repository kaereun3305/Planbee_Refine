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
	            System.out.println("회원 가입 실패 : 이미 가입 완료 된 ID 입니다.");
	            return -1;
	        }
	        if (isEmailExists(user.getUserEmail())) {
	            System.out.println("회원 가입 실패 : 이미 인증이 완료된 email 입니다.");
	            return -2;
	        }

	        // 입력한 인증 코드가 Temp_User 데이터베이스의 tempuser_code와 일치하는지 확인
	        String storedCode = tus.getTempUserCode(user.getUserEmail()); // DB에서 저장된 코드 가져오기
	        if (storedCode == null || !storedCode.equals(user.getTempUserCode())) {
	            System.out.println("회원 가입 실패 : 인증 코드 불일치");
	            return -3; // 인증 코드 불일치
	        }

	        // RealUser 테이블에 정보 저장
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
