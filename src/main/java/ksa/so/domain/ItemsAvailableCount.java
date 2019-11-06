package ksa.so.domain;

public class ItemsAvailableCount {
	
	private Branch branch;
	private Long   count;

	public ItemsAvailableCount(Branch branch, Long count) {
		this.branch = branch;
		this.count  = count;
	}

	public Branch getBranch() {
		return this.branch;
	}

	public Long getCount() {
		return this.count;
	}

}
