package ksa.so.beans;

import ksa.so.domain.MediaIcons;
import ksa.so.domain.MetaStatus;

public class LibraryCategoryResponse {
	Long id;
	Long langId;
	String title;
	String details;
	String fileName;
	String fileUrl;
	MetaStatus status;
	Long totalRecords;
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	public MetaStatus getStatus() {
		return status;
	}
	public void setStatus(MetaStatus status) {
		this.status = status;
	}
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
		this.langId = langId;
	}
	public LibraryCategoryResponse(Long id, Long langId, String title, String details, String fileName, String fileUrl,
			MetaStatus status) {
		super();
		this.id = id;
		this.langId = langId;
		this.title = title;
		this.details = details;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.status = status;
	}

}
