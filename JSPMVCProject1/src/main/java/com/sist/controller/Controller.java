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
/*					넘겨줄 수 있는 방법이 2개 뿐 
		a.java	=>  request / session => a.jsp 
		Model 			Controller		View		=> MVC 구조 
					-----------------
					Servlet				cf) jsp로 만들면, 정보들이 노출되기 때문에, Servlet 파일로 가리기
		
		jsp 파일 수만큼 java 파일이 존재 

		
		사용자 요청 ==> Controller가 요청을 받아서, 해당 Model을 찾음 (if... else if..) \
			==> Model에서 요청을 처리함 => 처리한 결과를 request 혹은 session에 담기 
			==> 담아진 결과(request,session)를 Controller에서 받음
			==> 해당 JSP를 찾아서 request, session을 전송 
			==> jsp에서 해당 값들을 통해 화면 출력 
		
		 단! Controller 하나로 처리하면, 여러 명의 요청에 대해 속도가 느려질 수 있음
		 	=> Controller 다수 작성을 통해 해결 => MSA (Micro Service Architecture)
		 	
		 
		 주방 	 => 		서빙 			=> 	손님의 테이블
	(자바 , Model)	(Servlet , Controller)	   (JSP , View)
		 


*/
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd=request.getParameter("cmd");
		String jsp="";
		if(cmd.equals("list")) {
			ListModel model=new ListModel();
			jsp=model.execute(request);
		}else if(cmd.equals("insert")) {
			InsertModel model=new InsertModel();
			jsp=model.execute(request);
		}else if(cmd.equals("update")) {
			UpdateModel model=new UpdateModel();
			jsp=model.execute(request);
		}else if(cmd.equals("delete")) {
			DeleteModel model=new DeleteModel();
			jsp=model.execute(request);
		}
		
		RequestDispatcher rd=request.getRequestDispatcher("view/"+jsp);
		rd.forward(request, response);
		
	}

}
