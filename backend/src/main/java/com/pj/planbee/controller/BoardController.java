package com.pj.planbee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.dto.GroupInfoDTO;
import com.pj.planbee.service.BoardService;
import com.pj.planbee.service.GroupService;

@RestController
@RequestMapping("/groups") //  group → groups로 RESTful하게 변경
@CrossOrigin(origins = "*", allowedHeaders="*", allowCredentials ="true")
public class BoardController {
	@Autowired BoardService bs;
	@Autowired GroupService gs;
	@Autowired HttpSession se;
	
	//  그룹 정보 조회
	@GetMapping(value="/{groupId}", produces="application/json; charset=utf-8")
	public GroupInfoDTO getGroupInfo(@PathVariable int groupId) {
	    return bs.boardGroup(groupId);
	}

	//  게시글 단일 조회
	@GetMapping(value="/{groupId}/boards/{postId}", produces = "application/json; charset=utf-8")
	public BoardDTO getPost(@PathVariable int postId) {
		BoardDTO dto = bs.getView(postId);
		System.out.println("ctrl"+ dto.getPostContent());
		return dto;
	}
	
	//  게시글 작성
	@PostMapping(value="/{groupId}/boards", produces = "application/json; charset=utf-8")
	public int createPost(@RequestBody BoardDTO dto) {
		String sessionId = (String) se.getAttribute("sessionId");
		dto.setUserId(sessionId);
		int groupId = bs.groupSearch(sessionId);
		dto.setGroupId(groupId);
		
		int result = bs.writePost(dto);
		return result;
	}

	//  게시글 수정
	@PutMapping(value="/{groupId}/boards/{postId}", produces = "application/json; charset=utf-8")
	public int updatePost(@RequestBody BoardDTO dto, @PathVariable int postId) {
		String sessionId = (String) se.getAttribute("sessionId");
		dto.setUserId(sessionId);
		int groupId = bs.groupSearch(sessionId);
		dto.setGroupId(groupId);
		int result = bs.boardModify(dto, sessionId, postId);
		return result;
	}
	
	//  게시글 삭제
	@DeleteMapping(value="/{groupId}/boards/{postId}", produces = "application/json; charset=utf-8")
	public int deletePost(@PathVariable int postId) {
		String sessionId = (String) se.getAttribute("sessionId");
		int result = bs.boardDel(postId, sessionId);
		return result;
	}
	
	//  게시글 조회수 증가
	@PutMapping(value="/{groupId}/boards/{postId}/hit", produces = "application/json; charset=utf-8")
	public int increasePostView(@PathVariable int postId) {
		int result = bs.boardHit(postId);
		return result;
	}

	//  내가 작성한 게시글 조회
	@GetMapping(value="/users/{userId}/boards", produces="application/json; charset=utf-8")
	public GroupInfoDTO getUserPosts(@PathVariable String userId){
		return bs.boardUser(userId);
	}

	//  게시글 조회수 순 정렬
	@GetMapping(value="/{groupId}/boards", produces="application/json; charset=utf-8")
	public GroupInfoDTO getSortedOrFilteredBoards(
	    @PathVariable int groupId, 
	    @RequestParam(required = false) String searchType, 
	    @RequestParam(required = false) String query, 
	    @RequestParam(required = false) String sort) {
	    
	    // 검색 우선
	    if ("content".equalsIgnoreCase(searchType)) {
	        return bs.contentSearch(groupId, query);
	    } else if ("title".equalsIgnoreCase(searchType)) {
	        return bs.titleSearch(groupId, query);
	    }
	    
	    // 정렬 적용
	    if ("hit".equalsIgnoreCase(sort)) {
	        return bs.maxHit(groupId);
	    } else if ("newest".equalsIgnoreCase(sort)) {
	        return bs.newestSort(groupId);
	    } else if ("oldest".equalsIgnoreCase(sort)) {
	        return bs.oldestSort(groupId);
	    }
	    
	    return bs.boardGroup(groupId);
	}

	
	//  그룹 탈퇴
	@PostMapping(value="/{groupId}/leave", produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String, Object>> leaveGroup(@PathVariable int groupId) {
	    String userId = (String) se.getAttribute("sessionId");
	    int success = gs.leaveGroup(userId, groupId);
	    
	    Map<String, Object> response = new HashMap<>();
	    if(success == 1) {
	        response.put("redirectUrl", "/planbee/group/list");
	    } else {
	        response.put("redirectUrl", "/planbee/group/" + groupId);
	    }
	    return ResponseEntity.ok(response);
	}
}
