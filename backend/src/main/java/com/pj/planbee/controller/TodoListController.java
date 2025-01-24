package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.service.TodoListService;

@RestController
@CrossOrigin
public class TodoListController {
	@Autowired TodoListService ts;
	
	List<TodoListDTO> list = new ArrayList<TodoListDTO>();
	public List<TodoListDTO>artificialData() {
		//임시데이터 생성
		for(int i = list.size(); i<3; i++) {
			 TodoListDTO dto = new TodoListDTO(); 
			 dto.setID("팥붕"+i);
			 dto.setToDoDate("20250124");
			 dto.setToDoID(i+1);
			 dto.setTodoMemo("팥붕어빵먹기, 슈붕어빵 먹기");
			 dto.setToDoProgress(0.5);
			 list.add(dto);
		}
		System.out.println("list: "+ list.get(0).getID());
		
	return list;
		
	}
	

	@GetMapping(value="todolist", produces="application/json; charset=utf-8")
	public List<TodoListDTO> getList(){ //전체 투두리스트 가져오는 기능, 일단 써둠
		return artificialData();
	}
	
	@GetMapping(value="todolist/{date}", produces="application/json; charset=utf-8")
	public List<TodoListDTO> getTodo(int todoId){ //하루의 투두리스트를 가져오는 기능
		List<TodoListDTO> list = new ArrayList<TodoListDTO>();
		list = ts.getTodo();
		return list;
	}
	
	
	@PostMapping(value="todolist/write{}", produces="application/json; charset=utf-8")
	public int todoWrite() { //투두리스트 작성하는 기능
		
		return ts.todoWrite();
	}
	
	@PutMapping(value="todolist/update{todoNo}", produces="application/json; charset=utf-8")
	public int todoUpdate() { //투두리스트 완료내역 업데이트 하는 기능
		
		return ts.todoUpdate(); 
	}
	public int todoModify() { //투두리스트 수정하는 기능, 시간지나면 수정불가
		return ts.todoModify();
	}
	@DeleteMapping(value="todolist/del{todoNo}", produces="application/json; charset=utf-8")
	public int todoDel() { //투두리스트 삭제하는 기능, 시간 지나면 삭제 불가
		
		return ts.todoDel();
	}
	
	@GetMapping(value="todolist/progress{today}", produces="application/json; charest=utf-8")
	public double todoProgress() {
		return ts.todoProgress();
	}
	
	@GetMapping(value="todolist/getMemo{date}", produces="application/json; chareset=utf-8")
	public Map<String, String> getMemo(){//메모를 가져오는 기능
		Map<String, String> memo = new HashMap<String, String>();
		
		return memo;
	}
	
	@PostMapping(value="todolist/memoWrite{}", produces="application/json; charset=utf-8")
	public int memoWrite() { //메모를 작성하는 기능
		
		return 1;
	}
	
	@PutMapping(value="todolist/memoUpdate{}", produces="application/json; charset=utf-8")
	public int memoUpdate() { //메모를 수정하는 기능
		return 1;
	}
	@DeleteMapping(value="todolist/memoDel{}", produces="application/json; charset=utf-8")
	public int memoDel() { //메모를 삭제하는 기능
		return 1;
	}
	
	@GetMapping(value="todolist/progress", produces="application/json; charset=utf-8")
	public double progress() {
		
		return 0.5;
	}
	
	
	
	
	
	

}
