package com.pj.planbee.service;

import com.pj.planbee.dto.TodoDTO;

public interface TodoService {
    TodoDTO getTodoById(int todoId);
    int updateTodoMemo(TodoDTO todo);
    int insertTodo(TodoDTO todo);
    int deleteTodo(int todoId);
    TodoDTO getTodoByDate(String userId, String todoDate); // 오늘/내일 조회용
    int initializeTodayAndTomorrow(String userId);         // 자동 생성용

}
