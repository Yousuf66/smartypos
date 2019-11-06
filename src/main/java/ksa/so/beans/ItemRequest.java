package ksa.so.beans;

import java.io.Serializable;

import ksa.so.domain.Branch;
import ksa.so.domain.Item;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaLanguage;

public class ItemRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	String searchTerm;
	Branch branchInfo;
	String orderBy;
	LibraryCategory libraryCategoryInfo;
	int listSize;

	public LibraryItem getLibraryItem() {
		return libraryItem;
	}

	public void setLibraryItem(LibraryItem libraryItem) {
		this.libraryItem = libraryItem;
	}

	MetaLanguage languageInfo;
	String timeStamp;
	Item item;
	int listStart;
	LibraryItem libraryItem;

	public int getListStart() {
		return listStart;
	}

	public void setListStart(int listStart) {
		this.listStart = listStart;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Branch getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(Branch branchInfo) {
		this.branchInfo = branchInfo;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public LibraryCategory getLibraryCategoryInfo() {
		return libraryCategoryInfo;
	}

	public void setLibraryCategoryInfo(LibraryCategory libraryCategoryInfo) {
		this.libraryCategoryInfo = libraryCategoryInfo;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
