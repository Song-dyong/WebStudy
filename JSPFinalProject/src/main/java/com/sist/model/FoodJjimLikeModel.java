package com.sist.model;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;
public class FoodJjimLikeModel {
	@RequestMapping("jjim/jjim_insert.do")
	public String jjim_insert(HttpServletRequest request, HttpServletResponse response) {
		String fno=request.getParameter("fno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		// forward (main.jsp)는 request 값이 넘어감. => setAttribute로 request에 값을 실어서 보내줌.
		// but! redirect: 는 request값이 초기화되버림.
		// 따라서, 필요한 데이터를 ? 뒤에 담아서 보내줘야함. => 보통 insert, delete, update등 request값이 필요없이 
		// 데이터 저장, 삭제 등을 한 뒤 화면만 전환할 때 redirect를 사용한다.
		// 현재의 경우, 찜하기 버튼을 클릭하면 dao에 연결하여 값을 넣어준 뒤, fno값에 해당하는 디테일 페이지로 화면 전환.
		// fno값이 필요하기 때문에 fno를 ?뒤에 담아서 전송.
		// 화면 이동 (서버)
		// sendRedirect() => 재호출 => .do(request를 초기화)
		// forward() => 새로운 데이터를 전송 (request에 값을 담아서 전송)
		FoodJjimVO vo=new FoodJjimVO();
		vo.setId(id);
		vo.setFno(Integer.parseInt(fno));
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodJjimInsert(vo);
		
		return "redirect:../food/food_detail.do?fno="+fno;
	}
	
	@RequestMapping("jjim/jjim_cancle.do")
	public String jjim_cancle(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");
		
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodJjimCancle(Integer.parseInt(no));
		
		return "redirect:../mypage/mypage_jjim_list.do";
	}
	
	@RequestMapping("like/like_insert.do")
	public String like_insert(HttpServletRequest request, HttpServletResponse response) {
		
		String fno=request.getParameter("fno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		FoodLikeVO vo=new FoodLikeVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setId(id);
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodLikeInsert(vo);
		
		return "redirect:../food/food_detail.do?fno="+fno;
	}
	
}
