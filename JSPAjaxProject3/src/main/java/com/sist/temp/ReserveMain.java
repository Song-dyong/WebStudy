package com.sist.temp;

import java.util.*;
import java.sql.*;

public class ReserveMain {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.238.142.124:1521:xe";
	
	public ReserveMain() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
		}
	}
	
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {}
	}
	
	public void reserveDayInsert(int day, String time) {
		try {
			getConnection();
			String sql="INSERT INTO reserve_day VALUES("
					+ "(SELECT NVL(MAX(rno)+1,1) FROM reserve_day), "
					+ "?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, day);
			ps.setString(2, time);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	
	public List<Integer> foodGetFnoData(){
		List<Integer> list=new ArrayList<Integer>();
		try {
			getConnection();
			String sql="SELECT fno FROM food_house "
					+ "ORDER BY fno ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	
	public String reserveTimeData() {
		String result="";
		int[] com=new int[(int)(Math.random()*7)+5];
		int rand=0;
		// 중복여부 확인
		boolean bCheck=false;
		for(int i=0;i<com.length;i++) {
			bCheck=true;
			while(bCheck) {
				rand=(int)(Math.random()*22)+1;
				bCheck=false;
				for(int j=0;j<i;j++) {
					if(com[j]==rand) {
						bCheck=true;
						break;
					}
				}
			}
			com[i]=rand;
		}
		Arrays.sort(com);
		System.out.println(Arrays.toString(com));
		for(int i=0;i<com.length;i++) {
			result+=com[i]+",";
		}
		result=result.substring(0,result.lastIndexOf(","));
		return result;
	}
	
	
	public String reserveDayData() {
		String result="";
		int[] com=new int[(int)(Math.random()*10)+10];
		int rand=0;
		// 중복여부 확인
		boolean bCheck=false;
		for(int i=0;i<com.length;i++) {
			bCheck=true;
			while(bCheck) {
				rand=(int)(Math.random()*31)+1;
				bCheck=false;
				for(int j=0;j<i;j++) {
					if(com[j]==rand) {
						bCheck=true;
						break;
					}
				}
			}
			com[i]=rand;
		}
		Arrays.sort(com);
		System.out.println(Arrays.toString(com));
		for(int i=0;i<com.length;i++) {
			result+=com[i]+",";
		}
		result=result.substring(0,result.lastIndexOf(","));
		return result;
	}
	
	public void foodReserveDayUpdate(int fno, String rday) {
		try {
			getConnection();
			String sql="UPDATE food_house SET "
					+ "reserve_day=? "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, rday);
			ps.setInt(2, fno);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	
	public static void main(String[] args) {
		ReserveMain rm=new ReserveMain();
		/*
		 * rm.reserveTimeData();
		 * 
		 * for(int i=1;i<=31;i++) { String s=rm.reserveTimeData();
		 * rm.reserveDayInsert(i, s); }
		 */
		List<Integer> list=rm.foodGetFnoData();
		for(int fno:list) {
			String s=rm.reserveDayData();
			rm.foodReserveDayUpdate(fno, s);
		}
		System.out.println("clear!");
	}
}
