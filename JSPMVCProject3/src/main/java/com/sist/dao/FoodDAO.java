package com.sist.dao;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/*
	1. 드라이버 등록
		=> 오라클과 연결해주는 라이브러리 (ojdbc8.jar)
		OracleDriver --> 메모리 할당
	2. 오라클 연결 
		Connection
	3. SQL문장을 전송
		PreparedStatement
	4. SQL문장 실행요청
		1) executeUpdate() = INSERT, UPDATE, DELETE
		   -------------- 자동 COMMIT (AutoCommit) 
		2) executeQuery() = SELECT
		   -------------- 결과값을 가지고 온다
		   				  -----
		   				 ResultSet
		   		ResultSet
		   		 String sql="SELECT id,name,sex,age "
		   		--------------------------------------
					id		name	sex		age
		   		--------------------------------------
		   			aaa		홍길동	남자		20	| --> first() => next()
		   											커서 위치 변경	/ 위치 변경 후, 값 읽기
		   		getString(1) gS(2) gS(3) getInt(4)	--> 컬럼 인덱스로 값 읽기
		   	getString("id") gS("name") gS("sex") getInt("age") --> 컬럼명으로 값 읽기
		   		--------------------------------------
		   			bbb		심청이	여자		23
		   		getString(1) gS(2) gS(3) getInt(4)
		   		--------------------------------------
					ccc		박문수	남자		27	| --> last() => previous() 
													커서 위치 변경	 / 위치 변경 후, 값 읽기
		   		getString(1) gS(2) gS(3) getInt(4)
		   		--------------------------------------
		   		| --> 메모리 할당시 커서 위치 --> 실행된 위치로 커서 옮기기 rs.next()
	5. 닫기 
		생성의 반대로 닫기
		rs.close() => ps.close() => conn.close()
	------------------------------------------------------- 오라클 연결 과정.
														   (Servlet => JSP)
		

*/
public class FoodDAO
{
	// 기능 -> INSERT (데이터 수집)   cf) 네트워크 프로젝트에서는 파일에 저장 --> 오라클에 저장하기 
	private Connection conn;		// 오라클 연결 객체 (데이터베이스 연결)
	private PreparedStatement ps;	// SQL문장 전송, 결과값 읽기 객체
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// MySQL => jdbc:mysql://localhost/mydb
	private static FoodDAO dao;	// 싱글턴 패턴
	// DAO 객체를 한 개만 사용 가능하게 만듦 --> new를 통해 메모리를 계속 할당하지 않도록 static 필드에 dao메모리를 하나만 생성해서 사용
	// 드라이버 설치 => 소프트웨어 (메모리 할당 요청) Class.forName()
	// 클래스의 정보를 전송
	// 드라이버 설치는 1번만 수행
	public void getConnection() {
		// Connection 객체 주소를 메모리에 저장 
		// 저장된 공간 => Pool 영역 (디렉토리 형식으로 저장) => JNDI (Java Naming Directory Interface)
		// 루트 => 저장 공간
		// java://env//comp => C드라이버 => jdbc/oracle(설정했던 이름)
		try {
			// 1. 탐색기를 연다
			Context init=new InitialContext();
			// 2. C드라이버에 연결
			Context cdriver=(Context)init.lookup("java://comp/env");
			// lookup => 문자열(key)를 통해 객체 찾아오는 메소드 (RMI - Remote Method Invocation: 원격 메소드 호출)
			// 3. Connection 객체 찾기
			DataSource ds=(DataSource)cdriver.lookup("jdbc/oracle");
			// 4. Connection 주소를 연결
			conn=ds.getConnection();
			// 282page의 내용
			// => 오라클 연결 시간 단축 
			// => Connection 객체의 수가 일정하므로, 관리 용이
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Connection 객체 사용 후에, 반환
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 싱글턴 객체 만들기
	public static FoodDAO newInstance() {
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	public void foodCategoryInsert(CategoryVO vo)
	{
		try
		{
			// 1. 연결 
			getConnection();
			// 2. SQL 문장 생성
			String sql="INSERT INTO food_category VALUES("+
						"fc_cno_seq.nextval,?,?,?,?)";
			/*
				"'"+vo.getTitle()+"','"
				INSERT ~ VALUES('홍길동', 서울) --> '가 빠지면 오류
				' 하나씩 넣기 불편하기 때문에, ?에 나중에 값을 채우는 방식 사용
			*/
			// 3. SQL 문장 오라클로 전송
			ps=conn.prepareStatement(sql);
			// 3-1. ?에 값 채우기
			ps.setString(1, vo.getTitle()); // "'"+vo.getTitle()+"'" 1은 첫 번째 물음표
			ps.setString(2, vo.getSubject());	// 단점: 번호가 잘못되면 오류 / 데이터형이 다르면 오류 (IN, OUT~)
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			// 4. SQL 문장 실행 명령 --> SQL문장 작성 
			ps.executeUpdate();	
		} catch (Exception e)
		{
			e.printStackTrace(); // 
		}
		finally {
			disConnection();	// 오라클 연결 해제 
		}
	}
	// 1-1 실제 맛집 정보 저장
	public void foodDataInsert(FoodVO vo)
	{
		try {
			// 1. 오라클 연결
			getConnection();
			// 2. SQL문장 만들기 
			String sql="INSERT INTO food_house VALUES("
						+"fh_fno_seq.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			// 4. ?에 값 채우기
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getName());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getPhone());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getParking());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setInt(11, vo.getGood());
			ps.setInt(12, vo.getSoso());
			ps.setInt(13, vo.getBad());
			ps.setString(14, vo.getPoster());
			
			// 5. 실행 요청
			ps.executeUpdate(); // 자동 커밋
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	// 2. SELECT => 전체 데이터 읽기 --> 30개 (한 개당 CategoryVO로 묶기)
	//		--> Collection or 배열에 데이터를 모아야 함 --> 컬렉션(리스트) 사용
	// ---> 브라우저에 30개 전송
	// 브라우저 <---> 자바 <---> 오라클
	public List<CategoryVO> foodCategoryData()
	{
		List<CategoryVO> list=new ArrayList<CategoryVO>();
		try {
			// 1. 오라클 연결
			getConnection();
			// 2. sql 문장 만들기
			String sql="SELECT cno, title, subject, poster, link FROM food_category ORDER BY cno"; 
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			// 4. 실행 후 결과값 받기
			ResultSet rs=ps.executeQuery();
			// 5. rs에 있는 데이터를 list에 저장
			while(rs.next())
			{
				CategoryVO vo=new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String poster=rs.getString(4);
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setLink("https://www.mangoplate.com"+rs.getString(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			// 오라클 닫기
			disConnection();
		}
		return list;
	}
	
	public CategoryVO foodCategoryInfoData(int cno) {
		CategoryVO vo=new CategoryVO();
		try {
			getConnection();
			String sql="SELECT title, subject "
					+ "FROM food_category "
					+ "WHERE cno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}

	// 카테고리에 해당되는 맛집 읽기
	public List<FoodVO> foodCategoryListData(int cno){
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			getConnection();
			String sql="SELECT fno, name, score, poster, address, phone, type "
					+ "FROM food_house "
					+ "WHERE cno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setPhone(rs.getString(6));
				vo.setType(rs.getString(7));
				String poster=rs.getString(4);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				String address=rs.getString(5);
				address=address.substring(0,address.indexOf("지번"));
				vo.setAddress(address);
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	// 상세보기
	public  FoodVO foodDetailData(int fno) {
		FoodVO vo=new FoodVO();
		try {
			getConnection();
			String sql="SELECT fno,cno,name,score,poster,address,type,parking,time,menu,phone,price "
					+ "FROM food_house "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setPoster(rs.getString(5));
			vo.setAddress(rs.getString(6));
			vo.setType(rs.getString(7));
			vo.setParking(rs.getString(8));
			vo.setTime(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPhone(rs.getString(11));
			vo.setPrice(rs.getString(12));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		return vo;
	}
	
}
