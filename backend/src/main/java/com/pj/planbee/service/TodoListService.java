package com.pj.planbee.service;

import java.util.List;
import java.util.Map;

import com.pj.planbee.dto.TodoListDTO;

public interface TodoListService {

	//todolist에 대한 기본 기능
	public List<TodoListDTO> getTodo(); //하루의 투두리스트 가져오는 기능
	public int todoWrite(); //투두리스트 작성하는 기능
	public int todoUpdate(); //투두리트 완료내역 업데이트 기능
	public int todoModify(); //투두리스트 수정하는기능
	public int todoDel(); //투두리스트 삭제하는 기능
	public double todoProgress(); //투두리스트 진척율 계산하는 기능
	
	//todolist의 메모에 대한 기능
	public Map<String, String> getMemo(); //하루의 메모가져오는 기능
	public int memoWrite(); //메모 작성하는 기능
	public int memoUpdate(); //메모 수정하는 기능
	public int memoDel(); //메모를 삭제하는 기능
	
	//진척도관련 기능
	public List<TodoListDTO> getProgress();//진척도 가져오는 기능
	public int PGUpdate();//진척도 업데이트 하는 기능
}
