package ksa.so.beans;

import java.util.List;

public class LibraryCategoryPagingResponse {
public LibraryCategoryPagingResponse() {
		super();
	}
List<LibraryCategoryResponse> libraryCategoryList;
Long totalRecords;
public LibraryCategoryPagingResponse(List<LibraryCategoryResponse> libraryCategoryList, Long totalRecords) {
	super();
	this.libraryCategoryList = libraryCategoryList;
	this.totalRecords = totalRecords;
}
public List<LibraryCategoryResponse> getLibraryCategoryList() {
	return libraryCategoryList;
}
public void setLibraryCategoryList(List<LibraryCategoryResponse> libraryCategoryList) {
	this.libraryCategoryList = libraryCategoryList;
}
public Long getTotalRecords() {
	return totalRecords;
}
public void setTotalRecords(Long totalRecords) {
	this.totalRecords = totalRecords;
}
}
