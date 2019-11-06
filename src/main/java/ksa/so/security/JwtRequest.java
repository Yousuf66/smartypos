package ksa.so.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaUserType;
import ksa.so.domain.User;
import ksa.so.domain.UserApp;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	/*
	 * private String username; private String password; private String phone;
	 * private String otp;
	 */
	UserApp userAppInfo;
	MetaLanguage languageInfo;
	Timestamp timeStamp;
	String loginType;
	User user;
	ArrayList<String> metaUserTypeInfoList;

	public ArrayList<String> getMetaUserTypeInfoList() {
		return metaUserTypeInfoList;
	}

	public void setMetaUserTypeInfoList(ArrayList<String> metaUserTypeInfoList) {
		this.metaUserTypeInfoList = metaUserTypeInfoList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// need default constructor for JSON Parsing
	public JwtRequest() {

	}

	/*
	 * public JwtRequest(String username, String password) {
	 * this.setUsername(username); this.setPassword(password); }
	 */

	/*
	 * public String getUsername() { return this.username; }
	 *
	 * public void setUsername(String username) { this.username = username; }
	 *
	 * public String getPassword() { return this.password; }
	 *
	 * public void setPassword(String password) { this.password = password; }
	 *
	 * public String getPhone() { return phone; }
	 *
	 * public void setPhone(String phone) { this.phone = phone; }
	 *
	 * public String getOtp() { return otp; }
	 *
	 * public void setOtp(String otp) { this.otp = otp; }
	 */

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public UserApp getUserAppInfo() {
		return userAppInfo;
	}

	public void setUserAppInfo(UserApp userAppInfo) {
		this.userAppInfo = userAppInfo;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

}