<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
		request / response / session => 웹 개발의 핵심
		------------------	--------- 프로그램 실행하는 동안 저장
		session은 서버에 저장: 사용자의 정보를 지속적으로 관리
		-------- 1) 장바구니, 결제 , 예약 , 추천 ...
				 2) session에 저장이 되면 모든 JSP에서 사용 가능 (전역변수)
				 
		클래스명 => HttpSession
					클라이언트마다 1개 생성 => id가 부여 (구분자)
										  -- sessionId => 채팅, 상담 .. 
		주요메소드
			1) String getId(): 세선마다 저장되는 구분자
			2) setMaxinactiveInterval(): 저장 기간 설정 
									default값은 1800 (30분)
			3) isNew(): ID가 할당 여부 확인
				=> 장바구니
			4) invalidate(): session에 저장된 모든 내용을 지운다
							로그아웃시 처리
			5) setAttribute(): session에 정보 저장
			6) getAttribute(): 저장된 데이터 읽기
			7) removeAttribute(): 저장된 데이터의 일부 삭제
			
				
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>HttpSession(session) - 177page</h1>
</body>
</html>