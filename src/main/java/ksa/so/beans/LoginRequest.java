package ksa.so.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.UserApp;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	String username;
	String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
