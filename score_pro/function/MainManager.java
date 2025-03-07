package schoolDB.function;

import java.util.Scanner;

public class MainManager {
	
	private Scanner sc = new Scanner(System.in);
	private String choice;
	private StudentManager sm = new StudentManager();
	private TeacherManager tm = new TeacherManager();
	
	public void mainMenu() {
		
		do {
			
			  System.out.println("███████╗ ██████╗██╗  ██╗ ██████╗  ██████╗ ██╗");
			  System.out.println("██╔════╝██╔════╝██║  ██║██╔═══██╗██╔═══██╗██║");
			  System.out.println("███████╗██║     ███████║██║   ██║██║   ██║██║");
			  System.out.println("╚════██║██║     ██╔══██║██║   ██║██║   ██║██║");
			  System.out.println("███████║╚██████╗██║  ██║╚██████╔╝╚██████╔╝███████╗");
			  System.out.println("╚══════╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚══════╝");
			 
			System.out.println("1.학생 메뉴로 이동");
			System.out.println("2.선생님 메뉴로 이동");
			System.out.println("0.종료");
			choice = sc.nextLine().trim();
			
			switch (choice) {
			case "1": 
				sm.mainMenu();
				break;
			case "2":
				tm.mainMenu();
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
		}
	}while(!choice.trim().equals("0"));
		
	}//메인 메뉴
}
