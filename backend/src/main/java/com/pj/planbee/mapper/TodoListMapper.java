package com.pj.planbee.mapper;

import java.util.List;

import com.pj.planbee.dto.TodoListDTO;

public interface TodoListMapper {
	
		
		public List<String> getDate();//날짜 가져오는 기능
		public int dateWrite(String userId); //날짜가 없는 경우 열에 입력하는 기능
		//memo관련 기능
		public List<TodoListDTO> getMemo(int todoId);
		public int memoWrite(TodoListDTO listDto);
		public int memoUpdate();
		public int memoDel(TodoListDTO listDto);
		
		
		//진척도 업데이트 하는 기능
		public double todoProgress();
		
}
