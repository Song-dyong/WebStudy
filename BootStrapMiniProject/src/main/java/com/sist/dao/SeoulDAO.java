package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;


public class SeoulDAO {
	private String[] tables= {
		"", "seoul_location", "seoul_nature", "seoul_shop"
	};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static SeoulDAO dao;
	
	public static SeoulDAO newInstance()
	{
		if(dao==null)
		{
			dao=new SeoulDAO();
		}
		return dao;
	}
	
	// 기능
	public List<SeoulVO> seoulListData(int type)
	{
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT no, title, poster, rownum "
					+ "FROM "+tables[type]
					+" WHERE rownum<=20";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SeoulVO vo=new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		
		return list;
	}
	
	// 2. 총 페이지 구하기
	// 	sql문장에서 tables[type] 대신 ?를 주면 안되는 이유
	// ?에 ps.setString(1, tables[type])으로 값을 집어넣으면, 'seoul_location'으로 입력되어 작은 따옴표 때문에
	// table을 불러올 수 없기 때문에
	// INSERT INOT table_name VALUES(?, ?)...
	// 홍길동 , 남자
	// INSERT INTO table_name VALUES('홍길동', '남자')
	// ps.setString(1, "홍길동") ==> 자동으로 '홍길동'으로 입력해줌
	public int seoulTotalPage(int type)
	{
		int total=0;
		try {
			conn=db.getConnection();
			String sql="SELECT CEIL(count(*)/12.0) "
					+ "FROM "+tables[type];
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	// 3. 상세보기
	public SeoulVO seoulDetailData(int no, int type)
	{
		SeoulVO vo=new SeoulVO();
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			db.disConnection(conn, ps);
			
		}
		return vo;
	}
}
