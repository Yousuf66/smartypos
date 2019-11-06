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

import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
public class MetaUserType {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
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

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(unique=true, nullable=false)
	@JsonProperty
	private String title = "-";
	
	@JsonIgnore
	@OneToMany(mappedBy = "userType")
	private List<User> userList;
	
	public String getCode() {
		return code;
	}
}
