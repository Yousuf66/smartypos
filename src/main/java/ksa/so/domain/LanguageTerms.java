package ksa.so.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import systemx.api.model.config.dynamicpages.Pages;


@Entity
@Table(name="CONFIG_LANGUAGETERMS")
@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler","languageGroup","pages"}
,allowSetters=true)
public class LanguageTerms {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name="DEFID",referencedColumnName="ID")
	LanguageTermDefinition termDefinition;

	@ManyToOne
	@JoinColumn(name="LANGUAGEID",referencedColumnName="ID")
	
	MetaLanguage language;

	@ManyToOne
	@JoinColumn(name="CONFIG_LANGUAGEGROUP",referencedColumnName="ID")
	LanguageGroup languageGroup;

	@Column(name="TERM", length = 500)
	private String term;

	@ManyToOne
	@JoinColumn(name="PAGE",referencedColumnName="PAGE_ID")
	Pages pages;

	public Pages getPages() {
		return pages;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LanguageTermDefinition getTermDefinition() {
		return termDefinition;
	}

	public void setTermDefinition(LanguageTermDefinition termDefinition) {
		this.termDefinition = termDefinition;
	}

	public MetaLanguage getLanguage() {
		return language;
	}

	public void setLanguage(MetaLanguage language) {
		this.language = language;
	}

	public LanguageGroup getLanguageGroup() {
		return languageGroup;
	}

	public void setLanguageGroup(LanguageGroup languageGroup) {
		this.languageGroup = languageGroup;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}



}
