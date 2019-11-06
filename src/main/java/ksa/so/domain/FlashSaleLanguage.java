package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class FlashSaleLanguage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty
	@Column(columnDefinition = "text")
	private String title = "-";

	@JsonProperty
	@Column(columnDefinition = "text")
	private String details = "-";

	@ManyToOne
	@JoinColumn(name = "FkFlashSale", nullable = false)
	private FlashSale flashSale;

	@ManyToOne
	@JoinColumn(name = "FkLanguage", nullable = false)
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

	public MetaLanguage getLanguage() {
		return language;
	}

	public FlashSale getFlashSale() {
		return flashSale;
	}

	public void setFlashSale(FlashSale flashSale) {
		this.flashSale = flashSale;
	}

	public void setLanguage(MetaLanguage language) {
		this.language = language;
	}

}
