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
public class OptLanguage {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(columnDefinition = "text")
	private String title = "-";
	
	@JsonProperty
	@Column(columnDefinition = "text")
	private String details = "-";
	
	@ManyToOne
    @JoinColumn(name = "FkOpt", nullable=false)
	private Opt opt;
	
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

	public Opt getOpt() {
		return opt;
	}

	public void setOpt(Opt opt) {
		this.opt = opt;
	}

	public MetaLanguage getLanguage() {
		return language;
	}

	public void setLanguage(MetaLanguage language) {
		this.language = language;
	}	
}
