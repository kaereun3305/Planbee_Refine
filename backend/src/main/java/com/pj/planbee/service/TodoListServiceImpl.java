package com.pj.planbee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.mapper.TDdetailMapper;
import com.pj.planbee.mapper.TodoListMapper;


@Service
public class TodoListServiceImpl implements TodoListService {
@Autowired TDdetailMapper tdMap;
@Autowired TodoListMapper tlMap;

public void inputRow() { //작업 진행중
	//오늘 날짜와 일치하는 탭이 있으면 try catch를 실행한다.
	String userId= "팥붕"; //세션아이디 쓰기 전에 잠깐 씁니다
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todayStr = today.format(form); //오늘 날짜를 위 형식으로 변환
		List <String> list= tlMap.getDate(); //todolist table에서 모든 날짜를 가져옴
		System.out.println("list: "+ list.size());
		if(list.size()==0) { //리스트가 아예 없는 경우
			tlMap.dateWrite(userId);
		}else {
			for(int i =0; i<list.size(); i++) {
				if (list.get(i).equals(todayStr)) { //리스트 중에 todayStr형식으로 된 같은거 찾으면
					String date = list.get(i);
					tlMap.dateWrite(userId);
				}else {
					
				}
			}
		}
}

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

@Override //투두리스트 작성기능에 if 문 사용해서 todolist 값이 없으면 입력하는 기능을 만들어야함! 
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

@Override
public List<TodoListDTO> getMemo(int todoId) {
	List<TodoListDTO> list = new ArrayList<TodoListDTO>();
	try {
		list = tlMap.getMemo(todoId);
		//System.out.println("ser:"+ list);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@Override
public int memoWrite(TodoListDTO listDto) {
	int result = 0;
	try {
		result = tlMap.memoWrite(listDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

@Override
public int memoDel(TodoListDTO listDto) {
	int result =0;
	
	try {
		result = tlMap.memoDel(listDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}


}
