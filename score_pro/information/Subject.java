package schoolDB.information;

public class Subject {
	
	private int student_num;
	private double korean;
	private double english;
	private double math;
	private double science;
	private double avg;
	private double sum;
	private int rank;
	
	public Subject() {}

	public Subject(int student_num, double korean, double english, double math, double science) {
		super();
		this.student_num = student_num;
		this.korean = korean;
		this.english = english;
		this.math = math;
		this.science = science;
	}
	

	public Subject(int student_num, double korean, double english, double math, double science, double avg, double sum,
			int rank) {
		super();
		this.student_num = student_num;
		this.korean = korean;
		this.english = english;
		this.math = math;
		this.science = science;
		this.avg = avg;
		this.sum = sum;
		this.rank = rank;
	}

	public int getStudent_num() {
		return student_num;
	}

	public void setStudent_num(int student_num) {
		this.student_num = student_num;
	}

	public double getKorean() {
		return korean;
	}

	public void setKorean(double korean) {
		this.korean = korean;
	}

	public double getEnglish() {
		return english;
	}

	public void setEnglish(double english) {
		this.english = english;
	}

	public double getMath() {
		return math;
	}

	public void setMath(double math) {
		this.math = math;
	}

	public double getScience() {
		return science;
	}

	public void setScience(double science) {
		this.science = science;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
	
	
}
