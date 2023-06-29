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
	public ReplyBoardVO boardDetailData(int no) {
		ReplyBoardVO vo=new ReplyBoardVO();
		try {
			getConnection();
			String sql="UPDATE replyBoard SET "
					+ "hit=hit+1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			//---------------------------- 조회수 증가 
			
			sql="SELECT no, name, subject, content, regdate, hit "
					+ "FROM replyBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
	// 3. 추가
	public void boardInsert(ReplyBoardVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO replyBoard(no, name, subject, content, pwd, group_id) "
					+ "VALUES(rb_no_seq.nextval, ?, ?, ?, ?,"
					+ "(SELECT NVL(MAX(group_id)+1,1) FROM replyBoard))";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			// 실행
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	// 4. 수정
	public ReplyBoardVO boardUpdateData(int no) {
		ReplyBoardVO vo=new ReplyBoardVO();
		try {
			getConnection();
			String sql="SELECT no, name, subject, content, regdate, hit "
					+ "FROM replyBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
	// 4-1. 수정
	public boolean boardUpdate(ReplyBoardVO vo) {
		boolean bCheck=false;
		try {
			getConnection();
			String sql="SELECT pwd FROM replyBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			if(db_pwd.equals(vo.getPwd())) {
				bCheck=true;
				sql="UPDATE replyBoard SET "
						+ "name=?, subject=?, content=? "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return bCheck;
	}
	// 5. 삭제
	public boolean boardDelete(int no, String pwd) {
		boolean bCheck=false;
		try {
			getConnection();
			// 1) 비밀번호 확인
			String sql="SELECT pwd FROM replyBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			if(db_pwd.equals(pwd)) {
				bCheck=true;
				// 2) 삭제 가능여부 확인
				sql="SELECT root, depth FROM replyBoard "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				rs=ps.executeQuery();
				rs.next();
				int root=rs.getInt(1);
				int depth=rs.getInt(2);
				rs.close();
				
				if(depth==0) {	// 게시물 아래에 답변이 없는 상태
					sql="DELETE FROM replyBoard "
							+ "WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setInt(1, no);
					ps.executeUpdate();
				}else {	// 게시물 아래에 답변이 있는 상태
					String msg="관리자가 삭제한 게시물입니다.";
					sql="UPDATE replyBoard SET "
							+ "subject=?, content=? "
							+ "WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, msg);
					ps.setString(2, msg);
					ps.setInt(3, no);
					ps.executeUpdate();
				}
				
				sql="UPDATE replyBoard SET "
						+ "depth=depth-1 "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, root);
				ps.executeUpdate();
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return bCheck;
	}
	// 6. 답변하기	root는 pno => 기존 게시판글의 no를 pno로 받아서, root로 지정
	public void replyInsert(int root, ReplyBoardVO vo) {
		// 상위 게시물의 정보 => group_id , group_step , group_tab 
		//					게시물 묶기	게시물 순서	 몇 번 떨어져야하는지
		// group_step 증가시키기
		// insert
		// depth 증가
		/*							gi		gs		gt
				AAAAAA				3		0		0
					BBBBBB			3		1		1
						CCCCCC		3		2		2
					DDDDDD			3		1		1
			
				새로운 답변이 올라오면, 기존의 글 위로 올라가도록 코딩했음
		*/
		try {
			getConnection();
			String sql="SELECT group_id, group_step, group_tab "
					+ "FROM replyBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int gi=rs.getInt(1);
			int gs=rs.getInt(2);
			int gt=rs.getInt(3);
			rs.close();
			
			sql="UPDATE replyBoard SET "
					+ "group_step=group_step+1 "
					+ "WHERE group_id=? AND group_step>?";
			// 답변형의 핵심 SQL
			ps=conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			
			// 추가
			sql="INSERT INTO replyBoard VALUES(rb_no_seq.nextval,"
					+ "?,?,?,?,SYSDATE,0,?,?,?,?,0)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, root);
			ps.executeUpdate();
			
			// depth 증가
			sql="UPDATE replyBoard SET "
					+ "depth=depth+1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		
	}
	// 7. 검색
	public int boardFindCount(String fs, String ss) {
		int count=0;
		try {
			getConnection();
			String sql="SELECT count(*) "
					+ "FROM replyBoard "
					+ "WHERE "+fs+" LIKE '%'||?||'%'";	// 컬럼명, 테이블명은 문자열 결합을 통해 처리 why? 작은 따옴표가 들어가면서, 에러 발생
			ps=conn.prepareStatement(sql);
			ps.setString(1, ss);
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
	
	public List<ReplyBoardVO> boardFindData(String fs, String ss) {
		List<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
		try {
			getConnection();
			String sql="SELECT no, subject, name, regdate, hit "
					+ "FROM replyBoard "
					+ "WHERE "+fs+" LIKE '%'||?||'%'";	// 컬럼명, 테이블명은 문자열 결합을 통해 처리 why? 작은 따옴표가 들어가면서, 에러 발생
			ps=conn.prepareStatement(sql);
			ps.setString(1, ss);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo=new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
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
}
