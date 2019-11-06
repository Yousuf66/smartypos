package ksa.so.beans;

import java.io.Serializable;

import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.UserApp;

public class AccountInformation implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String phone;
	private MetaCountry countryInfo;
	private MetaLanguage languageInfo;
	private UserApp user;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	String email;
	String firstName;
	String lastName;
	String gender;
	String otp;
	String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public MetaCountry getCountryInfo() {
		return countryInfo;
	}

	public void setCountryInfo(MetaCountry countryInfo) {
		this.countryInfo = countryInfo;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
