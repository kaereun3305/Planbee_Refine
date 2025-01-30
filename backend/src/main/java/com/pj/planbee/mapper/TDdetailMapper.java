package com.pj.planbee.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
@Mapper
public interface TDdetailMapper {
	
	public List<TDdetailDTO> getList();
	public List<TDdetailDTO> getTodo(int todoId);
	public int todoWrite(TDdetailDTO dto);
	

}
