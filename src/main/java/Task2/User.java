package Task2;


public class User {
	private String firstname;
	private String middlename;
	private String lastname;
	
	public User(String firstname, String middlename, String lastname) {
		this.setFirstname(firstname);
		this.setMiddlename(middlename);
		this.setLastname(lastname);
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
}
