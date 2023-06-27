package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class GoodsDAO {
	// 연결 객체 얻기
		private Connection conn;
		// SQL 송수신
		private PreparedStatement ps;
		// 싱글턴
		private static GoodsDAO dao;
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
		public static GoodsDAO newInstance() {
			if(dao==null)
				dao=new GoodsDAO();
			return dao;
		}
		// 로그인
		public String isLogin(String id, String pwd) {
			String result="";
			try {
				getConnection();
				// 1. id 존재여부 확인
				String sql="SELECT COUNT(*) FROM jspMember "
						+ "WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				rs.close();
				
				// 2. 비밀번호 확인
				if(count==0) {	// ID 없는 상태
					result="NOID";
				}else {	// ID 존재하는 상태
					sql="SELECT pwd, name "
							+ "FROM jspMember "
							+ "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					rs=ps.executeQuery();
					rs.next();
					String db_pwd=rs.getString(1);
					String name=rs.getString(2);
					rs.close();
					
					if(db_pwd.equals(pwd)) {	// 로그인 가능
						result=name;
					}else {						// 비밀번호 틀림
						result="NOPWD";
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				disConnection();
			}
			return result;
		}
		
		// 목록
		public List<GoodsBean> goodsListData(int page){
			List<GoodsBean> list= new ArrayList<GoodsBean>();
			try {
				getConnection();
				String sql="SELECT no, goods_name, goods_poster, num "
						+ "FROM (SELECT no, goods_name, goods_poster, rownum as num "
						+ "FROM (SELECT /*+ INDEX_ASC(goods_all ga_no_pk)*/no, goods_name, goods_poster "
						+ "FROM goods_all)) "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=12;
				int start=(rowSize*page)-(rowSize-1);
				int end= rowSize*page;
				ps.setInt(1, start);
				ps.setInt(2, end);
				ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					GoodsBean vo=new GoodsBean();
					vo.setNo(rs.getInt(1));
					vo.setName(rs.getString(2));
					vo.setPoster(rs.getString(3));
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
		
		public int goodsTotalPage() {
			int total=0;
			try {
				getConnection();
				String sql="SELECT CEIL(COUNT(*)/12.0) FROM goods_all";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				total=rs.getInt(1);
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				disConnection();
			}
			return total;
		}
		// 상세보기
		public GoodsBean goodsDetailData(int no){
			GoodsBean vo=new GoodsBean();
			try {
				getConnection();
				String sql="SELECT no, goods_name, goods_sub, goods_price, goods_discount, "
						+ "goods_first_price, goods_delivery, "
						+ "goods_poster "
						+ "FROM goods_all "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ResultSet rs=ps.executeQuery();
				rs.next();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSub(rs.getString(3));
				vo.setPrice(rs.getString(4));
				vo.setDiscount(rs.getInt(5));
				vo.setFp(rs.getString(6));
				vo.setDelivery(rs.getString(7));
				vo.setPoster(rs.getString(8));
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
			return vo;
		}
		// 장바구니 => session
		
		// 구매 (x) 
		
}
