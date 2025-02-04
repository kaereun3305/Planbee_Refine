package com.pj.planbee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired UserMapper mapper;
	
	@Autowired UserService us;
	
	@Autowired TempUserService tus;
	
	@Override
	public int regiseterUser(UserDTO user) {
		int result = 0;
		
		try {
			if(isUserIdExists(user.getUserId())) {
				System.out.println("회원 가입 실패 : 이미 가입 완료 된 ID 입니다.");
				return -1;
			}
			if(isEmailExists(user.getUserEmail())) {
				System.out.println("회원 가입 실패 : 이미 인증이 완료된 email 입니다.");
				return -2;
			}
			
			result = mapper.insertUser(user);
			if(result > 0) {
				tus.deleteTempUser(user.getUserEmail());
			}
			
		} catch(Exception e) {
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
