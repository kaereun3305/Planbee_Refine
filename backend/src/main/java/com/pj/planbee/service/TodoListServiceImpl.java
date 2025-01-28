package com.pj.planbee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.mapper.TodoListMapper;
import com.pj.planbee.tddetail.mapper.TDdetailMapper;


@Service
public class TodoListServiceImpl implements TodoListService {
@Autowired TDdetailMapper tdMap;


@Override
public List<TodoListDTO> getList() {
	List<TodoListDTO> list = new ArrayList<TodoListDTO>();
	try {
		list = tdMap.getList();
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	return list;
}
@Override
public List<TodoListDTO> getTodo(int todoId) {//하루의 투두리스트를 가져오는 기능
	List<TodoListDTO> list = new ArrayList<TodoListDTO>();
	try {
		list = tdMap.getTodo(todoId);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}


}
