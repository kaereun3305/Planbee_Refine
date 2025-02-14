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

    @Transactional
    public int insertTempUser(TempUserDTO dto) {
        int result = 0;
        
        try {
            // ID 중복 검사
            if (mapper.countUserId(dto.getTempUserId()) > 0) {
                //이미 가입된 ID입니다
                return -1; // ID 중복 오류 반환
            }

            // Email 중복 검사
            if (mapper.countUserEmail(dto.getTempUserEmail()) > 0) {
                //이미 가입된 이메일입니다
                return -2; // Email 중복 오류 반환
            }

            // 기존 데이터 삭제 (중복 방지)
            mapper.deleteTempUser(dto.getTempUserEmail());

            // 6자리 인증 코드 생성 및 저장
            String verificationCode = generateVerificationCode();
            dto.setTempUserCode(verificationCode);
            result = mapper.insertTempUser(dto);

            // 이메일 전송
            if (result > 0) {
                sendCode(dto.getTempUserEmail(), verificationCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("이메일 인증 실패");
        }
        return result;
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

            // 이메일 본문 생성
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
            result = 1; // 이메일 전송 성공

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int deleteTempUser(String tempUserEmail) {
        return mapper.deleteTempUser(tempUserEmail);
    }

    public String getTempUserCode(String tempUserEmail) {
        return mapper.getTempUserCode(tempUserEmail);
    }

	@Override
	public List<TempUserDTO> getTempUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TempUserDTO> getTempUserData(String tempUserId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TempUserDTO getTempUserByEmail(String tempUserEmail) {
	    return mapper.getTempUserByEmail(tempUserEmail);
	}
}