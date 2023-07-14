package com.sist.common;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.manager.*;

import javax.servlet.http.HttpServletRequest;

public class CommonModel {
	public static void commonRequestData(HttpServletRequest request) {
		// footer
		FoodDAO dao=FoodDAO.newInstance();
		// => 공지사항 => 최신 뉴스
		List<NewsVO> nList=NewsManager.newsSearchData("맛집");
		for(NewsVO vo:nList) {
			String title=vo.getTitle();
			if(title.length()>17) {
				title=title.substring(0,16)+"...";
				vo.setTitle(title);
			}
			vo.setTitle(title);
		}
		request.setAttribute("nList", nList);
		// => 방문 맛집 
		List<FoodVO> fList=dao.foodTop7();
		request.setAttribute("fList", fList);
		
	}
}
