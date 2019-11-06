package ksa.so.beans;

import java.util.List;

import ksa.so.domain.Item;
import ksa.so.domain.ItemLanguage;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemImages;
import ksa.so.domain.LibraryItemLanguage;


public class ItemListDto {
	List<Item> itemList;
	LibraryItemLanguage libraryItemLanguage; 
	ItemLanguage itemLanguages;
	LibraryCategory libraryCategory;
	LibraryItemImages libraryItemImage;
	LibraryItem libraryItem;
	List<Long> libraryItemIds;
	
	Long branchId;



	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}



	public List<Long> getLibraryItemIds() {
		return libraryItemIds;
	}

	public void setLibraryItemIds(List<Long> libraryItemIds) {
		this.libraryItemIds = libraryItemIds;
	}

	public LibraryItemLanguage getLibraryItemLanguage() {
		return libraryItemLanguage;
	}

	public void setLibraryItemLanguage(LibraryItemLanguage libraryItemLanguage) {
		this.libraryItemLanguage = libraryItemLanguage;
	}

	public ItemLanguage getItemLanguages() {
		return itemLanguages;
	}

	public void setItemLanguages(ItemLanguage itemLanguages) {
		this.itemLanguages = itemLanguages;
	}
	

	
	public LibraryItemImages getLibraryItemImage() {
		return libraryItemImage;
	}

	public void setLibraryItemImage(LibraryItemImages libraryItemImage) {
		this.libraryItemImage = libraryItemImage;
	}

	public LibraryItem getLibraryItem() {
		return libraryItem;
	}
	
	public void setLibraryItem(LibraryItem libraryItem) {
		this.libraryItem = libraryItem;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
}