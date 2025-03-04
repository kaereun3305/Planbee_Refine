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
	
	//get board list 전체를 가져오는 기능
	@GetMapping(value="/boardList", produces = "application/json; charset=utf-8")
	public List<BoardDTO> getAllList(){
				
		List<BoardDTO> totalBoard = new ArrayList<BoardDTO>();
		totalBoard = bs.getAllList();
		//값이 없으면 빈 배열이 반환되므로, 빈 배열 반환시 작성된 글이 없습니다 등 문구 넣으면 될듯
		return totalBoard;
	}
	
	//get 게시글 한 개를 가져오는 기능
	@GetMapping(value="/viewOne/{postId}", produces = "application/json; charset=utf-8")
	public BoardDTO getView(@PathVariable int postId) {
		BoardDTO dto = bs.getView(postId);
		System.out.println("ctrl"+ dto.getPostContent());
		return dto;
	}
	
	//post board 입력하는 기능
	@PostMapping(value="/boardWrite", produces = "application/json; charset=utf-8")
	public int boardWrite(@RequestBody BoardDTO dto, HttpSession se) {
		String userId = (String) se.getAttribute("sessionId");
		dto.setUserId(userId);
//		System.out.println("ctrl" + userId);
//		System.out.println(dto.getPostContent());
//		System.out.println(dto.getPostTitle());
		int result = bs.writePost(dto);
		
		return result;
		
	}
	

	//put board 수정하는 기능
	@PutMapping(value="/boardModi", produces = "application/json; charset=utf-8")
	public int boardModify(@RequestBody BoardDTO dto, HttpSession se) {
		String userId = (String) se.getAttribute("sessionId");
		
		
		int result = bs.boardModify(dto, userId);
		return result;
	}
	
	//del board 삭제하는 기능
	@DeleteMapping(value="/boardDel/{postId}", produces = "application/json; charset=utf-8")
	public int boardDel(@PathVariable int postId, HttpSession se) {
		String userId = (String) se.getAttribute("sessionId");
		int result = bs.boardDel(postId, userId);
		return result;
	}
	
	//게시글 클릭하면 조회수 +1 되는 기능
	@PutMapping(value="/boardHit/{postId}", produces = "application/json; charset=utf-8")
	public int boardHit(@PathVariable int postId) {
		int result = bs.boardHit(postId);
		return result;
	}
	//팀별 게시글 검색 기능
	@GetMapping(value="/boardGroup/{groupId}", produces="application/json; charset=utf-8")
	public List<BoardDTO> boardGroup(@PathVariable int groupId){ 
		List<BoardDTO> boardGroup = new ArrayList<BoardDTO>();
		boardGroup = bs.boardGroup(groupId);
		
		return boardGroup;
	}
	
//	@GetMapping(value="/boardLatest", produces="application/json; charset=utf-8")
//	public List<BoardDTO> boardLatest(){
//		return list;
//	}
//	//정렬기능 최신순
	

	
}
