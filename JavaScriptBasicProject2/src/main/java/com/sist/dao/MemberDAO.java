package com.sist.dao;

import java.util.*;
import java.sql.*;

public class MemberDAO {
	// 연결객체
	private Connection conn;
	// 송수신
	private PreparedStatement ps;
	// 오라클 URL주소 설정
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	// 싱글턴
	private static MemberDAO dao;

	// 1. 드라이버 등록 => 한 번만 수행 => 시작과 동시에 한 번 수행 or 멤버변수 초기화 = 생성자
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ClassNotFoundException => CheckedException => 반드시 예외처리 필요
			// java.io / java.net / java.sql => 거의 예외처리를 해야함
		} catch (Exception e) {

		}
	}

	// 2. 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			// conn hr/happy => 명령어를 오라클로 전송
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 3. 오라클 해제
	public void disConnection() {
		try {
			if (ps != null)
				ps.close(); // 송신이 열려있으면, 닫기
			if (conn != null)
				conn.close(); // exit => 오라클 닫기
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 4. 싱글턴 설정 => static은 메모리 공간이 하나 => 하나의 메모리만 사용해서 메모리 누수 방지
	// DAO new를 통해 생성하면, 사용하지 않는 DAO가 오라클에 연결되어있음
	// 싱글턴은 데이터베이스에서 필수 조건
	public static MemberDAO newInstance() {
		if (dao == null) {
			dao = new MemberDAO();
		}
		return dao;
	}
	
	// 5. 기능 = 우편번호 검색
	public List<ZipcodeVO> postfind(String dong){
		List<ZipcodeVO> list=new ArrayList<ZipcodeVO>();
		try {
			getConnection();
			String sql="SELECT zipcode,sido,gugun,dong, NVL(bunji,' ') "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ZipcodeVO vo=new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setBunji(rs.getString(5));
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
	
	public int postfindCount(String dong){
		int count=0;
		try {
			getConnection();
			String sql="SELECT count(*) "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return count;
	}
	
}
