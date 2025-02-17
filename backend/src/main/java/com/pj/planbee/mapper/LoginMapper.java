package com.pj.planbee.mapper;

import com.pj.planbee.dto.LoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    LoginDTO login(@Param("userId") String userId, @Param("userPw") String userPw);
}
