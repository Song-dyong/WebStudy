package com.sist.controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.model.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
// DispatcherServlet => Spring Controller의 이름 
@WebServlet("*.do")
// main.do , category.do, food_list.do, food_detail.do....
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Model> clsMap = new HashMap<String, Model>();

	public void init(ServletConfig config) throws ServletException {
		try {
			File file=new File("C:\\WebDev\\WebStudy\\JSPMVCProject4\\src\\main\\webapp\\WEB-INF\\cmd.xml");
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=dbf.newDocumentBuilder();
			// XML 파서기 (xml을 읽어오는 역할)
			// doc => 파싱한 내용이 저장되는 공간
			Document doc=db.parse(file);
			// 루트 가져오기 
			Element beans=doc.getDocumentElement();
			// bean으로 설정된 애들 긁어오기
			NodeList list=beans.getElementsByTagName("bean");
			for(int i=0;i<list.getLength();i++) {
				Element bean=(Element)list.item(i);
				String id=bean.getAttribute("id");
				String cls=bean.getAttribute("class");
				
				Class clsName=Class.forName(cls);
				Object obj=clsName.getDeclaredConstructor().newInstance();
				
				clsMap.put(id, (Model)obj);
			}
		
			
		} catch (Exception e) {}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd=request.getRequestURI();
		cmd=cmd.substring(cmd.lastIndexOf("/")+1);	// category.do
		Model model=clsMap.get(cmd);
		//System.out.println("Controller: "+request);
		String jsp=model.handlerRequest(request, response);
		RequestDispatcher rd=request.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}

}
