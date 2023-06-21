package com.sist.dao;

/*
		데이터베이스 연동
		JDBC (Java DataBase Connectivity)	=> DBCP => ORM (Object Relation Map - MyBatis, Hibernate) => JPA
												1차									2차					  3차
																							
		JDBC 단점: 요청시마다 연결하고, 닫기를 반복
				=> 연결에 소모되는 시간이 길다
				  Connection의 갯수 관리 불가능
				=> 서버 다운될 가능성 증가
		
		DBCP
		  1) 미리 연결 (Connection 갯수 지정)
		  2) 연결된 Connection을 얻어옴
		  3) 반환 => 재사용
		  4) 웹 프로그램의 일반화
		
		
*/
import java.util.*;
import java.sql.*;
// 서버가 아니라, 클라이언트 프로그램 (실제 서버: 오라클) 
public class FoodDAO {
	// 연결 객체 => Socket
	private Connection conn;
	// 송수신 (SQL => 결과값(데이터))
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// 싱글턴
	// static은 저장공간이 하나이므로, 한개의 dao만 생성
	private static FoodDAO dao;
	// 드라이버 등록
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 오라클 연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 오라클 해제
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 싱글턴 처리
	public static FoodDAO newInstance() {
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	//------------------------------- 11장 내용
	// 기능 수행
	public List<FoodVO> foodListData(){
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL문장 작성
			String sql="SELECT fno, poster, name, rownum "
					+ "FROM food_location "
					+ "WHERE rownum<=20";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				String poster=rs.getString(2);
				poster=poster.substring(0, poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return list;
	}
	public FoodVO foodDetailData(int fno) {
		FoodVO vo=new FoodVO();
		try {
			getConnection();
			String sql="SELECT fno, poster, name, score, tel, type, time, parking, price, menu, address "
					+ "FROM food_location WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setPoster(rs.getString(2).replace("#", "&"));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setTel(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setTime(rs.getString(7));
			vo.setParking(rs.getString(8));
			vo.setPrice(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setAddress(rs.getString(11));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}
	
}
