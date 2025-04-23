package com.pj.planbee.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TodoDTO;
import com.pj.planbee.service.TodoService;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "", allowedHeaders= "", allowCredentials = "true")
public class TodoController {

    @Autowired TodoService ts;
    
    @GetMapping("/today")
    public ResponseEntity<TodoDTO> getToday(@RequestParam String userId, @RequestParam String todoDate) {
        TodoDTO todo = ts.getTodoByDate(userId, todoDate);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/tomorrow")
    public ResponseEntity<TodoDTO> getTomorrow(@RequestParam String userId, @RequestParam String todoDate) {
        TodoDTO todo = ts.getTodoByDate(userId, todoDate);
        return ResponseEntity.ok(todo);
    }



    @PostMapping("/initialize/{userId}")
    public ResponseEntity<Integer> initTodayTomorrow(@PathVariable String userId) {
        int created = ts.initializeTodayAndTomorrow(userId);
        return ResponseEntity.ok(created); // 0이면 이미 존재, 2면 오늘 내일 다 만듦
    }


    @PutMapping(value = "/{todoId}/memo", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> updateMemo(@PathVariable int todoId, @RequestBody TodoDTO todo) {
        todo.setTodoId(todoId);
        int result = ts.updateTodoMemo(todo);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> insertTodo(@RequestBody TodoDTO todo) {
        int result = ts.insertTodo(todo);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{todoId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> deleteTodo(@PathVariable int todoId) {
        int result = ts.deleteTodo(todoId);
        return ResponseEntity.ok(result);
    }
}
