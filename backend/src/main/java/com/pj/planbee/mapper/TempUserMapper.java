package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.TempUserDTO;

@Mapper
public interface TempUserMapper {
	public List<TempUserDTO> getTempUser(); //테스트 기능 
	public List<TempUserDTO> getTempUserData(@Param("tempUserId") String tempUserId); //테스트 기능
	public int insertTempUser(TempUserDTO dto); //TempUser DB로 저장
	public int deleteTempUser(@Param("tempUserEmail") String tempUserEmail);
	public String getTempUserCode(String tempUserEmail); //TempUser DB에 임시 저장한 파일 삭제
}