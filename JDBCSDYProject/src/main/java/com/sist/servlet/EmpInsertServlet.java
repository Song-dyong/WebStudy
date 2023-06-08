package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// insert.jsp / insert_ok.jsp => service()
// join.jsp(화면폼을 보여주는 역할) / join_ok.jsp(데이터 베이스 연동 역할)
// 실행할 때, 자바가 우선순위 (먼저 수행) 
/*
	회원가입 클릭시 자바의 insert문장이 먼저 수행되어버리기 때문에, 
	폼을 먼저 보여주기위해 doGet에서 폼을 보여준 뒤, post를 통해 입력된 데이터를 처리해야함
*/
import java.util.*;
import com.sist.dao.*;
@WebServlet("/EmpInsertServlet")
public class EmpInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 입력폼 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		EmpDAO dao=new EmpDAO();
		List<Integer> mList=dao.empGetMgrData();
		List<Integer> dList=dao.empGetDeptnoData();
		List<Integer> sList=dao.empGetSalData();
		List<String> jList=dao.empGetJobData();
		
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
		out.write("<h1>사원 등록</h1>");
		out.write("<div class=row>");
		// EmpInsertServlet이 가지고 있는 doPost를 호출하라는 뜻
		out.write("<form method=post action=EmpInsertServlet>");
		out.write("<table class=table>");
		out.write("<tr>");
		out.write("<th width=20% class=\"text-right success\">이름</th>");
		out.write("<td width=80%><input type=text name=ename class=input-sm size=15></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th width=20% class=\"text-right success\">직위</th>");
		out.write("<td width=80%>");
		out.write("<select name=job class=input-sm>");
		for(String s:jList)
		{
			out.write("<option>"+s+"</option>");
		}
		out.write("</select>");
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th width=20% class=\"text-right success\">사수번호</th>");
		out.write("<td width=80%>");
		out.write("<select name=mgr class=input-sm>");
		for(int i:mList)
		{
			if(i==0) continue;	// 0일 경우, 제외
			out.write("<option>"+i+"</option>");
		}
		out.write("</select>");
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th width=20% class=\"text-right success\">급여</th>");
		out.write("<td width=80%>");
		out.write("<select name=sal class=input-sm>");
		for(int i:sList)
		{
			out.write("<option>"+i+"</option>");
		}
		out.write("</select>");
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th width=20% class=\"text-right success\">성과급</th>");
		out.write("<td width=80%><input type=number name=comm class=input-sm size=15 max=500 min=100 step=50></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th width=20% class=\"text-right success\">부서번호</th>");
		out.write("<td width=80%>");
		out.write("<select name=deptno class=input-sm>");
		for(int i:dList)
		{
			out.write("<option>"+i+"</option>");
		}
		out.write("</select>");
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td colspan=2 class=text-center>");
		out.write("<button class=\"btn btn-sm btn-primary\">등록</button>&nbsp;");
		out.write("<input type=button value=취소 class=\"btn btn-sm btn-primary\" onclick=\"javascript:history.back()\">");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		// form태그를 닫으면서 안의 데이터를 post 형식으로 doPost에 보냄
		out.write("</form>");
		out.write("</div>");
		out.write("</div>");
		out.write("</body>");
		out.write("</html>");
	}
	
	// 입력된 데이터를 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 한글변환 (encoding => decoding)
		request.setCharacterEncoding("UTF-8");
		// 2. 값을 받는다
		String ename=request.getParameter("ename");
		String job=request.getParameter("job");
		String mgr=request.getParameter("mgr");
		String sal=request.getParameter("sal");
		String comm=request.getParameter("comm");
		String deptno=request.getParameter("deptno");
		
		EmpVO vo = new EmpVO();
		vo.setEname(ename);
		vo.setJob(job);
		vo.setMgr(Integer.parseInt(mgr));
		vo.setSal(Integer.parseInt(sal));
		vo.setComm(Integer.parseInt(comm));
		vo.setDeptno(Integer.parseInt(deptno));
		
		// 오라클로 전송
		EmpDAO dao=new EmpDAO();
		dao.empInsert(vo);
		
		// 화면 이동
		response.sendRedirect("MainServlet");
	}

}
