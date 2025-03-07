package schoolDB.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import schoolDB.crud.TeacherCrud;
import schoolDB.information.Student;
import schoolDB.information.Subject;
import schoolDB.information.Teacher;

public class TeacherManager{
	private Scanner sc = new Scanner(System.in);
	private String choice;
	private TeacherCrud tc = new TeacherCrud();
	private boolean check;
	private MainManager mm;
	private List<Student> studentList;
	//메인 화면
	public void mainMenu() {
		do {
			mm = new MainManager();
			
			System.out.println("████████╗███████╗ █████╗  ██████╗██╗  ██╗███████╗██████╗");
			System.out.println("╚══██╔══╝██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██╔══██╗");
			System.out.println("   ██║   █████╗  ███████║██║     ███████║█████╗  ██████╔╝");
			System.out.println("   ██║   ██╔══╝  ██╔══██║██║     ██╔══██║██╔══╝  ██╔══██╗");
			System.out.println("   ██║   ███████╗██║  ██║╚██████╗██║  ██║███████╗██║  ██║");
			System.out.println("   ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝");
			
			System.out.println("1.로그인");
			System.out.println("2.회원 가입");
			System.out.println("0.이전 페이지로 이동");
			System.out.print("메뉴를 선택해주세요.");
			choice = sc.nextLine().trim();
			
			switch(choice) {
			case "1":
				login();
				break;
			case "2":
				signUp();
				break;
			case "0":
				System.out.println("이전 페이지로 돌아갑니다.");
				mm.mainMenu();
				
			}
		}while(!choice.trim().equals("0"));
	}
	
	//센세 로그인
	private void login() {
		tc = new TeacherCrud();
		System.out.println("선생님 번호를 입력해주세요.");
		int teacher_num = Integer.parseInt(sc.nextLine().trim());
		
		System.out.println("비밀번호를 입력해주세요.");
		String teacher_pwd = sc.nextLine().trim();
		
		
		Teacher t = new Teacher(teacher_num, teacher_pwd);
		t = tc.loginCheck(t);
		
		if(teacher_num == t.getTeacher_num() && teacher_pwd.equals(t.getTeacher_pwd())) {
			loginMenu(t);
		}else {
			System.out.println("로그인 할 수 없습니다.");
		}
			
	
	}
	//센세 로그인후 메뉴
	private void loginMenu(Teacher t) {
		
		do {
			System.out.println(t.getGrade()+"학년 "+t.getClasses()+"반 "+t.getTeacher_name() +" 선생님 로그인 상태입니다.");
			System.out.println("1.점수 등록");
			System.out.println("2.점수 수정");
			System.out.println("3.성적 출력");
			System.out.println("4.선생님 정보 수정");
			System.out.println("5.선생님 정보 삭제");
			System.out.println("0.로그아웃");
			choice = sc.nextLine().trim();
			switch(choice) {
			case "1":
				insert(t);
				break;
			case "2":
				modify(t);
				break;
			case "3":
				select(t);
				break;
			case "4":
				editMenu(t);
				break;
			case "5":
				delete(t);
				break;
			case "0":
				mainMenu();
				break;
			}
		}while(!choice.trim().equals("0"));
	}
	
	private void editMenu(Teacher t) {
		
		
		do {
			System.out.println("1.선생님 이름 수정");
			System.out.println("2.선생님 비밀번호 수정");
			System.out.println("3.선생님 학년 수정");
			System.out.println("4.선생님 반 수정");
			System.out.println("0.이전 페이지로 이동");
			System.out.println("메뉴를 입력해주세요.");
		choice = 
				sc.nextLine().trim();
		switch(choice) {
		case "1":
			editName(t);
			break;
		case "2":
			editPwd(t);
			break;
		case "3":
			editgrade(t);
			break;
		case "4":
			editClasses(t);
		case "0":
			System.out.println("이전 페이지로 이동합니다.");
			loginMenu(t);
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
		}
	}while(!choice.trim().equals("0"));
	}
	//학년 수정
	private void editgrade(Teacher t) {
		System.out.println("수정할 선생님 학년을 입력해주세요.");
		int grade = Integer.parseInt(sc.nextLine().trim());
		
		check = tc.updateGrade(t,grade);
		
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}
	//반 수정
	private void editClasses(Teacher t) {
		System.out.println("수정할 선생님 반을 입력해주세요.");
		int classes = Integer.parseInt(sc.nextLine().trim());
		
		check = tc.updateClasses(t,classes);
		
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}

	//비밀번호 수정
	private void editPwd(Teacher t) {
		System.out.println("수정할 선생님 비밀번호를 입력해주세요.");
		String teacher_pwd = sc.nextLine().trim();
		
		check = tc.updatePwd(t,teacher_pwd);
		
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}
	
	//이름 수정
	private void editName(Teacher t) {
		System.out.println("수정할 선생님 이름을 입력해주세요.");
		String teacher_name = sc.nextLine().trim();
		
		check = tc.updateName(t,teacher_name);
		
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}

	//선생님 회원가입 
	private void signUp() {
		System.out.println("비밀번호를 입력해주세요.");
		String teacher_pwd = sc.nextLine().trim();
		
		System.out.println("이름을 입력해주세요.");
		String teacher_name = sc.nextLine().trim();
		
		System.out.println("학년을 입력해주세요.");
		int grade = Integer.parseInt(sc.nextLine().trim());
		
		System.out.println("반을 입력해주세요.");
		int classes = Integer.parseInt(sc.nextLine().trim());
		
		check = tc.insert(teacher_pwd,teacher_name,grade,classes);
		if(check) {
			System.out.println("회원가입이 완료되었습니다.");
			System.out.println("───────────────────────────────────────────────────────────");
			Teacher t = tc.teacher_num_print(teacher_pwd,teacher_name,grade,classes);
			System.out.println("아이디 : "+ t.getTeacher_num());
			System.out.println("비밀번호 : " + t.getTeacher_pwd());
			System.out.println("이름 : " +t.getTeacher_name());
			System.out.println(t.getGrade() +"학년 " + t.getClasses()+ "반 지도");
			System.out.println("───────────────────────────────────────────────────────────");
		}else {
			System.out.println("회원가입을 완료하지 못했습니다.");
		}
	}

	//학생 성적 입력
	private void insert(Teacher t) {
		//성적 입력 가능한 학생 출력
		tc = new TeacherCrud();
		studentList = tc.studentList(t);
		if(!studentList.isEmpty()) {
			System.out.println("성적 입력 가능한 학생입니다.");
			System.out.println("───────────────────────────────────────────────────────────");
			System.out.printf("%10s %10s\n","학생번호","이름");
			System.out.println("───────────────────────────────────────────────────────────");
			//성적 입력 가능한 학생들 출력
			for(Student s : studentList) {
				System.out.printf("%10d %10s\n",s.getStudent_num(),s.getStudent_name());
			}
			System.out.println("성적 입력할 학생을 선택하세요.");
			int student_num = Integer.parseInt(sc.nextLine().trim());
			

			
			
			boolean studentCheck =false;
			for(Student s : studentList) {
				if(s.getStudent_num() == student_num) {
					studentCheck = true;
					break;
				}
			}
			if(studentCheck) {
				
				System.out.println("국어 점수를 입력하세요.");
				double korean = Double.parseDouble(sc.nextLine().trim());
				
				System.out.println("영어 점수를 입력하세요.");
				double english = Double.parseDouble(sc.nextLine().trim());
				
				System.out.println("수학 점수를 입력하세요.");
				double math = Double.parseDouble(sc.nextLine().trim());
				
				System.out.println("사회 점수를 입력하세요.");
				double science = Double.parseDouble(sc.nextLine().trim());
				Map<String, Double> subjectMap = new HashMap<String, Double>();
				subjectMap.put("korean", korean);
				subjectMap.put("english", english);
				subjectMap.put("math", math);
				subjectMap.put("science", science);
				tc.insertSubject(subjectMap, student_num);
				System.out.println("성적 입력이 완료 되었습니다.");
				tc.UpdateRank(t, student_num);
				//성적이 이미 있는지 확인
						
			}else {
				System.out.println("성적 입력 가능한 학생이 아닙니다.");
			}
		}else {
			System.out.println("성적 입력할 학생이 없습니다.");
		}
	}

	//선생님 정보 삭제
	private void delete(Teacher t) {
		
		check = tc.delete(t);
		if(check) {
			System.out.println("선생님 정보를 삭제했습니다.");
			mainMenu();
		}else {
			System.out.println("선생님 정보를 삭제하지 못했습니다.");
			loginMenu(t);
		}
	}
	//학생 성적 수정
	private void modify(Teacher t) {
		studentList = tc.studentList(t);
		if(!studentList.isEmpty()) {
			System.out.println("성적 수정 가능한 학생입니다.");
			System.out.println("───────────────────────────────────────────────────────────");
			System.out.printf("%10s %10s\n","학생번호","이름");
			System.out.println("───────────────────────────────────────────────────────────");
			//성적 입력 가능한 학생들 출력
			for(Student s : studentList) {
				System.out.printf("%10d %10s\n",s.getStudent_num(),s.getStudent_name());
			}
			System.out.println();
			
			System.out.println("성적 입력할 학생을 선택하세요.");
			int student_num = Integer.parseInt(sc.nextLine().trim());
			
			
			boolean studentCheck =false;
			for(Student s : studentList) {
				if(s.getStudent_num() == student_num) {
					studentCheck = true;
					break;
				}
			}
			if(studentCheck) {
				
				System.out.println("국어 점수를 입력하세요.");
				double korean = Double.parseDouble(sc.nextLine().trim());
				
				System.out.println("영어 점수를 입력하세요.");
				double english = Double.parseDouble(sc.nextLine().trim());
				
				System.out.println("수학 점수를 입력하세요.");
				double math = Double.parseDouble(sc.nextLine().trim());
				
				System.out.println("사회 점수를 입력하세요.");
				double science = Double.parseDouble(sc.nextLine().trim());
				Map<String, Double> subjectMap = new HashMap<String, Double>();
				subjectMap.put("korean", korean);
				subjectMap.put("english", english);
				subjectMap.put("math", math);
				subjectMap.put("science", science);
				check  = tc.updateSubject(subjectMap, student_num);
				tc.UpdateRank(t, student_num);

				if(check) {
					System.out.println("성적 수정이 완료 되었습니다.");
				}else {
					System.out.println("성적 수정을 하지 못했습니다.");
				}
	}else {
		System.out.println("성적을 수정할 학생이 아닙니다.");
	}
		}else {
			System.out.println("성적을 수정할 학생이 없습니다.");
		}
	}
	
	//학생 성적 전체 출력
	private void select(Teacher t) {
		List<Student> subjectList =  tc.selectSubject(t);
		
		if(!subjectList.isEmpty()) {
			System.out.println("학생 리스트");
			System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			System.out.printf("%8s %9s %7s %7s %7s %7s %7s %7s %7s\n","번호","이름","국어","영어","수학","과학","평균","합계","등수");
			System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			for(Student s : subjectList) {
				System.out.printf("%8d %8s %8.1f %8.1f %8.1f %8.1f %8.1f %8.1f %10d\n",
								s.getStudent_num(),s.getStudent_name(),s.getSubject().getKorean(),s.getSubject().getEnglish(),
								s.getSubject().getMath(),s.getSubject().getScience(),s.getSubject().getAvg(),s.getSubject().getSum(),s.getSubject().getRank());
			}
		}else {
			//비어 있을경우
			System.out.println("해당하는 학생이 없습니다.");
		}
	}
}
