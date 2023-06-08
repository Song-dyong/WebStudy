package com.sist.dao;

/*
	1. 사용자
		=> 등록
		=> 정보보기
		=> 수정
		=> 삭제
		=> 목록 출력
	2. 관리자 (*****)
		=> 부서별 통계
		=> 부서별 순위 (급여순위)


*/
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
import com.sist.dbconn.*;

public class EmpDAO {
	// 연결
	private Connection conn;
	// 송수신
	private PreparedStatement ps;
	// 연결 => 클래스화 
	private CreateDataBase db=new CreateDataBase();
	
	// 기능설정
	// 1. 등록 
	/* INSERT : 추가
	   ------
	   	 INSERT INTO 테이블명(컬럼명...) VALUES(값...)
	   	 	=> default / null 값을 허용 : 필요한 데이터만 출력
	   	 INSERT INTO 테이블명 VALUES(값...)
	   	 	=> 테이블이 가지고 있는 모든 데이터추가
	   	=> 8개 추가 (매개변수 8개를 사용하지 않고, 클래스로 모아서 첨부)
	   	=> 기본적으로 매개변수는 3개 이상을 초과하지 않음
	   	 					------- 배열 or 클래스 사용
	   	 					
	   	INSERT는 정해진 데이터 (deptno, mgr, job 등..)에 한해서 데이터가 들어가야함
	   	이를 브라우저상에서 선택하게 만들기 위해서 그 데이터를 DISTINCT로 SELECT한 뒤 출력할 필요가 있음
	   	=> 기능으로 구현
*/
	public void empInsert(EmpVO vo)
	{
		try {
			conn=db.getConnection();
			// JOIN은 SELECT에서만 사용 / SUBQUERY는 SELECT , INSERT , UPDATE , DELETE 모두 사용
			String sql="INSERT INTO myEmp VALUES("
						+"(SELECT NVL(MAX(empno)+1,7000) FROM myEmp),"
						+ "?,?,?,SYSDATE,?,?,?)";
			ps=conn.prepareStatement(sql);
			// ?에 값 채우기
			// setString() , setInt() , setDouble() , setDate()
			// -----------							  ---------
			// ' '를 자동으로 포함한다 => table명, 컬럼명에는 사용하지 않는다
			// MyBatis => $ , #
			ps.setString(1, vo.getEname());
			ps.setString(2, vo.getJob());
			ps.setInt(3, vo.getMgr());
			ps.setInt(4, vo.getSal());
			ps.setInt(5, vo.getComm());
			ps.setInt(6, vo.getDeptno());
			// 실행요청
			ps.executeUpdate();	// 자동 커밋 수행
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
	}
	// INSERT 전에 필요한 값 -> MGR / SAL / DEPTNO 
	public List<Integer> empGetMgrData()
	{
		List<Integer> list=new ArrayList<Integer>();
		// 제네릭에는 반드시 클래스만 사용 (Wrapper Class)
		try {
			conn=db.getConnection();
			String sql="SELECT DISTINCT mgr FROM myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				list.add(rs.getInt(1));
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
	public List<Integer> empGetSalData()
	{
		List<Integer> list=new ArrayList<Integer>();
		// 제네릭에는 반드시 클래스만 사용 (Wrapper Class)
		try {
			conn=db.getConnection();
			String sql="SELECT DISTINCT sal FROM myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				list.add(rs.getInt(1));
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
	public List<Integer> empGetDeptnoData()
	{
		List<Integer> list=new ArrayList<Integer>();
		// 제네릭에는 반드시 클래스만 사용 (Wrapper Class)
		try {
			conn=db.getConnection();
			String sql="SELECT DISTINCT deptno FROM myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				list.add(rs.getInt(1));
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
	public List<String> empGetJobData()
	{
		List<String> list=new ArrayList<String>();
		// 제네릭에는 반드시 클래스만 사용 (Wrapper Class)
		try {
			conn=db.getConnection();
			String sql="SELECT DISTINCT job FROM myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				list.add(rs.getString(1));
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
	
	public List<EmpVO> empListData()
	{
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			conn=db.getConnection();
			// 첫 번째 문제 => 오라클 조인으로 바꾸기
			String sql="SELECT empno, ename, job, TO_CHAR(hiredate,'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'),"
						+ " NVL(comm,0), dname, loc, grade "
						+"FROM myEmp me JOIN myDept md "
						+"ON me.deptno=md.deptno "
						+"JOIN myGrade ms "
						+"ON me.sal BETWEEN ms.losal AND ms.hisal "
						+ "ORDER BY empno DESC";
			
			String sql2="SELECT empno, ename, job, TO_CHAR(hiredate,'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'), "
					+ "NVL(comm, 0), dname, loc, grade "
					+ "FROM myEMP me, myDept md, myGrade ms "
					+ "WHERE me.deptno=md.deptno "
					+ "AND me.sal BETWEEN ms.losal AND ms.hisal "
					+ "ORDER BY empno DESC";
			
			ps=conn.prepareStatement(sql2);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setDbsal(rs.getString(5));
				vo.setComm(rs.getInt(6));
				vo.getDvo().setDname(rs.getString(7));
				vo.getDvo().setLoc(rs.getString(8));
				vo.getSvo().setGrade(rs.getInt(9));
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
	// 상세보기	=>  매개변수인 empno는 사용자가 보내줌 ?empno= 
	public EmpVO empDetailData(int empno)
	{
		EmpVO vo=new EmpVO();
		try {
			conn=db.getConnection();
			// 2번째 문제 => 스칼라 서브쿼리로 변경
			String sql="SELECT empno, ename, job, NVL(mgr,0), TO_CHAR(hiredate,'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'),"
					+ " NVL(comm, 0), dname, loc, grade "
					+ "FROM myEmp me, myDept md, myGrade mg "
					+ "WHERE me.deptno=md.deptno "
					+ "AND sal BETWEEN losal AND hisal "
					+ "AND empno=?";
			String sql2="SELECT empno, ename, job, NVL(mgr, 0),TO_CHAR(hiredate, 'YYYY-MM-DD'), TO_CHAR(sal, 'L999,999'), NVL(comm, 0),"
					+ "	(SELECT dname FROM myDept WHERE myEmp.deptno=deptno) AS dname,"
					+ "	(SELECT loc FROM myDept WHERE deptno=myEmp.deptno) AS loc, "
					+ "	(SELECT grade FROM myGrade WHERE sal BETWEEN losal AND hisal) AS grade"
					+ "	FROM myEmp "
					+ "WHERE empno=?";
			ps=conn.prepareStatement(sql2);
			// ?에 값 채우기
			ps.setInt(1, empno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setDbday(rs.getString(5));
			vo.setDbsal(rs.getString(6));
			vo.setComm(rs.getInt(7));
			vo.getDvo().setDname(rs.getString(8));
			vo.getDvo().setLoc(rs.getString(9));
			vo.getSvo().setGrade(rs.getInt(10));
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 수정 데이터 읽기
	public EmpVO empUpdateData(int empno)
	{
		EmpVO vo=new EmpVO();
		try {
			conn=db.getConnection();
			String sql="SELECT empno, ename, job, mgr, sal, comm, deptno "
					+ "FROM myEmp "
					+ "WHERE empno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, empno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setSal(rs.getInt(5));
			vo.setComm(rs.getInt(6));
			vo.setDeptno(rs.getInt(7));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	
	
}
