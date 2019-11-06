package ksa.so.beans;

import java.util.List;

public class LibraryItemPagingResponse {
List<LibraryItemPojo> libraryItemList;
Long totalRecords;
public LibraryItemPagingResponse() {
	super();
}
public LibraryItemPagingResponse(List<LibraryItemPojo> libraryItemList, Long totalRecords) {
	super();
	this.libraryItemList = libraryItemList;
	this.totalRecords = totalRecords;
}
public List<LibraryItemPojo> getLibraryItemList() {
	return libraryItemList;
}
public void setLibraryItemList(List<LibraryItemPojo> libraryItemList) {
	this.libraryItemList = libraryItemList;
}
public Long getTotalRecords() {
	return totalRecords;
}
public void setTotalRecords(Long totalRecords) {
	this.totalRecords = totalRecords;
}


}
