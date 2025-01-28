package com.pj.planbee.service;

import java.util.List;
import java.util.Map;

import com.pj.planbee.dto.TodoListDTO;

public interface TodoListService {

	//todolist에 대한 기본 기능
	public List<TodoListDTO> getList(); //전체의 투두리스트 가져오는 기능 테스트용
	public List<TodoListDTO> getTodo(int todoId); //하루의 투두리스트 가져오는 기능
	
}