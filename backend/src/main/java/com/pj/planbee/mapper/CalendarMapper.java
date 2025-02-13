package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.CalendarDTO;

public interface CalendarMapper {
	public List<CalendarDTO> getMemo(@Param("calDate") String calDate, @Param("sessionId") String sessionId);
}
