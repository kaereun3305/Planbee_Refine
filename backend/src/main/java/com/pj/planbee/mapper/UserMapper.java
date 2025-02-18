package com.pj.planbee.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pj.planbee.dto.UserDTO;

@Mapper
public interface UserMapper {
	public int insertUser(UserDTO dto);

	public int countUserId(String userId);

	public int countUserEmail(String userEmail);
	
	public void disableSafeUpdates();
}
