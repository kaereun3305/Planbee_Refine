package com.pj.planbee.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
@Mapper
public interface TDdetailMapper {
	
	public List<TDdetailDTO> getList(); //전체보는 테스트기능
	public List<TDdetailDTO> getTodo(int todoId);//하루의 투두리스트 보는기능
	public int todoWrite(TDdetailDTO dto); //투두리스트 작성기능
	public int updateState(@Param("ToDoDetailID") int ToDoDetailID, @Param("state") String state); //투두리스트 완료상황 업데이트기능
	//그냥 id,state로 적으면 인식을 못해서 @Param("")를 추가함
	public int todoModify(@Param("ToDoDetailID") int ToDoDetailID, TDdetailDTO dto);//투두리스트 수정기능 문제있음
	public int todoDel(@Param("ToDoDetailID") int ToDoDetailID); //투두리스트 한 개 삭제기능
	
	//진척도 계산을 위한 기능
	public double getComplete(@Param("todoId") int todoId); //완성한 것의 개수를 가져오는 기능
	public double getTotal(int todoId); //하루의 전체 투두리스트 개수를 가져오는 기능

}
