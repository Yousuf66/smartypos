package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.sqladmin.SQLAdmin.Users;
import com.google.cloud.Date;

@Entity
@Table(name="CONFIG_USERPROFILES")
@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler","createdBy" , "updatedBy" ,"created" , "updated","userId"}
,allowSetters=true)
public class UserProfiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@ManyToOne
	User userId;

	@ManyToOne
	MetaUserType metaUserType;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public MetaUserType getMetaUserType() {
		return metaUserType;
	}

	public void setMetaUserType(MetaUserType metaUserType) {
		this.metaUserType = metaUserType;
	}

}
