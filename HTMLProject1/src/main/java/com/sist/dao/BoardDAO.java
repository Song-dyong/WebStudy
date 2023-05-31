package com.sist.dao;
// 오라클만 연결 ==> SELECT, UPDATE, INSERT, DELETE 
import java.util.*;
import java.sql.*;

public class BoardDAO {
	// 연결 객체
	private Connection conn;
	// 송수신 객체 (오라클에 sql문장 전송, 실행 결과값 읽어오기)
	private PreparedStatement ps;
	// 모든 사용자가 1개의 dao만 사용할 수 있게 만들기 (싱글턴)
	private static BoardDAO dao;
	// 오라클 연결주소 => 상수형
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	
	// 1. 드라이버 설치
	public BoardDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 2. 싱글턴 --> new를 통해 생성하면, heap에 계속 누적되며 오라클에 연결 (메모리 누수)
	// 한 개의 메모리만 사용이 가능하게 만드는 싱글턴 --> 서버, 데이터베이스 프로그램에서 사용
	// *** 스프링은 모든 객체가 싱글턴 ***
	public static BoardDAO newInstance()
	{
		if(dao==null) {
			dao=new BoardDAO();
		}
		return dao;
	}
	// 3. 오라클 연결
	public void getConnection()
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 4. 오라클 해제
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// --------------------------> 필수 : 클래스화 (라이브러리)
	// 5. 기능
	// 5-1. 목록 출력 => 페이지 나누기 (인라인뷰) => SELECT 		<***어려움***>
	// 1page - 10ea		BoardVO (게시물 1개) --> list에 모으기
	public List<BoardVO> boardListData(int page)
	{
		List<BoardVO> list= new ArrayList<BoardVO>();
		try {
			// 1) 연결
			getConnection();
			// 2) sql문장 제작
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,num "
						+"FROM (SELECT no,subject,name,regdate,hit,rownum AS num "
						+"FROM (SELECT no,subject,name,regdate,hit "
						+"FROM freeboard ORDER BY no DESC)) "
						+"WHERE num BETWEEN ? AND ?";
			// 오라클의 rownum은 중간에 자를 수 없기 때문에, 인라인뷰를 2번 사용하여 rownum으로
			// 뒤집어진 테이블을 제작(넘버가 반대로 고정)한 뒤 그 넘버를 기준으로 between을 사용
			// 3) sql문장 전송
			ps=conn.prepareStatement(sql);
			// 4) 사용자가 요청한 데이터 첨부하기 (?에 값 채우기)
			// 4-1) ?에 값 채우기
			int rowSize=10;
			int start=(page*rowSize)-(rowSize-1);
			int end=page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			// int start=(page-1)*rowSize+1;
			// 5) 실행요청 후, 결과값 받기 
			ResultSet rs=ps.executeQuery();
			// 6) 받은 결과값을 list에 첨부 
			while(rs.next())
			{
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 해제
			disConnection();
		}
		return list;
	}
	// 5-1-1. 총페이지
	public int boardTotalPage()
	{
		int total=0;
		try {
			// 1) 연결
			getConnection(); // 반복 --> 메소드로 사용
			// 2) sql문장제작
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM freeboard";
			ps=conn.prepareStatement(sql);
			// 실행 요청
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return total;
	}
	// 5-2. 상세보기 => 조회수 증가 => UPDATE / 상세볼 게시물 읽기 => SELECT
	public BoardVO BoardDetailData(int no)
	{
		BoardVO vo=new BoardVO();
		try {
			getConnection();
			String sql="UPDATE freeboard SET hit=hit+1 WHERE no="
						+no;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql="SELECT no, name, subject, content, TO_CHAR(regdate,'yyyy-MM-dd'), hit "
					+"FROM freeboard WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}
	// 5-3. 게시물 등록 => INSERT
		// 용도 (SQL문장 사용법, HTML 태그를 통해 웹사이트 제작)
	public void boardInsert(BoardVO vo)
	{
		try {
			getConnection();
			String sql="INSERT INTO freeboard(no,name,subject,content,pwd)"
					+ " VALUES(fb_no_Seq.nextval,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			// 실행 요청 전에 ?에 값 채우기
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			//실행 요청
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			disConnection();
		}
	}
	// 5-4. 수정하기 => UPDATE (먼저 입력된 게시물 읽기, 실제 수정(비밀번호 검색))
	
	public void boardUpdate(BoardVO vo, int no)
	{
		try {
			getConnection();
			String sql="UPDATE freeboard SET name=?, subject=?, content=? WHERE no="+no;
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			
			ps.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	
	
	// 5-5. 삭제 (DELETE) => 비밀번호 검색  					<***어려움***>
	public boolean boardDelete(int no, String pwd)
	{
		boolean bCheck=false;	// 비밀번호 -> 본인 여부 확인  
								
		try {
			getConnection();
			String sql="SELECT pwd FROM freeboard WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);	// 비밀번호를 받아왔음
			rs.close();
			
			if(db_pwd.equals(pwd))
			{
				bCheck=true;
				sql="DELETE FROM freeboard WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return bCheck;
	}
	
	// 5-6. 찾기 (이름, 제목, 내용) => LIKE 
	
}
