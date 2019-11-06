package ksa.so.beans;

import java.util.List;

public class BranchItemsDto {
	Long branchId;

List<BranchItemsPojo> branchItems;

public Long getBranchId() {
	return branchId;
}


public void setBranchId(Long branchId) {
	this.branchId = branchId;
}

public List<BranchItemsPojo> getBranchItems() {
	return branchItems;
}

public BranchItemsDto() {
	super();
}




public void setBranchItems(List<BranchItemsPojo> branchItems) {
	this.branchItems = branchItems;
}

public BranchItemsDto(Long branchId, List<BranchItemsPojo> branchItems) {
	super();
	this.branchId = branchId;
	this.branchItems = branchItems;
}

}
