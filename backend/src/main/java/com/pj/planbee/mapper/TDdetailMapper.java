package com.pj.planbee.mapper;

import java.util.List;
import java.util.Map;

import com.pj.planbee.dto.TodoListDTO;

public interface TDdetailMapper {
	
	//todolist 관련 기능
	public List<TodoListDTO> getList();
	public List<TodoListDTO> getTodo();
	public int todoWrite();
	public int todoUpdate();
	public int todoModify();
	public int todoDel();
	
	
	

}
