package ksa.so.beans;

import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemImages;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaStatus;

public class LibraryItemDto {
MetaStatus metaStatus;
LibraryItem libraryItem;
LibraryCategory libraryCategory;
LibraryItemLanguage libraryItemLanguage;
LibraryItemImages libraryItemImages;

public LibraryItemImages getLibraryItemImages() {
	return libraryItemImages;
}
public void setLibraryItemImages(LibraryItemImages libraryItemImages) {
	this.libraryItemImages = libraryItemImages;
}
public LibraryItemLanguage getLibraryItemLanguage() {
	return libraryItemLanguage;
}
public void setLibraryItemLanguage(LibraryItemLanguage libraryItemLanguage) {
	this.libraryItemLanguage = libraryItemLanguage;
}
public LibraryItem getLibraryItem() {
	return libraryItem;
}
public void setLibraryItem(LibraryItem libraryItem) {
	this.libraryItem = libraryItem;
}
public LibraryItemDto(MetaStatus metaStatus, LibraryCategory libraryCategory,
		LibraryItem libraryItem,LibraryItemLanguage librarayItemLanguage, LibraryItemImages libraryItemImages) {
	super();
	this.metaStatus = metaStatus;
	this.libraryCategory = libraryCategory;
	this.libraryItem=libraryItem;
	this.libraryItemLanguage = librarayItemLanguage;
	this.libraryItemImages=libraryItemImages;
}
public MetaStatus getMetaStatus() {
	return metaStatus;
}
public void setMetaStatus(MetaStatus metaStatus) {
	this.metaStatus = metaStatus;
}
public LibraryItemDto() {
	super();
}
public LibraryCategory getLibraryCategory() {
	return libraryCategory;
}
public void setLibraryCategory(LibraryCategory libraryCategory) {
	this.libraryCategory = libraryCategory;
}

}
