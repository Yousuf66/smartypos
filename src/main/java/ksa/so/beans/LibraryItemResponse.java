package ksa.so.beans;

public class LibraryItemResponse {

	// private static final long serialVersionUID = 5926468583005150707L;

	Long id;
	String title;
	String details;

	String imgUrl;
	Long categoryId;

	public Long getId() {
		return id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public LibraryItemResponse(Long id, String title, String details, String imgUrl, Long categoryID) {
		super();
		this.id = id;
		this.title = title;
		this.details = details;

		this.imgUrl = imgUrl;

		this.categoryId = categoryID;
	}

}
