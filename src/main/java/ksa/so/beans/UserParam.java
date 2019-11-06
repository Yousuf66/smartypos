package ksa.so.beans;

public class UserParam {
String username;
String password;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public UserParam() {
	super();
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public UserParam(String username, String password) {
	super();
	this.username = username;
	this.password = password;
}
public UserParam(String username) {
	super();
	this.username = username;
}
}
