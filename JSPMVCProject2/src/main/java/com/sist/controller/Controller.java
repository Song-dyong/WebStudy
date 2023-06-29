package com.sist.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.*;

import java.util.*;
/*
		Controller
		 요청 받기 => 요청을 처리할 클래스(Model) 찾기 => 처리한 결과값 받기 => 결과값 JSP에 전송하기
		 결과값: request / session에 담기
		  -----------------------
		  setAttribute()  =>  JSP에서 ${}(EL)로 출력

		JSP에 자바코딩을 할 경우, 확장성(오버로딩, 라이딩)이 없으며, 코드가 노출되기 때문에 보안문제 발생
		=> Controller의 내용도 파일형식으로 바꾸면 보안성 증가 
		=> Model과 View의 영역을 나눔으로써 작업 속도 증가
		비지니스 로직 (model) / 프리젠테이션 로직 (view)  
		
		Model => DAO + VO + 요청 처리 => 자바로 처리하는 모든 것을 포함
				cf) Controller는 Servlet (자바 x) 
		

*/
@WebServlet("*.do") // URL 주소는 언제든지 변경 가능
// list.do, insert.do, delete.do ...	=>	어느 클래스를 찾을지 설정
//	.do는 Controller 호출
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map clsMap=new HashMap<>();
	
	public void init(ServletConfig config) throws ServletException {
		clsMap.put("list.do", new ListModel());
		clsMap.put("insert.do", new InsertModel());
		clsMap.put("update.do", new UpdateModel());
		clsMap.put("delete.do", new DeleteModel());
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd=request.getRequestURI();	// URI => /JSPMVCProject2/list.do
		cmd=cmd.substring(cmd.lastIndexOf("/")+1);
		// 요청 확인
		// Model Class 찾기 => 처리 
		Model model=(Model)clsMap.get(cmd);
		String jsp=model.execute(request, response);
		// request 전송
		RequestDispatcher rd=request.getRequestDispatcher(jsp);
		rd.forward(request, response);
		
	}

}
