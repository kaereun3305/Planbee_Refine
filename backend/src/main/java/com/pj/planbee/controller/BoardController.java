package com.pj.planbee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.pj.planbee.dto.PostWithReplyDTO;
import com.pj.planbee.dto.ReplyDTO;
import com.pj.planbee.service.BoardService;
import com.pj.planbee.service.GroupService;
import com.pj.planbee.service.ReplyService;

@RestController
@RequestMapping("/groups") //
@CrossOrigin(origins = "*", allowedHeaders="*", allowCredentials ="true")
public class BoardController {
	
	@Autowired BoardService bs;
	@Autowired GroupService gs;
	@Autowired HttpSession se;
	@Autowired ReplyService rs;
	
	//  그룹 정보 조회
	@GetMapping(value="/{groupId}", produces="application/json; charset=utf-8")
	public GroupInfoDTO getGroupInfo(@PathVariable int groupId) {
	    return bs.boardGroup(groupId);
	}

	// 단일 게시글 + 댓글 + 대댓글
    @GetMapping(value = "/{groupId}/boards/{postId}", produces = "application/json; charset=utf-8")
    public PostWithReplyDTO getPostWithReplies(@PathVariable int postId) {
        // 1. 게시글 조회
        BoardDTO post = bs.getView(postId);

        // 2. 댓글 및 대댓글 조회 (계층 구조로 반환)
        List<ReplyDTO> replies = rs.getReplysWithReplies(postId);

        // 3. DTO에 게시글과 댓글 + 대댓글 저장
        PostWithReplyDTO response = new PostWithReplyDTO(post, replies);
        System.out.println("결과값" + response);
        return response;
    }

	
	//  게시글 작성
    @PostMapping(value = "/{groupId}/boards", produces = "application/json; charset=utf-8")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody BoardDTO dto) {
        String sessionId = (String) se.getAttribute("sessionId");
        dto.setUserId(sessionId);

        int groupId = bs.groupSearch(sessionId);
        dto.setGroupId(groupId);

        int result = bs.writePost(dto); // 게시글 저장

        Map<String, Object> response = new HashMap<>();

        if (result == 1) {
            // 방금 작성된 postId 가져오기(FE에게 전달할 용도)
            int postId = bs.getLatestPostIdByUser(sessionId);

            response.put("message", "게시글 작성 성공");
            response.put("redirectUrl", "/planbee/groups/" + groupId + "/boards/" + postId);
            response.put("postId", postId);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "게시글 작성 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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

	// 검색과 정렬 동시 진행 가능
	@GetMapping(value="/{groupId}/boards", produces="application/json; charset=utf-8")
	public ResponseEntity<GroupInfoDTO> getSortedOrFilteredBoards(
	    @PathVariable int groupId, 
	    @RequestParam(required = false) String searchType, 
	    @RequestParam(required = false) String query, 
	    @RequestParam(required = false) String sort) {

	    GroupInfoDTO result = bs.getSortedOrFilteredBoards(groupId, searchType, query, sort);
	    return ResponseEntity.ok(result);
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
