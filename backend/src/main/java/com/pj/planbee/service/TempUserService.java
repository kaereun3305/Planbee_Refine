package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.TempUserDTO;

public interface TempUserService {
	public List<TempUserDTO> getTempUser();	//테스트 기능
	
	public List<TempUserDTO> getTempUserData(String tempUserId); //테스트 기능
	
	public int insertTempUser(TempUserDTO dto); // DB에 임시 저장 기능
	
	public int deleteTempUser(String tempUserEmail); // DB에 임시 저장한 파일 삭제 기능
	
	public void sendCode(String tempUserEmail, String tempUserCode) throws Exception; // 코드를 생성해 이메일로 보내는 기능
	
}
