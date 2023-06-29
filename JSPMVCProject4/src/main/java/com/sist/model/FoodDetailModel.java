package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;

public class FoodDetailModel implements Model{

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		FoodDAO dao=FoodDAO.newInstance();
		String fno=request.getParameter("fno");
		// db에서 값 가져오기
		FoodVO vo=dao.foodDetailData(Integer.parseInt(fno));
		String address=vo.getAddress();
		String addr1=address.substring(0, address.lastIndexOf("지"));
		String addr2=address.substring(address.lastIndexOf("지")+3);
		String temp=addr1.trim().substring(addr1.indexOf(" "));
		String addr3=temp.trim().substring(0,temp.trim().indexOf(" "));
		System.out.println(addr3);
		request.setAttribute("vo", vo);
		request.setAttribute("addr1", addr1);
		request.setAttribute("addr2", addr2);
		request.setAttribute("addr3", addr3);
		
		return "food/food_detail.jsp";
	}

}
