package schoolDB.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import schoolDB.application.ConnectionClass;
import schoolDB.information.Student;

public class StudentCrud{
	
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String sql;
	//로그인 체크
	public boolean isLogin(Student student) {
		String sql = "SELECT * FROM STUDENT WHERE STUDENT_NUM =? AND STUDENT_PWD = ?";
		ConnectionClass.conn = ConnectionClass.connection();
		Student s = new Student();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, student.getStudent_num());
			pstmt.setString(2, student.getStudent_pwd());
			rs =pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//회원가입 메서드
	public boolean isSignUp(String student_pwd, String teacher_name, String student_name) {
		ConnectionClass.conn = ConnectionClass.connection();
		
		String sql = "INSERT INTO STUDENT VALUES(STUDENT_NUM_SEQ.NEXTVAL, ?, (SELECT TEACHER_NUM FROM TEACHER WHERE TEACHER_NAME =?),?)";
		System.out.println(sql);
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, student_pwd);
			pstmt.setString(2, teacher_name);
			pstmt.setString(3, student_name);
			int rows = pstmt.executeUpdate();
			
			if(rows !=0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("isSignUp에러");
		} finally {
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		return false;
	}
	
	//학생 정보 출력
	public Student selectStudent(String student_pwd, String student_name) {
		ConnectionClass.conn = ConnectionClass.connection();
		String sql = "SELECT S.STUDENT_NUM, S.STUDENT_PWD, S.STUDENT_NAME, T.TEACHER_NAME,T.GRADE,T.CLASSES FROM STUDENT S JOIN  TEACHER T ON(S.TEACHER_NUM = T.TEACHER_NUM) WHERE S.STUDENT_PWD = ? AND S.STUDENT_NAME = ?";
		Student student = new Student();
		System.out.println(sql);
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, student_pwd);
			pstmt.setString(2, student_name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				student.setStudent_num(rs.getInt("student_num"));
				student.setStudent_pwd(rs.getString("student_pwd"));
				student.setStudent_name(rs.getString("student_name"));
				student.setTeacher_name(rs.getString("teacher_name"));
				student.setGrade(rs.getInt("grade"));
				student.setClasses(rs.getInt("classes"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				ConnectionClass.connectionLogout(ConnectionClass.conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return student;
	}
	//학생에 맞는 점수 출력
	public Student selectScore(Student student) {
		ConnectionClass.conn = ConnectionClass.connection();
		sql = "SELECT ST.STUDENT_NUM,ST.STUDENT_NAME,SU.KOREAN,SU.ENGLISH, SU.MATH, SU.SCIENCE, SU.AVG,SU.SUM, SU.RANK "
				+"FROM STUDENT ST JOIN SUBJECT SU ON(ST.STUDENT_NUM = SU.STUDENT_NUM) "
				+"WHERE ST.STUDENT_NUM = ?";
		Student s = new Student();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, student.getStudent_num());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				s.setStudent_num(rs.getInt("student_num"));
				s.setStudent_name(rs.getString("student_name"));
				s.getSubject().setKorean(rs.getDouble("korean"));
				s.getSubject().setEnglish(rs.getDouble("math"));
				s.getSubject().setMath(rs.getDouble("math"));
				s.getSubject().setScience(rs.getDouble("science"));
				s.getSubject().setAvg(rs.getDouble("avg"));
				s.getSubject().setSum(rs.getDouble("sum"));
				s.getSubject().setRank(rs.getInt("rank"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionClass.connectionLogout(ConnectionClass.conn);

		}
		
		return s;
		
	}
	//학생에 맞는 성적 정보 
	public Student informationStudent(Student student) {
		ConnectionClass.conn = ConnectionClass.connection();
		sql = "SELECT S.STUDENT_NUM, S.STUDENT_PWD, S.STUDENT_NAME, T.TEACHER_NAME, T.GRADE, T.CLASSES "
				+"FROM STUDENT S JOIN TEACHER T ON (S.TEACHER_NUM = T.TEACHER_NUM) "
				+"WHERE S.STUDENT_NUM=?";
		
		Student s = new Student();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, student.getStudent_num());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				s.setStudent_num(rs.getInt("student_num"));
				s.setStudent_pwd(rs.getString("student_pwd"));
				s.setStudent_name(rs.getString("student_name"));
				s.setTeacher_name(rs.getString("teacher_name"));
				s.setGrade(rs.getInt("grade"));
				s.setClasses(rs.getInt("classes"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	//학생 삭제
	public boolean deleteStudent(Student s) {
		ConnectionClass.conn = ConnectionClass.connection();
		
		//성적 먼저 삭제하고 학생 정보 삭제
		sql ="DELETE FROM SUBJECT WHERE STUDENT_NUM = ?";
		try {
			ConnectionClass.conn.setAutoCommit(false);
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, s.getStudent_num());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			try {
				ConnectionClass.conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//학생 정보 삭제
		 sql = "DELETE FROM STUDENT WHERE STUDENT_NUM = ?";
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, s.getStudent_num());
			int rows = pstmt.executeUpdate();
			if(rows !=0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		
		return false;
		
	}
	//선생님 이름 수정
	public boolean updateTeacher(Student student,String teacher_name) {
		ConnectionClass.conn = ConnectionClass.connection();
		
		String sql = "UPDATE STUDENT SET TEACHER_NUM = (SELECT TEACHER_NUM FROM TEACHER WHERE TEACHER_NAME=?) WHERE STUDENT_NUM=?";
		
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, teacher_name);
			pstmt.setInt(2, student.getStudent_num());
		int rows = pstmt.executeUpdate();
			
		if(rows !=0) {
			return true;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		return false;
		}
		
	//비밀번호 수정
	public boolean updatePwd(Student student, String student_pwd) {
		ConnectionClass.conn = ConnectionClass.connection();
		
		sql = "UPDATE STUDENT SET STUDENT_PWD =? WHERE STUDENT_NUM =?";
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, student_pwd);
			pstmt.setInt(2, student.getStudent_num());
			int rows = pstmt.executeUpdate();
			
			if(rows !=0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
			return false;
		
	}
	//이름 수정
	public boolean updateName(Student student, String student_name) {
		ConnectionClass.conn = ConnectionClass.connection();
		
		sql = "UPDATE STUDENT SET STUDENT_NAME =? WHERE STUDENT_NUM =?";
		
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, student_name);
			pstmt.setInt(2, student.getStudent_num());
			int rows = pstmt.executeUpdate();
			
			if(rows !=0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
			return false;
		
	}
	
	
	//
}
