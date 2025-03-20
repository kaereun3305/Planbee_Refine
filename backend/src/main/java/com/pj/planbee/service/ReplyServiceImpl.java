package com.pj.planbee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.ReplyDTO;
import com.pj.planbee.mapper.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired ReplyMapper rm;
	
	//댓글 작성
	@Override
	public int addReply(ReplyDTO reply) { 
		int result = 0;
		try {
			result = rm.insertReply(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 게시글의 id로 댓글들 불러오기
	@Override
	public List<ReplyDTO> getReplies(int postId) {
		 return rm.getRepliesByPostId(postId);
	}

	//댓글 수정
	@Override
	public int updateReply(ReplyDTO reply) {
		int result = 0;
		try {
			result = rm.updateReply(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//댓글 삭제
	@Override
	public int deleteReply(int replyId, int postId, String userId) {
	    int result = 0;
	    try {
	        result = rm.deleteReply(replyId, postId, userId);  // ostId & userId 추가
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}
