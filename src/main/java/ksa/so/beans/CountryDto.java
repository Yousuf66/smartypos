package ksa.so.beans;

public class CountryDto {
Long id;
String code;
String country;
String timezone;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}

public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getTimezone() {
	return timezone;
}
public void setTimezone(String timezone) {
	this.timezone = timezone;
}
public CountryDto(Long id, String code, String country, String timezone) {
	super();
	this.id = id;
	this.code = code;
	this.country = country;
	this.timezone = timezone;
}
}
