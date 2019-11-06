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
import javax.persistence.Table;

import org.json.JSONObject;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import ksa.so.repositories.LibraryCategoryLanguageRepository;

@Entity
@Table(name="library_category")
public class LibraryCategory {
	public LibraryCategory() {
		super();
	}

	public LibraryCategory(Long id, String fileName, String fileUrl, MetaStatus status, LibraryCategory libraryCategory,
			List<LibraryCategory> libraryCategoryList, MediaIcons icon, List<LibraryItem> libraryItemList,
			List<LibraryCategoryLanguage> libraryCategoryLanguageList, User createdBy, Date created, User updatedBy,
			Date updated) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.status = status;
		this.libraryCategory = libraryCategory;
		this.libraryCategoryList = libraryCategoryList;
		this.icon = icon;
		this.libraryItemList = libraryItemList;
		this.libraryCategoryLanguageList = libraryCategoryLanguageList;
		CreatedBy = createdBy;
		this.created = created;
		UpdatedBy = updatedBy;
		this.updated = updated;
	}

	@Id
	@JsonView(DataTablesOutput.View.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";

	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";

	@ManyToOne
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;

	@ManyToOne
	@JoinColumn(name = "FKLibraryCategory", nullable = true)
	private LibraryCategory libraryCategory;

	@OneToMany(mappedBy = "libraryCategory")
	private List<LibraryCategory> libraryCategoryList;

	public LibraryCategory getLibraryCategory() {
		return libraryCategory;
	}

	public void setLibraryCategory(LibraryCategory libraryCategory) {
		this.libraryCategory = libraryCategory;
	}

	@OneToOne
	@JoinColumn(name = "FkIcon")
	private MediaIcons icon;


	@OneToMany(mappedBy = "libraryCategory")
	private List<LibraryItem> libraryItemList;

	@OneToMany(mappedBy = "libraryCategory")
	private List<LibraryCategoryLanguage> libraryCategoryLanguageList;

	@ManyToOne
	@JoinColumn(name = "CreatedBy")
	private User CreatedBy;

	@Column(name = "CreatedAt")
	private Date created;

	@ManyToOne
	@JoinColumn(name = "UpdatedBy")
	private User UpdatedBy;

	@Column(name = "UpdatedAt")
	private Date updated;

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

	public List<LibraryItem> getLibraryItemList() {
		return libraryItemList;
	}

	public void setLibraryItemList(List<LibraryItem> libraryItemList) {
		this.libraryItemList = libraryItemList;
	}

	public List<LibraryCategoryLanguage> getCategoryLanguageList() {
		return libraryCategoryLanguageList;
	}

	public void setCategoryLanguageList(List<LibraryCategoryLanguage> libraryCategoryLanguageList) {
		this.libraryCategoryLanguageList = libraryCategoryLanguageList;
	}

	public MediaIcons getIcon() {
		return icon;
	}

	public void setIcon(MediaIcons icon) {
		this.icon = icon;
	}

	public JSONObject getInfo(MetaLanguage language,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {
		JSONObject json = new JSONObject();

		//
		LibraryCategoryLanguage libraryCategoryLanguage = libraryCategoryLanguageRepository
				.findByLibraryCategoryAndLanguage(this, language);
		//

		json.put("id", this.getId());
		json.put("type", "category");
		if (libraryCategoryLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", libraryCategoryLanguage.getTitle());
		}
		if (libraryCategoryLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", libraryCategoryLanguage.getDetails());
		}

		if (this.getFileName() == null) {
			json.put("fileName", "-");
		} else {
			json.put("fileName", this.getFileName());
		}
		if (this.getFileUrl() == null) {
			json.put("fileUrl", "-");
		} else {
			json.put("fileUrl", this.getFileUrl());
		}
		if (this.getLibraryCategory() != null) {
			json.put("MainCategory", this.getLibraryCategory().getInfo(language, libraryCategoryLanguageRepository));
		}
		// if(this.getIcon().getFileUrl() == null) {json.put("icon", "-");} else
		// {json.put("icon", this.getIcon().getFileUrl());}

		return json;
	}

	public JSONObject getTitle(MetaLanguage language,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {
		JSONObject json = new JSONObject();

		//
		LibraryCategoryLanguage libraryCategoryLanguage = libraryCategoryLanguageRepository
				.findByLibraryCategoryAndLanguage(this, language);
		//

		json.put("title", libraryCategoryLanguage.getTitle());
		return json;
	}
}
