package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MetaLanguage {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(nullable=false)
	private String title = "-";
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@Column(unique=true, nullable=false,name ="lang_code")
	@JsonProperty
	private String langCode = "-";
	
	public String getlangCode() {
		return langCode;
	}

	public void setlangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getTitleForeign() {
		return titleForeign;
	}

	public void setTitleForeign(String titleForeign) {
		this.titleForeign = titleForeign;
	}

	public String getImageClass() {
		return imageClass;
	}

	public void setImageClass(String imageClass) {
		this.imageClass = imageClass;
	}

	public String getLangDir() {
		return langDir;
	}

	public void setLangDir(String langDir) {
		this.langDir = langDir;
	}

	@Column(name="TITLEFOREIGN", length = 100)
	private String titleForeign;

	@Column(name="IMAGECLASS", length = 100)
	private String imageClass;

	@Column(name="LANGDIR", length = 100)
	private String langDir;
	

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
