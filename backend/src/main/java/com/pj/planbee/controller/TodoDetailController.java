package com.pj.planbee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TodoDetailDTO;
import com.pj.planbee.service.TodoDetailService;

@RestController
@RequestMapping("/todo/{todoId}/detail")
public class TodoDetailController {

    @Autowired TodoDetailService ds;

    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> insertDetail(@PathVariable int todoId, @RequestBody TodoDetailDTO detail) {
        detail.setTodoDetailId(todoId);
        int result = ds.insertDetail(detail);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{detailId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> updateDetail(@PathVariable int todoId, @PathVariable int detailId, @RequestBody TodoDetailDTO detail) {
        detail.setTodoId(todoId);
        detail.setTodoDetailId(detailId);
        int result = ds.updateDetail(detail);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{detailId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Integer> deleteDetail(@PathVariable int todoId, @PathVariable int detailId) {
        int result = ds.deleteDetail(detailId);
        return ResponseEntity.ok(result);
    }
}
