package com.pj.planbee.service;

import java.util.List;
import java.util.Map;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;

public interface TodoListService {

	//todolist에 대한 기본 기능
	public List<TDdetailDTO> getList(); //전체의 투두리스트 가져오는 기능 테스트용
	public List<TDdetailDTO> getTodo(int todoId); //하루의 투두리스트 가져오는 기능
	public int todoWrite(TDdetailDTO dto); //투두리스트 입력하는 기능
	public int updateState(int ToDoDetailID, String state); //완료상황 t/f 업데이트하는 기능
	public int todoModify(TDdetailDTO dto); //투두리스트 자체를 수정하는 기능
	public int todoDel(int ToDoDetailID); //투두리스트 한 개 삭제하는 기능
	public double todoProgress(int todoId);
}