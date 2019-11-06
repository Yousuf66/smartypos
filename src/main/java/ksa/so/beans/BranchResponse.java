package ksa.so.beans;

import ksa.so.domain.Branch;
import ksa.so.domain.MetaStatus;

public class BranchResponse {
Long id;
String address; 
String details;
Boolean hasDelivery;
Boolean hasTakeaway;
Long countryId;
Long currencyId;
Double shippingFees;
String maxOrderTime;
String maxKm;

public Double getShippingFees() {
	return shippingFees;
}
public void setShippingFees(Double shippingFees) {
	this.shippingFees = shippingFees;
}
public String getMaxOrderTime() {
	return maxOrderTime;
}
public void setMaxOrderTime(String maxOrderTime) {
	this.maxOrderTime = maxOrderTime;
}
public Long getCurrencyId() {
	return currencyId;
}
public void setCurrencyId(Long currencyId) {
	this.currencyId = currencyId;
}
public Long getCountryId() {
	return countryId;
}
public void setCountryId(Long countryId) {
	this.countryId = countryId;
}
public Boolean getHasDelivery() {
	return hasDelivery;
}
public void setHasDelivery(Boolean hasDelivery) {
	this.hasDelivery = hasDelivery;
}
public Boolean getHasTakeaway() {
	return hasTakeaway;
}
public void setHasTakeaway(Boolean hasTakeaway) {
	this.hasTakeaway = hasTakeaway;
}
public BranchResponse(Long id, String address, String details, String title, String fileName,
		String fileUrl, Long langId, String phone, Boolean is24hours, String lat, String lon, Long orderCompletionTime,
		String timeOpen, String timeClose, String phone1,  MetaStatus status,
		double rating,Boolean hasDelivery,Boolean hasTakeaway,Long countryId,
		Long currencyId,Double shippingFees,String maxOrderTime,String maxKm,String phone2) {
	super();
	this.id = id;
	this.address = address;
	this.details = details;
	this.title = title;
	this.fileName = fileName;
	this.fileUrl = fileUrl;
	this.langId = langId;
	this.phone = phone;
	this.is24hours = is24hours;
	this.lat = lat;
	this.lon = lon;
	this.orderCompletionTime = orderCompletionTime;
	this.timeOpen = timeOpen;
	this.timeClose = timeClose;
	this.phone1 = phone1;

	this.status = status;
	this.rating = rating;
	this.hasDelivery = hasDelivery;
	this.hasTakeaway = hasTakeaway;
	this.countryId = countryId;
	this.currencyId = currencyId;
	this.shippingFees = shippingFees;
	this.maxOrderTime = maxOrderTime;
	this.maxKm = maxKm;
	this.phone2 = phone2;
}
String title;
String fileName;
String fileUrl;
Branch branch;
Long langId;
String phone;
Boolean is24hours;
String lat;
String lon;
Long orderCompletionTime;
String timeOpen;
String timeClose;

public Long getLangId() {
	return langId;
}
public void setLangId(Long langId) {
	this.langId = langId;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public Boolean getIs24hours() {
	return is24hours;
}
public void setIs24hours(Boolean is24hours) {
	this.is24hours = is24hours;
}
public String getLat() {
	return lat;
}
public void setLat(String lat) {
	this.lat = lat;
}
public String getLon() {
	return lon;
}
public void setLon(String lon) {
	this.lon = lon;
}
public Long getOrderCompletionTime() {
	return orderCompletionTime;
}
public void setOrderCompletionTime(Long orderCompletionTime) {
	this.orderCompletionTime = orderCompletionTime;
}
public String getTimeOpen() {
	return timeOpen;
}
public void setTimeOpen(String timeOpen) {
	this.timeOpen = timeOpen;
}
public String getTimeClose() {
	return timeClose;
}
public void setTimeClose(String timeClose) {
	this.timeClose = timeClose;
}
public Branch getBranch() {
	return branch;
}
public BranchResponse(String details, String title, Branch branch) {
	super();
	this.details = details;
	this.title = title;
	this.branch = branch;
}
public void setBranch(Branch branch) {
	this.branch = branch;
}
//Long orderCompletionTime;
String phone1;
String phone2;
public String getMaxKm() {
	return maxKm;
}
public void setMaxKm(String maxKm) {
	this.maxKm = maxKm;
}
public String getPhone2() {
	return phone2;
}
public void setPhone2(String phone2) {
	this.phone2 = phone2;
}
Long count;
//MetaCountry metaCountry;
//MetaCurrency metaCurrency;
MetaStatus status;
double rating;
public Long getId() {
	return id;
}
public BranchResponse() {
	super();
}
public void setId(Long id) {
	this.id = id;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getFileUrl() {
	return fileUrl;
}
public void setFileUrl(String fileUrl) {
	this.fileUrl = fileUrl;
}
public String getPhone1() {
	return phone1;
}
public void setPhone1(String phone1) {
	this.phone1 = phone1;
}
public MetaStatus getMetaStatus() {
	return status;
}
public Long getCount() {
	return count;
}
public void setCount(Long count) {
	this.count = count;
}
public void setMetaStatus(MetaStatus status) {
	this.status = status;
}
public double getRating() {
	return rating;
}
public void setRating(double rating) {
	this.rating = rating;
}
public BranchResponse(Long id, String address, String details, String title,String fileName,String fileUrl,MetaStatus status) {
	super();
	this.id = id;
	this.address = address;
	this.details = details;
	this.title = title;
	this.fileName = fileName;
	this.fileUrl = fileUrl;
	this.status = status;



}




}
