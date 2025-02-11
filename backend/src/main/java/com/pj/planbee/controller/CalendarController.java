package com.pj.planbee.controller;

import com.pj.planbee.dto.CalendarDTO;
import com.pj.planbee.service.CalendarService;
import com.pj.planbee.service.TodoListService;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {

    @Autowired CalendarService cs;
    @Autowired TodoListService ts;
    
    
    @GetMapping(value="/dprogress/{calDate}", produces="application/json;charset=UTF-8") //공백들어가면 안됨 ㅠㅠ
    public double getProgress(@PathVariable String calDate, HttpSession se) { //일별 진척도 가져오는 기능
        
       // 세션만드는 메소드가 호출되어서 세션이 만들어짐
       String sessionId = (String) se.getAttribute("sessionId"); //결과값은 팥붕이 받아진다
       int tdId = cs.getProgress(calDate, sessionId);
       double progress = ts.todoProgress(tdId);
       
       
       return progress;
    }
}
    