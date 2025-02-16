package com.pj.planbee.controller;

import com.pj.planbee.dto.CalendarDTO;
import com.pj.planbee.service.CalendarService;
import com.pj.planbee.service.TodoListService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {

    @Autowired CalendarService cs;
    @Autowired TodoListService ts;
    
    //일별 진척도
    @GetMapping(value="/dprogress/{calDate}", produces="application/json;charset=UTF-8") //공백들어가면 안됨 ㅠㅠ
    public double getProgress(@PathVariable String calDate, HttpSession se) { //일별 진척도 가져오는 기능
        
       // 세션만드는 메소드가 호출되어서 세션이 만들어짐
       String sessionId = (String) se.getAttribute("sessionId"); //결과값은 팥붕이 받아진다
       int tdId = cs.getProgress(calDate, sessionId);
       double progress = ts.todoProgress(tdId);
       
       
       return progress;
    }
    
    // 현재 연속 달성일
    @GetMapping(value="/curStreak", produces="application/json;charset=UTF-8")
	public int curStreak(HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
    	Map<String, Integer> result = new HashMap<String, Integer>(); //결과 값을 받아오기 위함
    	result = cs.curProgress(userId); //받아옴
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
    
    // 메모 조회
    @GetMapping("/memo/{calDate}")
    public List<CalendarDTO> getMemo(@PathVariable String calDate, HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
    	
    	return cs.getMemo(calDate, userId);
    }
    
    //메모 추가
    @GetMapping("/addmemo")
    public int addMemo(@RequestBody CalendarDTO calendar, HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
    	calendar.setUserId(userId);
    	int result = cs.addMemo(calendar);
    	return result;
    }
    
    //메모 수정
    @GetMapping("/modimemo")
    public int modiMemo(@RequestBody CalendarDTO calendar, HttpSession se) {
    	String userId = (String) se.getAttribute("sessionId");
    	calendar.setUserId(userId);
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
    