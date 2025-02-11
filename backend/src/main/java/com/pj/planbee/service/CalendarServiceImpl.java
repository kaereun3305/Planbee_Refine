package com.pj.planbee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TDstartDTO;
import com.pj.planbee.mapper.TodoListMapper;


@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired TodoListMapper tlMap;
	
	@Override
	public int getProgress(String calDate, String sessionId) {
		
		int tdId = tdIdSearch(calDate, sessionId);
		
		return tdId;
	}

	public int tdIdSearch(String tdDate, String sessionId) { //날짜와 아이디에 해당하는 tdId를 써치하는 메소드
		   List<TDstartDTO> dateId = tlMap.getDate(sessionId);
		   //System.out.println("service: " + sessionId);
		   int selectedtdId = 0;
		   for (int i =0; i<dateId.size(); i++) {//dateId 리스트를 순회하며,todayStr과 같은 날짜가 있는지 확인 
		      if(dateId.get(i).getTodo_date().equals(tdDate)) {
		         //리스트 중에 입력한 날짜와 같은 열, 세션아이디와 같은 값을 가진 열을 찾으면 그 고유번호를 반환함
		         selectedtdId = i+1; //for문 사용하여 index번화 반환하므로 1 더해줌
		      }
		   }
		   return selectedtdId;
		}
}
