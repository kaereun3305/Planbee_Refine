package com.pj.planbee.dto;

public class TDdetailDTO {
	int ToDoDetailID, ToDoDeadLine, ToDoID;
	String TodoDate, ToDo;
	boolean ToDoStatement;
	
	
	public int getToDoDetailID() {
		return ToDoDetailID;
	}
	public void setToDoDetailID(int toDoDetailID) {
		ToDoDetailID = toDoDetailID;
	}
	public int getToDoDeadLine() {
		return ToDoDeadLine;
	}
	public void setToDoDeadLine(int toDoDeadLine) {
		ToDoDeadLine = toDoDeadLine;
	}
	public int getToDoID() {
		return ToDoID;
	}
	public void setToDoID(int toDoID) {
		ToDoID = toDoID;
	}
	public String getTodoDate() {
		return TodoDate;
	}
	public void setTodoDate(String todoDate) {
		TodoDate = todoDate;
	}
	public String getToDo() {
		return ToDo;
	}
	public void setToDo(String toDo) {
		ToDo = toDo;
	}
	public boolean isToDoStatement() {
		return ToDoStatement;
	}
	public void setToDoStatement(boolean toDoStatement) {
		ToDoStatement = toDoStatement;
	}
	
	
}
