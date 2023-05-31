	package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력을 전송 => HTML (HTML을 브라우저로 보낸다)
				response.setContentType("text/html;charset=UTF-8");
				// 메모리에 HTML을 저장 => 브라우저에서 읽어서 출력
				String no=request.getParameter("no");
				BoardDAO dao=BoardDAO.newInstance();
				BoardVO vo=dao.BoardDetailData(Integer.parseInt(no));
				PrintWriter out=response.getWriter();
				
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=stylesheet href=html/table.css>");
				out.println("</head>");
				out.println("<body>");
				out.println("<center>");
				out.println("<h1>글수정하기</h1>");
				// 입력된 데이터를 한 번에 action에 등록된 클래스로 전송 (input에 입력된 데이터만 전송)
				out.println("<form method=post action=BoardUpdateServlet>");
				// post로 보냈으면, 처리할 때 doPost메소드에서 처리
				// doPost()를 호출
				out.println("<table class=table_content width=700>");
				out.println("<tr>");
				out.println("<th width=20%>번호</th>");
				out.println("<td width=30% align=center>"+vo.getNo()+"</td>");
				out.println("<input type=hidden name=no value="+no+">");
				out.println("</tr>");
				out.println("<th width=15%>이름</th>");
				out.println("<td width=85%><input type=text name=name size=15 required value=\""
						+vo.getName()+
						"\"></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>제목</th>");
				out.println("<td width=85%><input type=text name=subject size=50 required value=\""
						+ vo.getSubject()
						+ "\"></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>내용</th>");
				out.println("<td width=85%><textarea rows=10 cols=50 name=content required>"
						+ vo.getContent()
						+ "</textarea></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>비밀번호</th>");
				out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td colspan=2 align=center>");
				out.println("<input type=submit value=글수정하기>");
				out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
				out.println("</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</form>");
				out.println("</center>");
				out.println("</body>");
				out.println("</html>");
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		try
		{
			request.setCharacterEncoding("UTF-8");// 한글변환 
			// 디코딩 => byte[]을 한글로 변환 
			// 자바 => 2byte => 브라우저는 1byte로 받는다 => 2byte
		}catch(Exception ex) {}
		// 값을 받는다
		String no = request.getParameter("no");
		String name=request.getParameter("name");	// name= 뒤의 값을 받아옴
		String subject=request.getParameter("subject");	// getParameter() 사이에는 "변수명"을 입력
		String content=request.getParameter("content");
		String pwd=request.getParameter("pwd");
		
		
//		System.out.println("이름: "+name);
//		System.out.println("제목: "+subject);
//		System.out.println("내용: "+content);
//		System.out.println("비밀번호: "+pwd);
		
		// dao에서 boardInsert는 BoardVO를 매개변수로 받기때문에, 위의 값을 vo에 모아야 함
		BoardVO bvo=new BoardVO();
		bvo.setName(name);
		bvo.setSubject(subject);
		bvo.setContent(content);
		bvo.setPwd(pwd);
		bvo.setNo(Integer.parseInt(no));
		
		// 오라클로 INSERT요청
		BoardDAO dao=BoardDAO.newInstance();
		dao.boardUpdate(bvo, bvo.getNo());
		
		// 화면이동
		response.sendRedirect("BoardListServlet");
		
		
	}
}
