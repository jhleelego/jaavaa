package HooonTalk;

public class StudentVO {
	private String id = "";
	private String pw = "";
	private String name = "";
	private int age = 0;
	private String email = "";
	public StudentVO() {
	
	}
	public StudentVO(String id, String pw, String name, int age, String email) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.age = age;
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		String result =  getName() + Integer.toString(getAge()) + getEmail() + "\n";
		return result;
	}
}
