package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.TodoDetailDTO;

public interface TodoDetailService {
    List<TodoDetailDTO> getDetailsByTodoId(int todoId);
    int insertDetail(TodoDetailDTO detail);
    int updateDetail(TodoDetailDTO detail);
    int deleteDetail(int detailId);
}
