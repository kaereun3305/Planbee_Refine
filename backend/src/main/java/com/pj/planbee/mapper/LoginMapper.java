package com.pj.planbee.mapper;

import java.util.Map;

import com.pj.planbee.dto.LoginDTO;

public interface LoginMapper {
    LoginDTO loginUser(Map<String, String> credentials);
}