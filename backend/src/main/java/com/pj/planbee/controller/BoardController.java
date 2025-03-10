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
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.service.BoardService;


@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "*", allowedHeaders="*", allowCredentials ="true")
public class BoardController {
	@Autowired BoardService bs;
	
	
	@PostMapping(value = "/makeSession", produces = "application/json; charset=utf-8") // 세션 설정 메소드
	public String session(HttpSession se) {
		se.setAttribute("sessionId", "팥붕");
		//세션 두 개 사용해서 매일 테스트 할 것
		System.out.println("ctrl mkSession: "+ se.getAttribute("sessionId"));
		return (String) se.getAttribute("sessionId");

	}
	
	//팀별 게시글 검색 기능
		@GetMapping(value="/boardGroup", produces="application/json; charset=utf-8")
		public List<BoardDTO> boardGroup(HttpSession se){
			String sessionId = (String) se.getAttribute("sessionId");
			int groupId = bs.groupSearch(sessionId);
			//로그인 된 세션아이디 기반으로 그룹 번호 가져와서
			List<BoardDTO> boardGroup = new ArrayList<BoardDTO>();
			boardGroup = bs.boardGroup(groupId);
			//그룹번호에 해당하는 글을 가져옴
			
			return boardGroup;
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
	@GetMapping(value="/viewOne/{postId}", produces = "application/json; charset=utf-8")
	public BoardDTO getView(@PathVariable int postId) {
		//postId가 pk역할을 하므로 따로 groupId로 식별하지 않았음
		BoardDTO dto = bs.getView(postId);
		System.out.println("ctrl"+ dto.getPostContent());
		return dto;
	}
	
	//post board 입력하는 기능
	@PostMapping(value="/boardWrite", produces = "application/json; charset=utf-8")
	public int boardWrite(@RequestBody BoardDTO dto, HttpSession se) {
		String sessionId = (String) se.getAttribute("sessionId");
		dto.setUserId(sessionId);
		int groupId = bs.groupSearch(sessionId);
		dto.setGroupId(groupId);
		
		//세션아이디 기반으로 유저아이디와, 그룹아이디를 dto에 set
		int result = bs.writePost(dto);
		
		return result;
		
	}
	

	//put board 수정하는 기능
	@PutMapping(value="/boardModi/{postId}", produces = "application/json; charset=utf-8")
	public int boardModify(@RequestBody BoardDTO dto, HttpSession se, @PathVariable int postId) {
		String sessionId = (String) se.getAttribute("sessionId");
		dto.setUserId(sessionId);
		int groupId = bs.groupSearch(sessionId);
		dto.setGroupId(groupId);
		int result = bs.boardModify(dto, sessionId, postId);
		return result;
	}
	
	//del board 삭제하는 기능
	@DeleteMapping(value="/boardDel/{postId}", produces = "application/json; charset=utf-8")
	public int boardDel(@PathVariable int postId, HttpSession se) {
		String sessionId = (String) se.getAttribute("sessionId");
		//postId는 pk이므로 따로 그룹설정 하지 않음
		int result = bs.boardDel(postId, sessionId);
		//로그인된 세션아이디와 글쓴이가 다를 경우 삭제 불가
		return result;
	}
	
	//게시글 클릭하면 조회수 +1 되는 기능
	@PutMapping(value="/boardHit/{postId}", produces = "application/json; charset=utf-8")
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
	
	@GetMapping(value="/boardUser/{userId}", produces="application/json; charset=utf-8")
	public List<BoardDTO> byUser(@PathVariable String userId){
		//유저가 하나의 그룹에만 가입할 수 있다면, 유저아이디가 그룹을 식별할 수 있게 해주므로
		//따로 그룹설정 하지 않음
		List<BoardDTO> byUser = new ArrayList<BoardDTO>();
		byUser = bs.boardUser(userId);
		
		return byUser;
	}
	
	@GetMapping(value="/maxHit", produces="application/json; charset=utf-8")
	public List<BoardDTO> maxHit(HttpSession se){
		String sessionId = (String) se.getAttribute("sessionId");
		int groupId = bs.groupSearch(sessionId);
		//로그인된 세션아이디 바탕으로 그룹아이디 추적
		List<BoardDTO> max = new ArrayList<BoardDTO>();
		max = bs.maxHit(groupId);
		//해당 그룹 중에서 가장 조회수가 높은 글을 검색함
		
		return max;
	}
	
	//내용으로 서치하는 기능
	@GetMapping(value="/contentSearch/{content}", produces="application/json; charset=utf-8")
	public List<BoardDTO> contentSearch(HttpSession se, @PathVariable String content){
		String sessionId = (String) se.getAttribute("sessionId");
		int groupId = bs.groupSearch(sessionId);
		//로그인된 세션아이디를 바탕으로 그룹아이디를 추적
		List<BoardDTO> contents = new ArrayList<BoardDTO>();
		contents = bs.contentSearch(groupId, content);
		//해당 그룹에 있는 글 중에서 content에 쓴 글 내용과 비슷한 내용이 있는 경우 검색됨
		
		return contents;
	}
	
	//제목으로 서치하는 기능
	@GetMapping(value="/titleSearch/{content}", produces="application/json; charset=utf-8")
	public List<BoardDTO> titleSearch(HttpSession se, @PathVariable String content){
		String sessionId = (String) se.getAttribute("sessionId");
		int groupId = bs.groupSearch(sessionId);
		//로그인된 세션아이디를 바탕으로 그룹아이디를 추적
		List<BoardDTO> contents = new ArrayList<BoardDTO>();
		contents = bs.titleSearch(groupId, content);
		//해당 그룹에 있는 글 중에서 content에 쓴 글 내용과 비슷한 내용이 있는 경우 검색됨
		
		return contents;
	}

	@GetMapping(value="/newestSort", produces="application/json; charset=utf-8")
	public List<BoardDTO> newestSort(HttpSession se){
		String sessionId = (String) se.getAttribute("sessionId");
		int groupId = bs.groupSearch(sessionId);
		//로그인된 세션아이디 바탕으로 그룹아이디 추적
		List<BoardDTO> newest = new ArrayList<BoardDTO>();
		newest = bs.newestSort(groupId);
		//해당 그룹 중에서 최신순 글 검색
		
		return newest;
	}
	
	@GetMapping(value="/oldestSort", produces="application/json; charset=utf-8")
	public List<BoardDTO> oldestSort(HttpSession se){
		String sessionId = (String) se.getAttribute("sessionId");
		int groupId = bs.groupSearch(sessionId);
		//로그인된 세션아이디 바탕으로 그룹아이디 추적
		List<BoardDTO> oldest = new ArrayList<BoardDTO>();
		oldest = bs.oldestSort(groupId);
		//해당 그룹 중에서 최신순 글 검색
		
		return oldest;
	}
	
}
