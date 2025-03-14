package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.TempUserDTO;

public interface TempUserService {
	public List<TempUserDTO> getTempUser();	//테스트 기능
	
	public List<TempUserDTO> getTempUserData(String tempUserId); //테스트 기능
	
	public int insertTempUser(TempUserDTO dto); // DB에 임시 저장 기능(insertorupdate가 작동 시 삭제)
	
	public int deleteTempUser(String tempUserEmail); // DB에 임시 저장한 파일 삭제 기능
	
	public int sendCode(String tempUserEmail, String tempUserCode) throws Exception; // 코드를 생성해 이메일로 보내는 기능
	
	public String generateVerificationCode();
	
	public String getTempUserCode(String tempUserEmail);
	
	public TempUserDTO getTempUserByEmail(String tempUserEmail);
	
	public int insertOrUpdateTempUser(TempUserDTO tempUser); // 이메일 중복 시 업데이트
	
	public int updateVerifyStatus(String tempUserEmail); // 이메일 인증 후 verify_status 변경
	
	public Integer getVerifyStatus(String tempUserEmail);
}