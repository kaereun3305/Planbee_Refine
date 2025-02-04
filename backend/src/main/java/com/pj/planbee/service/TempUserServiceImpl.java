package com.pj.planbee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.mapper.TempUserMapper;

@Service
public class TempUserServiceImpl implements TempUserService{
	
	@Autowired TempUserMapper mapper;
	
	@Autowired JavaMailSender mailSender;
	
	public List<TempUserDTO> getTempUser(){
		return mapper.getTempUser();
	}
	
	public List<TempUserDTO> getTempUserData(String tempUserId){
		List<TempUserDTO> list = mapper.getTempUserData(tempUserId);
		return list;
	}
	

	private String generateVerificationCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}
	
	public void sendCode(String recipientEmail, String code) throws Exception{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setTo(recipientEmail);
		helper.setSubject("PlanBee 회원가입 인증 코드");
		helper.setText("인증 코드는 " + code + "입니다. 정확히 입력해주세요." , true);
		mailSender.send(message);
	}
	
	
	public int insertTempUser(TempUserDTO dto) {
		int result = 0;
		
		try {
			String verficationCode = generateVerificationCode();
			sendCode(dto.getTempUserEmail(), verficationCode);
			dto.setTempUserCode(verficationCode);
			
			return mapper.insertTempUser(dto);
		} catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteTempUser(String tempUserEmail) {
		int result = 0;
		
		try {
			return mapper.deleteTempUser(tempUserEmail);
		} catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	
}