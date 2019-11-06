package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MetaMessageLanguage {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(columnDefinition = "text")
	private String title = "-";
	
	@ManyToOne
    @JoinColumn(name = "FkMessage", nullable=false)
	private MetaMessage message;
	
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

	public MetaMessage getMessage() {
		return message;
	}

	public void setMessage(MetaMessage message) {
		this.message = message;
	}

	public MetaLanguage getLanguage() {
		return language;
	}

	public void setLanguage(MetaLanguage language) {
		this.language = language;
	}
}
