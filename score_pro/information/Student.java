package schoolDB.information;

public class Student {
	private int student_num;
	private String student_pwd;
	private String student_name;
	private int teacher_num;
	private String teacher_name;
	private Subject subject = new Subject();
	private int grade;
	private int classes;
	
	public Student() {}

	public Student(int student_num, String student_pwd) {
		super();
		this.student_num = student_num;
		this.student_pwd = student_pwd;
	}

	public Student(int student_num, String student_pwd, String student_name, int teacher_num, String teacher_name,
			Subject subject, int grade, int classes) {
		super();
		this.student_num = student_num;
		this.student_pwd = student_pwd;
		this.student_name = student_name;
		this.teacher_num = teacher_num;
		this.teacher_name = teacher_name;
		this.subject = subject;
		this.grade = grade;
		this.classes = classes;
	}
	

	public int getStudent_num() {
		return student_num;
	}

	public void setStudent_num(int student_num) {
		this.student_num = student_num;
	}

	public String getStudent_pwd() {
		return student_pwd;
	}

	public void setStudent_pwd(String student_pwd) {
		this.student_pwd = student_pwd;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public int getTeacher_num() {
		return teacher_num;
	}

	public void setTeacher_num(int teacher_num) {
		this.teacher_num = teacher_num;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
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
