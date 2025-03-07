package schoolDB.information;

public class Teacher {
	
	private int teacher_num;
	private String teacher_pwd;
	private String teacher_name;
	private int grade;
	private int classes;
	
	public Teacher() {}

	
	public Teacher(int teacher_num, String teacher_pwd) {
		super();
		this.teacher_num = teacher_num;
		this.teacher_pwd = teacher_pwd;
	}


	public Teacher(int teacher_num, String teacher_pwd, String teacher_name, int grade, int classes) {
		super();
		this.teacher_num = teacher_num;
		this.teacher_pwd = teacher_pwd;
		this.teacher_name = teacher_name;
		this.grade = grade;
		this.classes = classes;
	}


	public int getTeacher_num() {
		return teacher_num;
	}


	public void setTeacher_num(int teacher_num) {
		this.teacher_num = teacher_num;
	}


	public String getTeacher_pwd() {
		return teacher_pwd;
	}


	public void setTeacher_pwd(String teacher_pwd) {
		this.teacher_pwd = teacher_pwd;
	}


	public String getTeacher_name() {
		return teacher_name;
	}


	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}


	public int getGrade() {
		return grade;
	}


	public void setGrade(int grade) {
		this.grade = grade;
	}


	public int getClasses() {
		return classes;
	}


	public void setClasses(int classes) {
		this.classes = classes;
	}

	
	
}
