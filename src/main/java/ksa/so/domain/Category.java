package ksa.so.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.CategoryLanguageRepository;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@ManyToOne
    @JoinColumn(name = "FkBranch", nullable=false)
	private Branch branch;
	
	@OneToOne
	@JoinColumn(name="FkIcon")
	private MediaIcons icon;
	
	@ManyToOne
	@JoinColumn(name="CreatedBy")
	private User CreatedBy;

	@Column(name="CreatedAt")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name="UpdatedBy")
	private User UpdatedBy;

	@Column(name="UpdatedAt")
	private Date updated;
	
	public MediaIcons getIcon() {
		return icon;
	}
	
	public void setIcon(MediaIcons icon) {
		this.icon = icon;
	}
	
	@OneToMany(mappedBy = "category")
	private List<Item> itemList;
	
	@OneToMany(mappedBy = "category")
	private List<CategoryLanguage> categoryLanguageList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public List<CategoryLanguage> getCategoryLanguageList() {
		return categoryLanguageList;
	}

	public void setCategoryLanguageList(List<CategoryLanguage> categoryLanguageList) {
		this.categoryLanguageList = categoryLanguageList;
	}
	
	public User getCreatedBy() {
		return CreatedBy;
		}

		public void setCreatedBy(User createdBy) {
		CreatedBy = createdBy;
		}

		public User getUpdatedBy() {
		return UpdatedBy;
		}

		public void setUpdatedBy(User updatedBy) {
		UpdatedBy = updatedBy;
		}

		public Date getCreatedAt() {
		return created;
		}

		public void setCreatedAt(Date created) {
		this.created = created;
		}

		public Date getUpdatedAt() {
		return updated;
		}

		public void setUpdatedAt(Date updated) {
		this.updated = updated;
		}
			
	
	public JSONObject getInfo(MetaLanguage language, CategoryLanguageRepository categoryLanguageRepository) {
		JSONObject json = new JSONObject();
		
		//
		CategoryLanguage categoryLanguage = categoryLanguageRepository.findByCategoryAndLanguage(this, language);
		//
		
		json.put("id", this.getId());
		json.put("type", "category");
		if(categoryLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", categoryLanguage.getTitle());}
		if(categoryLanguage.getDetails() == null){json.put("details", "-");} else {json.put("details", categoryLanguage.getDetails());}
		if(this.getFileName() == null) {json.put("fileName", "-");} else {json.put("fileName", this.getFileName());}
		if(this.getFileUrl() == null) {json.put("fileUrl", "-");} else {json.put("fileUrl", this.getFileUrl());}
		if(this.getIcon().getFileUrl() == null) {json.put("icon", "-");} else {json.put("icon", this.getIcon().getFileUrl());}
		
		return json;
	}
}
