package com.sist.model;

import javax.servlet.http.HttpServletRequest;

public class ListModel {
	public String execute(HttpServletRequest request) {
		request.setAttribute("msg", "목록출력");
		return "list.jsp";
	}
}
