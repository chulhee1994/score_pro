package schoolDB.function;

import java.util.List;
import java.util.Scanner;

import schoolDB.crud.StudentCrud;
import schoolDB.crud.TeacherCrud;
import schoolDB.information.Student;
import schoolDB.information.Teacher;

public class StudentManager {
	private Scanner sc = new Scanner(System.in);
	private String choice;
	private int student_num;
	private String student_pwd;
	private int teacher_num;
	private String teacher_name;
	private String student_name;
	private boolean check;
	private MainManager mm;
	private StudentCrud crud = new StudentCrud();
	private TeacherCrud tcrud = new TeacherCrud();
	private List<Teacher> teacherList;
	
	public void mainMenu() {
		mm =new MainManager();
		do {
			
		System.out.println("███████╗████████╗██╗   ██╗██████╗ ███████╗███╗   ██╗████████╗");
		System.out.println("██╔════╝╚══██╔══╝██║   ██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝");
		System.out.println("███████╗   ██║   ██║   ██║██║  ██║█████╗  ██╔██╗ ██║   ██║");
		System.out.println("╚════██║   ██║   ██║   ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║");
		System.out.println("███████║   ██║   ╚██████╔╝██████╔╝███████╗██║ ╚████║   ██║");
		System.out.println("╚══════╝   ╚═╝    ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝ ");
		
		System.out.println("1.로그인");
		System.out.println("2.회원 가입");
		System.out.println("0.이전 페이지로 이동");
		System.out.print("메뉴를 선택해주세요.");
		choice = 
				sc.nextLine().trim();
		
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
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
		}
			
		}while(!choice.trim().equals("0"));
	}

	
	private void loginMenu(Student student) {
		
		
		do {
			System.out.println("1.성적확인");
			System.out.println("2.학생 정보 수정");
			System.out.println("3.학생 정보 확인");
			System.out.println("4.학생 정보 삭제");
			System.out.println("5.로그아웃");
			System.out.println("메뉴를 입력해주세요.");
		choice = 
				sc.nextLine().trim();
		
		switch(choice) {
		case "1":
			score(student);
			break;
		case "2":
			editMenu(student);
			break;
		case "3":
			information(student);
			break;
		case "4":
			delete(student);
			break;
		case "5":
			System.out.println("로그아웃 합니다.");
			mainMenu();
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
			break;
		}
			
		}while(!choice.trim().equals("0"));
	}
	private void editMenu(Student student) {
		
		
		do {
			System.out.println("1.학생 이름 수정");
			System.out.println("2.학생 비밀번호 수정");
			System.out.println("3.선생님 이름 수정");
			System.out.println("0.이전 페이지로 이동");
			System.out.println("메뉴를 입력해주세요.");
		choice = 
				sc.nextLine().trim();
		
		switch(choice) {
		
		case "1":
			editName(student);
			break;
		case "2":
			editPwd(student);
			break;
		case "3":
			editTeacher(student);
			break;
		case "0":
			System.out.println("이전 페이지로 이동합니다.");
			loginMenu(student);
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
		
		}
		}while(!choice.trim().equals("0"));
		
	}
	
	//선생님 이름 수정
	private void editTeacher(Student student) {
		System.out.println("수정할 선생님 이름을 입력해주세요.");
		String teacher_name = sc.nextLine().trim();
		List<Teacher> teacherList =tcrud.teacherList();
		
		boolean teacherCheck = false;
		for(Teacher t : teacherList) {
			if(teacher_name.trim().equals(t.getTeacher_name().trim())) {
				teacherCheck = true;
				break;
			}
		}
		if(!teacherCheck) {
			while(!teacherCheck) {
				System.out.println("선생님 이름이 맞지 않습니다. 다시 입력해주세요.");
				teacher_name = sc.nextLine().trim();
				
				for(Teacher t : teacherList) {
					if(teacher_name.trim().equals(t.getTeacher_name().trim())) {
						teacherCheck = true;
						break;
					}
			}
		}
		}
		
		check = crud.updateTeacher(student,teacher_name);
		
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}

	//비밀번호 수정
	private void editPwd(Student student) {
		System.out.println("수정할 비밀번호를 입력해주세요.");
		String student_pwd = sc.nextLine().trim();
		boolean check = crud.updatePwd(student, student_pwd);
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}

	//이름 수정
	private void editName(Student student) {
		System.out.println("수정할 이름을 입력해주세요.");
		String student_name = sc.nextLine().trim();
		boolean check = crud.updateName(student,student_name);
		
		if(check) {
			System.out.println("수정이 완료 되었습니다.");
		}else {
			System.out.println("수정하지 못했습니다.");
		}
	}


	//로그인 메서드
	private void login() {
		System.out.println("학생 번호를 입력해주세요.");
		student_num = Integer.parseInt(
				sc.nextLine().trim());
		System.out.println("비밀번호를 입력해주세요.");
		student_pwd = sc.nextLine().trim();
		Student student =  new Student(student_num, student_pwd);
		boolean check = crud.isLogin(student);
		
		if(check) {
			System.out.println("로그인 되었습니다.");
			loginMenu(student);
		}else {
			System.out.println("로그인 하지 못했습니다.");
		}
		
	}
	
	
	
	//회원 정보 확인
	private void information(Student student) {
	Student s = crud.informationStudent(student);
	System.out.println("───────────────────────────────────────────────────────────");
	System.out.printf("%10s %10s %10s %10s %10s %10s\n","학생번호","비밀번호","이름","선생님","학년","반");
	System.out.printf("%10d %10s %10s %10s %10d %10d\n",
				s.getStudent_num(),s.getStudent_pwd(),
				s.getStudent_name(),s.getTeacher_name(),
				s.getGrade(),s.getClasses());
	System.out.println("───────────────────────────────────────────────────────────");
	}
	//회원가입 
	private void signUp() {
		System.out.println("비밀번호를 입력해주세요.");
		 student_pwd = sc.nextLine().trim();
		
		System.out.println("선생님 이름을 입력해주세요.");
		 teacher_name = sc.nextLine().trim();
		
		teacherList =  tcrud.teacherList();
		boolean teacherCheck = false;
		 for(Teacher t : teacherList) {
			 if(t.getTeacher_name().equals(teacher_name.trim())) {
				 teacherCheck = true;
				 break;
			 }
		 }
		 if(teacherCheck == false) {
			 do {
				 System.out.println("선생님 이름이 맞지 않습니다.");
				 System.out.println("선생님 이름을 다시 입력해주세요.");
				 teacher_name = sc.nextLine().trim();
				 
				 for(Teacher t : teacherList) {
					 if(t.getTeacher_name().equals(teacher_name.trim())) {
						 teacherCheck = true;
						 break;
					 }
				 }
				 
			 }while(!teacherCheck);
		 }
		 System.out.println("이름을 입력해주세요.");
		 student_name = sc.nextLine().trim();
		 
		 //선생님이 있으면
		 boolean check = crud.isSignUp(student_pwd, teacher_name, student_name);
		 Student s = crud.selectStudent(student_pwd, student_name);
		if(check) {
			System.out.println("회원가입이 완료 되었습니다.");
			System.out.println("───────────────────────────────────────────────────────────");
			System.out.println("번호 :" + s.getStudent_num());
			System.out.println("비밀번호 : " + s.getStudent_pwd());
			System.out.println(s.getGrade() +"학년 " +s.getClasses()+ "반");
			System.out.println("선생님 : "+ s.getTeacher_name());
			System.out.println("───────────────────────────────────────────────────────────");
		}else {
			System.out.println("회원가입을 하지 못했습니다.");
		}
		
	}
	
	private void score(Student student) {
	Student s =crud.selectScore(student);
	if(s.getStudent_num() !=0) {
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.printf("%8s %9s %7s %7s %7s %7s %7s %7s %7s\n","번호","이름","국어","영어","수학","과학","평균","합계","등수");
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.printf("%8d %8s %8.1f %8.1f %8.1f %8.1f %8.1f %8.1f %10d\n",
				s.getStudent_num(),s.getStudent_name(),s.getSubject().getKorean(),s.getSubject().getEnglish(),s.getSubject().getMath(),s.getSubject().getScience(),s.getSubject().getAvg(),s.getSubject().getSum(),s.getSubject().getRank());
	}else {
		System.out.println("성적이 입력되지 않았습니다.");
	}
	System.out.println();
	}
	

	private void delete(Student s) {
		check = crud.deleteStudent(s);
		if(check) {
			System.out.println("학생 정보를 삭제했습니다.");
			mainMenu();
		}else {
			System.out.println("학생 정보를 삭제하지 못했습니다.");
			loginMenu(s);
		}
	}


}
