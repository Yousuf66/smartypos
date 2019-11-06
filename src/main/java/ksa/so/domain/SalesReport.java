package ksa.so.domain;

public class SalesReport {
	
	private Long orderId;
	private String   barcode;
	private String   itemName;
	private String branchName;
	private int   quantity;
	private double price;
	private double cost;
	private double   discountAmount;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public SalesReport(Long orderId, String barcode, String itemName, String branchName, int quantity, double price,
			double cost, double discountAmount) {
		super();
		this.orderId = orderId;
		this.barcode = barcode;
		this.itemName = itemName;
		this.branchName = branchName;
		this.quantity = quantity;
		this.price = price;
		this.cost = cost;
		this.discountAmount = discountAmount;
	}
	
}
