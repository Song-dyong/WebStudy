package com.sist.servlet;

import java.io.*;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;

@WebServlet("/SeoulNatureServlet")
public class SeoulNatureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송방식 => 브라우저로 보낸다 (미리 알려준다) => text/html (html 보냄) 캐릭터셋은 utf-8
				// html => text/html , xml => text/xml , json => text/plain
				response.setContentType("text/html;charset=UTF-8");
				// html을 저장 => 브라우저에서 읽어가는 위치에 저장 (PrintWriter)
				PrintWriter out=response.getWriter();
				// print.out	=>------------------ 사용자의 브라우저
				// 데이터 베이스 연결
				SeoulDAO dao=SeoulDAO.newInstance();
				List<SeoulVO> list=dao.seoulNatureData(1); // 30개
				// 카테고리 정보를 오라클로부터 받아옴
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
				out.println("<style>");
				out.println(".container{margin-top:50px}"); // margin은 웹 페이지 전체 기준 패딩은 카드 기준
				out.println(".row{");
				out.println("margin:0px auto;"); // 가운데 정렬
				out.println("width:1024px}");
				out.println("</style>");
				out.println("</head>");
				out.println("<body>");
				out.println("<div class=container>");
				out.println("<h1>서울 자연</h1>");
				out.println("<div class=row>");
				// <a href=SeoulLocationServlet=?no=0 class=\"btn btn-size:xs btn-danger value\"></a>
				// 맨 위에 String 배열로 이름 설정 --> dao에서 from 찾아오는 부분을 배열의 이름으로 설정하면 넣을 때마다 값이 바뀔 수 있음
				for(int i=0;i<12;i++)
				{
					SeoulVO vo=list.get(i);
					out.println("<div class=\"col-md-3\">");	// 한 줄에 4개씩 출력
					out.println("<div class=\"thumbnail\">");		
					out.println("<a href=\"#\">");	// target=\"_blank\"
					out.println("<img src=\""+vo.getPoster()+"\" style=\"width:280px; height:250px\">");
					out.println("<div class=\"caption\">");
					out.println("<p style=\"font-size:9px\">"+vo.getTitle()+"</p>");
					out.println("</div>");
					out.println("</a>");
					out.println("</div>");
					out.println("</div>");
				}
				out.println("</div>");	// row
				out.println("</div>");	// container
				out.println("</head>");
				out.println("</html>");
	}

}
