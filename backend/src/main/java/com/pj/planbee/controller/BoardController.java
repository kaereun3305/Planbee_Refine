package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.view.RedirectView;

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.dto.GroupInfoDTO;
import com.pj.planbee.dto.PostListDTO;
import com.pj.planbee.service.BoardService;
import com.pj.planbee.service.GroupService;


@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "*", allowedHeaders="*", allowCredentials ="true")
public class BoardController {
	@Autowired BoardService bs;
	@Autowired GroupService gs;
	@Autowired HttpSession se;
	
	// 그룹 이름, 인원 수, 게시글 : { ... } 
	@GetMapping(value="/{groupId}", produces="application/json; charset=utf-8")
	public GroupInfoDTO boardGroup(@PathVariable int groupId) {
	    return bs.boardGroup(groupId);
	}
	
	//get board list 전체를 가져오는 기능은 테스트기능
//	@GetMapping(value="/boardList", produces = "application/json; charset=utf-8")
//	public List<BoardDTO> getAllList(){
//				
//		List<BoardDTO> totalBoard = new ArrayList<BoardDTO>();
//		totalBoard = bs.getAllList();
//		//값이 없으면 빈 배열이 반환되므로, 빈 배열 반환시 작성된 글이 없습니다 등 문구 넣으면 될듯
//		return totalBoard;
//	}
	
	//get 게시글 한 개를 가져오는 기능
	@GetMapping(value="/{groupId}/board/{postId}", produces = "application/json; charset=utf-8")
	public BoardDTO getView(@PathVariable int postId) {
		//postId가 pk역할을 하므로 따로 groupId로 식별하지 않았음
		BoardDTO dto = bs.getView(postId);
		System.out.println("ctrl"+ dto.getPostContent());
		return dto;
	}
	
	//post board 입력하는 기능
	@PostMapping(value="/{groupId}/board/boardWrite", produces = "application/json; charset=utf-8")
	public int boardWrite(@RequestBody BoardDTO dto) {
		String sessionId = (String) se.getAttribute("sessionId");
		dto.setUserId(sessionId);
		int groupId = bs.groupSearch(sessionId);
		dto.setGroupId(groupId);
		
		//세션아이디 기반으로 유저아이디와, 그룹아이디를 dto에 set
		int result = bs.writePost(dto);
		
		return result;
		
	}
	

	//put board 수정하는 기능
	@PutMapping(value="/{groupId}/board/boardModi/{postId}", produces = "application/json; charset=utf-8")
	public int boardModify(@RequestBody BoardDTO dto, @PathVariable int postId) {
		String sessionId = (String) se.getAttribute("sessionId");
		dto.setUserId(sessionId);
		int groupId = bs.groupSearch(sessionId);
		dto.setGroupId(groupId);
		int result = bs.boardModify(dto, sessionId, postId);
		return result;
	}
	
	//del board 삭제하는 기능
	@DeleteMapping(value="/{groupId}/board/boardDel/{postId}", produces = "application/json; charset=utf-8")
	public int boardDel(@PathVariable int postId) {
		String sessionId = (String) se.getAttribute("sessionId");
		//postId는 pk이므로 따로 그룹설정 하지 않음
		int result = bs.boardDel(postId, sessionId);
		//로그인된 세션아이디와 글쓴이가 다를 경우 삭제 불가
		return result;
	}
	
	//게시글 클릭하면 조회수 +1 되는 기능
	@PutMapping(value="/{groupId}/board/boardHit/{postId}", produces = "application/json; charset=utf-8")
	public int boardHit(@PathVariable int postId) {
		//postId는 pk이므로 따로 그룹설정 하지않음
		int result = bs.boardHit(postId);
		return result;
	}
	
	
//	@GetMapping(value="/boardLatest", produces="application/json; charset=utf-8")
//	public List<BoardDTO> boardLatest(){
//		return list;
//	}
//	//정렬기능 최신순
//	//내 게시글 가져오기 기능
//	@GetMapping(value="/boardMine", produces="application/json; charset=utf-8")
//	public List<BoardDTO> boardMine(HttpSession se){ 
//		String myId = (String) se.getAttribute("sessionId");
//		List<BoardDTO> myPost = new ArrayList<BoardDTO>();
//		myPost = bs.boardUser(myId);
//		
//		return myPost;
//	}
	
	@GetMapping(value="/{groupId}/board/boardUser/{userId}", produces="application/json; charset=utf-8")
	public GroupInfoDTO byUser(@PathVariable String userId){
		return bs.boardUser(userId);
	}
	
	@GetMapping(value="/{groupId}/board/maxHit", produces="application/json; charset=utf-8")
	public GroupInfoDTO maxHit(@PathVariable int groupId){
		return bs.maxHit(groupId);
	}
	
	//내용으로 서치하는 기능
	@GetMapping(value="/{groupId}/board/contentSearch/{content}", produces="application/json; charset=utf-8")
	public GroupInfoDTO contentSearch(@PathVariable int groupId, @PathVariable String content){
		return bs.contentSearch(groupId, content);
	}
	
	//제목으로 서치하는 기능
	@GetMapping(value="/{groupId}/board/titleSearch/{content}", produces="application/json; charset=utf-8")
	public GroupInfoDTO titleSearch(@PathVariable int groupId, @PathVariable String content){
		return bs.titleSearch(groupId, content);
	}

	@GetMapping(value="/{groupId}/board/newestSort", produces="application/json; charset=utf-8")
	public GroupInfoDTO newestSort(@PathVariable int groupId){
		return bs.newestSort(groupId);
	}
	
	@GetMapping(value="/{groupId}/board/oldestSort", produces="application/json; charset=utf-8")
	public GroupInfoDTO oldestSort(@PathVariable int groupId){
		return bs.oldestSort(groupId);
	}
	
	 // 그룹 탈퇴 (탈퇴 후 그룹 목록으로 이동)
    @PostMapping(value="/{groupId}/leave", produces = "application/json; charset=utf-8")
    public RedirectView leaveGroup(@PathVariable int groupId) {
        String userId = (String) se.getAttribute("sessionId");
       
        int success = gs.leaveGroup(userId, groupId);
        
        if(success == 1) {
        	return new RedirectView("http://localhost:8080/planbee/group/list");
        }
        
        System.out.println("탈퇴 실패");
        return new RedirectView("http://localhost:8080/planbee/group/"+groupId);
    }
	
}
