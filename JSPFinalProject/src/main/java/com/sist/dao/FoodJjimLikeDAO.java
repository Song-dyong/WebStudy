package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

public class FoodJjimLikeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static FoodJjimLikeDAO dao;
	
	public static FoodJjimLikeDAO newInstance() {
		if(dao==null)
			dao=new FoodJjimLikeDAO();
		return dao;
	}
	// 찜 저장
	public void foodJjimInsert(FoodJjimVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO jjim VALUES("
					+ "fj_no_seq.nextval,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getFno());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	// 찜 확인
	public int foodJjimCount(String id, int fno) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT count(*) "
					+ "FROM jjim "
					+ "WHERE fno=? AND id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ps.setString(2, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	// 찜 목록출력
	public List<FoodJjimVO> foodJjimListData(String id){
		List<FoodJjimVO> list=new ArrayList<FoodJjimVO>();
		try {
			conn=db.getConnection();
//			String sql="SELECT no,fno,"
//					+ "(SELECT poster FROM food_house WHERE fno=jjim.fno),"
//					+ "(SELECT name FROM food_house WHERE fno=jjim.fno),"
//					+ "(SELECT phone FROM food_house WHERE fno=jjim.fno) "
//					+ "FROM jjim "
//					+ "WHERE id=? "
//					+ "ORDER BY no DESC";
			String sql="SELECT no, fno, foodGetPoster(fno),foodGetName(fno),foodGetPhone(fno) "
					+ "FROM jjim "
					+ "WHERE id=? "
					+ "ORDER BY no DESC";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				FoodJjimVO vo=new FoodJjimVO();
				vo.setNo(rs.getInt(1));
				vo.setFno(rs.getInt(2));
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(4));
				vo.setTel(rs.getString(5));
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
	// 찜 취소하기
	public void foodJjimCancle(int no) {
		try {
			conn=db.getConnection();
			String sql="DELETE FROM jjim WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		} catch (Exception e) {
		}finally {
			db.disConnection(conn, ps);
		}
	}
	// 좋아요 저장
	public void foodLikeInsert(FoodLikeVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO food_like VALUES("
					+ "fl_no_seq.nextval,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getFno());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	// 좋아요 총 갯수
	public int foodLikeCount(int fno) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT count(*) FROM food_like "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	
	public int foodLikeOk(int fno, String id) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT count(*) FROM food_like "
					+ "WHERE fno=? AND id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ps.setString(2, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
}
