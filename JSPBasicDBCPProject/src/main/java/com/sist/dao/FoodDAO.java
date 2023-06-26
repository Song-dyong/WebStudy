package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.sql.*;		// 데이터베이스의 정보를 가지고 있는 DataSource 
import javax.naming.*;	// jdbc/oracle이란 이름의 객체주소 찾기 => Context

public class FoodDAO {
	// 연결 객체
	private Connection conn;
	// SQL 송수신
	private PreparedStatement ps;
	// 싱글턴
	private static FoodDAO dao;
	
	// 사용자 요청에 따라 Connection 객체 얻어오기 (미리 연결)
	/*
	 	
	 	XML은 대소문자 구분 / 태그 열고, 닫기 必 / 속성값에 " " 必 (React에서 태그는 HTML이지만, 문법은 XML이므로, 열고 닫기 주의)
	 																-------------------------
	 																		jsx (JavaScriptXml) 
	 	XML은 설정파일
	 	------------
	 		server.xml , web.xml	=> tomcat에서 사용
	 		application.xml			=> Spring
	 		config.xml				=> MyBatis
	 		mapper.xml				=> MyBatis
	 	
	 	<Context> => 프로젝트의 정보 
	 	
		<Resource
      		driverClassName="oracle.jdbc.driver.OracleDriver"
      		url="jdbc:oracle:thin:@localhost:1521:XE"
      		username="hr"
      		password="happy"			=> Connection 생성시 필요한 정보들
      		-------------------------------
      		maxActive="10"				=> Pool의 Connection 객체를 다 사용할 경우, 추가
      		maxIdle="8"					=> Pool안에 Connection 객체 저장 갯수
      		maxWait="-1"				=> Connection이 모두 사용중일 경우, 반환을 대기하는 시간 (-1은 무한 대기)
      		name="jdbc/oracle"			=> Connection 객체를 찾기 위한 이름 (key) ==> lookup(key) -> 객체 반환
      									   이때 지정하는 name은 임의 지정 가능 
      		auth="Container"			=> DBCP를 관리하는 관리자 이름 - Container(톰캣)
      		type="javax.sql.DataSource" => Connection 주소를 얻었을 경우, DataSource를 리턴 
      		 								DataSource는 DataBase 전체 연결 정보 
      	/>
					Pool : Connection()을 관리하는 영역 
		-----------------------------------------------------------------------------
			객체 주소									사용여부
		-----------------------------------------------------------------------------
		conn=DriverManager.getConnection()			false		==> 객체 얻기 => true 
		-----------------------------------------------------------------------------
		conn=DriverManager.getConnection()			true		==> 객체 반환 => false		
		-----------------------------------------------------------------------------
		conn=DriverManager.getConnection()			false
		-----------------------------------------------------------------------------
		conn=DriverManager.getConnection()			false
		-----------------------------------------------------------------------------
		conn=DriverManager.getConnection()			false
		-----------------------------------------------------------------------------
	*/
	public void getConnection() {
		try {
			// JNDI 연결 => 가상 디렉토리 공간에 저장 Java Naming Directory Interface
			Context init=new InitialContext();
			// Pool => java://comp/env (Root)						 
			Context cdriver=(Context)init.lookup("java://comp/env");
			// 저장된 Connection => jdbc/oracle 
			DataSource ds=(DataSource)cdriver.lookup("jdbc/oracle");	// xml 파일의 resource에서 지정한 name의 값
			// Connection 주소 저장
			conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 사용 종료 => 반환 
	public void disConnection() {
		// Connection을 close()하면 반환 ==> commons-dbcp.jar 라이브러리에서 자동으로 반환해줌
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 싱글턴 (한 개의 객체만 사용) => Spring의 기본 형식은 싱글턴 
	// Spring => Class(Component) 관리자(Container)
	public static FoodDAO newInstance() {		// static 필드에 생성했으므로, Connection()의 주소가 바뀌어도, 객체의 주소는 동일함. 
		if(dao==null) 
			dao=new FoodDAO();
		return dao;
	}
	// 기능: 맛집 찾기 
	public List<FoodBean> foodFindData(int page, String fd){
		List<FoodBean> list= new ArrayList<FoodBean>();
		try {
			// Connection 주소 얻기 
			getConnection();
			String sql="SELECT fno, name, poster, address, num "
					+ "FROM (SELECT fno, name, poster,address, rownum as num "
					+ "FROM (SELECT fno, name, poster, address "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%')) " 		// 조건은 마지막 SELECT에서 걸어야 함 -> 이후, rownum으로 정렬
					+ "WHERE num BETWEEN ? AND ?";				// 그 데이터를 페이징하기 위한 num
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			int rowSize=12;
			int start=(rowSize*page)-(rowSize-1);
			int end=(rowSize*page);
			ps.setInt(2, start);
			ps.setInt(3, end);
			 
			// 실행
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodBean vo=new FoodBean();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				// 서울특별시 강서구 마곡중앙5로1길 20 218호 지번 서울시 강서구 마곡동 757-3 218호
				String addr=rs.getString(4);
				String addr1=addr.substring(addr.indexOf(" "));
				// " "강서구 마곡중앙5로1길 20 218호 지번 서울시 강서구 마곡동 757-3 218호
				addr1=addr1.trim();
				// 강서구 마곡중앙5로1길 20 218호 지번 서울시 강서구 마곡동 757-3 218호
				String addr2=addr1.substring(0,addr1.indexOf(" "));
				// 강서구
				vo.setAddress(addr2);
				list.add(vo);
			}
			rs.close();
				
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();	// 반환 
		}
		return list;
	}
	
	public int foodTotalpage(String addr) {
		int total=0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/12.0) "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, addr);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return total;
	}
	
}
