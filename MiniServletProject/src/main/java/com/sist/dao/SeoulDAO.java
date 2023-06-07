package com.sist.dao;

import java.sql.*;
import java.util.*;

public class SeoulDAO {
	// 연결객체
		private Connection conn;
		// 송수신
		private PreparedStatement ps;
		// 오라클 URL주소 설정
		private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		// 싱글턴
		private static SeoulDAO dao;

		// 1. 드라이버 등록 => 한 번만 수행 => 시작과 동시에 한 번 수행 or 멤버변수 초기화 = 생성자
		public SeoulDAO() {
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
		public static SeoulDAO newInstance() {
			if (dao == null) {
				dao = new SeoulDAO();
			}
			return dao;
		}
		
		// 5. 기능
		// 목록 => SQL (인라인뷰 => 페이징 기법)
		public List<SeoulVO> seoulListData(int page)
		{
			List<SeoulVO> list=new ArrayList<SeoulVO>();
			try {
				getConnection();
				String sql="SELECT no, title, poster, num "
						+ "FROM (SELECT no, title, poster, rownum as num "
						+ "FROM (SELECT no, title, poster "
						+ "FROM seoul_location ORDER BY no ASC)) "
						+ "WHERE num BETWEEN ? AND ?";
				// rownum => 가상 컬럼 (오라클에서 지원) => 데이터 추출용
				// 단점 : 중간에 데이터를 추출할 수 없음 --> 로우넘을 넘버로 새로 저장해서 사용
				// 		  TOP-N 
				// sql문장 전송
				ps=conn.prepareStatement(sql);
				// ?에 값 채우기	=> IN,OUT 입출력 에러
				int rowSize=12;
				int start=page*rowSize-(rowSize-1);
				// 		  (page-1)*rowSize+1
				int end=(page*rowSize);
				ps.setInt(1, start);
				ps.setInt(2, end);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SeoulVO vo=new SeoulVO();
					vo.setNo(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
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
		public List<SeoulVO> seoulNatureData(int page)
		{
			List<SeoulVO> list=new ArrayList<SeoulVO>();
			try {
				getConnection();
				String sql="SELECT no,title,poster, num "
						+ "FROM (SELECT no, title, poster, rownum as num "
						+ "FROM (SELECT no, title, poster "
						+ "FROM seoul_nature ORDER BY no ASC)) "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				// ? 값 채우기
				int rowSize=12;
				int start=(page-1)*rowSize+1;
				int end=page*rowSize;
				ps.setInt(1, start);
				ps.setInt(2, end);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SeoulVO vo=new SeoulVO();
					vo.setNo(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
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
		
}
