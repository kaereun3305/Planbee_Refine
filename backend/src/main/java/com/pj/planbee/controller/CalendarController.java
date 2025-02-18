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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @Autowired TodoListService ts;
    
    @PostMapping(value="/makeSession", produces = "application/json; charset=utf-8")//세션 설정 메소드
    public int session(HttpSession se) { 
       se.setAttribute("sessionId", "슈붕");
       return 1;
       
    }
    
     @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8") // 로그인 상태 확인
     public int checkSession(HttpSession session) { //세션체크 -찬교님 코드 참고함
         return (session.getAttribute("sessionId") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
     }
    
    //일별 진척도
     @GetMapping("/dprogress")
     public double getProgress(@RequestParam String calDate, HttpSession se) {
         String sessionId = (String) se.getAttribute("sessionId");
         int tdId = cs.getProgress(calDate, sessionId);
         return ts.todoProgress(tdId);
     }

    
    // 현재 연속 달성일
    @GetMapping(value="/curStreak", produces="application/json;charset=UTF-8")
	public int curStreak(HttpSession se) {
    	String sessionId = (String) se.getAttribute("sessionId");
    	Map<String, Integer> result = new HashMap<String, Integer>(); //결과 값을 받아오기 위함
    	result = cs.curProgress(sessionId); //받아옴
    	int days = result.get("curStreak");
		return days;
	}
    
    // 최대 연속 달성일
    @GetMapping(value="/maxStreak", produces="application/json;charset=UTF-8")
    public int maxStreak(HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
    	Map<String, Integer> result = new HashMap<String, Integer>(); 
    	result = cs.curProgress(userId);
    	int max = result. get("maxStreak");
    	
    	return max;
    }
    
    @GetMapping("/memo/{calDate}")
    public List<CalendarDTO> getMemo(@PathVariable String calDate, HttpSession se) {
        String sessionId = (String) se.getAttribute("sessionId");
        System.out.println(sessionId);
        if (sessionId == null) {
            throw new RuntimeException("세션 ID가 존재하지 않습니다.");
        }
        return cs.getMemo(calDate, sessionId);
    }

    
    //메모추가
    @PostMapping("/addmemo/{calDate}/{sessionId}")
    public int addMemo(@PathVariable String calDate, @PathVariable String sessionId, @RequestBody CalendarDTO calendar) {
        calendar.setCalDate(calDate);
        calendar.setUserId(sessionId);
        int result = cs.addMemo(calendar);
        return result;
    }

    
    //메모 수정
    @PutMapping("/modimemo/{calId}")
    public int modiMemo(@PathVariable int calId, @RequestBody CalendarDTO calendar) {
    	 calendar.setCalId(calId);
    	int result = cs.modiMemo(calendar);
    	return result;
    }
    
    //메모 삭제
    @GetMapping("/delmemo")
    public int delMemo(@RequestBody CalendarDTO calendar, HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
    	calendar.setUserId(userId);
    	int result = cs.delMemo(userId);
    	return result;
    }
    
}
    