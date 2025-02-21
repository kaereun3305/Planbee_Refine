package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.CalendarDTO;

public interface CalendarMapper {
	public List<CalendarDTO> getMemo(@Param("calDate") String calDate, @Param("sessionId") String sessionId);

	public int countByMonth(@Param("monthPre") String monthPre, @Param("userId") String userId);

	public void insertNewDate(@Param("date") List<CalendarDTO> newDate);

	public List<CalendarDTO> getByMonth(@Param("monthPre") String monthPre, @Param("userId") String userId);

	public int addMemo(CalendarDTO calendar);

	public int updateMemo(CalendarDTO calendar);
	
	List<Double> getProgress(@Param("calDate") String calDate, @Param("userId") String userId);

	public int modiMemo(CalendarDTO calendar);

	public int delMemo(int calId);

}
