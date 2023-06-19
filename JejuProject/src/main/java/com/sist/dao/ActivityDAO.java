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
					+ "acc_accno_seq.nextval,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			ps.setDouble(5, vo.getScore());
			ps.setInt(6, vo.getReview_count());
			ps.setInt(7, vo.getPrice());
			ps.setString(8, vo.getDiscount_rate());
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
					+ "aci_acino_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setDouble(2, vo.getScore());
			ps.setInt(3, vo.getReviewCount());
			ps.setString(4, vo.getPlaytime());
			ps.setString(5, vo.getTicketOption1());
			ps.setString(6, vo.getTicketOption2());
			ps.setString(7, vo.getTicketOption3());
			ps.setString(8, vo.getContentSubject());
			ps.setString(9, vo.getContentCont());
			ps.setInt(10, vo.getPrice());
			ps.setString(11, vo.getPoster());
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
			String sql="SELECT acino, title, score, review_count, playtime, ticket_option1, ticket_option2"
					+ " ticket_option3, content_subject, content_cont, price, poster "
					+ "FROM activity_info";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ActivityInfoVO vo=new ActivityInfoVO();
				vo.setAcino(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setReviewCount(rs.getInt(4));
				vo.setPlaytime(rs.getString(5));
				vo.setTicketOption1(rs.getString(6));
				vo.setTicketOption2(rs.getString(7));
				vo.setTicketOption3(rs.getString(8));
				vo.setContentSubject(rs.getString(9));
				vo.setContentCont(rs.getString(10));
				String poster=rs.getString(11);
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
			String sql="SELECT acino, name, title, poster, link, score, review_count, "
					+ "price, discount_rate FROM activity_category ";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ActivityCategoryVO vo=new ActivityCategoryVO();
				vo.setAccno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setPoster(rs.getString(4));
				vo.setLink("링크"+rs.getString(5));
				vo.setScore(rs.getDouble(6));
				vo.setReview_count(rs.getInt(7));
				vo.setPrice(rs.getInt(8));
				vo.setDiscount_rate(rs.getString(9));
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
