package com.pj.planbee.controller;

import com.pj.planbee.dto.CalendarDTO;
import com.pj.planbee.service.CalendarService;
import com.pj.planbee.service.TodoListService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {

    @Autowired CalendarService cs;
    
    @PostMapping(value="/makeSession", produces = "application/json; charset=utf-8")//세션 설정 메소드
    public int session(HttpSession se) { 
       se.setAttribute("sessionId", "팥붕");
       return 1;
       
    }
    
     @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8") // 로그인 상태 확인
     public int checkSession(HttpSession session) { //세션체크 -찬교님 코드 참고함
         return (session.getAttribute("sessionId") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
     }
    
    //일별 진척도
     @GetMapping("/dprogress/{calDate}") //날짜를 가져와서 일별 진척도를 확인
     public double getProgress(@PathVariable String calDate, HttpSession session) {
         String sessionId = (String) session.getAttribute("sessionId");
      // getProgress 메소드를 호출하여 해당 날짜의 진척도 계산 후 반환
         return cs.getProgress(calDate, sessionId);
     }
     //월별 진척도
     @GetMapping("/mprogress/{yyMM}")
     public double monthProgress(@PathVariable String yyMM,HttpSession session) {
    	 // 년, 월만 받아와서 해당 월의 데이터를 가져옴
    	 
    	 String sessionId = (String) session.getAttribute("sessionId");
    	 return cs.monthProgress(yyMM, sessionId);
     }
     
    // 현재 연속 달성일
    @GetMapping(value="/curStreak", produces="application/json;charset=UTF-8") // 
	public int curStreak(HttpSession se) {
    	String sessionId = (String) se.getAttribute("sessionId");
    	//curProgress 메소드로 현재 및 최대 연속 달성일을 포함한 결과를 받음
    	 Map<String, Integer> result = cs.curProgress(sessionId); //결과 값을 받아오기 위함
    	result = cs.curProgress(sessionId); //받아옴
    	int days = result.get("curStreak");
		return days;
	}
    
    // 최대 연속 달성일
    @GetMapping(value="/maxStreak", produces="application/json;charset=UTF-8")
    public int maxStreak(HttpSession se) {
    	String sessionId = (String) se.getAttribute("sessionId");
   	 Map<String, Integer> result = cs.curProgress(sessionId); //결과 값을 받아오기 위함
   	result = cs.curProgress(sessionId); //받아옴
    	int max = result. get("maxStreak");
    	
    	return max;
    }
    //메모 조회
    @GetMapping("/memo/{calDate}")
    public List<CalendarDTO> getMemo(@PathVariable String calDate, HttpSession se) {
        String sessionId = (String) se.getAttribute("sessionId");
        return cs.getMemo(calDate, sessionId);
    }

    //메모추가
    @PostMapping("/addmemo/{calDate}")
    // CalendarDTO 객체에 날짜(calDate)와 사용자 아이디를 설정한 후, DB에 메모를 저장
    public int addMemo(@PathVariable String calDate, @RequestBody CalendarDTO calendar, HttpSession se) {
        String sessionId = (String) se.getAttribute("sessionId");
        calendar.setCalDate(calDate);
        calendar.setUserId(sessionId);
        return cs.addMemo(calendar);
    }


    
    //메모 수정
    @PutMapping("/modimemo/{calId}")
    public int modiMemo(@PathVariable int calId, @RequestBody CalendarDTO calendar) {
    	 // 수정할 메모의 식별자를 CalendarDTO에 설정
    	 calendar.setCalId(calId);
    	int result = cs.modiMemo(calendar);
    	return result;
    }
    
    //메모 삭제 성공 여부는 1, 0으로
    @DeleteMapping("/delmemo/{calId}")
    public int delMemo(@PathVariable int calId) {
        return cs.delMemo(calId);
       
    }
    
}
    