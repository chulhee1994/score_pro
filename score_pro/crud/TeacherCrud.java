package schoolDB.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import schoolDB.application.ConnectionClass;
import schoolDB.information.Student;
import schoolDB.information.Teacher;

public class TeacherCrud {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String sql;
	List<Teacher> teacherList;
	List<Student> studentList;
	
	int rows;
	//로그인 확인
	public Teacher loginCheck(Teacher t) {
		
		ConnectionClass.conn = ConnectionClass.connection();
		
		String sql = "select * from teacher where teacher_num=? and teacher_pwd =?";
		Teacher teacher = new Teacher();
		
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, t.getTeacher_num());
			pstmt.setString(2, t.getTeacher_pwd());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				teacher.setTeacher_num(rs.getInt("TEACHER_NUM"));
				teacher.setTeacher_pwd(rs.getString("TEACHER_PWD"));
				teacher.setTeacher_name(rs.getString("TEACHER_NAME"));
				teacher.setGrade(rs.getInt("GRADE"));
				teacher.setClasses(rs.getInt("CLASSES"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				rs.close();
				pstmt.close();
				ConnectionClass.connectionLogout(ConnectionClass.conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return teacher;
		
	}
	//아이디 확인
	public Teacher teacher_num_print(String teacher_pwd,String teacher_name,int grade,int classes) {
		int id =0;
		Teacher t= new Teacher();
		try {
			ConnectionClass.conn = ConnectionClass.connection();
			String sql = "select * FROM TEACHER WHERE teacher_pwd =? AND TEACHER_NAME = ? AND GRADE =? AND CLASSES = ?";
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, teacher_pwd);
			pstmt.setString(2, teacher_name);
			pstmt.setInt(3, grade);
			pstmt.setInt(4, classes);
			rs = pstmt.executeQuery();
		while(rs.next()) {
			t.setTeacher_num(rs.getInt("teacher_num"));
			t.setTeacher_pwd(rs.getString("teacher_pwd"));
			t.setTeacher_name(rs.getString("teacher_name"));
			t.setGrade(rs.getInt("grade"));
			t.setClasses(rs.getInt("classes"));
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		return t;
	}
	//회원가입
	public boolean insert(String teacher_pwd, String teacher_name, int grade, int classes) {
		
		ConnectionClass.conn = ConnectionClass.connection();
		
		String sql = "INSERT INTO TEACHER VALUES(TEACHER_NUM_SEQ.NEXTVAL,?,?,?,?)";
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, teacher_pwd);
			pstmt.setString(2, teacher_name);
			pstmt.setInt(3, grade);
			pstmt.setInt(4, classes);
			int rows = pstmt.executeUpdate();
			ConnectionClass.conn.commit();
			if(rows ==1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				ConnectionClass.connectionLogout(ConnectionClass.conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//선생님 리스트 확인
	public List<Teacher> teacherList(){
		teacherList = new ArrayList<Teacher>();
		String sql = "SELECT TEACHER_NAME FROM TEACHER";
		ConnectionClass.conn = ConnectionClass.connection();
		
		try {
			stmt = ConnectionClass.conn.createStatement();
			stmt.executeQuery(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String teacher_name = rs.getString("TEACHER_NAME");
				Teacher t = new Teacher();
				t.setTeacher_name(teacher_name);
				teacherList.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		return teacherList;
	}
	
	//선생님 정보 삭제
	public boolean delete(Teacher t) {
		ConnectionClass.conn = ConnectionClass.connection();
		String sql = "DELETE FROM TEACHER WHERE TEACHER_NUM = ?";
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, t.getTeacher_num());
			int rows = pstmt.executeUpdate();
			if(rows !=0) return true;
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
	
	//학생들 출력
	public List<Student> studentList(Teacher t){
		
		ConnectionClass.conn = ConnectionClass.connection();
		studentList = new ArrayList<Student>();

		String sql = "SELECT S.STUDENT_NUM, S.STUDENT_NAME FROM STUDENT S JOIN TEACHER T ON(S.TEACHER_NUM = T.TEACHER_NUM) WHERE T.TEACHER_NUM=?";
		System.out.println(sql);
		
		
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, t.getTeacher_num());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Student s = new Student();
				s.setStudent_num(rs.getInt("student_num"));
				s.setStudent_name(rs.getString("student_name"));
				studentList.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		return studentList;
		
	}
	//평균,합계 구하는 메서드
	public Map<String,Object> avgSumMap(Map<String,Double> subjectMap) {
		Map<String , Object> avgSumMap = new HashMap<String, Object>();
		double sum = 0;
		
		//합계
		Iterator<String> keys = subjectMap.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			sum += subjectMap.get(key);
		}
		
		double avg = (double)sum/subjectMap.size();
		
		avgSumMap.put("avg", avg);
		avgSumMap.put("sum", sum);
		return avgSumMap;
	}
	
	//학생 성적 입력
	public boolean insertSubject(Map<String, Double> subjectMap,int student_num) {
		ConnectionClass.conn = ConnectionClass.connection();
		sql = "SELECT STUDENT_NUM FROM SUBJECT WHERE STUDENT_NUM = ?";
		try {
			Map<String, Object> avgSumMap = avgSumMap(subjectMap);
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, student_num);
			rs = pstmt.executeQuery();
			double avg = (double)avgSumMap.get("avg");
			double sum =  (double)avgSumMap.get("sum");
			if(rs.next()) {
				//rs가 있으면 업데이트 (업데이트 처리문)
				System.out.println("이미 성적 등록된 학생입니다. 수정합니다.");
				return updateSubject(subjectMap,student_num);
			}else {
				//인서트 처리문 
				sql = "INSERT INTO SUBJECT(STUDENT_NUM,KOREAN, ENGLISH, MATH, SCIENCE,AVG,SUM) VALUES(?,?,?,?,?,?,?)";
				pstmt = ConnectionClass.conn.prepareStatement(sql);
				pstmt.setInt(1, student_num);
				pstmt.setDouble(2, subjectMap.get("korean"));
				pstmt.setDouble(3, subjectMap.get("english"));
				pstmt.setDouble(4, subjectMap.get("math"));
				pstmt.setDouble(5, subjectMap.get("science"));
				pstmt.setDouble(6, avg);
				pstmt.setDouble(7, sum);

				int rows = pstmt.executeUpdate();
				if(rows !=0) return true; 
				
				//등수 바꾸기
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionClass.connectionLogout(ConnectionClass.conn);

		}
		//이미 있는 성적이면 업데이트로 처리 없는 성적이면 insert로 처리 
		return false;
	}
	//랭크 구하는 메서드 
	public void UpdateRank(Teacher t,int Student_num) {	
		ConnectionClass.conn = ConnectionClass.connection();
		
		sql = "select student_num from student where teacher_num =?";
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, t.getTeacher_num());
			rs = pstmt.executeQuery();
			List<Integer> student_num_list = new ArrayList<Integer>();
			while(rs.next()) {
				student_num_list.add(rs.getInt("student_num"));
			}
			
			sql = "UPDATE SUBJECT\r\n"
					+ "SET RANK = (SELECT RANK\r\n"
					+ "            FROM (SELECT RANK() OVER(ORDER BY SUM DESC) AS RANK,ST.STUDENT_NUM,ST.TEACHER_NUM\r\n"
					+ "                        FROM STUDENT ST\r\n"
					+ "                                INNER JOIN SUBJECT SU ON ST.STUDENT_NUM = SU.STUDENT_NUM\r\n"
					+ "                                WHERE ST.TEACHER_NUM=?)\r\n"
					+ "                                WHERE STUDENT_NUM =? )           \r\n"
					+ "WHERE STUDENT_NUM=?";
			System.out.println(sql);
			
			for(int i=0; i<student_num_list.size(); i++) {
				
				pstmt = ConnectionClass.conn.prepareStatement(sql);
				pstmt.setInt(1, t.getTeacher_num());
				pstmt.setInt(2, student_num_list.get(i));
				pstmt.setInt(3, student_num_list.get(i));
				rows = pstmt.executeUpdate();
			}
			
			
			
			
			if(rows !=0) {
				System.out.println("업데이트 완료");
			}else {
				System.out.println("업데이트 불가");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		
		
		
		
		


		
	}
	//점수 수정 업데이트
	public boolean updateSubject(Map<String, Double> subjectMap,int student_num) {
		ConnectionClass.conn = ConnectionClass.connection();
		sql = "UPDATE SUBJECT SET KOREAN=?,ENGLISH=?,MATH=?,SCIENCE=?,AVG=?,SUM=? WHERE STUDENT_NUM=?";
		Map avgSumMap = avgSumMap(subjectMap);
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setDouble(1, subjectMap.get("korean"));
			pstmt.setDouble(2, subjectMap.get("english"));
			pstmt.setDouble(3, subjectMap.get("math"));
			pstmt.setDouble(4, subjectMap.get("science"));
			pstmt.setDouble(5, (Double)avgSumMap.get("avg"));
			pstmt.setDouble(6, (Double)avgSumMap.get("sum"));
			pstmt.setInt(7, student_num);
			
			rows = pstmt.executeUpdate();
			if(rows !=0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		return false;
		
	}
	
	//선생님에 맞는 학생 리스트
	public List<Student> selectSubject(Teacher t) {
		
		ConnectionClass.conn = ConnectionClass.connection();
		
		sql = "SELECT ST.STUDENT_NUM,ST.STUDENT_NAME,SU.KOREAN,SU.ENGLISH,SU.MATH,SU.SCIENCE,SU.AVG,SU.SUM,SU.RANK "
					+"FROM STUDENT ST JOIN SUBJECT SU ON(ST.STUDENT_NUM = SU.STUDENT_NUM) "
					+"WHERE ST.TEACHER_NUM = ?";
		
		System.out.println(sql);
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, t.getTeacher_num());
			rs = pstmt.executeQuery();
			studentList = new ArrayList<Student>();
			while(rs.next()) {
				Student s = new Student();
				s.setStudent_num(rs.getInt("student_num"));
				s.setStudent_name(rs.getString("student_name"));
				s.getSubject().setKorean(rs.getDouble("korean"));
				s.getSubject().setEnglish(rs.getDouble("english"));
				s.getSubject().setMath(rs.getDouble("math"));
				s.getSubject().setScience(rs.getDouble("science"));
				s.getSubject().setAvg(rs.getDouble("avg"));
				s.getSubject().setSum(rs.getDouble("sum"));
				s.getSubject().setRank(rs.getInt("rank"));
				studentList.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionClass.connectionLogout(ConnectionClass.conn);
		}
		
		return studentList;
		
		
	}
	//학년 수정
	public boolean updateGrade(Teacher t, int grade) {
		sql = "UPDATE TEACHER SET GRADE =? WHERE TEACHER_NUM=?";
		
		ConnectionClass.conn = ConnectionClass.connection();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, grade);
			pstmt.setInt(2, t.getTeacher_num());
			rows = pstmt.executeUpdate();
			
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
	//반 수정
	public boolean updateClasses(Teacher t, int classes) {
		sql = "UPDATE TEACHER SET CLASSES =? WHERE TEACHER_NUM=?";
		ConnectionClass.conn = ConnectionClass.connection();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setInt(1, classes);
			pstmt.setInt(2, t.getTeacher_num());
			rows = pstmt.executeUpdate();
			
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
	public boolean updatePwd(Teacher t, String teacher_pwd) {
		sql = "UPDATE TEACHER SET TEACHER_PWD =? WHERE TEACHER_NUM=?";
		ConnectionClass.conn = ConnectionClass.connection();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, teacher_pwd);
			pstmt.setInt(2, t.getTeacher_num());
			rows = pstmt.executeUpdate();
			
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
	public boolean updateName(Teacher t, String teacher_name) {
		
		sql = "UPDATE TEACHER SET TEACHER_NAME =? WHERE TEACHER_NUM=?";
		ConnectionClass.conn = ConnectionClass.connection();
		try {
			pstmt = ConnectionClass.conn.prepareStatement(sql);
			pstmt.setString(1, teacher_name);
			pstmt.setInt(2, t.getTeacher_num());
			rows = pstmt.executeUpdate();
			
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
}
