package ksa.so.beans;

public class ItemBasic {
	private Long id;
	private String title;
	private Long categoryId;
	private String imgUrl;
	private int quantity;

	public ItemBasic(Long id, String title, Long categoryId, String imgUrl) {
		super();
		this.id = id;
		this.title = title;
		this.categoryId = categoryId;
		this.imgUrl = imgUrl;
	}

	public ItemBasic(Long id, String title, int quantity) {
		super();
		this.id = id;
		this.title = title;
		this.quantity = quantity;
	}

	public String getId() {
		return id.toString();
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

	public String getCategoryId() {
		return categoryId.toString();
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
