package ksa.so.beans;

import java.io.Serializable;

import ksa.so.domain.Branch;
import ksa.so.domain.MetaLanguage;

public class ListRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	String searchTerm;

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	MetaLanguage languageInfo;

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	String timeStamp;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	int listSize;

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	Branch branchInfo;

	public Branch getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(Branch branchInfo) {
		this.branchInfo = branchInfo;
	}

	int listStart;
	
	public int getListStart() {
		return listStart;
	}

	public void setListStart(int listStart) {
		this.listStart = listStart;
	}

}
