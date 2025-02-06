package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.TDstartDTO;
import com.pj.planbee.dto.TodoListDTO;

public interface TodoListMapper {
	
		
		public List <TDstartDTO> getDate(String sessionId);//세션아이디에 맞는 날짜와 todo_id(고유번호) 가져오는 기능
		public int dateWrite(@Param("tdDate") String tdDate, @Param("sessionId") String userId); //날짜가 없는 경우 열에 입력하는 기능
		public int getLatest(); //가장 최신의 tdId를 가져오는 기능
		//memo관련 기능
		public List<TodoListDTO> getMemo(int todoId);
		public int memoWrite(TodoListDTO listDto);
		public int memoUpdate();
		public int memoDel(TodoListDTO listDto);
		
		
		//진척도 업데이트 하는 기능
		public double todoProgress();
		
}
