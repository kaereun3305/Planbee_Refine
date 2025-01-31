package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.service.TodoListService;

@RestController
@CrossOrigin
public class TodoListController {
	@Autowired TodoListService ts;
	

	@GetMapping(value="todolist", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getList(){
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getList();
		return list; //전체 투두리스트 

	}
	
	@GetMapping(value="todolist/{todoId}", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getTodo(@PathVariable int todoId){ //하루의 투두리스트를 가져오는 기능
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getTodo(todoId);
		return list;
	}
	
	
	@PostMapping(value="todolist/write", produces="application/json; charset=utf-8")
	public int todoWrite(@PathVariable TDdetailDTO dto) { //투두리스트 작성하는 기능
		
		return ts.todoWrite(dto);
	}
	
//	@PutMapping(value="todolist/update{todoNo}", produces="application/json; charset=utf-8")
//	public int todoUpdate(TDdetailDTO) { //투두리스트 완료내역 업데이트 하는 기능
//		
//		return ts.todoUpdate(); 
//	}
//	public int todoModify() { //투두리스트 수정하는 기능, 시간지나면 수정불가
//		return ts.todoModify();
//	}
//	@DeleteMapping(value="todolist/del{todoNo}", produces="application/json; charset=utf-8")
//	public int todoDel() { //투두리스트 삭제하는 기능, 시간 지나면 삭제 불가
//		
//		return ts.todoDel();
//	}
//	
//	@GetMapping(value="todolist/progress{today}", produces="application/json; charest=utf-8")
//	public double todoProgress() {
//		return ts.todoProgress();
//	}
//	
//	@GetMapping(value="todolist/getMemo{date}", produces="application/json; chareset=utf-8")
//	public Map<String, String> getMemo(){//메모를 가져오는 기능
//		Map<String, String> memo = new HashMap<String, String>();
//		
//		return memo;
//	}
//	
//	@PostMapping(value="todolist/memoWrite{}", produces="application/json; charset=utf-8")
//	public int memoWrite() { //메모를 작성하는 기능
//		
//		return 1;
//	}
//	
//	@PutMapping(value="todolist/memoUpdate{}", produces="application/json; charset=utf-8")
//	public int memoUpdate() { //메모를 수정하는 기능
//		return 1;
//	}
//	@DeleteMapping(value="todolist/memoDel{}", produces="application/json; charset=utf-8")
//	public int memoDel() { //메모를 삭제하는 기능
//		return 1;
//	}
//	
//	@GetMapping(value="todolist/progress", produces="application/json; charset=utf-8")
//	public double progress() {
//		
//		return 0.5;
//	}
//	
	
	
	
	
	

}
