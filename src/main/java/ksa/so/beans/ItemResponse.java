package ksa.so.beans;

public class ItemResponse {

	// private static final long serialVersionUID = 5926468583005150707L;

	Long id;
	String title;
	String details;
	Double discountAmount;
	Double discountPercentage;
	Double price;
	String brand;
	String keyFeatures;
	String branchTitle;

	public String getBranchTitle() {
		return branchTitle;
	}

	public void setBranchTitle(String branchTitle) {
		this.branchTitle = branchTitle;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	Long quantity;
	Long branchID;
	String imgUrl;
	Long categoryId;

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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getKeyFeatures() {
		return keyFeatures;
	}

	public void setKeyFeatures(String keyFeatures) {
		this.keyFeatures = keyFeatures;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getBranchID() {
		return branchID;
	}

	public void setBranchID(Long branchID) {
		this.branchID = branchID;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public ItemResponse(Long id, String title, String details, Double discountAmount, Double discountPercentage,
			Double price, String brand, String keyFeatures, Long quantity, String imgUrl, Long branchID,
			Long categoryID) {
		super();
		this.id = id;
		this.title = title;
		this.details = details;
		this.discountAmount = discountAmount;
		this.discountPercentage = discountPercentage;
		this.price = price;
		this.brand = brand;
		this.keyFeatures = keyFeatures;
		this.quantity = quantity;
		this.imgUrl = imgUrl;
		this.branchID = branchID;
		this.categoryId = categoryID;
	}
	
	public ItemResponse(Long id, String title, String details, Double discountAmount, Double discountPercentage,
			Double price, String brand, String keyFeatures, Long quantity, String imgUrl, Long branchID,
			Long categoryID, String branchTitle) {
		super();
		this.id = id;
		this.title = title;
		this.details = details;
		this.discountAmount = discountAmount;
		this.discountPercentage = discountPercentage;
		this.price = price;
		this.brand = brand;
		this.keyFeatures = keyFeatures;
		this.quantity = quantity;
		this.imgUrl = imgUrl;
		this.branchID = branchID;
		this.categoryId = categoryID;
		this.branchTitle = branchTitle;
	}
}
