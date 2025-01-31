package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.service.TodoListService;

@RestController
@CrossOrigin
public class TodoListController {
	@Autowired TodoListService ts;
	//앞으로 튀어나간 주석들은 문제점이 있는 경우
	//아무말 없는 경우 제대로 실행되는 것
	

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
	@ResponseBody
	public int todoWrite(@RequestBody TDdetailDTO dto) { //투두리스트 작성하는 기능
		
//		System.out.println("ctrl,todo:"+ dto.getTdDetail());
//		System.out.println(dto.getTdId());
		return ts.todoWrite(dto);
	}
	
	@PutMapping(value="todolist/update/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int updateState(@PathVariable int ToDoDetailID, String state) { //투두리스트 완료내역 업데이트 하는 기능
		int result = ts.updateState(ToDoDetailID, state);
		//t/f를 업데이트하면 자동으로 하루의 진척도가 업데이트되어야 하지 않을까?
		return result; 
	}
	@PutMapping(value="todolist/modify/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int todoModify(@PathVariable int ToDoDetailID, @RequestBody TDdetailDTO dto) { //투두리스트 수정하는 기능, 시간지나면 수정불가는 프론트에서 해주시길..
//DTO로 지정해서 했으나 parameter를 못찾는 문제가 발생함
		System.out.println("detail내용:" + dto.getTdDetail());
		return ts.todoModify(ToDoDetailID, dto);
	}
	
	@DeleteMapping(value="todolist/del/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int todoDel(@PathVariable int ToDoDetailID) { //투두리스트 삭제하는 기능, 시간 지나면 삭제 불가
		
		return ts.todoDel(ToDoDetailID);
	}
	
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
