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
public class MetaAppType {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(nullable=false)
	private String title = "-";
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@OneToMany(mappedBy = "appType")
	private List<AppVersion> appVersionList;
}
