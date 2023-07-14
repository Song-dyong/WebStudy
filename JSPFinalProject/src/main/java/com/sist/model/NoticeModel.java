package com.sist.model;

/*
	JSP (link)==> button , <a> , .ajax ==> .do (출력할 데이터 전송)
	-------------------------------------------- jsp -> jsp
	
	1) DispatcherServlet => Model안에서 @RequestMapping 통해 메소드를 찾고, request를 전달
	2) Model 안에서 요청에 대한 처리 => 결과값을 다시 request에 담기
	3) 결과값이 담긴 request를 DispatcherServlet이 다시 받는다
	4) request를 전달할 JSP를 찾아서 request를 전송
		=> 이 과정에서 DispatcherServlet은 감춰져있음. (자동화처리) => jar파일에서 하는 중.

*/

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.common.*;
import com.sist.controller.RequestMapping;

public class NoticeModel {
	@RequestMapping("notice/notice_list.do")
	public String notice_list(HttpServletRequest request, HttpServletResponse response) {
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		NoticeDAO dao=NoticeDAO.newInstance();
		List<NoticeVO> list=dao.noticeListData(curpage);
		int totalpage=dao.noticeTotalPage();
		
		String[] msg = {"","일반 공지","이벤트","맛집공지","여행공지","레시피공지"};
		for(NoticeVO vo:list) {
			vo.setNotice_type(msg[vo.getType()]);
		}
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		
		request.setAttribute("main_jsp", "../notice/notice_list.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("notice/notice_detail.do")
	public String notice_detail(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");
		NoticeDAO dao=NoticeDAO.newInstance();
		NoticeVO vo=dao.noticeDetailData(Integer.parseInt(no));
		String[] msg = {"","일반 공지","이벤트","맛집공지","여행공지","레시피공지"};
		vo.setNotice_type(msg[vo.getType()]);
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../notice/notice_detail.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
}
