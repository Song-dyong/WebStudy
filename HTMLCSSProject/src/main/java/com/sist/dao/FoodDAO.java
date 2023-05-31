package com.sist.dao;
import java.util.*;
import java.sql.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	private static FoodDAO dao;
	
	public FoodDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			
		}
	}
	public void getConnection()
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
		}
	}
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();		// 송신이 열려있으면, 닫기
			if(conn!=null) conn.close();	// exit => 오라클 닫기 
		} catch (Exception e) {
		}
	}
	public static FoodDAO newInstance()
	{
		if(dao==null)
		{
			dao=new FoodDAO();
		}
		return dao;
	}
	
	public List<FoodVO> foodAllData()
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			getConnection();
			String sql="SELECT name,address,poster,phone,type FROM food_house ORDER BY fno ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setName(rs.getString(1));
				String addr=rs.getString(2);
				addr=addr.substring(0, addr.lastIndexOf("지번"));
				vo.setAddress(addr.trim());
				String poster=rs.getString(3);
				poster=poster.substring(0, poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setTel(rs.getString(4));
				vo.setType(rs.getString(5));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		
		
		return list;
				
	}
	
}
