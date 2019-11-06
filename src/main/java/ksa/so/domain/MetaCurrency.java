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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MetaCurrency {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String symbol = "-";
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String title = "-";
	
	public MetaCurrency() {
		super();
	}

	public MetaCurrency(Long id, String code, String symbol, String title, List<Branch> branchList) {
		super();
		this.id = id;
		this.code = code;
		this.symbol = symbol;
		this.title = title;
		this.branchList = branchList;
	}

//	@JsonIgnore
	@OneToMany(mappedBy = "currency")
	private List<Branch> branchList;

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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("code", this.getCode());
		json.put("symbol", this.getSymbol());
		json.put("title", this.getTitle());
		
		return json;
	}
}
