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
@CrossOrigin(origins="*")
public class TodoListController {
	@Autowired TodoListService ts;
	//앞으로 튀어나간 주석들은 문제점이 있는 경우
	//아무말 없는 경우 제대로 실행되는 것
	

	@GetMapping(value="todolist", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getList(){
		//ts.inputRow();
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getList();
		return list; //전체 투두리스트 : 작업중인 api 일단 전체 가져오는 test기능으로 사용가능

	}
	
	@GetMapping(value="todolist/{todoId}", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getTodo(@PathVariable int todoId){ //하루의 투두리스트를 가져오는 기능, 고유아이디는 점진적으로 쌓임, 추후 고유아이디 날짜로 수정
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getTodo(todoId);
		return list;
	}
	
	
	@PostMapping(value="todolist", produces="application/json; charset=utf-8")
	@ResponseBody
	public int todoWrite(@RequestBody TDdetailDTO dto) { //투두리스트 작성하는 기능
		
//		System.out.println("ctrl,todo:"+ dto.getTdDetail());
//		System.out.println(dto.getTdId());
		return ts.todoWrite(dto);
	}
	
	@PutMapping(value="todolist/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int updateState(@PathVariable int ToDoDetailID, String state) { //투두리스트 완료내역 업데이트 하는 기능
		int result = ts.updateState(ToDoDetailID, state);
		//t/f를 업데이트하면 자동으로 하루의 진척도가 업데이트되어야 하지 않을까? 체크박스임
		return result; 
	}
	@PutMapping(value="todolist", produces="application/json; charset=utf-8")
	public int todoModify(@RequestBody TDdetailDTO dto) { //투두리스트 수정하는 기능, 시간지나면 수정불가는 프론트에서 해주시길..
		//System.out.println("detail내용:" + dto.getTdDetail());
		return ts.todoModify(dto);
	}
	
	@DeleteMapping(value="todolist/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int todoDel(@PathVariable int ToDoDetailID) { //투두리스트 삭제하는 기능, 시간 지나면 삭제 불가
		
		return ts.todoDel(ToDoDetailID);
	}
	
	@GetMapping(value="todolist/progress/{todoId}", produces="application/json; charest=utf-8")
	public double todoProgress(@PathVariable int todoId) { //하루의 투두리스트 진척도를 업데이트 하는 기능, ser에서 연산, 진척도 랜더링 직후 뜨도록 수정 예정 노터치
		return ts.todoProgress(todoId);
	}
	  
	@GetMapping(value="todolist/getMemo/{todoId}", produces="application/json; chareset=utf-8")
	public String getMemo(@PathVariable int todoId){ //하루의 메모를 가져오는 기능, 메모 한개이므로 String으로 받았음
		List<TodoListDTO> list= new ArrayList<TodoListDTO>();
		list = ts.getMemo(todoId);
		System.out.println("ctrl: "+ list.get(0).getTdMemo());
		return list.get(0).getTdMemo();
	}
	
	@PutMapping(value="todolist/memoWrite", produces="application/json; charset=utf-8")
	public int memoWrite(@RequestBody TodoListDTO listDto) { //메모를 작성하고 수정하는 기능
		//열을 미리 만들어두려고 하므로 메모의 작성과 수정을 모두 이 것을 사용하면 됨
		//System.out.println("controller: "+ listDto.getTdMemo());
		return ts.memoWrite(listDto);
	}
	

	@DeleteMapping(value="todolist/memoDel", produces="application/json; charset=utf-8")
	public int memoDel(@RequestBody TodoListDTO listDto) { //메모를 삭제하는 기능
		return ts.memoDel(listDto);
	}
	
//	@GetMapping(value="todolist/progress", produces="application/json; charset=utf-8")
//	public double progress() {
//		
//		return 0.5;
//	}
//	
	
	
	
	
	

}
