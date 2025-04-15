package com.pj.planbee.dto;

import java.util.List;

public class TodoDTO {
    
	int todoId;
    String todoDate;
    String todoMemo;
    float todoProgress;
    List<TodoDetailDTO> todoDetails;
    String userId;
    
	public int getTodoId() {
		return todoId;
	}
	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}
	public String getTodoDate() {
		return todoDate;
	}
	public void setTodoDate(String todoDate) {
		this.todoDate = todoDate;
	}
	public String getTodoMemo() {
		return todoMemo;
	}
	public void setTodoMemo(String todoMemo) {
		this.todoMemo = todoMemo;
	}
	public float getTodoProgress() {
		return todoProgress;
	}
	public void setTodoProgress(float todoProgress) {
		this.todoProgress = todoProgress;
	}
	public List<TodoDetailDTO> getTodoDetails() {
		return todoDetails;
	}
	public void setTodoDetails(List<TodoDetailDTO> todoDetails) {
		this.todoDetails = todoDetails;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

    
}