package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class LibraryCategoryLanguage {
	public LibraryCategoryLanguage(Long id, String title, String details, LibraryCategory libraryCategory,
			MetaLanguage language) {
		super();
		this.id = id;
		this.title = title;
		this.details = details;
		this.libraryCategory = libraryCategory;
		this.language = language;
	}

	public LibraryCategoryLanguage() {
		super();
	}

	@Id
	 @JsonView(DataTablesOutput.View.class)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@JsonView(DataTablesOutput.View.class)
	@Column(columnDefinition = "text")
	private String title = "-";
	
	@JsonProperty
	@JsonView(DataTablesOutput.View.class)
	@Column(columnDefinition = "text")
	private String details = "-";

	@ManyToOne
	  @JsonView(DataTablesOutput.View.class)
    @JoinColumn(name = "FkLibraryCategory", nullable=false)
	private LibraryCategory libraryCategory;
	
	@ManyToOne
    @JoinColumn(name = "FkLanguage", nullable=false)
	private MetaLanguage language;

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

	public LibraryCategory getLibraryCategory() {
		return libraryCategory;
	}

	public void setLibraryCategory(LibraryCategory libraryCategory) {
		this.libraryCategory = libraryCategory;
	}

	public MetaLanguage getLanguage() {
		return language;
	}

	public void setLanguage(MetaLanguage language) {
		this.language = language;
	}
}
