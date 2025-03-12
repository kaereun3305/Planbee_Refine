package com.pj.planbee.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.pj.planbee.dto.LoginDTO;

@Service
public interface LoginService {
    LoginDTO login(Map<String, Object> paramMap);
    public boolean isUserExists(String userId);
}
