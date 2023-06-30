package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.vo.*;

public class ActivityDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:xe";

	private static ActivityDAO dao;

	public ActivityDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ActivityDAO newInstance() {
		if (dao == null)
			dao = new ActivityDAO();
		return dao;
	}

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
		}
	}

	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
		}
	}

	public void activityCategoryInsert(ActivityCategoryVO vo)
	{
		try {
			getConnection();
			String sql="INSERT INTO activity_category VALUES("
					+ "acc_accno_seq.nextval,?,?)";
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getLink());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}

	public void activityInfoInsert(ActivityInfoVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO activity_info VALUES("
					+ "aci_acino_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			/*
   title           VARCHAR2(1000) CONSTRAINT aci_title_nn NOT NULL,
   score           NUMBER(2,1),
   review_count    VARCHAR2(100),
   price           NUMBER,
   reviewer VARCHAR2(51),
   review_content VARCHAR2(4000),
   hours_use CLOB,
   location_name VARCHAR2(1000),
   location_poster VARCHAR2(300),
   how_use CLOB,
   poster  CLOB,
   main_poster VARCHAR2(300),
			
			*/
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getAccno());
			ps.setString(2, vo.getTitle());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getReview_count());
			ps.setInt(5, vo.getPrice());
			ps.setString(6, vo.getReviewer());
			ps.setString(7, vo.getReview_content());
			ps.setString(8, vo.getHours_use());
			ps.setString(9, vo.getLocation_name());
			ps.setString(10, vo.getLocation_poster());
			ps.setString(11, vo.getHow_use());
			ps.setString(12, vo.getPoster());
			ps.setString(13, vo.getMain_poster());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	
	public List<ActivityInfoVO> activityInfoData(){
		List<ActivityInfoVO> list= new ArrayList<ActivityInfoVO>();
		try {
			getConnection();
			String sql="SELECT /*+ INDEX_ASC(activity_info aci_acino_pk)*/acino, title, score, review_count,"
					+ " price, reviewer, review_content, "
					+ " hours_use, location_name, location_poster, how_use, poster, main_poster "
					+ "FROM activity_info";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ActivityInfoVO vo=new ActivityInfoVO();
				vo.setAcino(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setReview_count(rs.getString(4));
				vo.setPrice(rs.getInt(5));
				vo.setReviewer(rs.getString(6));
				vo.setReview_content(rs.getString(7));
				vo.setHours_use(rs.getString(8));
				vo.setLocation_name(rs.getString(9));
				vo.setLocation_poster(rs.getString(10));
				String poster=rs.getString(11);
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				String main_poster=rs.getString(12);
				main_poster=main_poster.replace("#", "&");
				vo.setMain_poster(main_poster);
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
	
	public List<ActivityCategoryVO> activityCategoryData(){
		List<ActivityCategoryVO> list = new ArrayList<ActivityCategoryVO>();
		try {
			getConnection();
			String sql="SELECT /*+ INDEX_ASC(activity_category acc_accno_pk)*/accno, name, link FROM activity_category";
					
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ActivityCategoryVO vo=new ActivityCategoryVO();
				vo.setAccno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setLink(rs.getString(3));
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
