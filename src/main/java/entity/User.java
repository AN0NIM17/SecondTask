package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class User {
	
	private String firstname;
	private String middlename;
	private String lastname;

	public User() {

	}

}
