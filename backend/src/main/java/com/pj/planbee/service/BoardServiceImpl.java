package com.pj.planbee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.mapper.BoardMapper;
@Service
public class BoardServiceImpl implements BoardService{
	@Autowired BoardMapper btMap;

	@Override
	public List<BoardDTO> getAllList() {
		List<BoardDTO> totalBoard = new ArrayList<BoardDTO>();
		
		try {
			totalBoard = btMap.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalBoard;
	}

	@Override
	public BoardDTO getView(int postId) {
		
		BoardDTO dto = null;
		try {
			dto = btMap.getView(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public int writePost(BoardDTO dto) {
		int result = 0;
		try {
			result = btMap.writePost(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int boardModify(BoardDTO dto, String userId) {
		int result = 0;
		if(dto.getUserId().equals(userId)) {
			try {
				result = btMap.boardModify(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			result = -1;
		}
		
		return result;
	}

	@Override
	public int boardDel(int postId, String userId) {
		String writer = btMap.getWriter(postId);
		int result = 0; 
		if(writer.equals(userId)) {
			try {
				result = btMap.boardDel(postId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			result = -1;
		}
		return result;
	}

	@Override
	public int boardHit(int postId) {
		int result = 0;
		try {
			result = btMap.boardHit(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<BoardDTO> boardGroup(int groupId) {
		List<BoardDTO> group = new ArrayList<BoardDTO>();
		try {
			group = btMap.boardGroup(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

}
