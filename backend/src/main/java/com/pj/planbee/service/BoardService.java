package com.pj.planbee.service;

import java.util.List;



import com.pj.planbee.dto.BoardDTO;


public interface BoardService {
	public List<BoardDTO> getAllList();  //모든 게시글 가져오는 기능
	public BoardDTO getView(int postId); //하나의 게시글 가져오는 기능
	public int writePost(BoardDTO dto); //글을 쓰는 기능
	public int boardModify(BoardDTO dto, String sessionId, int postId); //글을 수정하는 기능
	public int boardDel(int postId, String sessionId); //글을 삭제하는 기능
	public int boardHit(int postId); //글 클릭하면 조회수 올라가는 기능
	public List<BoardDTO> boardGroup(int groupId); //그룹별 글 조회기능
	public List<BoardDTO> boardUser(String userId); //아이디로 글 조회기능
	public List<BoardDTO> maxHit();
}
