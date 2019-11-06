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
public class MetaDay {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@Column(unique=true)
	@JsonProperty
	private int sequence;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

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
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("code",this.getCode());
		json.put("sequence",this.getSequence());
		
		return json;
	}
}
