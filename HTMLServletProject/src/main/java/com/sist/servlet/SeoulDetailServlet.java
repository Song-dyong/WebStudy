package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
	WEB
		Java => jsp / servlet (Spring)
		c# 	 => aspx / asp 
		python => jango
		php
		nodejs
		------------------------------ request / response / cookie / session

		-사용자의 정보를 톰캣으로 (?뒤의 내용) request에 담아서 받아옴
		-서버에서 값을 받아서 response에 담아서 클라이언트에 전송 (넘길 때, 네트워크 사용 --> 톰캣에서 해주는 중)
		
		-브라우저는 html만 인식할 수 있음 => 브라우저 내에서 자바의 코딩은 일반 텍스트와 같음 
		-out.println내의 내용을 html의 형식으로 코딩해서 넘겨줌
		
		
	브라우저 / 자바 / DAO / 오라클
	
	자바에서 브라우저의 값을 받음 --> dao에서 오라클에 연결해서 값을 가져옴 --> 자바에서 오라클의 값을 브라우저로 다시 전송
	
	자바에서 브라우저에 연결할 수 있게 하는 프로그램 --> servlet / JSP 
	

*/

@WebServlet("/SeoulDetailServlet")
public class SeoulDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
