package com.pj.planbee.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TodoDetailDTO {
	
    int todoDetailId;
    String todoDetail;
    int todoDetailState;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime todoDetailTime;
    int todoId;
    
	public int getTodoDetailId() {
		return todoDetailId;
	}
	public void setTodoDetailId(int todoDetailId) {
		this.todoDetailId = todoDetailId;
	}
	public String getTodoDetail() {
		return todoDetail;
	}
	public void setTodoDetail(String todoDetail) {
		this.todoDetail = todoDetail;
	}
	public int getTodoDetailState() {
		return todoDetailState;
	}
	public void setTodoDetailState(int todoDetailState) {
		this.todoDetailState = todoDetailState;
	}
	public LocalTime getTodoDetailTime() {
		return todoDetailTime;
	}
	public void setTodoDetailTime(LocalTime todoDetailTime) {
		this.todoDetailTime = todoDetailTime;
	}
	public int getTodoId() {
		return todoId;
	}
	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}

    
}
