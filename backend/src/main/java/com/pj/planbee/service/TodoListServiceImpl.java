package com.pj.planbee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.mapper.TDdetailMapper;
import com.pj.planbee.mapper.TodoListMapper;


@Service
public class TodoListServiceImpl implements TodoListService {
@Autowired TodoListMapper tlMap;
@Autowired TDdetailMapper tdMap;

@Override
public List<TodoListDTO> getTodo() {//하루의 투두리스트를 가져오는 기능
	List<TodoListDTO> list = new ArrayList<TodoListDTO>();
	try {
		list = tdMap.getTodo();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@Override
public int todoWrite() {
	try {
		tdMap.todoWrite();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return 0;
}
@Override
public int todoUpdate() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int todoModify() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int todoDel() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public double todoProgress() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public Map<String, String> getMemo() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public int memoWrite() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int memoUpdate() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int memoDel() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public List<TodoListDTO> getProgress() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public int PGUpdate() {
	// TODO Auto-generated method stub
	return 0;
}

	
	
}
