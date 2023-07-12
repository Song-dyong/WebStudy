package com.sist.model;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class ReserveModel {
	@RequestMapping("reserve/diary.do")
	public String diaryData(HttpServletRequest request, HttpServletResponse response) {
		
		String fno=request.getParameter("fno");
		String strYear = request.getParameter("year");
		String strMonth = request.getParameter("month");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		String today = sdf.format(date);
		StringTokenizer st = new StringTokenizer(today, "-");
		String sy = st.nextToken();
		String sm = st.nextToken();
		String sd = st.nextToken();
		if (strYear == null)
			strYear = sy;
		if (strMonth == null)
			strMonth = sm;

		int year = Integer.parseInt(strYear);
		int month = Integer.parseInt(strMonth);
		int day = Integer.parseInt(sd);
		// 요일
		String[] strWeek = { "일", "월", "화", "수", "목", "금", "토" };

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1); // 1일자 계산

		int week = cal.get(Calendar.DAY_OF_WEEK); // 요일 구하기
		int lastday = cal.getActualMaximum(Calendar.DATE); // 각 달의 마지막 날

		week = week - 1;

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("week", week);
		request.setAttribute("lastday", lastday);
		request.setAttribute("strWeek", strWeek);
		// 오라클 데이터 읽기
		int[] rday=new int[32];
		ReserveDAO dao=ReserveDAO.newInstance();
		String r=dao.foodReserveDay(Integer.parseInt(fno));
		st=new StringTokenizer(r,",");
		while(st.hasMoreTokens()) {
			int a=Integer.parseInt(st.nextToken());
			// 오늘 날짜 이전은 예약 불가능으로 설정
			if(a>=day) {
				rday[a]=1;
			}
		}
		request.setAttribute("rday", rday);
		return "../reserve/diary.jsp";
	}
	@RequestMapping("reserve/food_list.do")
	public String food_list(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {}
		
		String type=request.getParameter("type");
		if(type==null)
			type="한식";
		ReserveDAO dao=ReserveDAO.newInstance();
		List<FoodVO> list=dao.foodReserveDate(type);
		request.setAttribute("list", list);
		
		return "../reserve/food_list.jsp";
	}
	@RequestMapping("reserve/reserve_main.do")
	public String reserve_main(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("main_jsp", "../reserve/reserve_main.jsp");
		return "../main/main.jsp";
	}
	@RequestMapping("reserve/time.do")
	public String reserve_time(HttpServletRequest request, HttpServletResponse response) {
		
		String day=request.getParameter("day");
		ReserveDAO dao=ReserveDAO.newInstance();
		String times=dao.reserve_day_time(Integer.parseInt(day));
		StringTokenizer st=new StringTokenizer(times,",");
		List<String> list=new ArrayList<String>();
		while(st.hasMoreTokens()) {
			String time=dao.reserve_get_time(Integer.parseInt(st.nextToken()));
			list.add(time);
		}
		request.setAttribute("list", list);
		return "../reserve/time.jsp";
	}
	
	@RequestMapping("reserve/inwon.do")
	public String reserve_inwon(HttpServletRequest request, HttpServletResponse response) {
		return "../reserve/inwon.jsp";
	}
	
	@RequestMapping("reserve/reserve_ok.do")
	public String reserve_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String fno=request.getParameter("fno");
		String rday=request.getParameter("rday");
		String rtime=request.getParameter("rtime");
		String inwon=request.getParameter("inwon");
		
		// DAO 연결
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		ReserveVO vo=new ReserveVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setId(id);
		vo.setRday(rday);
		vo.setRtime(rtime);
		vo.setInwon(inwon);
		
		ReserveDAO dao=ReserveDAO.newInstance();
		dao.reserve_ok(vo);
		
		return "redirect:../mypage/mypage_reserve.do";
	}

	@RequestMapping("reserve/reserve_info.do")
	public String reserve_info(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");	// reserve info
		String fno=request.getParameter("fno");	// food_house info
		
		// DataBase
		ReserveDAO dao=ReserveDAO.newInstance();
		ReserveVO rvo=dao.reserveInfoData(Integer.parseInt(no));
		FoodVO fvo=dao.reserveFoodInfoData(Integer.parseInt(fno));
		
		request.setAttribute("rvo", rvo);
		request.setAttribute("fvo", fvo);
		
		return "../reserve/reserve_info.jsp";
	}
	// reserve info 
}
