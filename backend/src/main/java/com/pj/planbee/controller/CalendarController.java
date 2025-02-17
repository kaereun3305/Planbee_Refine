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
    
    @GetMapping(value = "/progress/{calDate}/{sessionId}", produces = "application/json;charset=UTF-8")
    public double getProgress(@PathVariable String calDate, @PathVariable String sessionId) {  
        if (sessionId == null || sessionId.isEmpty()) {
            return 0.0;
        }
        double progress = cs.getProgress(calDate, sessionId);return progress;
    }

    // 현재 연속 달성일
    @GetMapping(value="/curStreak/{sessionId}", produces="application/json;charset=UTF-8")
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
    
    @GetMapping("/memo/{calDate}/{sessionId}")
    public List<CalendarDTO> getMemo(@PathVariable String calDate, @PathVariable String sessionId) {
        return cs.getMemo(calDate, sessionId);
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
    