package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MetaRating {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String score = "-";
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("code", this.getCode());
		json.put("score", this.getScore());
		return json;
	}
}
