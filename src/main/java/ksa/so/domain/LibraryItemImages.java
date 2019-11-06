package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class LibraryItemImages {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "FkLibraryItem", nullable=false)
	private LibraryItem item;
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";
	
	long sortOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LibraryItem getItem() {
		return item;
	}

	public void setItem(LibraryItem item) {
		this.item = item;
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

	public long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(long sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		if(this.getFileName() == null) {json.put("fileName", "-");} else {json.put("fileName", this.getFileName());}
		if(this.getFileUrl() == null) {json.put("fileUrl", "-");} else {json.put("fileUrl", this.getFileUrl());}
		
		return json;
	}
	
}
