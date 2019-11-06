package ksa.so.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity

@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler", "createdBy" , "updatedBy" , "created" , "updated"}
,allowSetters=true)
public class Tables
{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TABLE_ID")
	private Long id;

	@Column(name="TABLENAME", length = 250)
	private String tableName;
	
	@Column(name="MODELNAME", length = 250)
	private String modelName;
	
	@ManyToOne
//	@JoinColumn(name="STATUS_ID",referencedColumnName="STATUS_ID")
	MetaStatus status;

	@ManyToOne
//	@JoinColumn(name="CREATEDBY",referencedColumnName="USER_ID")
	User createdBy;

	@ManyToOne
//	@JoinColumn(name="UPDATEDBY",referencedColumnName="USER_ID")
	User updatedBy;

	@Column(name="CREATEDON")
	private Date created;

	@Column(name="UPDATEDON")
	private Date updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
