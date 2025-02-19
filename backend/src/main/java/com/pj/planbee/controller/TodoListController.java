package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.pj.planbee.dto.TodoListDTO.SubTodoListDTO;
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
	@PostMapping(value="/makeSession", produces = "application/json; charset=utf-8")//세션 설정 메소드
	public int session(HttpSession se) { 
		se.setAttribute("sessionId", "팥붕");
		return 1;
		
	}
	
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8") // 로그인 상태 확인
    public int checkSession(HttpSession session) { //세션체크
        return (session.getAttribute("sessionId") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
    }
	
	
	@GetMapping(value="/getTodo/{tdDate}", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getToday(@PathVariable String tdDate, HttpSession se){ //오늘의 투두리스트를 가져오는 기능
		//input값: yyMMdd 형식의 날짜 데이터
		//sessionId 임의지정함, 추후 전역에서 세션 지정되면 세션파트는 지워도 될듯
		
		String sessionId = (String) se.getAttribute("sessionId");
		int todoId;
		int result = ts.checkRow(tdDate, sessionId); //열 있는지 찾아오기,
		//System.out.println("result" + result);
		
		if(result ==0) {
			ts.inputRow(tdDate, sessionId); //
			todoId = ts.tdIdSearch(tdDate, sessionId);//추가한 후 todoId 고유번호를 반환하도록 설정
		}else {
			todoId = result;
		}
		//System.out.println("ctrl:" + todoId);
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getTodo(todoId);
		return list;
	}
	//정상작동됨
	
	@PostMapping(value="/write/{tdDate}", produces="application/json; charset=utf-8")
	@ResponseBody
	public int todoWrite(@RequestBody TDdetailDTO dto, @PathVariable String tdDate, HttpSession se) { //투두리스트 작성하는 기능
		//input값: 할 일에 대한 String tododetail내용, yyMMdd 형식의 날짜
		//tdId는 sessionId 이용
		
		String sessionId = (String) se.getAttribute("sessionId");
		
		//sessionId와 tdDate를 이용해서 tdId를 가져오는 메소드
		int tdId = ts.tdIdSearch(tdDate, sessionId);
//		System.out.println("ctrl,todo:"+ dto.getTdDetailTime());
//		System.out.println(dto.getTdId());
		dto.setTdId(tdId);
		return ts.todoWrite(dto); //세션아이디 넣을 필요 없음, 위에서 세션값을 통해 tdId를 반환해옴
	}
	//정상작동됨
	
	@PutMapping(value="/state", produces="application/json; charset=utf-8")
	public double updateState(@RequestBody TDdetailDTO dto, HttpSession se) { //투두리스트 완료내역 업데이트 하는 체크박스
		//input값: body에서 tdDetailId, tdDetailState, tdId
		
		ts.updateState(dto.getTdDetailId(), dto.isTdDetailState()); 
		System.out.println("tdDetailId: " + dto.getTdDetailId());
		System.out.println("state:" + dto.isTdDetailState());
		//String sessionId = (String) se.getAttribute("sessionId");
		//String tdDate = ts.dateSearch(dto.getTdId()); //tdId기반으로 tdDate가져옴
		//System.out.println("todoId날짜"+ dto.getTdId());
		//postman입력값을 dto이름과 맞춰줘야함!!!!
		double progress = ts.todoProgress(dto.getTdId()); //업데이트 하면 자동으로 현재 진척도를 가져오는 기능
		ts.regiProgress(dto.getTdId(), progress); //업데이트된 진척도를 저장함
		return progress; 
	}
	@PutMapping(value="/modify", produces="application/json; charset=utf-8")
	public int todoModify(@RequestBody TDdetailDTO dto) { //투두리스트 수정하는 기능, 시간지나면 수정불가는 프론트에서 해주시길..
	//input 값: detailDTO, 변동없는 경우에는 기존값이 입력되도록 해야함
		//System.out.println("boolean 값 :" + dto.isTdDetailState());
		return ts.todoModify(dto);
	}
	//정상 작동완료, 내용수정위한 기능 
	//

	
	//delMemo기능 삭제로 빈칸 만들어둠
	
	
	
	
	@Transactional
	@GetMapping(value="/getMemo/{tdDate}", produces="application/json; charset=utf-8")
	public List<SubTodoListDTO> getMemo(@PathVariable String tdDate, HttpSession se){ //하루의 메모를 가져오는 기능, 메모 한개이므로 String으로 받았음
	//input값: yyMMdd형식의 String날짜
		
		String sessionId = (String) se.getAttribute("sessionId");
		//System.out.println("ctrl:" + sessionId);
		int tdId = ts.tdIdSearch(tdDate, sessionId);
		System.out.println("Ctrl " + tdId);
		List<SubTodoListDTO> list = ts.getMemo(tdId);
		System.out.println(tdDate+"일 때 리스트 사이즈: "+ list.get(0).getTdMemo() );
		if(list.isEmpty()) { //만약 todolist에 해당날짜에 대한 열이 입력되어있지 않으면 inputRow를 실행함
			ts.inputRow(tdDate, sessionId);
			tdId = ts.tdIdSearch(tdDate, sessionId);
			list = ts.getMemo(tdId);
		}
		return list;
	}
	//정상 작동됨
	
	@PutMapping(value="/memoWrite", produces="application/json; charset=utf-8")
	public int memoWrite(@RequestBody TodoListDTO listDto) { //메모를 작성하고 수정하는 기능
	
		//열을 미리 만들어두려고 하므로 메모의 작성과 수정을 모두 이 것을 사용하면 됨
		//System.out.println("controller: "+ listDto.getTdMemo());
		return ts.memoWrite(listDto);
	}
	//정상 작동됨
	
//메모딜리트 기능은 사용하지 않기로 협의함-> 메모 수정기능을 사용해서 메모만 ""으로 바꾸는 것으로
	@DeleteMapping(value="/memoDel/{tdDate}", produces="application/json; charset=utf-8")
	public int memoDel(@PathVariable String tdDate, HttpSession se) { //메모를 삭제하는 기능
	//input값: yyMMdd형식의 String날짜	
		
		String sessionId = (String) se.getAttribute("sessionId");
		int tdId = ts.tdIdSearch(tdDate, sessionId); //td고유Id로 변환
		return ts.memoDel(tdId);
	}
	//정상 작동됨
	
	@GetMapping(value="/progress/{tdDate}", produces="application/json; charset=utf-8")
	public double getProgress(@PathVariable String tdDate, HttpSession se) { //진척도 랜더링하는 기능
	//input값: yyMMdd형식의 String날짜
		//250206과 같은 날짜값을 String으로 입력하고 세션아이디 값을 받아서 td고유Id로 변환
		
		String sessionId = (String) se.getAttribute("sessionId");
		System.out.println(sessionId);
		int tdId = ts.tdIdSearch(tdDate,sessionId);
		System.out.println(tdId);
		//tdId에 대한 진척도를 가져옴
		//추가 코드-> progress저장하는 기능
		double progress = ts.todoProgress(tdId);
		ts.regiProgress(tdId, progress);
		
		return ts.todoProgress(tdId);
	}
	//정상 작동함
	
	
	
	
	
	
	

}
