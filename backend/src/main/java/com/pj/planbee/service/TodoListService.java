package com.pj.planbee.service;

import java.util.Map;

public interface TodoListService {

	//todolist에 대한 기본 기능
	public Map<String, String> getTodo(); //하루의 투두리스트 가져오는 기능
	public int todoWrite(); //투두리스트 작성하는 기능
	public int todoUpdate(); //투두리스트 수정하는 기능
	public int todoDel();
	public double todoProgress(); //투두리스트 진척율 계산하는 기능
	
	//todolist의 메모에 대한 기능
	public Map<String, String> getMemo(); //하루의 메모가져오는 기능
	public int memoWrite(); //메모 작성하는 기능
	public int memoUpdate(); //메모 수정하는 기능
	public int memoDel(); //메모를 삭제하는 기능
}
