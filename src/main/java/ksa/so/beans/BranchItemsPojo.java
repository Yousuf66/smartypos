package ksa.so.beans;

import java.util.List;

import ksa.so.domain.Item;

public class BranchItemsPojo {

	Item item;
	 
	Long libraryItemId;



	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BranchItemsPojo(Long libraryItemId, Item item) {
		super();
		this.item = item;
		this.libraryItemId = libraryItemId;
	}

	public BranchItemsPojo() {
		super();
	}

	public Long getLibraryItemId() {
		return libraryItemId;
	}

	public void setLibraryItemId(Long libraryItemId) {
		this.libraryItemId = libraryItemId;
	}

	
	
}
