package ksa.so.beans;

public class LibraryCategoryLanguageDto {
Long id;
String title;
String details;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}

public LibraryCategoryLanguageDto(Long id, String title, String details) {
	super();
	this.id = id;
	this.title = title;
	this.details = details;
}

}
