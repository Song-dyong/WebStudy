package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
@WebServlet("/EmpDetailServlet")
/*
	EmpDetailServlet => detail.jsp : jsp는 메소드 안에 코딩 servlet의 doGet메소드 내에 코딩하는 것과 같음
					Servlet은 하나의 클래스이며, doGet과 doPost 메소드 안에 코딩
					JSP는 그 자체가 메소드 + out.write가 생략되기 때문에 편함 
					단! jsp는 자바 코딩을 <% %> 안에 해야하므로, 자바와 html을 정확히 구분해야함
					
						ex)
							<table>
								<tr>
									<th>2단</th>
									<th>3단</th>
									<th>4단</th>
									<th>5단</th>
									<th>6단</th>
									<th>7단</th>
									<th>8단</th>
									<th>9단</th>
								</tr>
								<tr>
								<%
									for(int i=1;i=<9;i++)
									{
										for(int j=2;j<=9;j++}
										{
										%>
											<td><% j+"x"+i+"="+(j*i)%></td>
										<%
										}
									}
								%>
							</table>
							
							JSTL
							<c:forEach var="i" begin="1" end="9">
								<c:forEach var="j" begin="2" end="9">
*/
public class EmpDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 출력 위치
		response.setContentType("text/html;charset=UTF-8");
		// 사용자가 보내준 데이터 받기
		String empno=request.getParameter("empno");
		// String empno = "7788"
		EmpDAO dao=new EmpDAO();
		EmpVO vo=dao.empDetailData(Integer.parseInt(empno));
		// vo 데이터 출력
		PrintWriter out=response.getWriter();
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.write("<style>");
		out.write(".container{ margin-top:50px }");
		out.write(".row{ margin: 0px auto; width: 600px }");
		out.write("h1{text-align:center}");
		out.write("</style>");
		out.write("</head>");
		out.write("<body>");
		out.write("<div class=container>");
		out.write("<h1>"+vo.getEname()+"사원의 개인정보</h1>");
		out.write("<div class=row>");
		out.write("<table class=table>");
		out.write("<tr>");
		out.write("<th class=\"text-center success\" width=20%>사번</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getEmpno()+"</td>");
		out.write("<th class=\"text-center success\" width=20%>이름</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getEname()+"</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center success\" width=20%>직위</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getJob()+"</td>");
		out.write("<th class=\"text-center success\" width=20%>입사일</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getDbday()+"</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center success\" width=20%>급여</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getDbsal()+"</td>");
		out.write("<th class=\"text-center success\" width=20%>성과급</th>");
		out.write("<td class=\"text-center\" width=30%>"+(vo.getComm()==0?"":vo.getComm())+"</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center success\" width=20%>사수번호</th>");
		out.write("<td class=\"text-center\" width=30%>"+(vo.getMgr()==0?"":vo.getMgr())+"</td>");
		out.write("<th class=\"text-center success\" width=20%>부서명</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getDvo().getDname()+"</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center success\" width=20%>근무지</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getDvo().getLoc()+"</td>");
		out.write("<th class=\"text-center success\" width=20%>호봉</th>");
		out.write("<td class=\"text-center\" width=30%>"+vo.getSvo().getGrade()+"</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td colspan=4 class=text-right>");
		out.write("<a href=MainServlet?mode=4&empno="+empno+" class=\"btn btn-xs btn-danger\">수정</a>&nbsp;");
		out.write("<a href=# class=\"btn btn-xs btn-success\">삭제</a>&nbsp;");
		out.write("<a href=MainServlet class=\"btn btn-xs btn-primary\">목록</a>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</div>");
		out.write("</div>");
		out.write("</body>");
		out.write("</html>");
	}

}
