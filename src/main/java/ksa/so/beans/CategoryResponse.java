package ksa.so.beans;

import java.util.List;

import ksa.so.domain.MediaIcons;
import ksa.so.domain.MetaStatus;

public class CategoryResponse {

	Long id;
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}



	String title;
	String details;
	String fileUrl;
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public CategoryResponse(Long id, String fileUrl, String fileName, MediaIcons icon, MetaStatus status) {
		super();
		this.id = id;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.icon = icon;
		this.status = status;
	}
	public CategoryResponse(Long id, String fileUrl, String fileName, MediaIcons icon, MetaStatus status,String title,String details) {
		super();
		this.id = id;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.icon = icon;
		this.status = status;
		this.title = title;
		this.details = details;
				
	}

	public MediaIcons getIcon() {
		return icon;
	}

	public void setIcon(MediaIcons icon) {
		this.icon = icon;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}



	String fileName;
//	Integer Icon;
	MediaIcons icon;
	MetaStatus status;
	Long parentId;
	List<CategoryResponse> subCategory;
	String parentTitle;
	String parentFileUrl;

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	public String getParentFileUrl() {
		return parentFileUrl;
	}

	public void setParentFileUrl(String parentFileUrl) {
		this.parentFileUrl = parentFileUrl;
	}

	public List<CategoryResponse> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<CategoryResponse> subCategory) {
		this.subCategory = subCategory;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public CategoryResponse(Long id, String title, String fileUrl, Long parentId) {
		super();
		this.id = id;
		this.title = title;
		this.fileUrl = fileUrl;
		this.parentId = parentId;
	}

	public CategoryResponse(Long id, String title, String fileUrl, Long parentId, String parentTitle,
			String parentFileUrl) {
		super();
		this.id = id;
		this.title = title;
		this.fileUrl = fileUrl;
		this.parentId = parentId;
		this.parentTitle = parentTitle;
		this.parentFileUrl = parentFileUrl;
	}

	public CategoryResponse() {
// TODO Auto-generated constructor stub
	}
}