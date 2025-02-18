package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.TempUserDTO;

@Mapper
public interface TempUserMapper {
	public List<TempUserDTO> getTempUser(); //테스트 기능 
	public List<TempUserDTO> getTempUserData(@Param("tempUserId") String tempUserId); //테스트 기능
	public int insertTempUser(TempUserDTO dto); //TempUser DB로 저장(insertorupdate가 작동 시 삭제)
	public int deleteTempUser(@Param("tempUserEmail") String tempUserEmail);
	public String getTempUserCode(String tempUserEmail); //TempUser DB에 임시 저장한 파일 삭제
	public int countUserId(String tempUserId);
	public int countUserEmail(String tempUserEmail);
	public TempUserDTO getTempUserByEmail(String tempUserEmail);
	public int updateVerifyStatus(@Param("tempUserEmail") String tempUserEmail); // 이메일 인증 후 verify_status 변경
	public Integer getVerifyStatus(@Param("tempUserEmail") String tempUserEmail);
	public Integer checkTempUserExists(String tempUserEmail);
	public int updateTempUser(TempUserDTO dto);
	public void deleteExpiredTempUsers();
}