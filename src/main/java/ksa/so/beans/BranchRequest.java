package ksa.so.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import ksa.so.domain.Branch;
import ksa.so.domain.MetaLanguage;

public class BranchRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	MetaLanguage languageInfo;

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	Timestamp timeStamp;

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Branch getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(Branch branchInfo) {
		this.branchInfo = branchInfo;
	}

	Branch branchInfo;

}
