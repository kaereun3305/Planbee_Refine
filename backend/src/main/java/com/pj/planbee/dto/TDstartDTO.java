package com.pj.planbee.dto;

public class TDstartDTO { 
	//제일 첫 페이지에서 todolist 페이지에 들어갔을때, 
	//todolist 페이지에 입력된 열이 있는지 확인하기 위한 DTO
	//DB의 열 이름과 맞춰야함!!!!!!!
	String todo_date;
	int todo_Id;
	public String getTodo_date() {
		return todo_date;
	}
	public void setTodo_date(String todo_date) {
		this.todo_date = todo_date;
	}
	public int getTodo_Id() {
		return todo_Id;
	}
	public void setTodo_Id(int todo_Id) {
		this.todo_Id = todo_Id;
	}
	
	
	
	
	
}
