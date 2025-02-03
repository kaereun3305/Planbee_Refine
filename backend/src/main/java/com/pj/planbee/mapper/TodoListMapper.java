package com.pj.planbee.mapper;

import java.util.List;

import com.pj.planbee.dto.TodoListDTO;

public interface TodoListMapper {
	//memo관련 기능
		public List<TodoListDTO> getMemo(int todoId);
		public int memoWrite();
		public int memoUpdate();
		public int memoDel();
		
		
		//진척도 업데이트 하는 기능
		public double todoProgress();
		
}
