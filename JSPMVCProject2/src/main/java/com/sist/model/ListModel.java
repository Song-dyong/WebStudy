package com.sist.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListModel implements Model {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<String> list= new ArrayList<>();
		list.add("홍길동");
		list.add("심청이");
		list.add("박문수");
		list.add("강감찬");
		list.add("이순신");
		request.setAttribute("list", list);
		return "view/list.jsp";
	}

}
