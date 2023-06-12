package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;

@WebServlet("/StudentUpdate")
public class StudentUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 사용자가 보내준 값 받기
		String hakbun=request.getParameter("hakbun");
		StudentDAO dao=StudentDAO.newInstance();
		StudentVO vo=dao.studentDetail(Integer.parseInt(hakbun));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{ margin:0px auto; width:350px;}");
		out.println("h1{text-align:center}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1>학생 수정</h1>");
		out.println("<div class=row>");
		out.println("<form method=post action=StudentUpdate>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td width=35% class=text-center>이름</td>");
		out.println("<td width=65%>");
		out.println("<input type=hidden name=hakbun value="+hakbun+">");// hakbun을 출력하진 않지만, 값을 전송할 목적으로 hidden 사용
		// insert의 경우, hakbun을 자동생성하기 때문에 필요 없지만, update는 기존의 hakbun을 사용해야하므로, 숨긴 채 값을 넘겨줘야한다.
		out.println("<input type=text name=name size=20 class=input-sm value="+vo.getName()+">");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td width=35% class=text-center>국어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=kor size=20 class=input-sm max=100 min=0 step=1 value="+vo.getKor()+">");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td width=35% class=text-center>영어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=eng size=20 class=input-sm max=100 min=0 step=1 value="+vo.getEng()+">");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td width=35% class=text-center>수학</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=math size=20 class=input-sm max=100 min=0 step=1 value="+vo.getMath()+">");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit value=수정 class=\"btn btn-sm btn-warning\">");
		out.println("<input type=button value=취소 class=\"btn btn-sm btn-danger\" onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 사용자가 보내준 값 받아서 채우기
		// name을 설정하지 않으면, null값이 넘어옴 
		// 항상 name을 설정할 것!
		// servlet에서만 적용되는 것이 아니라, JSP / Spring도 마찬가지
		String name=request.getParameter("name");
		String kor=request.getParameter("kor");
		String eng=request.getParameter("eng");
		String math=request.getParameter("math");
		String hakbun=request.getParameter("hakbun");
		// public String insert(StudentVO vo) => 스프링
		StudentVO vo=new StudentVO();
		vo.setName(name);
		vo.setKor(Integer.parseInt(kor));
		vo.setEng(Integer.parseInt(eng));
		vo.setMath(Integer.parseInt(math));
		vo.setHakbun(Integer.parseInt(hakbun));
		
		StudentDAO dao=StudentDAO.newInstance();
		dao.studentUpdate(vo);
		
		// 이동
		response.sendRedirect("StudentList");
	}

}
