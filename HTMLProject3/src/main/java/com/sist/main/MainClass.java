package com.sist.main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import com.sist.dbconn.CreateDatabase;

public class MainClass {
	public static void main(String[] args) {
		CreateDatabase db=new CreateDatabase();
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=db.getConnection();
			String sql="SELECT empno,ename,job FROM emp";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "
						+rs.getString(2)+" "
						+rs.getString(3));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
	}
}
