package ksa.so.beans;

import ksa.so.domain.MetaStatus;

public class BranchItemPojo {
	Long id;
	Double price;
	Double cost;
	Long quantity;
	MetaStatus status;
	Double discountAmount;
	Long branchId; 
	Double netSalePrice;
	String title;
	String details;
	String barcode;
	String fileName;
	String fileUrl;
	String name;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public BranchItemPojo(Long id, Double price, Double cost, Long quantity, MetaStatus status,
			Double discountAmount,Long branchId,Double netSalePrice) {
		super();
		this.id = id;
		this.price = price;
		this.cost = cost;
		this.quantity = quantity;
		this.status = status;
		this.discountAmount = discountAmount;
		this.branchId = branchId;
		this.netSalePrice = netSalePrice;
	}
	public BranchItemPojo(Long id, Double price, Double cost, Long quantity, MetaStatus status,
			Double discountAmount,Long branchId,Double netSalePrice,String title,String details,String barcode,
			String fileName, String fileUrl,String name) {
		super();
		this.id = id;
		this.price = price;
		this.cost = cost;
		this.quantity = quantity;
		this.status = status;
		this.discountAmount = discountAmount;
		this.branchId = branchId;
		this.netSalePrice = netSalePrice;
		this.title = title;
		this.details = details;
		this.barcode=barcode;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getNetSalePrice() {
		return netSalePrice;
	}
	public void setNetSalePrice(Double netSalePrice) {
		this.netSalePrice = netSalePrice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public BranchItemPojo() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public MetaStatus getStatus() {
		return status;
	}
	public void setStatus(MetaStatus status) {
		this.status = status;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
}
