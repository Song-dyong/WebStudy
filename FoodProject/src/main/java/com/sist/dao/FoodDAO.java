package com.sist.dao;

import java.sql.*;
import java.util.*;
import com.sist.vo.*;

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
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e){}
	}
	
	//오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			// 오라클 전송 : conn hr/happy
		} catch (Exception e){}
	}
	// 오라클 연결 해제(종료)
	public void disConnection()
	{
		try
		{
			if(ps!=null)
			{
				ps.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
			// 오라클 전송: exit
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	// DAO객체를 1개만 생성해서 사용 --> 메모리 누수현상 방지 (싱글턴 패턴)
	// 싱글턴 / 팩토리 패턴 ==> 면접에서 자주 나오는 질문 (스프링)
	public static FoodDAO newInstance()
	{
		// newInstance() , getInstance() ==> 싱글턴
		// Calendar cal=Calendar.getInstance(); --> 싱글턴 예시
		if(dao==null)
		{
			dao=new FoodDAO();
		}
		return dao;		// 생성이 안되어있으면, 생성하고 / 생성되어있으면 생성된 dao를 리턴하기
	}
	// --------------------------------------------------- 기본 세팅 (모든 DAO) 
	// 기능  
	// 1. 데이터 수집(INSERT)
	/*
	 	Statement 	=> SQL제작시, 생성과 동시에 데이터가 추가되어야 함
	 					"'"+NAME+"','"+SEX+"','" ... (오류 확률 高)
	 	PreparedStatement => 미리 SQL문장을 만들고, 나중에 값을 채움
	 						(default) 
	 	CallableStatement => Procedure 호출
	 
	 
	*/
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
	// 3. 상세보기 => WHERE
	
}
