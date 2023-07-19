package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.CartDAO;
import com.sist.dao.FoodJjimLikeDAO;
import com.sist.dao.ReserveDAO;
import com.sist.vo.*;
import com.sist.vo.FoodJjimVO;
import com.sist.vo.ReserveVO;

import java.util.*;
public class MyPageModel {
	@RequestMapping("mypage/mypage_main.do")
	public String mypage_main(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("mypage_jsp", "../mypage/mypage_reserve.jsp");
		request.setAttribute("main_jsp", "../mypage/mypage_main.jsp");
		
		return "../main/main.jsp";
	}
	
	@RequestMapping("mypage/mypage_reserve.do")
	public String mypage_reserve(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		ReserveDAO dao=ReserveDAO.newInstance();
		List<ReserveVO> list=dao.reserve_infoData(id);
		
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp", "../mypage/mypage_reserve.jsp");
		request.setAttribute("main_jsp", "../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("mypage/mypage_jjim_list.do")
	public String mypage_jjim(HttpServletRequest request, HttpServletResponse response) {
	
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		List<FoodJjimVO> list = dao.foodJjimListData(id);
		
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp", "../mypage/mypage_jjim.jsp");
		request.setAttribute("main_jsp", "../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}

	@RequestMapping("mypage/mypage_cart.do")
	public String mypage_cart(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		CartDAO dao=CartDAO.newInstance();
		List<CartVO> list=dao.mypageCartListData(id);
		
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp", "../mypage/mypage_cart.jsp");
		request.setAttribute("main_jsp", "../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("mypage/cart_cancel.do")
	public String mypage_cancel(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");
		CartDAO dao=CartDAO.newInstance();
		dao.cartCancel(Integer.parseInt(no));
		
		
		return "redirect:../mypage/mypage_cart.do";
	}
	
	@RequestMapping("mypage/mypage_buy.do")
	public String mypage_buy(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		CartDAO dao=CartDAO.newInstance();
		List<CartVO> list=dao.mypageCartBuyListData(id);
		
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp", "../mypage/mypage_buy.jsp");
		request.setAttribute("main_jsp", "../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
}
