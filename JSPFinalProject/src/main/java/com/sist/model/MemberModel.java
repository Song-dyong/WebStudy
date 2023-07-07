package com.sist.model;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
import com.sist.vo.ZipcodeVO;

public class MemberModel {
	@RequestMapping("member/join.do")
	public String memberJoin(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../member/join.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("member/idcheck.do")
	public String memberIdCheck(HttpServletRequest request, HttpServletResponse response) {
		return "../member/idcheck.jsp";
	}
	@RequestMapping("member/idcheck_ok.do")
	public void memberIdCheckOk(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");	// ajax의 data에 설정한값과 동일한 값을 넣어주어야한다	
		
		MemberDAO dao=MemberDAO.newInstance();
		
		int count=dao.memberIdCheck(id);
		// data ajax로 전송 ==> success:function(result) --> result 안에 넣어줌
		// ajax는 값을 요청하므로, 화면을 굳이 변경하지 않아도 된다. 보이
		try {
			PrintWriter out=response.getWriter();
			out.println(count);
		} catch (Exception e) {
		}
		
	}
	@RequestMapping("member/postfind.do")
	public String memberPostFind(HttpServletRequest request, HttpServletResponse response) {
		//에이잭스가 아닌 경우, 화면을 변경해야하므로, 리턴으로 jsp파일을 전송해야한다 
		return "../member/postFind.jsp"; // 화면 출력
	}
	
	@RequestMapping("member/postfind_result.do")
	public String memberPostFindResult(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			
		}
		String dong=request.getParameter("dong");
		MemberDAO dao=MemberDAO.newInstance();
		int count=dao.postFindCount(dong);
		List<ZipcodeVO> list=dao.postFindData(dong);
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		return "../member/postfind_result.jsp";
	}
	
	@RequestMapping("member/join_ok.do")
	public String memberJoinOk(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
		}
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		String birthday=request.getParameter("birthday");
		String email=request.getParameter("email");
		String post=request.getParameter("post");
		String addr1=request.getParameter("addr1");
		String addr2=request.getParameter("addr2");
		String phone1=request.getParameter("phone1");
		String phone=request.getParameter("phone");
		String content=request.getParameter("content");
		
		MemberVO vo=new MemberVO();
		vo.setId(id);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setSex(sex);
		vo.setBirthday(birthday);
		vo.setPost(post);
		vo.setAddr1(addr1);
		vo.setAddr2(addr2);
		vo.setPhone(phone1+"-"+phone);
		vo.setContent(content);
		
		MemberDAO dao=MemberDAO.newInstance();
		dao.memberInsert(vo);
		
		//화면이동
		return "redirect:../main/main.do";
		
	}
	
	@RequestMapping("member/login.do")
	public void memberLogin(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		MemberDAO dao=MemberDAO.newInstance();
		MemberVO vo=dao.memberLogin(id, pwd);
		HttpSession session=request.getSession();
		// 로그인이 되면, 사용자의 일부 정보를 세션에 저장.
		if(vo.getMsg().equals("OK")) {
			session.setAttribute("id", vo.getId());
			session.setAttribute("name", vo.getName());
			session.setAttribute("sex", vo.getSex());
			session.setAttribute("admin", vo.getAdmin());
			// 전역변수 => 모든 jsp에서 사용이 가
		}
		// 결과값을 전송 => ajax
		try {
			PrintWriter out=response.getWriter();
			// 사용자 브라우저에서 읽어가는 메모리 공간.
			out.println(vo.getMsg()); // NOID, NOPWD, OK
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@RequestMapping("member/logout.do")
	public String memberLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		session.invalidate();
		return "redirect:../main/main.do";
	}
	
}
