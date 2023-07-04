package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
/*
	 디스패쳐 서블릿(컨트롤러)을 호출하는 .do => 요청에 따라 값을 담아주는 setAttribute (모델에서 담당) 

*/
import com.sist.common.*;

public class FreeBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static FreeBoardDAO dao;
	
	public static FreeBoardDAO newInstance() {
		if(dao==null)
			dao=new FreeBoardDAO();
		return dao;
	}
	// 기능
	// 1. 목록출력 => inline View (Phasing)
	public List<FreeBoardVO> freeboardListData(int page){
		List<FreeBoardVO> list=new ArrayList<FreeBoardVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT no, subject, name, TO_CHAR(regdate,'yyyy-mm-dd'), "
					+ "hit, num "
					+ "FROM (SELECT no, subject, name, regdate, hit, rownum as num "
					+ "FROM (SELECT /*+ INDEX_DESC(project_freeboard pf_no_pk)*/no, subject, name, regdate, hit "
					+ "FROM project_freeboard)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			// 결과값 읽기
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FreeBoardVO vo=new FreeBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setRownum(rs.getInt(6));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	
	public int freeboardTotalPage() {
		int total=0;
		try {
			conn=db.getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM project_freeboard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	// 2. 탑7 => rownum => 히트수가 많은 위에서부터 7개 자르기 => top-N
	// 3. 글쓰기
	public void freeboardInsert(FreeBoardVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO project_freeboard VALUES("
					+ "pf_no_seq.nextval,?,?,?,?,SYSDATE,0)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate();	// commit => autoCommit
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}

	
	// 4. 상세보기
	
	
	
	// 5. 수정하기 ==> AJax
	// 6. 삭제하기 ==> AJax

	
}
