package com.sist.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.vo.LinkVO;
import com.sist.vo.StoreDetailVO;

public class StoreDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:xe";

	private static StoreDAO dao;

	public StoreDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static StoreDAO newInstance() {
		if (dao == null)
			dao = new StoreDAO();
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

	public void storeLinkInsert(LinkVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO store_link VALUES("
					+ "stli_no_seq.nextval,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getMain_poster());
			ps.setString(2, vo.getLink());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	
	public List<LinkVO> getLinkPosterList(){
		List<LinkVO> list=new ArrayList<LinkVO>();
		try {
			getConnection();
			String sql="SELECT main_poster,link FROM store_link ORDER BY no ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				LinkVO vo=new LinkVO();
				vo.setMain_poster(rs.getString(1));
				vo.setLink(rs.getString(2));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	
	public void storeDetailInsert(StoreDetailVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO wadiz_store_detail(wsno,main_poster,detail_poster,parti_count,tag,detail_intro,"
					+ "goods_title,goods_subtitle,jjim_count,scno) "
					+ "VALUES(wsd_wsno_seq.nextval,?,?,?,?,?,?,?,?,?)";
			/*
    wsno NUMBER,
	main_poster varchar2(3000),
	detail_poster varchar2(3000),
	parti_count NUMBER,
	tag varchar2(100),
	detail_intro CLOB,
	goods_title varchar2(2000),
	goods_subtitle varchar2(2000),
	jjim_count NUMBER,
	scno NUMBER,
	acno NUMBER DEFAULT 2, 				
	CONSTRAINT wf_wsno_pk PRIMARY KEY(wsno),
	CONSTRAINT scno FOREIGN KEY(scno)
	REFERENCES wadiz_store_category(scno)
			*/
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getWsno());
			ps.setString(2, vo.getMain_poster());
			ps.setString(3, vo.getDetail_poster());
			ps.setInt(4, vo.getParti_count());
			ps.setString(5, vo.getTag());
			ps.setString(6, vo.getDetail_intro());
			ps.setString(7, vo.getGoods_title());
			ps.setString(8, vo.getGoods_subtitle());
			ps.setInt(9, vo.getJjim_count());
			ps.setInt(10, vo.getScno());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	
//	public List<StoreDetailVO> activityInfoData(){
//		List<ActivityInfoVO> list= new ArrayList<ActivityInfoVO>();
//		try {
//			getConnection();
//			String sql="SELECT /*+ INDEX_ASC(activity_info aci_acino_pk)*/acino, title, score, review_count,"
//					+ " price, reviewer, review_content, "
//					+ " hours_use, location_name, location_poster, how_use, poster, main_poster "
//					+ "FROM activity_info";
//			ps=conn.prepareStatement(sql);
//			ResultSet rs=ps.executeQuery();
//			while(rs.next())
//			{
//				ActivityInfoVO vo=new ActivityInfoVO();
//				vo.setAcino(rs.getInt(1));
//				vo.setTitle(rs.getString(2));
//				vo.setScore(rs.getDouble(3));
//				vo.setReview_count(rs.getString(4));
//				vo.setPrice(rs.getInt(5));
//				vo.setReviewer(rs.getString(6));
//				vo.setReview_content(rs.getString(7));
//				vo.setHours_use(rs.getString(8));
//				vo.setLocation_name(rs.getString(9));
//				vo.setLocation_poster(rs.getString(10));
//				String poster=rs.getString(11);
//				poster=poster.replace("#", "&");
//				vo.setPoster(poster);
//				String main_poster=rs.getString(12);
//				main_poster=main_poster.replace("#", "&");
//				vo.setMain_poster(main_poster);
//				list.add(vo);
//			}
//			rs.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			disConnection();
//		}
//		return list;
//	}
	
//	public List<ActivityCategoryVO> activityCategoryData(){
//		List<ActivityCategoryVO> list = new ArrayList<ActivityCategoryVO>();
//		try {
//			getConnection();
//			String sql="SELECT /*+ INDEX_ASC(activity_category acc_accno_pk)*/accno, name, link FROM activity_category";
//					
//			ps=conn.prepareStatement(sql);
//			ResultSet rs=ps.executeQuery();
//			while(rs.next()) {
//				ActivityCategoryVO vo=new ActivityCategoryVO();
//				vo.setAccno(rs.getInt(1));
//				vo.setName(rs.getString(2));
//				vo.setLink(rs.getString(3));
//				list.add(vo);
//			}
//			rs.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			disConnection();
//		}
//		return list;
//	}
}
