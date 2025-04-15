package com.pj.planbee.mapper;

import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.TodoDTO;

public interface TodoMapper {
    TodoDTO selectTodoById(int todoId);
    int updateTodoMemo(TodoDTO todo);
    int insertTodo(TodoDTO todo);  
    int deleteTodo(int todoId);    
    TodoDTO selectTodoByDate(@Param("userId") String userId, @Param("todoDate") String todoDate);
    int existsTodoByDate(@Param("userId") String userId, @Param("todoDate") String todoDate);
    int insertTodoForDate(@Param("userId") String userId, @Param("todoDate") String todoDate);
}
