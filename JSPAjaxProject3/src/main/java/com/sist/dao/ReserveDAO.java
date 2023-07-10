package com.sist.dao;
import java.util.*;
import java.sql.*;

public class ReserveDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static ReserveDAO dao;
	
	public static ReserveDAO newInstance() {
		if(dao==null)
			dao=new ReserveDAO();
		return dao;
	}
	
	public List<FoodVO> foodReserveDate(String type){
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			conn=db.getConnection();
			String slq="SELECT poster, name, phone, reserve_day, fno "
					+ "FROM food_house "
					+ "WHERE type LIKE '%'||?||'%'";
			ps=conn.prepareStatement(slq);
			ps.setString(1, type);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				FoodVO vo=new FoodVO();
				String poster=rs.getString(1);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(2));
				vo.setPhone(rs.getString(3));
				vo.setReserve_day(rs.getString(4));
				vo.setFno(rs.getInt(5));
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
	
	// fno에 해당하는 맛집의 예약 가능 날짜 가져오기
	public String foodReserveDay(int fno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT reserve_day "
					+ "FROM food_house "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		
		return result;
	}
	
	public String reserve_day_time(int tno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT time "
					+ "FROM reserve_day "
					+ "WHERE rno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	
	public String reserve_get_time(int tno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT time "
					+ "FROM reserve_time "
					+ "WHERE tno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	
}
