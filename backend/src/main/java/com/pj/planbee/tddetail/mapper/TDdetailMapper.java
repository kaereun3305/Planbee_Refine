package com.pj.planbee.tddetail.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pj.planbee.dto.TodoListDTO;
@Mapper
public interface TDdetailMapper {
	
	//todolist 관련 기능
	public List<TodoListDTO> getList();
	public List<TodoListDTO> getTodo(int todoId);
	

}
