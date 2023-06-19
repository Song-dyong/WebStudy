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
	private final String URL = "jdbc:oracle:thin:@211.238.142.124:1521:xe";

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
					+ "acc_accno_seq.nextval,?)";
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			
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
					+ "aci_acino_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setDouble(2, vo.getScore());
			ps.setInt(3, vo.getReview_count());
			ps.setString(4, vo.getPlaytime());
			ps.setString(5, vo.getTicket_option1());
			ps.setString(6, vo.getTicket_option2());
			ps.setString(7, vo.getTicket_option3());
			ps.setString(8, vo.getContent_subject());
			ps.setString(9, vo.getContent_cont());
			ps.setInt(10, vo.getPrice());
			ps.setString(11, vo.getDiscount_rate());
			ps.setString(12, vo.getReviewer());
			ps.setString(13, vo.getReview_content());
			ps.setString(14, vo.getHours_use());
			ps.setString(15, vo.getLocation_name());
			ps.setString(16, vo.getLocation_poster());
			ps.setString(17, vo.getHow_use());
			ps.setString(18, vo.getPoster());
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
			String sql="SELECT /*+ INDEX_ASC(activity_info aci_acino_pk)*/acino, title, score, review_count, playtime, ticket_option1, ticket_option2"
					+ " ticket_option3, content_subject, content_cont, price, discount_rate, reviewer, review_content, "
					+ " hours_use, location_name, location_poster, how_use, poster "
					+ "FROM activity_info";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ActivityInfoVO vo=new ActivityInfoVO();
				vo.setAcino(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setReview_count(rs.getInt(4));
				vo.setPlaytime(rs.getString(5));
				vo.setTicket_option1(rs.getString(6));
				vo.setTicket_option2(rs.getString(7));
				vo.setTicket_option3(rs.getString(8));
				vo.setContent_subject(rs.getString(9));
				vo.setContent_cont(rs.getString(10));
				vo.setPrice(rs.getInt(11));
				vo.setDiscount_rate(rs.getString(12));
				vo.setReviewer(rs.getString(13));
				vo.setReview_content(rs.getString(14));
				vo.setHours_use(rs.getString(15));
				vo.setLocation_name(rs.getString(16));
				vo.setLocation_poster(rs.getString(17));
				String poster=rs.getString(18);
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
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
			String sql="SELECT accno, name FROM activity_category";
					
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ActivityCategoryVO vo=new ActivityCategoryVO();
				vo.setAccno(rs.getInt(1));
				vo.setName(rs.getString(2));
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
