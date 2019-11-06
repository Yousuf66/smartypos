package ksa.so.beans;

import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryCategoryLanguage;
import ksa.so.domain.MetaStatus;

public class LibraryCategoryDto {
LibraryCategory libraryCategory;
LibraryCategoryLanguage libraryCategoryLanguage;
MetaStatus status;
public LibraryCategoryDto() {
	super();
}



public LibraryCategory getLibraryCategory() {
	return libraryCategory;
}


public void setLibraryCategory(LibraryCategory libraryCategory) {
	this.libraryCategory = libraryCategory;
}
public LibraryCategoryLanguage getLibraryCategoryLanguage() {
	return libraryCategoryLanguage;
}
public void setLibraryCategoryLanguage(LibraryCategoryLanguage libraryCategoryLanguage) {
	this.libraryCategoryLanguage = libraryCategoryLanguage;
}
public MetaStatus getStatus() {
	return status;
}
public void setStatus(MetaStatus status) {
	this.status = status;
}
public LibraryCategoryDto(LibraryCategory libraryCategory, LibraryCategoryLanguage libraryCategoryLanguage,
		MetaStatus status) {
	super();
	this.libraryCategory = libraryCategory;
	this.libraryCategoryLanguage = libraryCategoryLanguage;
	this.status = status;
}

}
