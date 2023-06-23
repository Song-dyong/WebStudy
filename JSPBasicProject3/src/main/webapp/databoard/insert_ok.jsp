<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*, java.io.*"%>
<%@ page import=" com.oreilly.servlet.*" %>
<%@ page import=" com.oreilly.servlet.multipart.*" %>
<%
	// _ok.jsp : 기능처리 (member_ok , update_ok ...)
	// 데이터베이스 처리 => list.jsp로 이동 
	// 1. 한글 처리
	request.setCharacterEncoding("UTF-8");
	// 1-1. 파일 업로드 클래스 생성 
	String path="c://download";
	int size=1024*1024*100;	// 최대 파일 크기 100mb 설정
	String enctype="UTF-8";		// 한글 파일명 가능하도록 설정
	MultipartRequest mr=new MultipartRequest(request, path, size, enctype, new DefaultFileRenamePolicy());
											// 매개변수 多 ==> 업로드용			---------- 같은 파일 중복 방지를 위해 이름 변경 1,2,3..
	
	// 2. 요청데이터 받기
	String name=mr.getParameter("name");			// multipartRequest는 request를 포함하므로, mr로 받기
	String subject=mr.getParameter("subject");
	String content=mr.getParameter("content");
	String pwd=mr.getParameter("pwd");
	// 3. vo에 데이터값 묶기
	DataBoardVO vo=new DataBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	// 오리지널 파일은 사용자가 보내준 파일
//	String filename=mr.getOriginalFileName("upload");
	// 밑이 실제 저장된 파일
	String filename=mr.getFilesystemName("upload");

	if(filename==null){		// 업로드가 안된 상태
		vo.setFilename("");
		vo.setFilesize(0);
	}else{					// 업로드가 된 상태
		File file=new File(path+"\\"+filename);
	vo.setFilename(filename);
	vo.setFilesize((int)file.length());
		
	}
	// 4. DAO로 전송 
	DataBoardDAO dao=DataBoardDAO.newInstance();
	dao.databoardInsert(vo);
	// 5. 화면 이동 (list.jsp)
	response.sendRedirect("list.jsp");
	
%>