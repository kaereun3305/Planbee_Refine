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
@RequestMapping("/todolist") //�닚�꽌 諛붽퓞
@CrossOrigin(origins="*")
public class TodoListController {
	@Autowired TodoListService ts;
	//�븵�쑝濡� ���뼱�굹媛� 二쇱꽍�뱾�� 臾몄젣�젏�씠 �엳�뒗 寃쎌슦
	//�븘臾대쭚 �뾾�뒗 寃쎌슦 �젣��濡� �떎�뻾�릺�뒗 寃�
	

//	@GetMapping(value="todolist", produces="application/json; charset=utf-8")
//	public List<TDdetailDTO> getList(){ //�뀒�뒪�듃�슜 
//		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
//		list = ts.getList();
//		return list; 
//
//	}
	@PostMapping(value="/makeSession", produces = "application/json; charset=utf-8")//�꽭�뀡 �꽕�젙 硫붿냼�뱶
	public int session(HttpSession se) { 
		se.setAttribute("sessionId", "팥붕");
		return 1;
		
	}
	
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8") // 로그인 상태 확인
    public int checkSession(HttpSession session) { //세션체크
        return (session.getAttribute("sessionId") != null) ? 1 : 0; // 1: 로그인된 상태, 0: 로그인되지 않음
    }
	
	
	@GetMapping(value="/getTodo/{tdDate}", produces="application/json; charset=utf-8")
	public List<TDdetailDTO> getToday(@PathVariable String tdDate, HttpSession se){ //�삤�뒛�쓽 �닾�몢由ъ뒪�듃瑜� 媛��졇�삤�뒗 湲곕뒫
		//input媛�: yyMMdd �삎�떇�쓽 �궇吏� �뜲�씠�꽣
		//sessionId �엫�쓽吏��젙�븿, 異뷀썑 �쟾�뿭�뿉�꽌 �꽭�뀡 吏��젙�릺硫� �꽭�뀡�뙆�듃�뒗 吏��썙�룄 �맆�벏
		
		String sessionId = (String) se.getAttribute("sessionId");
		int todoId;
		int result = ts.checkRow(tdDate, sessionId); //�뿴 �엳�뒗吏� 李얠븘�삤湲�,
		//System.out.println("result" + result);
		
		if(result ==0) {
			ts.inputRow(tdDate, sessionId); //
			todoId = ts.tdIdSearch(tdDate, sessionId);//異붽��븳 �썑 todoId 怨좎쑀踰덊샇瑜� 諛섑솚�븯�룄濡� �꽕�젙
		}else {
			todoId = result;
		}
		//System.out.println("ctrl:" + todoId);
		List<TDdetailDTO> list = new ArrayList<TDdetailDTO>();
		list = ts.getTodo(todoId);
		return list;
	}
	//�젙�긽�옉�룞�맖
	
	@PostMapping(value="/write/{tdDate}", produces="application/json; charset=utf-8")
	@ResponseBody
	public int todoWrite(@RequestBody TDdetailDTO dto, @PathVariable String tdDate, HttpSession se) { //�닾�몢由ъ뒪�듃 �옉�꽦�븯�뒗 湲곕뒫
		//input媛�: �븷 �씪�뿉 ���븳 String tododetail�궡�슜, yyMMdd �삎�떇�쓽 �궇吏�
		//tdId�뒗 sessionId �씠�슜
		
		String sessionId = (String) se.getAttribute("sessionId");
		
		//sessionId�� tdDate瑜� �씠�슜�빐�꽌 tdId瑜� 媛��졇�삤�뒗 硫붿냼�뱶
		int tdId = ts.tdIdSearch(tdDate, sessionId);
//		System.out.println("ctrl,todo:"+ dto.getTdDetailTime());
//		System.out.println(dto.getTdId());
		dto.setTdId(tdId);
		return ts.todoWrite(dto); //�꽭�뀡�븘�씠�뵒 �꽔�쓣 �븘�슂 �뾾�쓬, �쐞�뿉�꽌 �꽭�뀡媛믪쓣 �넻�빐 tdId瑜� 諛섑솚�빐�샂
	}
	//�젙�긽�옉�룞�맖
	
	@PutMapping(value="/state", produces="application/json; charset=utf-8")
	public double updateState(@RequestBody TDdetailDTO dto, HttpSession se) { //�닾�몢由ъ뒪�듃 �셿猷뚮궡�뿭 �뾽�뜲�씠�듃 �븯�뒗 泥댄겕諛뺤뒪
		//input媛�: body�뿉�꽌 tdDetailId, tdDetailState, tdId
		
		ts.updateState(dto.getTdDetailId(), dto.isTdDetailState()); 
		System.out.println("tdDetailId: " + dto.getTdDetailId());
		System.out.println("state:" + dto.isTdDetailState());
		//String sessionId = (String) se.getAttribute("sessionId");
		//String tdDate = ts.dateSearch(dto.getTdId()); //tdId湲곕컲�쑝濡� tdDate媛��졇�샂
		//System.out.println("todoId�궇吏�"+ dto.getTdId());
		//postman�엯�젰媛믪쓣 dto�씠由꾧낵 留욎떠以섏빞�븿!!!!
		double progress = ts.todoProgress(dto.getTdId()); //�뾽�뜲�씠�듃 �븯硫� �옄�룞�쑝濡� �쁽�옱 吏꾩쿃�룄瑜� 媛��졇�삤�뒗 湲곕뒫
		ts.regiProgress(dto.getTdId(), progress); //�뾽�뜲�씠�듃�맂 吏꾩쿃�룄瑜� ���옣�븿
		return progress; 
	}
	@PutMapping(value="/modify", produces="application/json; charset=utf-8")
	public int todoModify(@RequestBody TDdetailDTO dto) { //�닾�몢由ъ뒪�듃 �닔�젙�븯�뒗 湲곕뒫, �떆媛꾩��굹硫� �닔�젙遺덇��뒗 �봽濡좏듃�뿉�꽌 �빐二쇱떆湲�..
	//input 媛�: detailDTO, 蹂��룞�뾾�뒗 寃쎌슦�뿉�뒗 湲곗〈媛믪씠 �엯�젰�릺�룄濡� �빐�빞�븿
		//System.out.println("boolean 媛� :" + dto.isTdDetailState());
		return ts.todoModify(dto);
	}
	//�젙�긽 �옉�룞�셿猷�, �궡�슜�닔�젙�쐞�븳 湲곕뒫 
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
	//�젙�긽 �옉�룞�맖
	
	@PutMapping(value="/memoWrite", produces="application/json; charset=utf-8")
	public int memoWrite(@RequestBody TodoListDTO listDto) { //硫붾え瑜� �옉�꽦�븯怨� �닔�젙�븯�뒗 湲곕뒫
	
		//�뿴�쓣 誘몃━ 留뚮뱾�뼱�몢�젮怨� �븯誘�濡� 硫붾え�쓽 �옉�꽦怨� �닔�젙�쓣 紐⑤몢 �씠 寃껋쓣 �궗�슜�븯硫� �맖
		//System.out.println("controller: "+ listDto.getTdMemo());
		return ts.memoWrite(listDto);
	}
	//�젙�긽 �옉�룞�맖
	
//硫붾え�뵜由ы듃 湲곕뒫�� �궗�슜�븯吏� �븡湲곕줈 �삊�쓽�븿-> 硫붾え �닔�젙湲곕뒫�쓣 �궗�슜�빐�꽌 硫붾え留� ""�쑝濡� 諛붽씀�뒗 寃껋쑝濡�
	@DeleteMapping(value="/memoDel/{tdDate}", produces="application/json; charset=utf-8")
	public int memoDel(@PathVariable String tdDate, HttpSession se) { //硫붾え瑜� �궘�젣�븯�뒗 湲곕뒫
	//input媛�: yyMMdd�삎�떇�쓽 String�궇吏�	
		
		String sessionId = (String) se.getAttribute("sessionId");
		int tdId = ts.tdIdSearch(tdDate, sessionId); //td怨좎쑀Id濡� 蹂��솚
		return ts.memoDel(tdId);
	}
	//�젙�긽 �옉�룞�맖
	
	@GetMapping(value="/progress/{tdDate}", produces="application/json; charset=utf-8")
	public double getProgress(@PathVariable String tdDate, HttpSession se) { //吏꾩쿃�룄 �옖�뜑留곹븯�뒗 湲곕뒫
	//input媛�: yyMMdd�삎�떇�쓽 String�궇吏�
		//250206怨� 媛숈� �궇吏쒓컪�쓣 String�쑝濡� �엯�젰�븯怨� �꽭�뀡�븘�씠�뵒 媛믪쓣 諛쏆븘�꽌 td怨좎쑀Id濡� 蹂��솚
		
		String sessionId = (String) se.getAttribute("sessionId");
		System.out.println(sessionId);
		int tdId = ts.tdIdSearch(tdDate,sessionId);
		System.out.println(tdId);
		//tdId�뿉 ���븳 吏꾩쿃�룄瑜� 媛��졇�샂
		//異붽� 肄붾뱶-> progress���옣�븯�뒗 湲곕뒫
		double progress = ts.todoProgress(tdId);
		ts.regiProgress(tdId, progress);
		
		return ts.todoProgress(tdId);
	}
	//�젙�긽 �옉�룞�븿
	
	
	
	
	
	
	

}
