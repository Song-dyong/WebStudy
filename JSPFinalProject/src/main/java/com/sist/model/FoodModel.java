package com.sist.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.common.CommonModel;
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
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	/*
		MVC
		---
		 1. DAO연결 => 오라클 연결 담당
		 2. DAO , 브라우저(JSP) => 연결 => 결과값 => Model
		 --------------------------------------------
		 3. 브라우저로 값을 전송 / 요청 => Controller (Front Controller)
		 	DispatcherServlet
		 4. 전송받은 데이터 출력 : View (JSP)
		 	--------------- request, session 만 전송받을 수 있음 
		-----------------------------------------------------------
		동작과정
		  1. 사용자의 요청: <a> , <form> , $.ajax
		  	=> 지정된 URL을 사용 (*.do)
		  2-1. DispatcherServlet이 요청 내용을 받는다 
		  	=> service()메소드가 URI를 받아서 ~.do를 추출
		  2-2. 요청 처리에 해당되는 메소드를 찾는다
		  	=> 어노테이션 (@RequestMapping)
		  	   ------- Index(찾기기능 담당)
		  	   		1) 메소드 찾기: @Target(METHOD)
		  	   		2) 클래스 찾기: Model만 찾는다.
		  	   		3) 생성자 찾기
		  	   		4) 매개변수 찾기
		  2-3. 메소드를 찾아서 => 요청 데이터를 모델로 전송
		  		=> m.invoke(obj,request,response)
		  		   ---------------------------
		  	   		해당 요청 처리에 해당되는 메소드 호출
		  	   		=> 메소드명은 자유롭게 만들 수 있음
		  	   		=> DI
		  3. Model
		  	=> 요청 처리, 결과값 담기
		  	=> DAO(오라클) 연결 => 데이터 읽기
		  	=> request에 값 담아주는 역할
		  4. DispatcherServlet
		  	=> request에 담긴 데이터를 JSP에 전송
		  	=> RequestDispatcher rd=request.getRequestDispatcher(jsp)
		  		rd.forward(request,response)
		  		---------------------------
		  			해당 JSP를 찾아서 request를 전송 역 
		  5. JSP : 화면 출
		  	=> request에 담긴 데이터 출력
		  	=> EL/JSTL
			------------------------------
			어노테이션: 찾기 역할 -> if문 
			------- 밑에 있는 클래스, 메소드, 변수 
			@Controller => 메모리 할당 관련
			class A
			{
				@Autowired
				B b; => 자동으로 b의 주소값 대입
				@RequestMapping("food/list.do")
				public void display(@Resource int a){
					=> 이 메소드 호출 
				}
			}
			
	*/
	@RequestMapping("food/food_category_list.do")
	public String food_list(HttpServletRequest request,HttpServletResponse response) {
		// cno: 카테고리 번호
		String cno=request.getParameter("cno");
		// DAO 연결해서 데이터 가져오기
		FoodDAO dao=FoodDAO.newInstance();
		CategoryVO cvo=dao.foodCategoryInfoData(Integer.parseInt(cno));
		List<FoodVO> list=dao.foodCategoryListData(Integer.parseInt(cno));
		// 출력할 JSP로 데이터 전송 
		// HttpServletRequest request,HttpServletResponse response
		// --> DispatcherServlet의 request, response
		// 디스패처가 가지고 있는 리퀘스트를 모델로 (매개변수로) 보내면, 거기서 값을 채워준 뒤, 디스패처(컨트롤러)로 이동시킴 =>이 리퀘스트를 다시 뷰로 보내주면
		//  MVC구조 
		request.setAttribute("cvo", cvo);
		request.setAttribute("list", list);
		// 전송 => return에 있는 jsp가 받는다.
		// include시에는 include된 모든 jsp에서 사용 가능 (request 공유)
		request.setAttribute("main_jsp", "../food/food_category_list.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("food/food_detail_before.do")	// cookie저장용 메소드
	public String food_detail_before(HttpServletRequest request,HttpServletResponse response) {
		
		// Cookie 생성
		String fno=request.getParameter("fno");
		Cookie cookie = new Cookie("food_"+fno, fno);
		// 쿠키의 단점 : 문자열만 저장 가능 => 요청한 사용자의 브라우저로 전송.
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		return "redirect:../food/food_detail.do?fno="+fno;
	}
	/*
		회원가입 /로그인 => main.do
		예약 , 장바구니 , 결제 => mypage.do
	
	*/
	@RequestMapping("food/food_detail.do")
	public String food_detail(HttpServletRequest request, HttpServletResponse response)
	{
		String fno=request.getParameter("fno");
		// DAO 연결
		FoodDAO dao=FoodDAO.newInstance();
		// 결과 request에 담기 (setAttribute())
		FoodVO vo=dao.foodDetailData(Integer.parseInt(fno));
		request.setAttribute("vo", vo);
		String address=vo.getAddress();
		String addr1=address.substring(0,address.indexOf("지번"));
		String addr2=address.substring(address.indexOf("지번")+3);
		request.setAttribute("addr1", addr1.trim());
		request.setAttribute("addr2", addr2.trim());
		// 인근 명소 , 레시피 ... 나중에 ~ 
		request.setAttribute("main_jsp", "../food/food_detail.jsp");
		CommonModel.commonRequestData(request);
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		if(id!=null) {
			FoodJjimLikeDAO jdao=FoodJjimLikeDAO.newInstance();
			int jjim_count=jdao.foodJjimCount(id, Integer.parseInt(fno));
			request.setAttribute("jjim_count", jjim_count);
			int like_count=jdao.foodLikeOk(Integer.parseInt(fno), id);
			int like_total=jdao.foodLikeCount(Integer.parseInt(fno));
			request.setAttribute("like_count", like_count);
			request.setAttribute("like_total", like_total);
		}
		// 레시피 읽기
		String type=vo.getType();
		// /가 있는지 없는지 확인 
		int num=type.indexOf("/");
		if(num>=0) {
			type=type.replace("/", "|");
			// 라멘 | 소바 | 우동 => regexp_like 함수 활용하기 위해서 형식 변경
		}else {
			type=type.substring(0,type.indexOf(" "));
			// 태국 음식 -> 태국만 가져오기
		}
		List<RecipeVO> rList=dao.foodRecipeData(type);
		
		request.setAttribute("reList", rList);
		// 댓글 읽기
		ReplyDAO rdao=ReplyDAO.newInstance();
		List<ReplyVO> list=rdao.replyListData(1, Integer.parseInt(fno));
		request.setAttribute("rList", list);
		
		return "../main/main.jsp";
	}
	
}

























