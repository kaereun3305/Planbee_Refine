package com.pj.planbee.mapper;

import java.util.List;

import com.pj.planbee.dto.TodoDetailDTO;

public interface TodoDetailMapper {
    List<TodoDetailDTO> selectDetailsByTodoId(int todoId);
    int insertTodoDetail(TodoDetailDTO detail);
    int updateTodoDetail(TodoDetailDTO detail);
    int deleteTodoDetail(int todoDetailId);
}
