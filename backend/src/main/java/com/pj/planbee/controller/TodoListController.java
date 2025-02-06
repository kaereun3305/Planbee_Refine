package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.service.TodoListService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/todolist")
public class TodoListController {
	@Autowired TodoListService ts;
	//앞으로 튀어나간 주석들은 문제점이 있는 경우
	//아무말 없는 경우 제대로 실행되는 것
	

//	@GetMapping(value="todolist", produces="application/json; charset=utf-8")
//	public List<TDdetailDTO> getList(){ //테스트용 
//		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
//		list = ts.getList();
//		return list; 
//
//	}
	public void session(HttpSession se) { //세션일단 설정 추후 삭제예정
		se.setAttribute("sessionId", "팥붕");
		
		
	}
	
	@GetMapping(value="/{tdDate}", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getTodo(@PathVariable String tdDate, HttpSession se){ //하루의 투두리스트를 가져오는 기능
		//sessionId 임의지정함, 추후 전역에서 세션 지정되면 세션파트는 지워도 될듯
		session(se);
		String sessionId = (String) se.getAttribute("sessionId");
		int todoId = ts.inputRow(tdDate, sessionId); //테이블에 열이 있는지 먼저 확인 후 없으면 추가
		//추가한 후 todoId 고유번호를 반환하도록 설정
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getTodo(todoId);
		return list;
	}
	
	
	@PostMapping(value="/write", produces="application/json; charset=utf-8")
	@ResponseBody
	public int todoWrite(@RequestBody TDdetailDTO dto) { //투두리스트 작성하는 기능
		
//		System.out.println("ctrl,todo:"+ dto.getTdDetail());
//		System.out.println(dto.getTdId());
		return ts.todoWrite(dto);
	}
	
	@PutMapping(value="/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int updateState(@PathVariable int ToDoDetailID, String state) { //투두리스트 완료내역 업데이트 하는 기능
		int result = ts.updateState(ToDoDetailID, state);
		//t/f를 업데이트하면 자동으로 하루의 진척도가 업데이트되어야 하지 않을까?
		return result; 
	}
	@PutMapping(value="/modify", produces="application/json; charset=utf-8")
	public int todoModify(@RequestBody TDdetailDTO dto) { //투두리스트 수정하는 기능, 시간지나면 수정불가는 프론트에서 해주시길..
		//System.out.println("detail내용:" + dto.getTdDetail());
		return ts.todoModify(dto);
	}
	
	@DeleteMapping(value="delete/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int todoDel(@PathVariable int ToDoDetailID) { //투두리스트 삭제하는 기능, 시간 지나면 삭제 불가
		
		return ts.todoDel(ToDoDetailID);
	}
	
	@GetMapping(value="/progress/{todoId}", produces="application/json; charest=utf-8")
	public double todoProgress(@PathVariable int todoId) { //하루의 투두리스트 진척도를 업데이트 하는 기능, ser에서 연산
		return ts.todoProgress(todoId);
	}
	  
	@GetMapping(value="/getMemo/{todoId}", produces="application/json; chareset=utf-8")
	public String getMemo(@PathVariable int todoId){ //하루의 메모를 가져오는 기능, 메모 한개이므로 String으로 받았음
		List<TodoListDTO> list= new ArrayList<TodoListDTO>();
		list = ts.getMemo(todoId);
		System.out.println("ctrl: "+ list.get(0).getTdMemo());
		return list.get(0).getTdMemo();
	}
	
	@PutMapping(value="/memoWrite", produces="application/json; charset=utf-8")
	public int memoWrite(@RequestBody TodoListDTO listDto) { //메모를 작성하고 수정하는 기능
		//열을 미리 만들어두려고 하므로 메모의 작성과 수정을 모두 이 것을 사용하면 됨
		//System.out.println("controller: "+ listDto.getTdMemo());
		return ts.memoWrite(listDto);
	}
	

	@DeleteMapping(value="/memoDel", produces="application/json; charset=utf-8")
	public int memoDel(@RequestBody TodoListDTO listDto) { //메모를 삭제하는 기능
		return ts.memoDel(listDto);
	}
	
	@GetMapping(value="todolist/progress", produces="application/json; charset=utf-8")
	public double progress() {
		
		return 0.5;
	}
	
	
	
	
	
	

}
