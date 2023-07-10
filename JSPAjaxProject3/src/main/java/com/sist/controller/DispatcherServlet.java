package com.sist.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Spring MVC Structure
/*
	화면단(View단)	: JSP로 구성 => EL / JSTL 
		=>	View
	구현단(business단)	: Java로 구성 => DataBase Connect (request 처리) 
		=>	Model (VO, DAO , Model, Manager)
	연결 (화면 - 구현)	: Servlet 
		=>	Controller
	--------------------------------------------------------------
	Model 1 : JSP로 코딩 => 분석 편리 but! 확장성, 추가 기능, 재사용 어려움 
	Model 2 : JSP / Java 분리 코딩 => 코딩이 복잡함 but! 여러 명이 동시에 개발 가능
													확장성, 재사용성 => 유지보수
								=> Controller가 집중됨 	
	Domain형식 : Controller를 여러개 사용 (분산 작업) => 스프링 5~6
				기능별로 서버를 나눠서, 독립적으로 개발 
				MSA (MicroService Architecture) => Spring - Cloud
				--------------------------------------------------- CI / CD
							Git , Docker , 쿠바네티스 , 젠킨스 
							DevOps (개발/운영)
	MVC의 동작 구조
	------------
	*Controller를 찾는 방법: *.do (URL주소 설정)
				
				*.do 
			   --- 어떤 단어가 들어와도 상관 없음
	
	사용자 요청 --------- Controller (DispatcherServlet) --------- Model -------- DAO 	
					(Front Controller)
							|
					저장되어있는 모델 클래스를 찾기
					=> 요청 처리에 대한 메소드를 찾아서 호출
					=> 결과값을 request에 담아서 JSP로 전송
		< Spring (DispatcherServelt) / Struts (FilterDispatcher) >
 					=> 이미 만들어진 메소드를 호출 (재호출) : redirect
 					=> 새로운 데이터를 JSP로 전송 : forward 
 					=> Annotation = if 
 						------- find by index
 		
 						
 						
*/

import java.util.*;
import com.sist.model.*;
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> clsList=new ArrayList<String>();
	
	public void init(ServletConfig config) throws ServletException {
		clsList.add("com.sist.model.DiaryModel");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자가 보내준 uri 주소를 읽어온다. 
		String cmd=request.getRequestURI();
		// http://localhost/JSPAjaxProject3/diary3/diary.jsp
		//					-------------------------------- URI 
		System.out.println("cmd="+cmd);
		cmd=cmd.substring(request.getContextPath().length()+1);
		System.out.println("cmd="+cmd);
		try {
			// find method and call
			for(String cls:clsList) {
				Class clsName=Class.forName(cls);
				// class information read
				Object obj=clsName.getDeclaredConstructor().newInstance();
				// 클래스 메모리 할당
				Method[] methods=clsName.getDeclaredMethods();
				// 메소드 전체를 읽어온다
				// 메소드 위에 있는 어노테이션 읽기
				for(Method m:methods) {
					RequestMapping rm=m.getAnnotation(RequestMapping.class);
					if(rm.value().equals(cmd)) {
						// 조건에 맞는 메소드를 호출
						String jsp=(String)m.invoke(obj, request,response);
						// request를 보내주고, 결과값을 담아서 들어온다.
						RequestDispatcher rd=request.getRequestDispatcher(jsp);
						rd.forward(request, response);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
