package com.pj.planbee.mapper;

import java.util.List;

import com.pj.planbee.dto.BoardDTO;

public interface BoardMapper {
	public List<BoardDTO> getAllList();
	public BoardDTO getView(int postId);
	public int writePost(BoardDTO dto);
	public int boardModify(BoardDTO dto);
	public int boardDel(int PostId);
	public int boardHit(int postId);
	public List<BoardDTO> boardGroup(int groupId);
	public String getWriter(int postId); //postId기반으로 글쓴이 누구인지 확인
	public List<BoardDTO> boardMine(String sessionId);
	public List<BoardDTO> boardUser(String userId);
	public List<BoardDTO> maxHit();
	
}
