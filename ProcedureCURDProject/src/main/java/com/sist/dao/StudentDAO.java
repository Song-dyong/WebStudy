package com.sist.dao;
import java.util.*;

import oracle.jdbc.OracleTypes;

import java.sql.*;

public class StudentDAO {
	
	// 연결
	private Connection conn;
	// 함수(프로시저) 호출
	private CallableStatement cs;
	// URL
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	// singleTurn
	private static StudentDAO dao;
	
	// 드라이버 등록
	public StudentDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 싱글턴
	public static StudentDAO newInstance() {
		if(dao==null)
		{
			dao=new StudentDAO();
		}
		return dao;
	}
	// 데이터 추가
	/*
		CREATE OR REPLACE PROCEDURE studentInsert(
			pName student.name%TYPE,
			pKor student.kor%TYPE,
			pEng student.eng%TYPE,
			pMath student.math%TYPE
		)
		IS
		BEGIN 
			INSERT INTO student VALUES (
			(SELECT NVL(max(hakbun)+1,1) FROM student),
			pName, pKor, pEng, pMath
			);
			COMMIT;
		END;
		/
	*/
	public void studentInsert(StudentVO vo) {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			// (오라클에서 만들어둔) 함수만 호출하면 됨 
			String sql="{CALL studentInsert(?,?,?,?)}";	// { } 필요!
			cs=conn.prepareCall(sql); // 함수 호출시 사용하는 메소드 prepareCall / ERP => 메뉴얼
			// ?에 값 채운 다음 실행
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			// 실행 요청
			cs.executeQuery(); // cs.executeQuery()로 실행 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null)
				{
					cs.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// 데이터 수정
	/*
			CREATE OR REPLACE PROCEDURE studentUpdate(
				pHakbun student.hakbun%TYPE,
				pName student.name%TYPE,
				pKor student.kor%TYPE,
				pEng student.eng%TYPE,
				pMath student.math%TYPE
			)
			IS 
			BEGIN 
				UPDATE student SET
				name=pName, kor=pKor, eng=pEng, math=pMath
				WHERE hakbun=pHakbun;
			COMMIT;
			END;
			/
	*/
	public void studentUpdate(StudentVO vo) {
		try {
			// 프로시저는 반복수행이 많을 경우 사용
			// ex) 모든 테이블의 데이터를 페이징할 경우
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL studentUpdate(?,?,?,?,?)}";
			cs=conn.prepareCall(sql);
			// ? 에 값 채우기
			cs.setInt(1, vo.getHakbun());
			cs.setString(2, vo.getName());
			cs.setInt(3, vo.getKor());
			cs.setInt(4, vo.getEng());
			cs.setInt(5, vo.getMath());
			// 실행 요청
			cs.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null)
				{
					cs.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// 데이터 삭제
	/*
		CREATE OR REPLACE PROCEDURE studentDelete(
			pHakbun student.hakbun%TYPE
		)
		IS 
		BEGIN 
			DELETE FROM student
			WHERE hakbun=pHakbun;
		COMMIT;
		END;
		/
	*/
	public void studentDelete(int hakbun) {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL studentDelete(?)}";
			cs=conn.prepareCall(sql);
			// ?에 값 채우기
			cs.setInt(1, hakbun);
			// 실행 요청
			cs.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null)
				{
					cs.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// 데이서 상세보기
	/*
		CREATE OR REPLACE PROCEDURE studentDetailData(
			pHakbun IN student.hakbun%TYPE, 	-- IN은 값을 대입할 때 사용
			pName OUT student.name%TYPE,		-- IN을 통해 해당 ROW의 값들을 받아오는 형식
			pKor OUT student.kor%TYPE,
			pEng OUT student.eng%TYPE,
			pMath OUT student.math%TYPE			-- OUT은 값을 받아올 때 사용 
		)
		IS 
		BEGIN 
			SELECT name, kor, eng, math INTO pName, pKor, pEng, pMath
			FROM student
			WHERE hakbun=pHakbun;
		END;
		/
	*/
	public StudentVO studentDetail(int hakbun) {
		StudentVO vo=new StudentVO();
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL studentDetailData(?,?,?,?,?)}";
			cs=conn.prepareCall(sql);
			// ?에 값 채우기
			cs.setInt(1, hakbun);
			// OUT 변수일 경우 registerOutParameter 사용! => 메모리에 저장한다
			// OracleTypes는 internal 말고 그냥 jdbc
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.INTEGER);
			cs.registerOutParameter(4, OracleTypes.INTEGER);
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			// 실행 요청
			cs.executeQuery();
			vo.setName(cs.getString(2));
			vo.setKor(cs.getInt(3));
			vo.setEng(cs.getInt(4));
			vo.setMath(cs.getInt(5));
			vo.setHakbun(hakbun);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null)
				{
					cs.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return vo;
	}
	// 데이터 전체보기
	/*
			CREATE OR REPLACE PROCEDURE studentListData(
				pResult OUT SYS_REFCURSOR 	
			)
			IS 
			BEGIN 
				OPEN pResult FOR
					SELECT * FROM student;
			END;
			/
	
	
	*/
	public List<StudentVO> studentListData() {
		List<StudentVO> list= new ArrayList<StudentVO>();
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL studentListData(?)}";
			cs=conn.prepareCall(sql);
			// ? 값 채우기
			// 오라클에서 CURSOR로 설정했으므로 registerOutParameter의 첫 물음표에 CURSOR로 설정
			/*
					NUMBER 			=> INTEGER / DOUBLE
					VARCHAR2, CHAR 	=> VARCHAR
					CURSOR 			=> CURSOR
					
				registerOutParameter: 저장해주는 공간 설정 
			*/
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.executeQuery();
			// 결과값 받는다
			// ResultSet으로 받는다 => 단, 커서로 받았기 때문에, 데이터형을 ResultSet으로 변환해야함
			ResultSet rs=(ResultSet)cs.getObject(1);
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null)
				{
					cs.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	// 중복 코드가 있는지 여부 --> 반복수행을 메소드화, 클래스화 (공통 모듈)
}
