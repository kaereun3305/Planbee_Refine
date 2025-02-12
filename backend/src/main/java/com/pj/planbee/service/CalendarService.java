package com.pj.planbee.service;

import java.util.Map;

import com.pj.planbee.dto.CalendarDTO;

public interface CalendarService {

   public int getProgress(String calDate, String sessionId);
   
   public Map<String, Integer> curProgress(String userId);

}