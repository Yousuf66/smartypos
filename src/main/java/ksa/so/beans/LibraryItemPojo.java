package ksa.so.beans;

import java.util.Date;

import ksa.so.domain.LibraryItemImages;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaStatus;

public class LibraryItemPojo {

	Long id;
	MetaStatus metaStatus;
	String title;
	
	String details;
	String category;
	Long imageId;
	Long langId;
	Long categoryId;
	String fileUrl;
	String fileName;
	Long createdBy;
	Long branchId;
	String branchTitle;
	String username;
	Date created;
	String name;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public LibraryItemPojo(Long id, MetaStatus metaStatus, String title, String details, String category,
			Long imageId,Long libraryItemLangId,Long categoryId,String fileUrl,String fileName,Long createdBy,
			Long branchId,String branchTitle,String username,Date created,String name) {
		super();
		this.id = id;
		this.metaStatus = metaStatus;
		this.title = title;
		this.details = details;
		this.category = category;

		this.imageId = imageId;
		this.langId = libraryItemLangId;
		this.categoryId = categoryId;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.createdBy = createdBy;
		this.branchId = branchId;
		this.branchTitle = branchTitle;
		this.username = username;
		this.created = created;
		this.name = name;
	
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchTitle() {
		return branchTitle;
	}
	public void setBranchTitle(String branchTitle) {
		this.branchTitle = branchTitle;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
		this.langId = langId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MetaStatus getMetaStatus() {
		return metaStatus;
	}
	public void setMetaStatus(MetaStatus metaStatus) {
		this.metaStatus = metaStatus;
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

	
	public LibraryItemPojo(Long id, MetaStatus metaStatus, String title, String details, String category,
			Long imageId,Long libraryItemLangId,Long categoryId,String fileUrl,String fileName,String name) {
		super();
		this.id = id;
		this.metaStatus = metaStatus;
		this.title = title;
		this.details = details;
		this.category = category;

		this.imageId = imageId;
		this.langId = libraryItemLangId;
		this.categoryId = categoryId;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.name = name;
		
	}
	public LibraryItemPojo() {
		super();
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
