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
import com.google.gson.annotations.Expose;

@Entity
public class AppVersion {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String version = "-";
	
	@ManyToOne
	@JoinColumn(name = "FkAppType", nullable=false)
	private MetaAppType appType;
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public MetaAppType getAppType() {
		return appType;
	}

	public void setAppType(MetaAppType appType) {
		this.appType = appType;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("version", this.getVersion());
		
		return json;
	}
}
