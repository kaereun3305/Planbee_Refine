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
	System.out.println("service 실행: "+ list);
	return list;
}

@Override
public List<TDdetailDTO> getTodo(int todoId) { //하루의 투두리스트를 가져오는 기능, todolist고유 아이디로 가져옴
	List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
	list = tdMap.getTodo(todoId);
	return list;
}

@Override
public int todoWrite(TDdetailDTO dto) {
	int result =0;
	try {
		result = tdMap.todoWrite(dto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}






}
