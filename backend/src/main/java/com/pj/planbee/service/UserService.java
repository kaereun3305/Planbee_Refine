package com.pj.planbee.service;

import com.pj.planbee.dto.UserDTO;

public interface UserService {
	public int regiseterUser(UserDTO user);

	boolean isUserIdExists(String userId);

	boolean isEmailExists(String email);
}
