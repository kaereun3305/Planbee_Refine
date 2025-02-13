package com.pj.planbee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.CalendarDTO;
import com.pj.planbee.dto.TDstartDTO;
import com.pj.planbee.mapper.CalendarMapper;
import com.pj.planbee.mapper.TodoListMapper;


@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired TodoListMapper tlMap;
	@Autowired CalendarMapper calMap;
	
	@Override
	public int getProgress(String calDate, String sessionId) {
		
		int tdId = tdIdSearch(calDate, sessionId);
		
		return tdId;
	}

	public int tdIdSearch(String tdDate, String sessionId) { //날짜와 아이디에 해당하는 tdId를 써치하는 메소드
		   List<TDstartDTO> dateId = tlMap.getDate(sessionId);
		   System.out.println("service: "+dateId.get(3).getTodo_Id());
		   int selectedtdId = 0;
		   for (int i =0; i<dateId.size(); i++) {//dateId 리스트를 순회하며,todayStr과 같은 날짜가 있는지 확인 
		      if(dateId.get(i).getTodo_date().equals(tdDate)) {
		         //리스트 중에 입력한 날짜와 같은 열, 세션아이디와 같은 값을 가진 열을 찾으면 그 고유번호를 반환함
		         selectedtdId = dateId.get(i).getTodo_Id(); //for문 사용하여 index번화 반환하므로 1 더해줌
		      }
		   }
		   return selectedtdId;
		
		}

	@Override
	public Map<String, Integer> curProgress(String userId) {
		LocalDateTime today = LocalDateTime.now(); //현재 날짜
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		int curStreak = 0;
		int maxStreak = 0;
		int tempStreak = 0;
		
		ArrayList<Double> userProgress = new ArrayList<Double>();
		userProgress = tlMap.userProgress(userId); // mapper에서 가져온
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyMMdd"); //날짜 변환
		String todayStr = today.format(form); //오늘 날짜를 위 형식으로 변환
		
		for(int i = 0; i < userProgress.size(); i++) {
			if(userProgress.get(i) > 0.8) {
				tempStreak++;
				maxStreak = Math.max(maxStreak, tempStreak);
			} else {
				tempStreak = 0; //연속 달성일 초기화
			}
			if(!userProgress.isEmpty() && userProgress.get(userProgress.size() - 1) > 0.8) {
				curStreak = tempStreak;
			} //0.8% 미만일 시에 연속 달성일 초기화
			
			result.put("curStreak", curStreak);//현재 연속 달성일
			result.put("maxStreak", maxStreak); //최대 연속 달성일
			result.put("tempStreak", tempStreak);
		}
		return result;
	

	}

	@Override
	public List<CalendarDTO> getMemo(String calDate, String sessionId) {
		List<CalendarDTO> cal = new ArrayList<CalendarDTO>();
			cal = calMap.getMemo(calDate, sessionId);
				
		return cal;
	}

	@Override
	public int addMemo(CalendarDTO calendar) {
		
		return 0;
	}

	@Override
	public int modiMemo(CalendarDTO calendar) {
		
		return 0;
	}

	@Override
	public int delMemo(String userId) {
		
		return 0;
	}
	
}
