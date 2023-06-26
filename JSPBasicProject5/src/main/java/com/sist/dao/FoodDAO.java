package com.sist.dao;
import java.sql.*;
import java.util.*;
// DBCP 
import javax.sql.*;
import javax.naming.*;

public class FoodDAO {
	// 연결 객체 얻기
	private Connection conn;
	// SQL 송수신
	private PreparedStatement ps;
	// 싱글턴
	private static FoodDAO dao;
	// 출력 갯수 확인 
	private final int ROW_SIZE=20;
	// Pool 영역에서 Connection 객체 얻기
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
	
	// 기능 영역 => 기존과 동일 (오라클 연결 후, SQL 문장 실행)
	public List<FoodBean> foodListData(int page){
		List<FoodBean> list=new ArrayList<FoodBean>();
		try {
			// 이때는 새로 Connection 객체를 생성하는 것이 아닌, 만들어진 Connection 객체의 주소를 얻어옴
			getConnection();
			String sql="SELECT fno, poster, name, num "
					+ "FROM (SELECT fno, poster, name, rownum as num "
					+ "FROM (SELECT /*+ INDEX_ASC(food_location fl_fno_pk)*/fno, poster, name "
					+ "FROM food_location)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=ROW_SIZE;
			int start=(rowSize*page)-(rowSize-1); // rownum은 1번부터 시작
			int end=rowSize*page;
			// ?에 값 채우기
			ps.setInt(1, start);
			ps.setInt(2, end);
			// 실행요청
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodBean vo=new FoodBean();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(3));
				String poster=rs.getString(2);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
			// 이때의 disConnection은 연결 종료가 아닌, Connection 객체의 반환
		}
		return list;
	}
	
	public int foodTotalPage() {
		int totalpage=0;
		try {
			// 주소값 얻기
			getConnection();
			// SQL 문장 전송
			String sql="SELECT CEIL(COUNT(*)/"+ROW_SIZE+") "
					+ "FROM food_location";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			totalpage=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return totalpage;
	}
	
}
