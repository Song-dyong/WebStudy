package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
public class CategoryModel implements Model {
	// 			request   	request
	// Controller => Model 		=> 		JSP
	//		Call By Reference
	/*
		Class A{
			int aa;
		}
		public void set (A a){
			a.aa=100;
		}
		
		A b=new A();
		set(b)			A의 메소드 set => A a 가 매개변수로 들어왔을 때, a의 변수 aa에 100이라는 값을 채움 => A 객체 b를 생성해서 set(b)를 했을 때, 
		b.aa => 100이 됨 
		
		DispatcherServlet의 request를 Model로 보내서 값을 채운 뒤,, 그 값을 JSP로 전송
		Call By Reference
	
	*/
	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("CategoryModel: "+request);
		FoodDAO dao=FoodDAO.newInstance();
		List<CategoryVO> list=dao.foodCategoryData();
		// request => DispatcherServlet의 request 
		request.setAttribute("list", list);
		return "food/category.jsp";
	}

}
