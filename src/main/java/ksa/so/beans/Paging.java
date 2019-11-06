package ksa.so.beans;

import java.util.Date;

import ksa.so.domain.MetaStatus;

public class Paging {
int page;
int size;
Long id;
String title;
String barcode;
Date date;
public String getBarcode() {
	return barcode;
}
public void setBarcode(String barcode) {
	this.barcode = barcode;
}
MetaStatus metaStatus;
public MetaStatus getMetaStatus() {
	return metaStatus;
}
public void setMetaStatus(MetaStatus metaStatus) {
	this.metaStatus = metaStatus;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

public Paging() {
	super();
}
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getSize() {
	return size;
}
public void setSize(int size) {
	this.size = size;
}
public Paging(int page, int size, Long id, String title,MetaStatus metaStatus,String barcode,Date date) {
	super();
	this.page = page;
	this.size = size;
	this.id = id;
	this.title = title;
	this.metaStatus = metaStatus;
	this.barcode = barcode;
	this.date = date;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}


}
