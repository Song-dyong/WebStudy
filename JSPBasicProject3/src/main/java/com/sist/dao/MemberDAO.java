package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MemberDAO {
	// 연결 객체 => Socket
		private Connection conn;
		// 송수신 (SQL => 결과값(데이터))
		private PreparedStatement ps;
		private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
		// 싱글턴
		// static은 저장공간이 하나이므로, 한개의 dao만 생성
		private static MemberDAO dao;

		// 드라이버 등록
		public MemberDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 오라클 연결
		public void getConnection() {
			try {
				conn = DriverManager.getConnection(URL, "hr", "happy");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 오라클 해제
		public void disConnection() {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// 싱글턴 처리
		public static MemberDAO newInstance() {
			if (dao == null)
				dao = new MemberDAO();
			return dao;
		}
}
