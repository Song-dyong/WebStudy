package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.sql.*;			// context
import javax.naming.*;		// DataSource

//DBCP DataBase Connection Pool

public class ReplyBoardDAO {
	// 연결객체
	private Connection conn;		// java.sql
	// 송수신 객체
	private PreparedStatement ps;	// java.sql
	// 싱글턴
	private static ReplyBoardDAO dao;
	
	public static ReplyBoardDAO newInstance() {
		if(dao==null)
			dao=new ReplyBoardDAO();
		return dao;
	}
	
	// getConnection => 미리 생성된 커넥션의 주소값 가져오기
	public void getConnection() {
		try {
			Context init=new InitialContext();						// 탐색기 열기
			Context c=(Context)init.lookup("java://comp/env");		// 이 안을 탐색
			DataSource ds=(DataSource)c.lookup("jdbc/oracle");		// jdbc/oracle 이라는 주소 가져오기
			conn=ds.getConnection();								// 커넥션 객체에 대입 (미리 만들어진 8개의 커넥션 중 하나)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 사용한 커넥션 반환
	public void disConnection() {
		try {
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	// 기능 수행
	/*
	ORDER BY group_id DESC, group_step ASC 

					  	no		group_id 	group_step	group_tab	root	depth
	AAAAA				1			2			0			0		 0		  1				
		=> BBBBB		2			2			1			1		 1		  1
			=> CCCCC	3			2			2			2		 2		  0
			
	DDDDD				4			1			0			0		 0		  2
		=> EEEEE		5			1			1			1		 5		  1
			=> FFFFF	6			1			2			2		 6		  0
		=> GGGGG		7			1			3			1		 5		  0
		
	group_tab => 처음에서 떨어진 간격 (스탭은 댓글의 갯수 / tab은 댓글의 댓글들을 구분하기 위함)
	root 	  => 댓글의 고유번호 -> 답변의 루트는 A와 D
	depth	  => 댓글이 달린 갯수 -> depth가 0이면, 삭제해도 다른 댓글들에 영향x
				=> depth가 존재하는 댓글을 삭제한 경우, <관리자에 의해 삭제된 댓글> 문구 필요
					why? 아래 댓글을 단 유저가 있으므로
		
		root와 depth => 댓글 삭제를 위해
		나머지는 댓글 출력을 위해 
	 
	*/
	// 1. 목록
	public List<ReplyBoardVO> boardListData(int page){
		List<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
		try {
			getConnection();
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,group_tab,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab "
					+ "FROM replyBoard ORDER BY group_id DESC, group_step ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ReplyBoardVO vo=new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
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
	
	// 1-1. 총 페이지
	public int boardRowCount() {
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM replyBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return count;
	}
	
	// 2. 상세보기
	// 3. 추가
	// 4. 수정
	// 5. 삭제
	// 6. 답변하기
	// 7. 검색
	
}
