package com.pj.planbee.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.LoginDTO;
import com.pj.planbee.mapper.LoginMapper;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired LoginMapper mapper;

    @Override
    public LoginDTO login(Map<String, Object> paramMap) {
        return mapper.login(paramMap); 
    }
}