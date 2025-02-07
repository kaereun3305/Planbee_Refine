package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/todolist") //순서 바꿈
@CrossOrigin(origins="*")
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
		int todoId = ts.inputRow(tdDate, sessionId); //오늘과 내일의 열이 없으면 입력하고, 있으면 오늘의 tdId 반환해주는 메소드
		//추가한 후 todoId 고유번호를 반환하도록 설정
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getTodo(todoId);
		return list;
	}
	
	
	@PostMapping(value="/write", produces="application/json; charset=utf-8")
	@ResponseBody
	public int todoWrite(@RequestBody TDdetailDTO dto, HttpSession se) { //투두리스트 작성하는 기능
		//tdId는 sessionId 이용
		
//		System.out.println("ctrl,todo:"+ dto.getTdDetail());
//		System.out.println(dto.getTdId());
		return ts.todoWrite(dto); //이후 세션아이디 넣어야함
	}
	
	@PutMapping(value="/{ToDoDetailID}", produces="application/json; charset=utf-8")
	public int updateState(@PathVariable int ToDoDetailID, String state, HttpSession se) { //투두리스트 완료내역 업데이트 하는 체크박스
		int result = ts.updateState(ToDoDetailID, state);
		
		String sessionId = (String) se.getAttribute("sessionId");
		//int tdId = ts.tdIdSearch(tdDate,sessionId);
//업데이트 하면 자동으로 현재 진척도를 가져오도록 수정할것
//현재 프론트에서 요청을 위해 입력하는 값이 무엇인지 확인이 필요함
		return result; 
	}
	@PutMapping(value="/modify", produces="application/json; charset=utf-8")
	public int todoModify(@RequestBody TDdetailDTO dto) { //투두리스트 수정하는 기능, 시간지나면 수정불가는 프론트에서 해주시길..
		//System.out.println("detail내용:" + dto.getTdDetail());
		return ts.todoModify(dto);
	}
	
	@DeleteMapping(value="delete/{tdDate}", produces="application/json; charset=utf-8")
	public int todoDel(@PathVariable int ToDoDetailID) { //투두리스트 삭제하는 기능, 시간 지나면 삭제 불가
		
		return ts.todoDel(ToDoDetailID);
	}
	
	@GetMapping(value="/progress/{tdId}", produces="application/json; charest=utf-8")
	public double getProgress(@PathVariable int tdId) { //하루의 투두리스트 진척도를 업데이트 하는 기능, ser에서 연산
		return ts.todoProgress(tdId); //진척도 업데이트시 자동으로 진척도 결과값 반환
	}
	  
	@GetMapping(value="/getMemo/{tdDate}", produces="application/json; chareset=utf-8")
	public String getMemo(@PathVariable String tdDate, HttpSession se){ //하루의 메모를 가져오는 기능, 메모 한개이므로 String으로 받았음
		String sessionId = (String) se.getAttribute("sessionId");
		int tdId = ts.tdIdSearch(tdDate, sessionId);
		
		List<TodoListDTO> list= new ArrayList<TodoListDTO>();
		list = ts.getMemo(tdId);
		System.out.println("ctrl: "+ list.get(0).getTdMemo());
		return list.get(0).getTdMemo();
	}
	
	@PutMapping(value="/memoWrite", produces="application/json; charset=utf-8")
	public int memoWrite(@RequestBody TodoListDTO listDto) { //메모를 작성하고 수정하는 기능
		//열을 미리 만들어두려고 하므로 메모의 작성과 수정을 모두 이 것을 사용하면 됨
		//System.out.println("controller: "+ listDto.getTdMemo());
		return ts.memoWrite(listDto);
	}
	

	@DeleteMapping(value="/memoDel/{tdDate}", produces="application/json; charset=utf-8")
	public int memoDel(@PathVariable String tdDate, HttpSession se) { //메모를 삭제하는 기능
		String sessionId = (String) se.getAttribute("sessionId");
		int tdId = ts.tdIdSearch(tdDate, sessionId); //td고유Id로 변환
		return ts.memoDel(tdId);
	}
	
	@GetMapping(value="/progress/{tdDate}", produces="application/json; charset=utf-8")
	public double getProgress(@PathVariable String tdDate, HttpSession se) { //진척도 랜더링하는 기능
		//250206과 같은 날짜값을 String으로 입력하고 세션아이디 값을 받아서 td고유Id로 변환
		String sessionId = (String) se.getAttribute("sessionId");
		int tdId = ts.tdIdSearch(tdDate,sessionId);
		//tdId에 대한 진척도를 가져옴
		return ts.todoProgress(tdId);
	}
	
	
	
	
	
	

}
