package com.sist.model;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.*;
import com.sist.dao.*;
import com.sist.vo.*;

public class FreeBoardModel {
	@RequestMapping("board/list.do")
	public String board_list(HttpServletRequest request, HttpServletResponse response) {
		// JSP 첫 줄에 <% %> 나오는 영역 코딩
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		// DAO 연동
		FreeBoardDAO dao=FreeBoardDAO.newInstance();
		// board list
		List<FreeBoardVO> list=dao.freeboardListData(curpage);
		// total page
		int totalpage=dao.freeboardTotalPage();
		
		// 출력에 필요한 데이터를 모아서, 전송
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("list", list);
		// board/list.jsp 로 전송 
		// main.jsp, list.jsp로 전송해도 같음 => list.jsp를 인클루드하고 있으므로, 
		// 화면 출력을 main.jsp로 하면, 출력 가능 
		// why? main_jsp가 ../board/list.jsp 로 설정되어 있으므로, 
		// main.jsp에 설정된 ${main_jsp}가 ../board/list.jsp로 바뀜.
		request.setAttribute("main_jsp", "../board/list.jsp");
		// main_jsp => 화면 출력
		return "..main/main.jsp";
	}
	
	@RequestMapping("board/insert.do")
	public String board_insert(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../board/insert.jsp");
		
		return "..main/main.jsp";
	}
	
	@RequestMapping("board/insert_ok.do")
	public String board_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		FreeBoardVO vo=new FreeBoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		FreeBoardDAO dao=FreeBoardDAO.newInstance();
		dao.freeboardInsert(vo);
		
		
		return "redirect:../board/list.do";
	}
	
}
