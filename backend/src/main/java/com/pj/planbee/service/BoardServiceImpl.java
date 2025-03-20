package com.pj.planbee.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.dto.GroupInfoDTO;
import com.pj.planbee.dto.PostListDTO;
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
	public int boardModify(BoardDTO dto, String sessionId, int postId) {
		int result = 0;
		String writer = btMap.getWriter(postId);
		dto.setPostId(postId);
		if(writer.equals(sessionId)) {
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
	public int boardDel(int postId, String sessionId) {
		String writer = btMap.getWriter(postId);
		int result = 0; 
		if(writer.equals(sessionId)) {
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
	public GroupInfoDTO boardGroup(int groupId) {
	    List<PostListDTO> posts = new ArrayList<>();
	    String groupName = "";
	    int groupMemberCount = 0;

	    try {
	        // 1. 게시글 목록 조회
	        posts = btMap.boardGroup(groupId);
	        
	        // 2. 그룹 이름 조회
	        groupName = btMap.getGroupName(groupId);
	        
	        // 3. 그룹 인원 수 조회
	        groupMemberCount = btMap.getGroupMemberCount(groupId);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}


	@Override
	public GroupInfoDTO boardUser(String userId) {
		List<PostListDTO> posts = new ArrayList<>();
	    String groupName = "";
	    int groupMemberCount = 0;

	    try {
	        // 1. 게시글 목록 조회
	        posts = btMap.boardUser(userId);
	        
	        // 2. 그룹 이름 조회
	        groupName = btMap.getGroupNameWithUserId(userId);
	        
	        // 3. 그룹 인원 수 조회
	        groupMemberCount = btMap.getGroupMemberCountWithUserId(userId);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}
	
	@Override
	public GroupInfoDTO maxHit(int groupId) {
		 List<PostListDTO> posts = new ArrayList<>();
		    String groupName = "";
		    int groupMemberCount = 0;

		    try {
		        // 1. 게시글 목록 조회
		        posts = btMap.maxHit(groupId);
		        
		        // 2. 그룹 이름 조회
		        groupName = btMap.getGroupName(groupId);
		        
		        // 3. 그룹 인원 수 조회
		        groupMemberCount = btMap.getGroupMemberCount(groupId);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}
	
	@Override
	public GroupInfoDTO newestSort(int groupId){
		 List<PostListDTO> posts = new ArrayList<>();
		    String groupName = "";
		    int groupMemberCount = 0;

		    try {
		        // 1. 게시글 목록 조회
		        posts = btMap.newestSort(groupId);
		        
		        // 2. 그룹 이름 조회
		        groupName = btMap.getGroupName(groupId);
		        
		        // 3. 그룹 인원 수 조회
		        groupMemberCount = btMap.getGroupMemberCount(groupId);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}
	
	@Override
	public GroupInfoDTO oldestSort(int groupId){
		 List<PostListDTO> posts = new ArrayList<>();
		    String groupName = "";
		    int groupMemberCount = 0;

		    try {
		        // 1. 게시글 목록 조회
		        posts = btMap.oldestSort(groupId);
		        
		        // 2. 그룹 이름 조회
		        groupName = btMap.getGroupName(groupId);
		        
		        // 3. 그룹 인원 수 조회
		        groupMemberCount = btMap.getGroupMemberCount(groupId);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}

	@Override
	public int groupSearch(String sessionId) {
		int groupId = btMap.groupSearch(sessionId);
		return groupId;
	}

	@Override
	public GroupInfoDTO contentSearch(int groupId, String content) {
		List<PostListDTO> posts = new ArrayList<>();
	    String groupName = "";
	    int groupMemberCount = 0;

	    try {
	        // 1. 게시글 목록 조회
	        posts = btMap.contentSearch(groupId, content);
	        
	        // 2. 그룹 이름 조회
	        groupName = btMap.getGroupName(groupId);
	        
	        // 3. 그룹 인원 수 조회
	        groupMemberCount = btMap.getGroupMemberCount(groupId);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}

	@Override
	public GroupInfoDTO titleSearch(int groupId, String content) {
		List<PostListDTO> posts = new ArrayList<>();
	    String groupName = "";
	    int groupMemberCount = 0;

	    try {
	        // 1. 게시글 목록 조회
	        posts = btMap.titleSearch(groupId, content);
	        
	        // 2. 그룹 이름 조회
	        groupName = btMap.getGroupName(groupId);
	        
	        // 3. 그룹 인원 수 조회
	        groupMemberCount = btMap.getGroupMemberCount(groupId);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}
	
	@Override
	public GroupInfoDTO getSortedOrFilteredBoards(int groupId, String searchType, String query, String sort) {
	    List<PostListDTO> posts = new ArrayList<>();
	    String groupName = "";
	    int groupMemberCount = 0;

	    try {
	        // 검색 적용 (searchType과 query가 있을 경우)
	        if ("content".equalsIgnoreCase(searchType)) {
	            posts = btMap.contentSearch(groupId, query);
	        } else if ("title".equalsIgnoreCase(searchType)) {
	            posts = btMap.titleSearch(groupId, query);
	        } else {
	            posts = btMap.getAllPost(groupId); // groupId 기반으로 전체 게시글 조회
	        }

	        // 정렬 적용 (sort가 있을 경우)
	        if ("hit".equalsIgnoreCase(sort)) {
	            posts = btMap.maxHit(groupId);
	        } else if ("newest".equalsIgnoreCase(sort)) {
	            posts = btMap.newestSort(groupId);
	        } else if ("oldest".equalsIgnoreCase(sort)) {
	            posts = btMap.oldestSort(groupId);
	        }

	        // 그룹 정보 조회
	        groupName = btMap.getGroupName(groupId);
	        groupMemberCount = btMap.getGroupMemberCount(groupId);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new GroupInfoDTO(groupName, groupMemberCount, posts);
	}

}
