package com.pj.planbee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.mapper.TDdetailMapper;
import com.pj.planbee.mapper.TodoListMapper;


@Service
public class TodoListServiceImpl implements TodoListService {
@Autowired TDdetailMapper tdMap;

@Override
public List<TDdetailDTO> getList() { //전체 투두리스트 가져오는 기능, 테스트용
	List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
	list = tdMap.getList();
	//System.out.println("service 실행: "+ list);
	return list;
}

@Override
public List<TDdetailDTO> getTodo(int todoId) { //하루의 투두리스트를 가져오는 기능, todolist고유 아이디로 가져옴
	List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
	list = tdMap.getTodo(todoId);
	return list;
}

@Override
public int todoWrite(TDdetailDTO dto) { //투두리스트 작성하는 기능, 성공시 결과값은 1
	int result =0;
	try {
		result = tdMap.todoWrite(dto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

@Override
public int updateState(int ToDoDetailID, String state) {  //투두리스트 작업상태 업데이트 하는 기능
	//완료시 True혹은 t, 기본값은 False혹은 f
	int result =0;
	try {
		result = tdMap.updateState(ToDoDetailID, state);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

@Override
public int todoModify(TDdetailDTO dto) { //투두리스트 자체 수정기능
	int result = 0;
	try {
		result = tdMap.todoModify(dto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

@Override
public int todoDel(int ToDoDetailID) { //투두리스트 한 개 삭제하는 기능
	int result = 0;
	try {
		result = tdMap.todoDel(ToDoDetailID);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

@Override
public double todoProgress(int todoId) {
	double progress = 0.0;
	try {
		double complete = tdMap.getComplete(todoId); //완료한 것만 가져옴
		double total = tdMap.getTotal(todoId); //전체 리스트를 가져옴
		progress = complete/total;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return progress;
}








}
