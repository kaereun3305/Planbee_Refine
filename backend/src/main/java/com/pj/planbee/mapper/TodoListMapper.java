package com.pj.planbee.mapper;

import java.util.Map;

public interface TodoListMapper {
	//memo관련 기능
		public Map<String, String> getMemo();
		public int memoWrite();
		public int memoUpdate();
		public int memoDel();
		
		
		//진척도 업데이트 하는 기능
		public double todoProgress();
		
}
