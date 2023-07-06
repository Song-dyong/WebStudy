package com.sist.model;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class GoodsModel {
	@RequestMapping("goods/goods_list.do")
	public String goods_list(HttpServletRequest request, HttpServletResponse response) {
		
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		String type=request.getParameter("type");
		if(type==null)
			type="1";
		int curpage=Integer.parseInt(page);
		//DB connect
		GoodsDAO dao=GoodsDAO.newInstance();
		List<GoodsVO> list=dao.goodsListData(curpage, Integer.parseInt(type));
		int totalpage=dao.goodsTotalPage(Integer.parseInt(type));
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("type", type);
		String[] msg= {"","전체 상품 목록","베스트 상품 목록","신상품 목록","특가 상품"};
		request.setAttribute("msg", msg[Integer.parseInt(type)]);
		request.setAttribute("main_jsp", "../goods/goods_list.jsp");
		
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("goods/goods_detail_before.do")
	public String goods_detail_before(HttpServletRequest request, HttpServletResponse response) {
		
		// no(고유번호) 받아두기
		String no=request.getParameter("no");
		String type=request.getParameter("type");
		// 전송값 => 상세보기: primary key로 설정된 변수 사용
		// 검색 => 검색어
		// 로그인 (id, pwd) , 회원가입 (회원가입에 사용된 데이터) 
		// 주고 받는 데이터 확인 => 받은 값을 어떻게 처리해서 어디로 전송하는지 확인
		
		// Cookie에 저장
		Cookie cookie=new Cookie("goods_"+no, no);
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		
		return "redirect:../goods/goods_detail.do?no="+no+"&type="+type;
		// sendRedirect() => 기존에 제작된 메소드 재사용
		// 반복 제거 
		// forward => 새로운 데이터를 전송 (request에 담긴 값을 통해 새로운 값을 만든 뒤, response로 전송)
		// sendRedirect => _ok.do , _before.do 
	}
	
	@RequestMapping("goods/goods_detail.do")
	public String goods_detail(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");
		String type=request.getParameter("type");
		// DAO 연결
		GoodsDAO dao=GoodsDAO.newInstance();
		// 결과값을 request에 묶어서 => goods_detail.jsp로 전송 
		GoodsVO vo=dao.goodsDetailData(Integer.parseInt(no), Integer.parseInt(type));
		request.setAttribute("vo", vo);
		request.setAttribute("type", type);
		// 데이터 => goods_detail
		request.setAttribute("main_jsp", "../goods/goods_detail.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	
}
