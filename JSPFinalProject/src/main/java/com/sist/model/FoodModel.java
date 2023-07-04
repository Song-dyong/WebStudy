package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;


public class FoodModel {
	@RequestMapping("food/location_find.do")
	public String food_find(HttpServletRequest request, HttpServletResponse response) {
		
		// 검색어를 받는다.
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		String fd=request.getParameter("fd");
		if(fd==null) {
			fd="마포";
		}
		// JSP안에서 페이지는 내장 객
		String page=request.getParameter("page");
		if(page==null) {
			page="1";
		}
		int curpage=Integer.parseInt(page);
		// => 디에이오를 연결해서 값 전송.
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodLocationFindData(fd, curpage);
		int totalpage=dao.foodLocationTotalPage(fd);
		
		final int BLOCK=10;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) {
			endPage=totalpage;
		}
		
		// location_find.jsp로 전송할 데이터 
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("list", list);
		request.setAttribute("fd", fd);
		
		request.setAttribute("main_jsp", "../food/location_find.jsp");
		return "../main/main.jsp";
	}
}