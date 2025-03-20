package com.pj.planbee.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.pj.planbee.dto.GroupDTO;
import com.pj.planbee.service.GroupService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders= "*", allowCredentials = "true")
@RequestMapping("/group")
public class GroupController {
	
    @Autowired GroupService gs;
    @Autowired HttpSession se;
    
    //세션 생성 메소드(로그인 연결시 삭제 예정)
  	@PostMapping(value = "/makeSession", produces = "application/json; charset=utf-8")
  	public String session() {
  		se.setAttribute("sessionId", "슈붕");
  		return (String) se.getAttribute("sessionId");
  	}
  	
  	// 로그아웃(로그인 연결시 삭제 예정)
  	@PostMapping(value = "/logout", produces = "application/json; charset=utf-8")
  	public int logout() {
  		se.invalidate();
  		return 1; // 로그아웃 성공
  	}
  	
  	// 로그인 상태 확인(로그인 연결시 삭제 예정)
  	@GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8")
  	public int checkSession() {
  		return (se.getAttribute("sessionId") != null) ? 1 : 0;  // 1: 로그인된 상태, 0: 로그인되지 않음
  	}

  	// 사용자가 가입한 그룹 확인 후 리다이렉트
  	@GetMapping(value="", produces = "application/json; charset=utf-8")
  	public RedirectView checkUserGroup() {
  		String userId = (String) se.getAttribute("sessionId");
  		
  		Integer groupId = gs.getUserGroupId(userId);

  	    // 가입된 그룹이 없으면 그룹 목록 페이지로 이동
  	    if (groupId == null || groupId == 0) {
  	        return new RedirectView("http://localhost:8080/planbee/group/list");
  	    }

  	    // 가입된 그룹이 있으면 해당 그룹의 게시판으로 이동
  	    return new RedirectView("http://localhost:8080/planbee/group/" + groupId); // 그룹 게시판으로 이동
  	}
  	
  	 // 모든 그룹 조회
    @GetMapping(value="/list", produces = "application/json; charset=utf-8")
    public List<GroupDTO> getAllGroups() {
        return gs.getAllGroups();
    }

    // 그룹 가입 (가입 후 바로 그룹 게시판으로 이동)
    @PostMapping(value="/join", produces = "application/json; charset=utf-8")
    public RedirectView joinGroup(@RequestParam int groupId) {
    	String userId = (String) se.getAttribute("sessionId");
       
        int success = gs.joinGroup(userId, groupId);
        
        if(success == 1) {
        	return new RedirectView("http://localhost:8080/planbee/group/"+groupId);
        }
        
        System.out.println("가입 실패");
        return new RedirectView("http://localhost:8080/planbee/group/list");
    }
    
    /*  
    @GetMapping("/{groupId}")
    public String groupPage(@PathVariable int groupId) {
        return "groupPage"; // 해당 그룹의 게시판 페이지 반환 (ViewResolver 필요)
    }
    
    @GetMapping(value="/{groupId}", produces="application/json; charset=utf-8")
    public ResponseEntity<String> groupPage(@PathVariable int groupId) {
        return ResponseEntity.ok("그룹 " + groupId + " 게시판 페이지가 아직 구현되지 않았습니다.");
    }*/
}
