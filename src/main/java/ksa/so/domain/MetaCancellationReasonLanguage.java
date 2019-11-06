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
public class MetaCancellationReasonLanguage {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(columnDefinition = "text")
	private String title = "-";
	
	@ManyToOne
    @JoinColumn(name = "FkCancellationReason", nullable=false)
	private MetaCancellationReason cancellationReason;
	
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

	public MetaCancellationReason getCancellationReason() {
		return cancellationReason;
	}

	public void setMessage(MetaCancellationReason cancellationReason) {
		this.cancellationReason = cancellationReason;
	}

	public MetaLanguage getLanguage() {
		return language;
	}

	public void setLanguage(MetaLanguage language) {
		this.language = language;
	}

	
}
