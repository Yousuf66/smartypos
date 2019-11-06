package ksa.so.beans;

public class CurrencyDto {
Long id;
public CurrencyDto(Long id, String code, String symbol, String title) {
	super();
	this.id = id;
	this.code = code;
	this.symbol = symbol;
	this.title = title;
}
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
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
String code;
String symbol;
String title;
}
