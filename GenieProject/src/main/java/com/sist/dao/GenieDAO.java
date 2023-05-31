package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.vo.*;

public class GenieDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static GenieDAO dao;
	
	// 1. 드라이버 설치
	public GenieDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	// 2. 오라클 연결
	public void getConnection()
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {}
	}
	// 3. 오라클 연결 해제
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {}
	}
	// 4. 싱글턴 DAO 객체 생성하기
	public static GenieDAO newInstance()
	{
		if(dao==null) dao=new GenieDAO();
		return dao;
	}
	// 메소드 1.데이터 수집 (insert)
	public void genieCategoryInsert(CategoryVO vo)
	{
		try {
			// 1) 오라클 연결
			getConnection();
			// 2) sql문장 작성
			String sql="INSERT INTO music_category VALUES(mc_cno_seq.nextval, ?)";
			// 3) sql문장 전송
			ps=conn.prepareStatement(sql);
			// 3-1) ?에 값 채우기
			ps.setString(1, vo.getTitle());
			// 4) sql 문장 실행하기
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	
	// 메소드 2. SELECT 전체 데이터 읽기 --> 한 개당 CategoryVO에 묶기 
	public List<CategoryVO> musicCategoryData()
	{
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			// 1) 오라클 연결
			getConnection();
			// 2) sql 문장 작성
			String sql="SELECT cno, title FROM music_category ORDER BY cno";
			// 3) sql 문장 전송
			ps=conn.prepareStatement(sql);
			// 4) 실행 후, 결과값 받기
			ResultSet rs=ps.executeQuery();
			// 5) rs에 있는 데이터를 list에 저장하기
			while(rs.next())
			{
				CategoryVO vo=new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
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
	
	// 3. 실제 음악 정보 넣기 
	public void MusicDataInsert(MusicVO vo)
	{
		try {
			getConnection();
	//		String sql="INSERT INTO genie_"
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
