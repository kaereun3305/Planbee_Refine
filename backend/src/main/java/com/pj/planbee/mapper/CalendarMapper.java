package com.pj.planbee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.CalendarDTO;
import com.pj.planbee.dto.ProgressDTO;

public interface CalendarMapper {
	public List<ProgressDTO> getMemo(@Param("yyMM") String yyMM, @Param("sessionId") String sessionId);

	public int countByMonth(@Param("monthPre") String monthPre, @Param("userId") String userId);

	public void insertNewDate(@Param("date") List<CalendarDTO> newDate);

	public List<CalendarDTO> getByMonth(@Param("monthPre") String monthPre, @Param("userId") String userId);

	public int addMemo(CalendarDTO calendar);

	public int updateMemo(CalendarDTO calendar);
	
	List<Double> getProgress(@Param("yyMM") String yyMM, @Param("userId") String userId);

	public int modiMemo(CalendarDTO calendar);
	
	public double monthProgress(@Param("yyMM") String yyMM, @Param("sessionId") String sessionId);

	public int delMemo(Map<String, Object> params);

	
}
