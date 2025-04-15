package com.pj.planbee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TodoDetailDTO;
import com.pj.planbee.mapper.TodoDetailMapper;

@Service
public class TodoDetailServiceImpl implements TodoDetailService {

    @Autowired TodoDetailMapper mapper;

    @Override
    public List<TodoDetailDTO> getDetailsByTodoId(int todoId) {
        return mapper.selectDetailsByTodoId(todoId);
    }

    @Override
    public int insertDetail(TodoDetailDTO detail) {
        return mapper.insertTodoDetail(detail);
    }

    @Override
    public int updateDetail(TodoDetailDTO detail) {
        return mapper.updateTodoDetail(detail);
    }

    @Override
    public int deleteDetail(int detailId) {
        return mapper.deleteTodoDetail(detailId);
    }
}
