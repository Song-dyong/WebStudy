package com.sist.dao;

// 카테고리 출력 => 카테고리별 맛집 => 맛집에 대한 상세보기 => 지도 출력, 검색 (Ajax)
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
public class FoodDAO {
	// 연결객체
	private Connection conn;
	//송수신
	private PreparedStatement ps;
	// 오라클 URL주소 설정
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	//싱글턴
	private static FoodDAO dao;
	
	// 1. 드라이버 등록 => 한 번만 수행 => 시작과 동시에 한 번 수행 or 멤버변수 초기화 = 생성자
	public FoodDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ClassNotFoundException => CheckedException => 반드시 예외처리 필요
			// java.io / java.net / java.sql => 거의 예외처리를 해야함
		} catch (Exception e) {
			
		}
	}
	
	// 2. 오라클 연결
	public void getConnection()
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			// conn hr/happy => 명령어를 오라클로 전송
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 3. 오라클 해제
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();		// 송신이 열려있으면, 닫기
			if(conn!=null) conn.close();	// exit => 오라클 닫기 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 4. 싱글턴 설정 => static은 메모리 공간이 하나 => 하나의 메모리만 사용해서 메모리 누수 방지
	// DAO new를 통해 생성하면, 사용하지 않는 DAO가 오라클에 연결되어있음
	// 싱글턴은 데이터베이스에서 필수 조건
	public static FoodDAO newInstance()
	{
		if(dao==null)
		{
			dao=new FoodDAO();
		}
		return dao;
	}
	// 5. 기능들
	// 5-1. 카테고리 출력
	public List<CategoryVO> food_category_list()
	{
		// 카테고리 1개의 정보 (번호, 이미지, 제목, 부제목)
		List<CategoryVO> list=new ArrayList<CategoryVO>();
		try {
			getConnection();
			String sql="SELECT cno,title,subject,poster FROM food_category ORDER BY cno ASC";
			//String sql="SELECT /*+ INDEX_ASC(food_category fc_cno_pk)*/ cno,title,subject,poster FROM food_category";
			// 자동으로 생성되는 INDEX => PRIMARY, UNIQUE
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				CategoryVO vo=new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			// list를 받아서 브라우저로 전송 후 실행
			// 			   ------------> Servlet , JSP
			//				Spring => Servlet 라이브러리를 제공 (스프링 자체에서 전송하는 것은 아님) DispatcherServlet
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return list;
	}
	// 5-1-1. 카테고리 정보 => 카테고리 클릭시 페이지 상단에 노출되는 카테고리이름과 부제목 가져오기
	public CategoryVO food_category_info(int cno)
	{
		CategoryVO vo=new CategoryVO();
		try {
			getConnection();
			String sql="SELECT title, subject FROM food_category "
					+ "WHERE cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}
	// 5-2. 카테고리별 맛집 출력
	public List<FoodVO> food_category_data(int cno)
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			getConnection();
			// 댓글이 데이터베이스에 없으므로, 주소랑 전화번호로 대체
			String sql="SELECT fno,name,poster,address,phone,type,score FROM food_house "
					+ "WHERE cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				//https://mp-seoul-image-production-s3.mangoplate.com/403237/1260230_1599740580296_10969?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^
				//https://mp-seoul-image-production-s3.mangoplate.com/403237/2058719_1670770897488_1000009579?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^
				//https://mp-seoul-image-production-s3.mangoplate.com/403237/2058719_1670770897488_1000009574?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^
				//https://mp-seoul-image-production-s3.mangoplate.com/403237/2058719_1670770897488_1000009458?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^
				//https://mp-seoul-image-production-s3.mangoplate.com/403237/2058719_1670770897488_1000009453?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				// 이미지 5개를 ^로 나눠서 저장해둔 상태 ==> &가 오라클에서 입출력이므로 #으로 대체해놨음 
				// 이미지 1개만 잘라서 가져오고, #으로 저장된 값을 &로 바꿔서 가져오기
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));	// 포스터를 5개씩 가져왔기 때문에,
				poster=poster.replace('#', '&');
				vo.setPoster(poster);
				String address=rs.getString(4);
				//	서울특별시 종로구 돈화문로11가길 25
				//  지번 서울시 종로구 익선동 156
				//  지번에서 잘라서 저장 
				address=address.substring(0,address.lastIndexOf("지"));
				vo.setAddress(address.trim());
				vo.setPhone(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setScore(rs.getDouble(7));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		
		return list;
	}
	
	// 5-3. 맛집 상세보기
	public FoodVO foodDetailData(int fno)
	{
		FoodVO vo=new FoodVO();
		try {
			getConnection();
			String sql="SELECT fno,cno,name,poster,phone,type,address,time,parking,menu,price,score "
						+"FROM food_house WHERE fno=?";
			ps=conn.prepareStatement(sql);
			//?에 값 채우기
			ps.setInt(1, fno);
			
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setPhone(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setAddress(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setParking(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPrice(rs.getString(11));
			vo.setScore(rs.getDouble(12));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		
		return vo;
	}
	
	
	// 5-4. 맛집 검색
	public List<FoodVO> foodFindData(String addr, int page)
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			getConnection();
//			String sql="SELECT fno,name,poster,score FROM food_house "
//						+"WHERE address LIKE '%'||?||'%'";
			// mysql => LIKE CONCAT('%',?,'%')
			String sql="SELECT fno,name,poster,score,num "
					+ "FROM (SELECT fno,name,poster,score,rownum as num "
					+ "FROM (SELECT fno,name,poster,score "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%')) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(rowSize*page)-(rowSize-1);
			int end=(rowSize*page);
			ps.setString(1, addr);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			// 결과값 받기
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setScore(rs.getDouble(4));
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
	
	// 5-4-1. 총 페이지
	public int foodRowCount(String addr)
	{
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM food_location WHERE address LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, addr);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return count;
	}
	
	// 5-5. 댓글(CURD) => 로그인
	
}
