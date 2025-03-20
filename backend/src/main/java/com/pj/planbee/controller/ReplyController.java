package com.pj.planbee.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.ReplyDTO;
import com.pj.planbee.service.ReplyService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders= "*", allowCredentials = "true")
@RequestMapping("/groups/{groupId}/boards/{postId}/reply")
public class ReplyController {
	
	@Autowired ReplyService rs;
	@Autowired HttpSession se;
    
	// 댓글 작성 (postId를 URL에서 가져와 DTO에 설정 + 세션에서 userId 가져오기)
    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> createReply(@PathVariable int postId, @RequestBody ReplyDTO reply) {
    	
        String userId = (String) se.getAttribute("sessionId"); // 현재 로그인한 사용자의 ID 가져오기
        
        if (userId == null) {
            return ResponseEntity.ok(0); // 로그인되지 않은 사용자면 실패 (0 반환)
        }

        reply.setPostId(postId);
        reply.setUserId(userId); // 세션에서 가져온 userId 설정

        int result = rs.addReply(reply);
        return ResponseEntity.ok(result);
    }

    //댓글 수정
    @PutMapping(value="/{commentId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> updateReply(@PathVariable int postId, @PathVariable int commentId, @RequestBody ReplyDTO reply) {

    	    String userId = (String) se.getAttribute("sessionId");

    	    if (userId == null) {
    	        return ResponseEntity.ok(0); // 로그인되지 않은 사용자면 실패
    	    }

    	    reply.setReplyId(commentId);
    	    reply.setPostId(postId);  // postId 설정 추가!
    	    reply.setUserId(userId);

    	    int result = rs.updateReply(reply);

    	    return ResponseEntity.ok(result);
    	}



    // 댓글 삭제 (commentId를 URL에서 받아서 삭제)
    @DeleteMapping(value="/{commentId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> deleteReply(@PathVariable int postId, @PathVariable int commentId) {

        String userId = (String) se.getAttribute("sessionId");
      
        if (userId == null) {
            return ResponseEntity.ok(0); // 로그인되지 않은 사용자면 실패
        }

        int result = rs.deleteReply(commentId, postId, userId);  // postId와 userId 추가

        return ResponseEntity.ok(result);
    }

}
