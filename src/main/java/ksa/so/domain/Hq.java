package ksa.so.domain;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.HqLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
public class Hq {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	private String title = "-";
	
	@JsonProperty
	private String address = "-";
	
	@JsonProperty
	private String phone1 = "-";
	
	@JsonProperty
	private String phone2 = "-";
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";
	
	@OneToMany(mappedBy = "hq")
	private List<Branch> branchList;
	
	@OneToMany(mappedBy = "hq")
	private List<HqLanguage> hqLanguageList;
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@ManyToOne
	@JoinColumn(name="CreatedBy")
	private User CreatedBy;

	@Column(name="CreatedAt")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name="UpdatedBy")
	private User UpdatedBy;

	@Column(name="UpdatedAt")
	private Date updated;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}
	
	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}
	
	public User getCreatedBy() {
		return CreatedBy;
		}

		public void setCreatedBy(User createdBy) {
		CreatedBy = createdBy;
		}

		public User getUpdatedBy() {
		return UpdatedBy;
		}

		public void setUpdatedBy(User updatedBy) {
		UpdatedBy = updatedBy;
		}

		public Date getCreatedAt() {
		return created;
		}

		public void setCreatedAt(Date created) {
		this.created = created;
		}

		public Date getUpdatedAt() {
		return updated;
		}

		public void setUpdatedAt(Date updated) {
		this.updated = updated;
		}
			
	public JSONObject getInfo(MetaLanguage language, HqLanguageRepository hqLanguageRepository) {
		JSONObject json = new JSONObject();
		
		//
		HqLanguage hqLanguage = hqLanguageRepository.findByHqAndLanguage(this, language);
		//
		
		json.put("id", this.getId());
		if(hqLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", hqLanguage.getTitle());}
		if(hqLanguage.getDetails() == null){json.put("details", "-");} else {json.put("details", hqLanguage.getDetails());}
		if(this.getAddress() == null) {json.put("address", "-");} else {json.put("address", this.getAddress());}
		if(this.getPhone1() == null) {json.put("phone1", "-");} else {json.put("phone1", this.getPhone1());}
		if(this.getPhone2() == null) {json.put("phone2", "-");} else {json.put("phone2", this.getPhone2());}
		
		return json;
	}

}
