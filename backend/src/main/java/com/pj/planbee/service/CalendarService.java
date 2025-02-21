package com.pj.planbee.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.CalendarDTO;

public interface CalendarService {

	public double getProgress(@Param("calDate") String calDate, @Param("sessionId") String sessionId);

   
   public Map<String, Integer> curProgress(String userId);
   
   List<CalendarDTO> getMemo (String calDate, String sessionId);
   public int addMemo (CalendarDTO calendar); //메모 추가
   public int modiMemo (CalendarDTO calendar); //메모 수정
   public int delMemo(int calId); //메모 삭제
   
   public void checkMonthly(int year, int month, String userId);
   List<CalendarDTO> getMonthly(int year, int month, String userId, String fileterId);


   
   
}