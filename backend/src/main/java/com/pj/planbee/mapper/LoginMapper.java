package com.pj.planbee.mapper;

import com.pj.planbee.dto.LoginDTO;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

	LoginDTO login(Map<String, Object> paramMap);
}
