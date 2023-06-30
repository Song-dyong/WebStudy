package com.sist.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// [서귀포] 자유로운 다이빙을 원한다면 여기서 2회! 누디다이브 펀다이빙!

/*
		웹에서 사용자 요청 => servlet / jsp
		servlet: 화면 출력은 하지 않는다 (연결)
		화면: jsp (View)
		MVC에서
			Controller: Servlet으로 만들어짐
				Spring : DispatcherServlet
				Struts : ActionServlet
				Struts2 : FilerDispatcher
			Model : 요청 처리를 하는 부분 (Java)
			View : 화면 출력 (JSP)
		
		MVC 동작과정
		----------
		1. 요청 (JSP)	===> DispatcherServlet을 찾음
			list.do					|
			insert.do				|
			---------			서버에서 받을 수 있는 부분은 URL, URI
								URL, URI를 통해 해당 Model을 찾아야함	
								URL에서 ~~.do라는 파일을 보내면, 앞에 무슨 단어가 붙던 *.do에 의해 DispatcherServlet이 호출됨
								=> DispatcherServlet에서 ~~.do에 해당하는 모델과 연결 (request를 통해 필요한 데이터를 가져온 뒤, JSP에 전송)
									a.do?no=3  a.do에서 no에 3이라는 값을 request에 담아서 전송 
									(?뒤의 부분은 uri에 포함되지 않고, request에 담겨서 전송된다)
								
		2. DispatcherServlet (Controller)
		 => Front Controller : 요청 => Model 연결 => request를 전송
		 							  ---------
		 							  요청 처리 기능
		 
		3. MVC 목적 : 보안 (JSP => 배포 : 소스가 통째로 보여짐)
					역할분담 (Front-JSP / Back-Java, dao)
					자바와 HTML을 분리하는 이유
					---
					확장성 , 재사용 , 변경이 쉽다 (JSP는 한 번 사용하면 버려짐)
					
		4. 동작 순서
							~~.do?request					request 
			JSP (링크, 버튼클릭) --------- DispatcherServlet ---------- Model (dao <--> 오라클)
																	결과값을 request에 담아서 보내줌
																	request.setAttribute()
								request						request	
			request.getAttribute() => ${} (EL)
		
		5. DispatcherServlet은 최대한 고정
		
		6. 등록 (Model클래스) => XML로 셋팅 (메뉴판)
		
		7. 메소드 찾기 => 어노테이션 (메소드 자동 호출)
		--------------------------------------

	아래의 코딩은 Spring DispatcherServlet에 이미 만들어진 상태
	=> 메커니즘에 대한 이해 필요 => 암기x, 이해해서 공부하기 ㄱㄱ

*/
import java.net.*;
import java.util.*;
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> clsList=new ArrayList<String>();
	
	// 초기화 => XML에 등록된 클래스 읽기 (메뉴) 
	public void init(ServletConfig config) throws ServletException {
		try {
			URL url=this.getClass().getClassLoader().getResource(".");
			File file=new File(url.toURI());
			//System.out.println(file.getPath());
			String path=file.getPath();	
			//System.out.println("1. "+path);
			path=path.replace("\\", File.separator);
			//System.out.println("2. "+path);
			// separator => window \\ , Mac /  운영체제에 따라 경로를 자동으로 변경해줌
			path=path.substring(0,path.lastIndexOf(File.separator));
			//System.out.println("3. "+path);
			path=path+File.separator+"application.xml";
			
			// XML 파싱
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			// 파서기 (XML => DocumentBuilder , HTML => Jsoup)
			DocumentBuilder db=dbf.newDocumentBuilder();
			// 파서하기 (저장은 Document에)
			Document doc=db.parse(new File(path));
			// 필요한 데이터 읽기
			// root태그 => beans
			Element beans = doc.getDocumentElement();
			System.out.println(beans.getTagName());
			
			// 같은 태그를 묶어서 사용
			NodeList list=beans.getElementsByTagName("bean");
			for(int i=0;i<list.getLength();i++) {
				// bean 태그를 1개씩 가져옴 => id와 class에 설정된 값들을 매칭시키기 위해 ==> main.do => com.sist.model.MainModel 찾기 
				Element bean=(Element)list.item(i);
				String id=bean.getAttribute("id");
				String cls=bean.getAttribute("class");
				System.out.println(id+": "+cls);
				clsList.add(cls);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String path=request.getRequestURI();
		path=path.substring(request.getContextPath().length()+1);
		// http://localhost
		// /JSPMVCFinalProject/food/category.do
		// getContextPath() => /JSPMVCFinalProject
		// request.getContextPath().length()+1 =>  food/category.do
		for(String cls:clsList) {
			// Class 정보 읽기 => 리플렉션
			Class<?> clsName=Class.forName(cls);
			// 메모리 할당
			Object obj=clsName.getDeclaredConstructor().newInstance();
			// 메소드 읽기		clsName에 선언된 모든 메소드 가져오기 
			Method[] methods=clsName.getDeclaredMethods();	
			for(Method m:methods) {
				RequestMapping rm=m.getAnnotation(RequestMapping.class);
				if(rm.value().equals(path)) {
					String jsp=(String)m.invoke(obj, request, response);
					if(jsp==null) {	// return이 void일 경우	=> ajax에서 사용 多
						return;
					}else if(jsp.startsWith("redirect:")) {
						// sendRedirect일 경우,
						response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
					}else {
						// forward일 경우,
						RequestDispatcher rd=request.getRequestDispatcher(jsp);
						rd.forward(request, response);
					}
					return;
				}
			}
		}
		}catch(Exception e) {}
	}

}
