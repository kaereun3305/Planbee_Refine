package com.pj.planbee.service;

import java.util.Map;

import com.pj.planbee.dto.LoginDTO;

public interface LoginService {
    LoginDTO login(Map<String, Object> paramMap);
}
