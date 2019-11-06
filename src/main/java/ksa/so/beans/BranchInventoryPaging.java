package ksa.so.beans;

import java.util.List;

public class BranchInventoryPaging {
List<BranchItemPojo> branchItems;
Long totalRecords;

public BranchInventoryPaging(List<BranchItemPojo> branchItems, Long totalRecords) {
	super();
	this.branchItems = branchItems;
	this.totalRecords = totalRecords;
}
public BranchInventoryPaging() {
	super();
}
public List<BranchItemPojo> getBranchItems() {
	return branchItems;
}
public void setBranchItems(List<BranchItemPojo> branchItems) {
	this.branchItems = branchItems;
}

public Long getTotalRecords() {
	return totalRecords;
}
public void setTotalRecords(Long totalRecords) {
	this.totalRecords = totalRecords;
}
}
