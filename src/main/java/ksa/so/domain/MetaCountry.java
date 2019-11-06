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
public class MetaCountry {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String country = "-";
	
	@Column(nullable=false)
	@JsonProperty
	private String timezone = "-";
	
	@OneToMany
	private List<MetaCity> cities; 

	public List<MetaCity> getCities() {
		return cities;
	}

	public void setCities(List<MetaCity> cities) {
		this.cities = cities;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("code", this.getCode());
		json.put("country", this.getCountry());
		json.put("timezone", this.getTimezone());
		json.put("cities", this.getCities());
		
		return json;
	}
}
