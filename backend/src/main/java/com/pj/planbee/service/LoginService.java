package com.pj.planbee.service;

import com.pj.planbee.dto.LoginDTO;

public interface LoginService {
	LoginDTO login(String userId, String userPw);
}
