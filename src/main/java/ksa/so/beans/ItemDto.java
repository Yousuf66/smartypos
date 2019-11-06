package ksa.so.beans;

import ksa.so.domain.Branch;
import ksa.so.domain.Item;
import ksa.so.domain.ItemLanguage;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public class ItemDto {
LibraryCategory libraryCategory;
MetaStatus metaStatus;
Branch branch;
LibraryItem libraryItem;
Item item;
MetaLanguage metaLanguage;
ItemLanguage itemLanguage;

public ItemDto(LibraryCategory libraryCategory, MetaStatus metaStatus, Branch branch, LibraryItem libraryItem,
		Item item, MetaLanguage metaLanguage,ItemLanguage itemLanguage) {
	super();
	this.libraryCategory = libraryCategory;
	this.metaStatus = metaStatus;
	this.branch = branch;
	this.libraryItem = libraryItem;
	this.item = item;
	this.metaLanguage = metaLanguage;
	this.itemLanguage = itemLanguage;
}


public ItemDto() {
	super();
}
public LibraryCategory getLibraryCategory() {
	return libraryCategory;
}
public void setLibraryCategory(LibraryCategory libraryCategory) {
	this.libraryCategory = libraryCategory;
}
public MetaStatus getMetaStatus() {
	return metaStatus;
}
public void setMetaStatus(MetaStatus metaStatus) {
	this.metaStatus = metaStatus;
}
public Branch getBranch() {
	return branch;
}
public void setBranch(Branch branch) {
	this.branch = branch;
}
public LibraryItem getLibraryItem() {
	return libraryItem;
}
public void setLibraryItem(LibraryItem libraryItem) {
	this.libraryItem = libraryItem;
}
public Item getItem() {
	return item;
}
public void setItem(Item item) {
	this.item = item;
}
public MetaLanguage getMetaLanguage() {
	return metaLanguage;
}
public void setMetaLanguage(MetaLanguage metaLanguage) {
	this.metaLanguage = metaLanguage;
}

public ItemLanguage getItemLanguage() {
	return itemLanguage;
}
public void setItemLanguage(ItemLanguage itemLanguage) {
	this.itemLanguage = itemLanguage;
}

}
