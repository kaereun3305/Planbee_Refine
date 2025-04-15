package com.pj.planbee.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.TodoDTO;
import com.pj.planbee.dto.TodoDetailDTO;
import com.pj.planbee.mapper.TodoDetailMapper;
import com.pj.planbee.mapper.TodoMapper;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired TodoMapper mapper;
    @Autowired TodoDetailMapper detailMapper;
    
    @Override
    public TodoDTO getTodoByDate(String userId, String todoDate) {
        TodoDTO todo = mapper.selectTodoByDate(userId, todoDate);
        if (todo != null) {
            List<TodoDetailDTO> details = detailMapper.selectDetailsByTodoId(todo.getTodoId());
            todo.setTodoDetails(details);
        }
        return todo;
    }

    @Override
    public int initializeTodayAndTomorrow(String userId) {
        int result = 0;

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyMMdd"));

        if (mapper.existsTodoByDate(userId, today) == 0) {
            result += mapper.insertTodoForDate(userId, today);
        }
        if (mapper.existsTodoByDate(userId, tomorrow) == 0) {
            result += mapper.insertTodoForDate(userId, tomorrow);
        }

        return result; // 생성된 개수 (0~2)
    }


    @Override
    public TodoDTO getTodoById(int todoId) {
        return mapper.selectTodoById(todoId);
    }

    @Override
    public int updateTodoMemo(TodoDTO todo) {
        return mapper.updateTodoMemo(todo);
    }

    @Override
    public int insertTodo(TodoDTO todo) {
        return mapper.insertTodo(todo);
    }

    @Override
    public int deleteTodo(int todoId) {
        return mapper.deleteTodo(todoId);
    }
    
    
}
