package com.pj.planbee.service;

import java.util.List;
import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;

public interface TodoListService {

	//todolist 켰을때 열이 있는지 확인하고 한 열을 만드는 기능
	public int inputRow(String tdDate, String sessionId);
	
	//todolist에 대한 기본 기능
		//public List<TDdetailDTO> getList(); //전체의 투두리스트 가져오는 기능 테스트용
	public List<TDdetailDTO> getTodo(int tdId); //하루의 투두리스트 가져오는 기능
	public int todoWrite(TDdetailDTO dto); //투두리스트 입력하는 기능
	public int updateState(int ToDoDetailID, String state); //완료상황 t/f 업데이트하는 기능
	public int todoModify(TDdetailDTO dto); //투두리스트 자체를 수정하는 기능
	public int todoDel(int ToDoDetailID); //투두리스트 한 개 삭제하는 기능
	public double todoProgress(int todoId);
	
	
	//todolist의 memo에 대한 기능들
	public List<TodoListDTO> getMemo(int todoId); //하루의 메모 가져오는 기능, 하나 밖에 없으므로 string으로 받아옴
	public int memoWrite(TodoListDTO listDto);//메모를 작성하고 수정하는 기능
	public int memoDel(TodoListDTO listDto); //메모를 삭제하는 기능

}