package com.pj.planbee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	

	public String generateVerificationCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}
	
	public int sendCode(String recipientEmail, String code) {
	    int result = 0;

	    try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(recipientEmail);
	        helper.setSubject("PlanBee 회원가입 인증 코드");

	        // HTML 이메일 본문 생성 (테이블 기반 레이아웃)
	        String htmlContent = "<html><body style='margin: 0; padding: 0;'>"
	                + "<table role='presentation' width='100%' cellspacing='0' cellpadding='0' border='0' style='background-color: #000000; width: 100%; padding: 20px 0;'>"
	                + "<tr><td align='center'>"
	                + "<table role='presentation' width='500' cellspacing='0' cellpadding='0' border='0' style='background-color: #000000; color: #ffffff; border-radius: 10px; padding: 20px; text-align: center;'>"
	                + "<tr><td>"
	                + "<h1 style='color: #FFD700;'>PlanBee 회원가입 인증 코드</h1>"
	                + "<p style='font-size: 16px; margin-top: 20px;'>아래의 인증 코드를 입력하여 회원가입을 완료하세요.</p>"
	                + "<div style='background-color: #FFD700; color: #000000; font-size: 24px; font-weight: bold; padding: 10px; border-radius: 5px; display: inline-block; margin: 10px 0;'>"
	                + code + "</div>"
	                + "<p style='margin-top: 20px;'>정확히 입력해주세요.</p>"
	                + "<p style='margin-top: 30px; font-size: 12px; color: #aaaaaa;'>이 이메일은 자동 발송 메일입니다. 회신하지 마세요.</p>"
	                + "</td></tr></table>"
	                + "</td></tr></table>"
	                + "</body></html>";

	        helper.setText(htmlContent, true);
	        
	        mailSender.send(message);
	        result = 1; // 이메일 전송 성공 시 1 반환
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}


	@Transactional
	public int insertTempUser(TempUserDTO dto) {
	    int result = 0;
	    
	    try {
	        // **여기서는 인증 코드 생성 X**
	        // 인증 코드는 sendVerificationCode()에서 이미 생성됨

	        // Temp_User 테이블에 데이터 저장 (dto에 있는 코드 사용)
	        result = mapper.insertTempUser(dto);

	    } catch (Exception e) {
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
	
	public String getTempUserCode(String tempUserEmail) {
	    return mapper.getTempUserCode(tempUserEmail); // 데이터베이스에서 해당 이메일의 인증 코드 가져오기
	}
}