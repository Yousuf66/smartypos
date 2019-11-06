package ksa.so.beans;

import java.util.List;

import ksa.so.domain.Branch;

public class BranchRecords {
Long totalRecords;
List<BranchResponse> branchResponseList;


public BranchRecords() {
	super();
}
public BranchRecords(Long totalRecords, List<BranchResponse> branchResponseList) {
	super();
	this.totalRecords = totalRecords;
	this.branchResponseList = branchResponseList;
}
public Long getTotalRecords() {
	return totalRecords;
}
public void setTotalRecords(Long totalRecords) {
	this.totalRecords = totalRecords;
}
public List<BranchResponse> getBranchResponseList() {
	return branchResponseList;
}
public void setBranchResponseList(List<BranchResponse> branchResponseList) {
	this.branchResponseList = branchResponseList;
}

}
